package model.competicion.commands;

import java.util.List;

import model.competicion.CompeticionDto;
import model.competicion.PosicionDto;
import util.database.Database;


public class GetClasificacionByCategoria{

	private static final String OBTENER_RESULTADOS = "select i.posicion, a.sexo, i.nombreAtleta, i.tiempo, i.estadoInscripcion from Inscripcion i, Atleta a where i.idCompeticion = ? and i.categoria = ? and a.email=i.emailAtleta order by i.posicion";

	private Database db = Database.getInstance();
	private String id_competicion;
	private String categoria;
		
	public GetClasificacionByCategoria(CompeticionDto comp, String categoria) {
		this.id_competicion = comp.id;
		this.categoria = categoria;
	}
	
	
	public List<PosicionDto> execute() {
		return db.executeQueryPojo(PosicionDto.class, OBTENER_RESULTADOS, id_competicion, categoria);
	}
	
}
