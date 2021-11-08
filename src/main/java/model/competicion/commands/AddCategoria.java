package model.competicion.commands;

import model.competicion.CategoriaDto;

public class AddCategoria {

	private static String AÑADIR_CATEGORIA = "insert into Categoria(nombreCategoria, idCompeticion, edadMinima, edadMaxima, sexo) values (?,?,?,?,?)";
	
	private CategoriaDto cat;

	public AddCategoria(CategoriaDto cat) {
		this.cat = cat;
	}

	public boolean execute() {
		// TODO Auto-generated method stub
		return true;
	}

}
