package model.atleta.commands;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import giis.demo.util.Database;
import model.Command;
import model.atleta.AtletaDto;
import model.competicion.CompeticionDto;
import model.inscripcion.EstadoInscripcion;
import model.inscripcion.InscripcionDto;

public class RegisterAtletaToCompetition implements Command {
	
	private static final String NO_REINSRIBIRSE = "select * from Inscripcion WHERE idCompeticion = ? and emailAtleta = ?";
	private static final String PLAZO_INSCRIPCION = "select fecha from Competicion WHERE id = ?";
	private static final String PLAZAS_LIBRES = "select plazas - count(*) from Inscripcion WHERE idCompeticion = ?";
	
	private static final String ADD_ATLETA = "insert into Inscripcion(idCompeticion, emailAtleta, nombreAtleta, categoria, fechaInscripcion, estadoInscripcion) VALUES (?, ?, ?, ?, ?, ?)";
	
	private AtletaDto atleta;
	private CompeticionDto competicion;
	
	private Database db = new Database();
	private Connection c = null;
	
	public RegisterAtletaToCompetition(AtletaDto atleta, CompeticionDto competicion) {
		this.atleta = atleta;
		this.competicion = competicion;
	}

	public InscripcionDto execute() {
		InscripcionDto inscripcion = new InscripcionDto(); 
		
		try {
			c = db.getConnection();
			
			// check: 
			//		1. No inscribirse dos veces a la misma competición.
			//		2. esté abierto el plazo de inscripción.
			//		3. existan plazas libres.
			checkCanEnroll();
			
			PreparedStatement pst = c.prepareStatement(ADD_ATLETA);
			
			inscripcion.nombreAtleta = atleta.nombre;
			inscripcion.idCompeticion = competicion.id;
			// inscripcion.categoria = ; TODO
			inscripcion.fechaInscripcion = new Date(System.currentTimeMillis());
			inscripcion.cuotaInscripcion = competicion.cuota;
			inscripcion.estadoInscripcion = EstadoInscripcion.PRE_INSCRITO.toString();
			
			pst.setString(1, inscripcion.nombreAtleta);
			pst.setString(2, inscripcion.idCompeticion);
			pst.setString(3, inscripcion.categoria);
			pst.setDate(4, inscripcion.fechaInscripcion);
			pst.setDouble(5, inscripcion.cuotaInscripcion);
			pst.setString(6, inscripcion.estadoInscripcion);
			
			pst.executeUpdate();
			
			pst.close();
			c.close();
		}catch(SQLException e) {
			// TODO throw exception
		}
		
		return inscripcion;
	}

	private void checkCanEnroll() {
		checkNoReinscripcion();
		checkPlazoAbierto();
		checkPlazasLibres();
	}

	private void checkNoReinscripcion() {
		try {
			PreparedStatement pst = c.prepareStatement(NO_REINSRIBIRSE);
			pst.setString(1, competicion.id);
			pst.setString(2, atleta.email);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) // Si ya se había registrado
				throw new IllegalArgumentException();
			
			rs.close();
			pst.close();
		} catch(SQLException e) {
			// TODO throw exception
		}
	}
	
	private void checkPlazoAbierto() {
		try {
			PreparedStatement pst = c.prepareStatement(PLAZO_INSCRIPCION);
			pst.setString(1, competicion.id);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) // Si el plazo no está abierto
				throw new IllegalArgumentException();
			
			rs.close();
			pst.close();
		} catch(SQLException e) {
			// TODO throw exception
		}
	}
	
	private void checkPlazasLibres() {
		try {
			PreparedStatement pst = c.prepareStatement(PLAZAS_LIBRES);
			pst.setString(1, competicion.id);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) // Si no hay plazas libres
				throw new IllegalArgumentException();
			
			rs.close();
			pst.close();
		} catch(SQLException e) {
			// TODO throw exception
		}
	}


}
