package model.competicion.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.database.Database;
import util.exceptions.ModelException;

public class DeleteAllPlazosCancelaciones {

	private static final String REMOVE_PLAZOS_CANCELACIONES = "DELETE FROM PlazoCancelacion WHERE idCompeticion = ?";
	
	private Database db = Database.getInstance();
	private String competicionId;
	
	public DeleteAllPlazosCancelaciones(String id) {
		this.competicionId = id;
	}

	public void execute() throws ModelException {
		try {
			Connection c = db.getConnection();

			PreparedStatement pst = c.prepareStatement(REMOVE_PLAZOS_CANCELACIONES);

			pst.setString(1, competicionId);

			pst.executeUpdate();

			pst.close();
			c.close();
		} catch (SQLException e) {
			throw new ModelException(e.getMessage());
		}
	}

}
