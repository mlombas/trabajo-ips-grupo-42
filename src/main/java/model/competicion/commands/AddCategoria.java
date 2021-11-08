package model.competicion.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import model.competicion.CategoriaDto;
import util.database.Database;
import util.exceptions.ModelException;

public class AddCategoria {

	private static String EXISTE_COMPETICION = "select * from Competicion where id = ?";
	private static String OBTENER_CATEGORIAS = "select * from Categoria where idCompeticion = ?";
	private static String AÑADIR_CATEGORIA = "insert into Categoria(nombreCategoria, idCompeticion, edadMinima, edadMaxima, sexo) values (?,?,?,?,?)";

	private CategoriaDto cat;
	private Database db = Database.getInstance();

	public AddCategoria(CategoriaDto cat) {
		this.cat = cat;
	}

	public boolean execute() throws ModelException {
		if (!existsCompeticion()) {
			throw new ModelException("La competicion no existe");
		}
		List<CategoriaDto> categorias = obtenerCategorias();
		if (!categorias.isEmpty()) {
			checkValidAgeGap(categorias);
		}
		try {
			Connection c = db.getConnection();

			PreparedStatement pst = c.prepareStatement(AÑADIR_CATEGORIA);

			pst.setString(1, cat.nombreCategoria);
			pst.setString(2, cat.idCompeticion);
			pst.setInt(3, cat.edadMinima);
			pst.setInt(4, cat.edadMaxima);
			pst.setString(5, cat.sexo);

			pst.executeUpdate();

			pst.close();
			c.close();
		} catch (SQLException e) {
			throw new ModelException(e.getMessage());
		}
		return true;
	}

	private void checkValidAgeGap(List<CategoriaDto> categorias) throws ModelException {
		int edadMin = Integer.MAX_VALUE;
		int edadMax = 0;
		for (CategoriaDto categ : categorias.stream().filter(p ->p.sexo == cat.sexo).collect(Collectors.toList())) {
			if (categ.edadMaxima > edadMax) {
				edadMax = categ.edadMaxima;
			}
			if (categ.edadMinima < edadMin) {
				edadMin = categ.edadMinima;
			}
		}
		if (cat.edadMinima != edadMax + 1 || cat.edadMaxima != edadMin - 1) {
			throw new ModelException("Selección de edad inválida, la edad no puede inteferir con las edades de otras categorias");
		}
	}

	private List<CategoriaDto> obtenerCategorias() {
		return db.executeQueryPojo(CategoriaDto.class, OBTENER_CATEGORIAS, cat.idCompeticion);
	}

	private boolean existsCompeticion() throws ModelException {
		boolean aux = false;
		try {
			Connection c = db.getConnection();

			PreparedStatement pst = c.prepareStatement(EXISTE_COMPETICION);

			pst.setString(1, cat.idCompeticion);

			pst.executeQuery();

			aux = pst.getResultSet().next();

			pst.close();
			c.close();
		} catch (SQLException e) {
			throw new ModelException(e.getMessage());
		}
		return aux;
	}

}
