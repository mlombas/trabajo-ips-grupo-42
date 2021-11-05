package model.competicion.commands;

import java.util.List;

import model.competicion.CompeticionDto;
import model.competicion.ClasificacionDto;
import util.database.Database;


public class GetClasificacionByCategoria{

	private static final String OBTENER_RESULTADOS = "select c.posicion, c.dorsal, c.tiempoSalida, c.tiempoLlegada, a.sexo, a.nombre, i.categoria from Clasificacion c, Atleta a, Inscripcion i where c.idCompeticion = ? and a.email=c.emailAtleta and i.idCompeticion = c.idCompeticion and i.emailAtleta = c.emailAtleta and i.categoria=? order by c.posicion";

	private Database db = Database.getInstance();
	private String id_competicion;
	private String categoria;
		
	public GetClasificacionByCategoria(CompeticionDto comp, String categoria) {
		this.id_competicion = comp.id;
		this.categoria = categoria;
	}
	
	
	public List<ClasificacionDto> execute() {
		return db.executeQueryPojo(ClasificacionDto.class, OBTENER_RESULTADOS, id_competicion, categoria);
	}
	
}
