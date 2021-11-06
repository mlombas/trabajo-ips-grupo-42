package model.competicion.commands;

import java.util.List;

import model.competicion.CategoriaDto;
import util.database.Database;

public class GetCategorias {

	private static String OBTENER_CATEGORIAS = "select nombreCategoria from Categorias where idCompeticion = ?";
	
	private Database db = Database.getInstance();
	private String competicionId;
	
	public GetCategorias(String competicionId) {
		this.competicionId = competicionId;
	}

	public List<CategoriaDto> execute() {
		return db.executeQueryPojo(CategoriaDto.class, OBTENER_CATEGORIAS, competicionId);
	}

}
