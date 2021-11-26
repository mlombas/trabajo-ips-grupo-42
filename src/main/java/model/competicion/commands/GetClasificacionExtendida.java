package model.competicion.commands;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.competicion.ClasificacionDto;
import model.competicion.ClasificacionExtendidaDto;
import model.competicion.CompeticionDto;
import util.database.Database;

public class GetClasificacionExtendida {

	private static final String OBTENER_RESULTADOS = "select c.dorsal, c.tiempoSalida, c.tiempoLlegada, a.sexo, a.nombre, i.categoria from Clasificacion c, Atleta a, Inscripcion i where c.idCompeticion = ? and a.email=c.emailAtleta and i.idCompeticion = c.idCompeticion and i.emailAtleta = c.emailAtleta and c.tiempoLlegada is not null order by c.tiempoLlegada-c.tiempoSalida";
	private static final String OBTENER_RESULTADOS_DNS = "select a.sexo, a.nombre, i.categoria from Clasificacion c, Atleta a, Inscripcion i where c.idCompeticion = ? and a.email=c.emailAtleta and i.idCompeticion = c.idCompeticion and i.emailAtleta = c.emailAtleta and c.dorsal is null";
	private static final String OBTENER_RESULTADOS_DNF = "select c.dorsal, c.tiempoSalida, a.sexo, a.nombre, i.categoria from Clasificacion c, Atleta a, Inscripcion i where c.idCompeticion = ? and a.email=c.emailAtleta and i.idCompeticion = c.idCompeticion and i.emailAtleta = c.emailAtleta and c.dorsal is not null and c.tiempoLlegada is null order by c.dorsal";
	private static final String OBTENER_NO_PRESENTADOS = "select a.sexo, a.nombre, i.categoria from Clasificacion c, Atleta a, Inscripcion i where i.idCompeticion = ? and a.email=i.emailAtleta and i.idCompeticion = c.idCompeticion and i.emailAtleta not in (select cl.emailAtleta from Clasificacion cl where cl.idCompeticion = ?) group by i.emailAtleta";

	private Database db = Database.getInstance();
	private String id_competicion;

	public GetClasificacionExtendida(CompeticionDto comp) {
		this.id_competicion = comp.id;
	}

	public List<ClasificacionExtendidaDto> execute() {
		List<ClasificacionExtendidaDto> noPresentados = db.executeQueryPojo(ClasificacionExtendidaDto.class, OBTENER_NO_PRESENTADOS,
				id_competicion, id_competicion);
		for (ClasificacionDto c : noPresentados) {
			c.presentado = false;
		}
		List<ClasificacionExtendidaDto> clasificados = db.executeQueryPojo(ClasificacionExtendidaDto.class, OBTENER_RESULTADOS,
				id_competicion);
		int posicion = 0;
		for (ClasificacionDto c : clasificados) {
			c.posicion = ++posicion;
		}
		return Stream.concat(
				Stream.concat(
						Stream.concat(clasificados.stream(),
								db.executeQueryPojo(ClasificacionExtendidaDto.class, OBTENER_RESULTADOS_DNF, id_competicion)
										.stream()),
						db.executeQueryPojo(ClasificacionExtendidaDto.class, OBTENER_RESULTADOS_DNS, id_competicion).stream()),
				noPresentados.stream()).collect(Collectors.toList());
	}

}
