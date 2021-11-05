package model.competicion.commands;

import java.util.List;

import model.competicion.CompeticionDto;
import model.competicion.ClasificacionDto;
import util.database.Database;

public class GetClasificacion{

	private static final String OBTENER_RESULTADOS = "select c.posicion, c.dorsal, c.tiempoSalida, c.tiempoLlegada, a.sexo, a.nombre from Clasificacion c, Atleta a where c.idCompeticion = ? and a.email=c.emailAtleta order by c.posicion";

	private Database db = Database.getInstance();
	private String id_competicion;
		
	public GetClasificacion(CompeticionDto comp) {
		this.id_competicion = comp.id;
	}
	
	public List<ClasificacionDto> execute() {
		return db.executeQueryPojo(ClasificacionDto.class, OBTENER_RESULTADOS, id_competicion);
	}
	
	
	
}
