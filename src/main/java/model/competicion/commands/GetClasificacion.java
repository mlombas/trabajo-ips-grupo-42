package model.competicion.commands;

import java.util.List;

import giis.demo.util.Database;
import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;

public class GetClasificacion{

	private static final String OBTENER_RESULTADOS = "select * from Inscripcion where idCompeticion = ?";

	private Database db = new Database();
	private String id_competicion;
		
	public GetClasificacion(CompeticionDto comp) {
		this.id_competicion = comp.id;
	}
	
	public List<InscripcionDto> execute() {
		return db.executeQueryPojo(InscripcionDto.class, OBTENER_RESULTADOS, id_competicion);
	}
	
	
	
}
