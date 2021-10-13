package model.inscripcion.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import giis.demo.util.Database;
import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;
import util.ModelException;

public class GetAtletasForCompetition {

	private static String GET_ATLETAS_FROM_COMPETITION = ""
			+ "select a.dni, a.nombre, i.categoria, i.fechaInscripcion, i.estado"
			+ "		from Atleta a, Inscripcion i"
			+ "		where a.email = i.emailAtleta"
			+ "			and i.idCompeticion = ?"
			+ "		order by i.fechaInscripcion, i.estado";
	
	private CompeticionDto competition;
	
	private Database db = new Database();
	private Connection c = null;
	
	public GetAtletasForCompetition(CompeticionDto competition) {
		this.competition = competition;
	}
	
	public List<InscripcionDto> execute() throws ModelException {
		List<InscripcionDto> inscripciones = new ArrayList<InscripcionDto>();
	
		try {
			c = db.getConnection();
			
			PreparedStatement pst = c.prepareStatement(GET_ATLETAS_FROM_COMPETITION);
			pst.setString(1, competition.id);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				InscripcionDto inscripcion = new InscripcionDto();
				
				inscripcion.dniAtleta = rs.getString(1);
				inscripcion.nombreAtleta = rs.getString(2);
				inscripcion.categoria = rs.getString(3);
				inscripcion.fechaInscripcion = rs.getDate(4);
				inscripcion.estadoInscripcion = rs.getString(5);
				
				inscripciones.add(inscripcion);
			}
			
			rs.close();
			pst.close();
			c.close();
		}catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
		
		return inscripciones;
	}

}
