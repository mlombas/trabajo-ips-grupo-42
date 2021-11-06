package model.atleta.commands;

import java.sql.Date;
import java.util.List;

import bank_mockup.Bank;
import model.competicion.CompeticionDto;
import model.inscripcion.EstadoInscripcion;
import model.inscripcion.InscripcionDto;
import util.database.Database;
import util.exceptions.ApplicationException;

public class PendingPayWithTransaccion {
	
	private static final String UPDATEINSCRIPCION = "update inscripcion set estadoInscripcion = ?, fechaCambioEstado = ? where idCompeticion = ? and emailAtleta = ? ";
	private static final String GETINSCRIPCION = "select * from inscripcion where idCompeticion = ? and emailAtleta = ? ";
	private static final String GETCOMPETICION = "select * from competicion where id = ?";
	
	private InscripcionDto inscripcion;
	
	private Database db = Database.getInstance();
	
	private Bank bank = new Bank();
	
	public PendingPayWithTransaccion(InscripcionDto inscripcion) {
		this.inscripcion = inscripcion;
	}

	public String execute() {
		checkArguments();
		checkCanPay();
		
		Date dt = new Date(System.currentTimeMillis());
		db.executeUpdate(UPDATEINSCRIPCION, "Pago-pendiente", dt, inscripcion.idCompeticion, inscripcion.emailAtleta);
		
		String code = addTransaction();
		return code;
	}
	
	private String addTransaction() {
		double cuota = db.executeQueryPojo(CompeticionDto.class, GETCOMPETICION, inscripcion.idCompeticion).get(0).cuota;
		String code = bank.addPendingTransaction(cuota);
		return code;
	}

	private void checkCanPay() {
		List<InscripcionDto> ins = db.executeQueryPojo(InscripcionDto.class, GETINSCRIPCION, inscripcion.idCompeticion, inscripcion.emailAtleta);
		if(ins.size() <= 0) {
			throw new ApplicationException("No existe una inscripcion en esta competicion");
		}
		if(!ins.get(0).estadoInscripcion.equals(EstadoInscripcion.PRE_INSCRITO)) {
			throw new ApplicationException("La inscripcion ya ha sido pagada");
		}
	}

	private void checkArguments() {
		if(inscripcion.emailAtleta == null || inscripcion.emailAtleta.trim().length() <= 0 ) {
			throw new IllegalArgumentException("El atleta no es válido");
		} 
		if(inscripcion.idCompeticion == null || inscripcion.idCompeticion.trim().length() <= 0 ) {
			throw new IllegalArgumentException("La competición no es válida");
		} 	
	}
	
}