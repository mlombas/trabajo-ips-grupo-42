package model.competicion.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.competicion.CompeticionDto;
import util.database.Database;
import util.exceptions.ModelException;

public class UpdateCompeticion {

	private static final String UPDATE_COMPETICION = "update Competicion set estadoCarrera = ? where id = ?";
	
	private CompeticionDto competicion;
	private Database db = Database.getInstance();
	
	public UpdateCompeticion(CompeticionDto competicion) {
		this.competicion = competicion;
	}

	public void execute() throws ModelException {
		try {
			Connection c = db.getConnection();
			
			PreparedStatement pst = c.prepareStatement(UPDATE_COMPETICION);
			
			pst.setString(1, competicion.estadoCarrera);
			pst.setString(2, competicion.id);
			
			pst.executeUpdate();
			
			pst.close();
			c.close();
		}catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
	}
	
}
