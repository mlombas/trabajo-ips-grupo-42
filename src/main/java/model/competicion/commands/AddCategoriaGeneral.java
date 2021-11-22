package model.competicion.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.database.Database;
import util.exceptions.ModelException;

public class AddCategoriaGeneral {

	private static String EXISTE_COMPETICION = "select * from Competicion where id = ?";
	private static String OBTENER_EDAD = "select max(edadMaxima) from Categoria where idCompeticion = ? and sexo = ?";
	private static String AÑADIR_CATEGORIA = "insert into Categoria(nombreCategoria, idCompeticion, edadMinima, edadMaxima, sexo) values (?,?,?,?,?)";

	private String idCompeticion;
	private Database db = Database.getInstance();

	public AddCategoriaGeneral(String idCompeticion) {
		this.idCompeticion = idCompeticion;
	}

	public boolean execute() throws ModelException {
		if (!existsCompeticion()) {
			throw new ModelException("La competicion no existe");
		}
		
		createCategoriaAbsolutaSexo("H");
		createCategoriaAbsolutaSexo("M");
		
		return true;
	}

	private void createCategoriaAbsolutaSexo(String sexo) throws ModelException {
		try {
			Connection c = db.getConnection();

			PreparedStatement pst = c.prepareStatement(AÑADIR_CATEGORIA);

			pst.setString(1, "General " + sexo);
			pst.setString(2, idCompeticion);
			pst.setInt(3, getEdadMasAlta(sexo));
			pst.setInt(4, 999);
			pst.setString(5, sexo);

			pst.executeUpdate();

			pst.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModelException(e.getMessage());
		}
	}

	private int getEdadMasAlta(String sexo) throws ModelException {
		int aux = 18;
		try {
			ResultSet rs;
			Connection c = db.getConnection();

			PreparedStatement pst = c.prepareStatement(OBTENER_EDAD);

			
			
			pst.setString(1, idCompeticion);
			pst.setString(2, sexo);
			
			rs = pst.executeQuery();
			if(rs.next())
				aux = rs.getInt(1);

			pst.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModelException(e.getMessage());
		}
		return aux;
	}

	private boolean existsCompeticion() throws ModelException {
		boolean aux = false;
		try {
			Connection c = db.getConnection();

			PreparedStatement pst = c.prepareStatement(EXISTE_COMPETICION);

			pst.setString(1, idCompeticion);

			aux = pst.executeQuery().next();

			pst.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModelException(e.getMessage());
		}
		return aux;
	}
	
}
