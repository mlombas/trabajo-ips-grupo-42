package model.competicion.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.database.Database;
import util.exceptions.ModelException;

public class RemoveCompeticion {

	private static final String ADD_COMPETICION = "DELETE FROM Competicion WHERE id = ?";
	
	private String competicionId;
	private Database db = Database.getInstance();
	
	public RemoveCompeticion(String competicionId) {
		this.competicionId = competicionId;
	}

	public boolean execute() throws ModelException {
		try {
			Connection c = db.getConnection();
			
			PreparedStatement pst = c.prepareStatement(ADD_COMPETICION);
			
			pst.setString(1, competicionId);
			
			int ans = pst.executeUpdate();
			
			pst.close();
			c.close();
			
			return ans == 1;
		}catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
	}

}
