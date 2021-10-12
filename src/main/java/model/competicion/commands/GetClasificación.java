package model.competicion.commands;

import java.util.List;

import giis.demo.util.Database;
import model.Command;
import model.inscripcion.InscripcionDto;

public class GetClasificación implements Command{

	private static final String OBTENER_RESULTADOS = "select * from Inscripcion where idCompeticion = ? and categoria = ?";

	private Database db = new Database();
	private String id_competicion;
	private String categoria;
		
	public GetClasificación(String id_competicion, String categoria) {
		this.id_competicion = id_competicion;
		this.categoria = categoria;
	}
	
	@Override
	public List<InscripcionDto> execute() {
		return db.executeQueryPojo(InscripcionDto.class, OBTENER_RESULTADOS, id_competicion, categoria);
	}
	
	
	
}
