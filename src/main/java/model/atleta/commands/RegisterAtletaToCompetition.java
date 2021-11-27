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
import java.util.List;

import model.ModelFactory;
import model.atleta.AtletaDto;
import model.competicion.CompeticionDto;
import model.inscripcion.EstadoInscripcion;
import model.inscripcion.InscripcionDto;
import util.database.Database;
import util.exceptions.AtletaNoValidoException;
import util.exceptions.ModelException;

public class RegisterAtletaToCompetition {
	
	private static final String NO_REINSCRIBIRSE = "select * from Inscripcion WHERE idCompeticion = ? and emailAtleta = ?";
	private static final String PLAZO_INSCRIPCION = "select fecha from Competicion WHERE id = ?";
	private static final String ADD_ATLETA = "insert into "
						+ "Inscripcion(idCompeticion, emailAtleta, nombreAtleta, categoria, "
								+ "fechaInscripcion, cuotaInscripcion, estadoInscripcion, fechaCambioEstado, nombreCompeticion, clubAtleta) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_CATEGORIA = "select nombreCategoria "
												+ "from Categoria "
												+ "where edadMinima <= ? "
													+ "and edadMaxima >= ? "
													+ "and sexo = ? "
													+ "and idCompeticion = ?";
	
	private CompeticionDto competicion;
	
	private Database db = Database.getInstance();
	private Connection c = null;
	
	public RegisterAtletaToCompetition(CompeticionDto competicion) {
		this.competicion = competicion;
	}

	public InscripcionDto execute(List<AtletaDto> atletas) throws AtletaNoValidoException, ModelException {
		InscripcionDto inscripcion = new InscripcionDto(); 
		PreparedStatement pst;
		
		try {
			c = db.getConnection();
			
			for (AtletaDto atleta : atletas) {
				checkCanEnroll(atleta);
				
				pst = c.prepareStatement(ADD_ATLETA);
				inscripcion = createInscripcion(atleta,
						EstadoInscripcion.INSCRITO_COMO_CLUB);
				createStatement(pst, inscripcion);
				
				pst.executeUpdate();
				
				pst.close();
			}
			
			c.close();
		}catch(Exception e) {
			try {
				c.rollback();
			} catch (SQLException sqle) { }
			throw new ModelException(e.getMessage());
		}
		
		return inscripcion;
	}
	
	public InscripcionDto execute(AtletaDto atleta) throws AtletaNoValidoException, ModelException {
		try {
			c = db.getConnection();
			
			// check: 
			//		1. No inscribirse dos veces a la misma competición.
			//		2. esté abierto el plazo de inscripción.
			//		3. existan plazas libres.
			checkCanEnroll(atleta);
			
			PreparedStatement pst = c.prepareStatement(ADD_ATLETA);
			InscripcionDto inscripcion = createInscripcion(atleta, 
					EstadoInscripcion.PRE_INSCRITO);
			createStatement(pst, inscripcion);
			
			pst.executeUpdate();
			
			pst.close();
			c.close();
			
			return inscripcion;
		}catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
	}
	
	private InscripcionDto createInscripcion(AtletaDto atleta, EstadoInscripcion estado) throws AtletaNoValidoException, ModelException {
		InscripcionDto inscripcion = new InscripcionDto(); 
		
		inscripcion.nombreAtleta = atleta.nombre;
		inscripcion.emailAtleta = atleta.email;
		inscripcion.clubAtleta = atleta.club;
		inscripcion.idCompeticion = competicion.id;
		inscripcion.categoria = getCategoria(atleta);
		inscripcion.fechaInscripcion = LocalDate.now();
		inscripcion.cuotaInscripcion = competicion.cuota;
		inscripcion.estadoInscripcion = estado;
		inscripcion.fechaCambioEstado = LocalDate.now();
		inscripcion.nombreCompeticion = competicion.nombreCarrera;
		
		return inscripcion;
	}
	
	private void createStatement(PreparedStatement pst, InscripcionDto inscripcion) throws SQLException {
		pst.setString(1, inscripcion.idCompeticion);
		pst.setString(2, inscripcion.emailAtleta);
		pst.setString(3, inscripcion.nombreAtleta);
		pst.setString(4, inscripcion.categoria);
		pst.setString(5, inscripcion.fechaInscripcion.toString());
		pst.setDouble(6, inscripcion.cuotaInscripcion);
		pst.setString(7, inscripcion.estadoInscripcion.toString());
		pst.setString(8, inscripcion.fechaCambioEstado.toString());
		pst.setString(9, inscripcion.nombreCompeticion);
		pst.setString(10, inscripcion.clubAtleta);
	}

	private void checkCanEnroll(AtletaDto atleta) throws AtletaNoValidoException, ModelException {
		checkNoReinscripcion(atleta);
		checkPlazoAbierto();
		checkPlazasLibres();
	}

	private void checkPlazasLibres() throws AtletaNoValidoException, ModelException {
		int plazasLibres = ModelFactory.forCarreraCrudService().getPlazasLibres(competicion);
		
		if(plazasLibres <= 0) // Si no hay plazas libres
			throw new AtletaNoValidoException("No quedan plazas libres");
	}

	private void checkNoReinscripcion(AtletaDto atleta) throws AtletaNoValidoException, ModelException {
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
	

	
	private String getCategoria(AtletaDto atleta) throws AtletaNoValidoException, ModelException {
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
		} catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
		
		return cat;
	}

}
