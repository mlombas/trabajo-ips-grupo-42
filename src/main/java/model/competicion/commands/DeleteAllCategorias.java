package model.competicion.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.database.Database;
import util.exceptions.ModelException;

public class DeleteAllCategorias {

	private static final String REMOVE_CATEGORIAS = "DELETE FROM Categoria WHERE idCompeticion = ?";

	private String idCompeticion;
	private Database db = Database.getInstance();

	public DeleteAllCategorias(String idCompeticion) {
		this.idCompeticion = idCompeticion;

	}

	public void execute() throws ModelException {
		try {
			Connection c = db.getConnection();

			PreparedStatement pst = c.prepareStatement(REMOVE_CATEGORIAS);

			pst.setString(1, idCompeticion);

			pst.executeUpdate();

			pst.close();
			c.close();

		} catch (SQLException e) {
			throw new ModelException(e.getMessage());
		}
	}

}
