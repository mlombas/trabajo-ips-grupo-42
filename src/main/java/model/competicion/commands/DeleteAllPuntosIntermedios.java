package model.competicion.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.database.Database;
import util.exceptions.ModelException;

public class DeleteAllPuntosIntermedios {

	private static final String REMOVE_PUNTOS_INTERMEDIOS = "DELETE FROM PuntoIntermedio WHERE idCompeticion = ?";
	
	private Database db = Database.getInstance();
	private String competicionId;
	
	public DeleteAllPuntosIntermedios(String competicionId) {
		this.competicionId = competicionId;
	}

	public void execute() throws ModelException {
		try {
			Connection c = db.getConnection();

			PreparedStatement pst = c.prepareStatement(REMOVE_PUNTOS_INTERMEDIOS);
			pst.setString(1, competicionId);

			pst.executeUpdate();

			pst.close();
			c.close();
		} catch (SQLException e) {
			throw new ModelException(e.getMessage());
		}
	}

}
