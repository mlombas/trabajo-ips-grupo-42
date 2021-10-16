package model.atleta.commands;

import java.sql.Date;
import java.util.List;

import bank_mockup.Bank;
import giis.demo.util.ApplicationException;
import giis.demo.util.Database;
import model.atleta.TarjetaDto;
import model.inscripcion.InscripcionDto;

public class PendingPayWithTransaccion {
	private static final String UPDATEINSCRIPCION = "update Inscripcion set estadoInscripcion = ?, set fechaCambioEstado = ? where idCompeticion = ? and emailAtleta = ? ";
	private static final String GETINSCRIPCION = "select * from Inscripcion where idCompeticion = ? and emailAtleta = ? ";
	
	private InscripcionDto inscripcion;
	
	private Database db = new Database();
	
	
	public PendingPayWithTransaccion(InscripcionDto inscripcion) {
		this.inscripcion = inscripcion;
	}

	public Date execute() {
		checkArguments();
		checkCanPay();
		
		Date dt = new Date(System.currentTimeMillis());
		db.executeUpdate(UPDATEINSCRIPCION, "Pago-pendiente", dt, inscripcion.idCompeticion, inscripcion.emailAtleta);
		
		return  dt;
	}

	private void checkCanPay() {
		List<InscripcionDto> ins = db.executeQueryPojo(InscripcionDto.class, GETINSCRIPCION, inscripcion.idCompeticion, inscripcion.emailAtleta);
		if(ins.size() <= 0) {
			throw new ApplicationException("No existe una inscripcion en esta competicion");
		}
		if(!ins.get(0).estadoInscripcion.equals("Pre-inscrito")) {
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
