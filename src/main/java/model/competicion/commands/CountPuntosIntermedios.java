package model.competicion.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.competicion.CompeticionDto;
import util.database.Database;
import util.exceptions.ModelException;

public class CountPuntosIntermedios {

	private static final String PUNTOS_INTERMEDIOS = "select count(*) as puntos from PuntoIntermedio WHERE idCompeticion = ? ";

	private Connection c = null;
	private Database db = Database.getInstance();

	private CompeticionDto competicion;

	public CountPuntosIntermedios(CompeticionDto competicion) {
		this.competicion = competicion;
	}

	public int execute() throws ModelException {
		try {
			c = db.getConnection();
			
			PreparedStatement pst = c.prepareStatement(PUNTOS_INTERMEDIOS);
			pst.setString(1, competicion.id);
			ResultSet rs = pst.executeQuery();
			
			int puntos = rs.getInt(1);
			
			rs.close();
			pst.close();
			
			c.close();
			
			return puntos;
		} catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
	}

}
