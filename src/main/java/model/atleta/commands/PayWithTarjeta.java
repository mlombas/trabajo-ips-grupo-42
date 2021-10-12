package model.atleta.commands;

import java.sql.Date;

import bank_mockup.Bank;
import giis.demo.util.ApplicationException;
import giis.demo.util.Database;
import model.atleta.AtletaDto;
import model.atleta.TarjetaDto;
import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;

public class PayWithTarjeta {
	
	private static final String UPDATEINSCRIPCION = "update Inscripcion set estadoInscripcion = ?, set fechaCambioEstado = ? where idCompeticion = ? and emailAtleta = ? ";
	private static final String GETINSCRIPCION = "select * from Inscripcion where idCompeticion = ? and emailAtleta = ? ";
	
	private AtletaDto atleta;
	private CompeticionDto competicion;
	
	private Database db = new Database();
	private Bank bank = new Bank();
	private TarjetaDto tarjeta;
	
	
	public PayWithTarjeta(AtletaDto atleta, CompeticionDto competicion,TarjetaDto tarjeta) {
		this.atleta = atleta;
		this.competicion = competicion;
		this.tarjeta = tarjeta;
	}

	public Date execute() {
		
		
		//chekear que no sean nulos los dtos
		checkArguments();
		
		//que exista una inscripcion con ese atleta y la competicion
		//que la inscripcion esté "Pre-inscrito"
		checkCanPay();
		
		//que la tarjeta sea válida
		checkTartjeta();
		
		Date dt = new Date(System.currentTimeMillis());
		db.executeUpdate(UPDATEINSCRIPCION, "Inscrito", dt,competicion.id, atleta.email);
		
		return  dt;
	}

	private void checkTartjeta() {
		if(!bank.payWithCard(tarjeta.number, tarjeta.expiration, tarjeta.cvc)) {
			throw new ApplicationException("La tarjeta no es válida");
		}
		
	}

	private void checkCanPay() {
		InscripcionDto ins = db.executeQueryPojo(InscripcionDto.class, GETINSCRIPCION, competicion.id, atleta.email).get(0);
		if(ins.estadoInscripcion.trim().length() <= 0) {
			throw new ApplicationException("No existe una inscripcion en esta competicion");
		}
		if(!ins.estadoInscripcion.equals("Pre-inscrito")) {
			throw new ApplicationException("La inscripcion ya ha sido pagada");
		}
	}

	private void checkArguments() {
		if(atleta == null ) {
			throw new IllegalArgumentException("El AtletaDto es nulo");
		} 
		if(competicion == null ) {
			throw new IllegalArgumentException("El CompeticionDto es nulo");
		} 
		
		
	}
	
	

}
