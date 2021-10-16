package model.atleta.commands;

import java.sql.Date;
import java.util.List;

import bank_mockup.Bank;
import giis.demo.util.ApplicationException;
import giis.demo.util.Database;
import model.inscripcion.InscripcionDto;

public class ConfirmPendingPay {
	private static final String UPDATEINSCRIPCION = "update Inscripcion set estadoInscripcion = ?, set fechaCambioEstado = ? where idCompeticion = ? and emailAtleta = ? ";
	private static final String GETINSCRIPCION = "select * from Inscripcion where idCompeticion = ? and emailAtleta = ? ";
	
	private InscripcionDto inscripcion;
	
	private Database db = new Database();
	private String code;
	private static Bank bank = new Bank();
	
	public ConfirmPendingPay(InscripcionDto inscripcion, String code) {
		this.inscripcion = inscripcion;
		this.code = code;
	}

	public Date execute() {
		checkArguments();
		checkExists();
		checkIsPaid();

		Date dt = new Date(System.currentTimeMillis());
		db.executeUpdate(UPDATEINSCRIPCION, "Inscrito", dt, inscripcion.idCompeticion, inscripcion.emailAtleta);
		
		return dt;
	}

	private void checkIsPaid() {
		if(!bank.isPaid(code)) {
			throw new ApplicationException("La inscripcion ya ha sido pagada");
		}
	}
	
	private void checkExists() {
		List<InscripcionDto> ins = db.executeQueryPojo(InscripcionDto.class, GETINSCRIPCION, inscripcion.idCompeticion, inscripcion.emailAtleta);
		if(ins.size() <= 0) {
			throw new ApplicationException("No existe una inscripcion en esta competicion");
		}
		if(!ins.get(0).estadoInscripcion.equals("Pago-pendiente")) {
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
