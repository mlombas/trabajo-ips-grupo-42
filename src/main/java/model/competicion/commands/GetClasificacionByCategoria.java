package model.competicion.commands;

import java.util.List;

import giis.demo.util.Database;
import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;

public class GetClasificacionByCategoria{

	private static final String OBTENER_RESULTADOS = "select * from Inscripcion where idCompeticion = ? and categoria = ?";

	private Database db = new Database();
	private String id_competicion;
	private String categoria;
		
	public GetClasificacionByCategoria(CompeticionDto comp, String categoria) {
		this.id_competicion = comp.id;
		this.categoria = categoria;
	}
	
	
	public List<InscripcionDto> execute() {
		return db.executeQueryPojo(InscripcionDto.class, OBTENER_RESULTADOS, id_competicion, categoria);
	}
	
	
	
}
