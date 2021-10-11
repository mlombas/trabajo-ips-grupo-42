package model.atleta.commands;


import java.util.Date;

import giis.demo.util.ApplicationException;
import giis.demo.util.Database;
import model.atleta.AtletaDto;
import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;

public class PayWithTarjeta {
	
	private static final String UPDATEINSCRIPCION = "update Inscripcion set estadoInscripcion = ?, set fechaCambioEstado = ? where idCompeticion = ? and emailAtleta = ? ";
	private static final String GETINSCRIPCION = "select * from Inscripcion where idCompeticion = ? and emailAtleta = ? ";
	
	private AtletaDto atleta;
	private CompeticionDto competicion;
	
	private Database db = new Database();

	public PayWithTarjeta(AtletaDto atleta, CompeticionDto competicion) {
		this.atleta = atleta;
		this.competicion = competicion;
	}

	public Date execute() {
		
		
		//chekear que no sean nulos los dtos
		checkArguments();
		//que exista una inscripcion con ese atleta y la competicion
		//que la inscripcion esté "Pre-inscrito"
		checkCanPay();
		//que la tarjeta sea válida
		// TODO
		
		Date dt = new Date();
		db.executeUpdate(UPDATEINSCRIPCION, "Inscrito", dt,competicion.id, atleta.email);
		
		return  dt;
	}

	private void checkCanPay() {
		InscripcionDto ins = db.executeQueryPojo(InscripcionDto.class, GETINSCRIPCION, competicion.id, atleta.email).get(0);
		if(ins.estadoInscripcion.trim().length() > 0) {
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
