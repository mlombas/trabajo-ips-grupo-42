package model.atleta.commands;

import java.time.LocalDate;
import java.util.List;

import bank_mockup.Bank;
import model.atleta.TarjetaDto;
import model.inscripcion.EstadoInscripcion;
import model.inscripcion.InscripcionDto;
import util.database.Database;
import util.exceptions.ApplicationException;

public class PayWithTarjeta {
	
	private static final String UPDATEINSCRIPCION = "update Inscripcion set estadoInscripcion = ?, fechaCambioEstado = ? where idCompeticion = ? and emailAtleta = ? ";
	private static final String GETINSCRIPCION = "select * from Inscripcion where idCompeticion = ? and emailAtleta = ? ";
	
	private InscripcionDto inscripcion;
	
	private Database db = Database.getInstance();
	private Bank bank = new Bank();
	private TarjetaDto tarjeta;
	
	
	public PayWithTarjeta(InscripcionDto inscripcion,TarjetaDto tarjeta) {
		this.inscripcion = inscripcion;
		this.tarjeta = tarjeta;
	}

	public LocalDate execute() {
		//chekear que no sean nulos los dtos
		checkArguments();
		
		//que exista una inscripcion con ese atleta y la competicion
		//que la inscripcion esté "Pre-inscrito"
		checkCanPay();
		
		//que la tarjeta sea válida
		checkTartjeta();
		
		LocalDate dt = LocalDate.now();
		db.executeUpdate(UPDATEINSCRIPCION, "Inscrito", dt, inscripcion.idCompeticion, inscripcion.emailAtleta);
		
		return  dt;
	}

	private void checkTartjeta() {
		if(!bank.payWithCard(tarjeta.number, tarjeta.expiration, tarjeta.cvc)) {
			throw new ApplicationException("La tarjeta no es válida");
		}
		
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
