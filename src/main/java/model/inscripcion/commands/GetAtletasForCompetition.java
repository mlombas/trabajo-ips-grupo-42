package model.inscripcion.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import model.competicion.CompeticionDto;
import model.inscripcion.EstadoInscripcion;
import model.inscripcion.InscripcionDto;
import util.database.Database;
import util.exceptions.ModelException;

public class GetAtletasForCompetition {

	private static String GET_ATLETAS_FROM_COMPETITION = ""
			+ "select a.dni, a.nombre, i.categoria, i.fechaInscripcion, i.estadoInscripcion"
			+ "		from Atleta a, Inscripcion i"
			+ "		where a.email = i.emailAtleta"
			+ "			and i.idCompeticion = ?"
			+ "		order by i.fechaInscripcion, i.estadoInscripcion";
	
	private CompeticionDto competition;
	
	private Database db = Database.getInstance();
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
				
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					long time = sdf.parse(rs.getString("fechaInscripcion")).getTime();
					inscripcion.fechaInscripcion = Instant.ofEpochMilli(time)
							  .atZone(ZoneId.systemDefault())
							  .toLocalDate();;
				} catch (ParseException e) {
					// DO nothing
				}
				
				inscripcion.estadoInscripcion = EstadoInscripcion.valueOf(rs.getString(5));
				
				inscripciones.add(inscripcion);
			}
			
			rs.close();
			pst.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
			throw new ModelException(e.getMessage());
		}
		
		return inscripciones;
	}

}
