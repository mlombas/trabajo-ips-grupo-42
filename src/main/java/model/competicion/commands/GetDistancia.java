package model.competicion.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.competicion.CompeticionDto;
import util.database.Database;
import util.exceptions.ModelException;

public class GetDistancia {

	private static final String GET_DISTANCIA = "select distancia from Competicion where id = ?";
	
	private Connection c = null;
	private Database db = Database.getInstance();
	
	private CompeticionDto competicion;
	
	public GetDistancia(CompeticionDto competicion) {
		this.competicion = competicion;
	}

	public int execute() throws ModelException {
		try {
			c = db.getConnection();
			
			PreparedStatement pst = c.prepareStatement(GET_DISTANCIA);
			pst.setString(1, competicion.id);
			ResultSet rs = pst.executeQuery();
			
			int distancia = rs.getInt(1);
			
			rs.close();
			pst.close();
			
			c.close();
			
			return distancia;
		} catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
	}

}
