package model.atleta.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import giis.demo.util.Database;
import model.atleta.AtletaDto;
import model.competicion.CompeticionDto;
import model.inscripcion.EstadoInscripcion;
import model.inscripcion.InscripcionDto;
import util.AtletaNoValidoException;
import util.ModelException;

public class RegisterAtletaToCompetition {
	
	private static final String NO_REINSCRIBIRSE = "select * from Inscripcion WHERE idCompeticion = ? and emailAtleta = ?";
	private static final String PLAZO_INSCRIPCION = "select fecha from Competicion WHERE id = ?";
	private static final String PLAZAS_LIBRES = "select c.plazas - count(*) from Competicion c, Inscripcion i WHERE i.idCompeticion = ?";
	private static final String ADD_ATLETA = "insert into "
						+ "Inscripcion(idCompeticion, emailAtleta, nombreAtleta, categoria, fechaInscripcion, cuotaInscripcion, estadoInscripcion) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_CATEGORIA = "select nombreCategoria "
												+ "from Categoria where edadMinima <= ? "
													+ "and edadMaxima > ? "
													+ "and sexo = ? "
													+ "and idCompeticion = ?";
	
	private AtletaDto atleta;
	private CompeticionDto competicion;
	
	private Database db = new Database();
	private Connection c = null;
	
	public RegisterAtletaToCompetition(AtletaDto atleta, CompeticionDto competicion) {
		this.atleta = atleta;
		this.competicion = competicion;
	}

	public InscripcionDto execute() throws AtletaNoValidoException, ModelException {
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
			inscripcion.emailAtleta = atleta.email;
			inscripcion.idCompeticion = competicion.id;
			inscripcion.categoria = getCategoria();
			inscripcion.fechaInscripcion = LocalDate.now();
			inscripcion.cuotaInscripcion = competicion.cuota;
			inscripcion.estadoInscripcion = EstadoInscripcion.PRE_INSCRITO.toString();
			
			pst.setString(1, inscripcion.idCompeticion);
			pst.setString(2, inscripcion.emailAtleta);
			pst.setString(3, inscripcion.nombreAtleta);
			pst.setString(4, inscripcion.categoria);
			pst.setString(5, inscripcion.fechaInscripcion.toString());
			pst.setDouble(6, inscripcion.cuotaInscripcion);
			pst.setString(7, inscripcion.estadoInscripcion);
			
			pst.executeUpdate();
			
			pst.close();
			c.close();
		}catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
		
		return inscripcion;
	}

	private void checkCanEnroll() throws AtletaNoValidoException, ModelException {
		checkNoReinscripcion();
		checkPlazoAbierto();
		checkPlazasLibres();
	}

	private void checkNoReinscripcion() throws AtletaNoValidoException, ModelException {
		try {
			PreparedStatement pst = c.prepareStatement(NO_REINSCRIBIRSE);
			pst.setString(1, competicion.id);
			pst.setString(2, atleta.email);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) // Si ya se había registrado
				throw new AtletaNoValidoException("Te estás intentando reinscribir");
			
			rs.close();
			pst.close();
		} catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
	}
	
	private void checkPlazoAbierto() throws AtletaNoValidoException, ModelException {
		LocalDate date = null;
		
		try {
			PreparedStatement pst = c.prepareStatement(PLAZO_INSCRIPCION);
			pst.setString(1, competicion.id);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					long time = sdf.parse(rs.getString("fecha")).getTime();
					date = Instant.ofEpochMilli(time)
							  .atZone(ZoneId.systemDefault())
							  .toLocalDate();;
				} catch (ParseException e) {
					throw new AtletaNoValidoException("No se encuentra la fecha");
				}	
			} else
				throw new AtletaNoValidoException("No se encuentra la competición");			
			
			if(LocalDate.now().compareTo(date) > 0)
				throw new AtletaNoValidoException("Ha pasado el plazo de inscripción");
			
			
			rs.close();
			pst.close();
		} catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
	}
	
	private void checkPlazasLibres() throws AtletaNoValidoException, ModelException {
		try {
			PreparedStatement pst = c.prepareStatement(PLAZAS_LIBRES);
			pst.setString(1, competicion.id);
			ResultSet rs = pst.executeQuery();
			int plazasLibres = rs.getInt(1);
			
			if(plazasLibres <= 0) // Si no hay plazas libres
				throw new AtletaNoValidoException("No quedan plazas libres");
			
			rs.close();
			pst.close();
		} catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
	}
	
	private String getCategoria() throws AtletaNoValidoException, ModelException {
		String cat = null;
		try {
			PreparedStatement pst = c.prepareStatement(GET_CATEGORIA);
			int edad = LocalDate.now().getYear() - atleta.fechaNacimiento.getYear();
			
			pst.setInt(1, edad);
			pst.setInt(2, edad);
			pst.setString(3, atleta.sexo);
			pst.setString(4, competicion.id);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next())
				cat = rs.getString(1);
			else
				throw new AtletaNoValidoException("No hay categoria válida");
			
			rs.close();
			pst.close();
			return cat;
		} catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
	}

}
