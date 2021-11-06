package model.competicion.commands;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.competicion.CompeticionDto;
import model.competicion.ClasificacionDto;
import util.database.Database;

public class GetClasificacion {

	private static final String OBTENER_RESULTADOS = "select c.posicion, c.dorsal, c.tiempoSalida, c.tiempoLlegada, a.sexo, a.nombre, i.categoria from Clasificacion c, Atleta a, Inscripcion i where c.idCompeticion = ? and a.email=c.emailAtleta and i.idCompeticion = c.idCompeticion and i.emailAtleta = c.emailAtleta and c.posicion is not null and c.tiempoLlegada is not null order by c.posicion";
	private static final String OBTENER_RESULTADOS_DNS = "select a.sexo, a.nombre, i.categoria from Clasificacion c, Atleta a, Inscripcion i where c.idCompeticion = ? and a.email=c.emailAtleta and i.idCompeticion = c.idCompeticion and i.emailAtleta = c.emailAtleta and c.dorsal is null";
	private static final String OBTENER_RESULTADOS_DNF = "select c.dorsal, c.tiempoSalida, a.sexo, a.nombre, i.categoria from Clasificacion c, Atleta a, Inscripcion i where c.idCompeticion = ? and a.email=c.emailAtleta and i.idCompeticion = c.idCompeticion and i.emailAtleta = c.emailAtleta and c.dorsal is not null and c.tiempoLlegada is null";
	private static final String OBTENER_NO_PRESENTADOS = "select a.sexo, a.nombre, i.categoria from Clasificacion c, Atleta a, Inscripcion i where i.idCompeticion = ? and a.email=i.emailAtleta and i.idCompeticion = c.idCompeticion and i.emailAtleta not in (select cl.emailAtleta from Clasificacion cl where cl.idCompeticion = ?) group by i.emailAtleta";

	private Database db = Database.getInstance();
	private String id_competicion;

	public GetClasificacion(CompeticionDto comp) {
		this.id_competicion = comp.id;
	}

	public List<ClasificacionDto> execute() {
		List<ClasificacionDto> noPresentados = db.executeQueryPojo(ClasificacionDto.class, OBTENER_NO_PRESENTADOS,
				id_competicion, id_competicion);
		for(ClasificacionDto c : noPresentados) {
			c.presentado = false;
		}
		return Stream.concat(Stream.concat(
				Stream.concat(db.executeQueryPojo(ClasificacionDto.class, OBTENER_RESULTADOS, id_competicion).stream(),
						db.executeQueryPojo(ClasificacionDto.class, OBTENER_RESULTADOS_DNF, id_competicion).stream()),
				db.executeQueryPojo(ClasificacionDto.class, OBTENER_RESULTADOS_DNS, id_competicion).stream()),
				noPresentados.stream()).collect(Collectors.toList());
	}

}
