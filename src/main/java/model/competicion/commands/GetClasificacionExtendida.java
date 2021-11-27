package model.competicion.commands;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.competicion.ClasificacionDto;
import model.competicion.CompeticionDto;
import model.competicion.PuntoIntermedioClasficacionDto;
import model.competicion.PuntoIntermedioDto;
import util.database.Database;

public class GetClasificacionExtendida {

	private static final String OBTENER_RESULTADOS = "select c.dorsal, c.tiempoSalida, c.tiempoLlegada, a.sexo, a.nombre, i.categoria, i.clubAtleta, c.minsByKm, c.diferenciaTiempo from Clasificacion c, Atleta a, Inscripcion i where c.idCompeticion = ? and a.email=c.emailAtleta and i.idCompeticion = c.idCompeticion and i.emailAtleta = c.emailAtleta and c.tiempoLlegada is not null order by c.tiempoLlegada-c.tiempoSalida";
	private static final String OBTENER_RESULTADOS_DNS = "select a.sexo, a.nombre, i.categoria, i.clubAtleta from Clasificacion c, Atleta a, Inscripcion i where c.idCompeticion = ? and a.email=c.emailAtleta and i.idCompeticion = c.idCompeticion and i.emailAtleta = c.emailAtleta and c.dorsal is null";
	private static final String OBTENER_RESULTADOS_DNF = "select c.dorsal, c.tiempoSalida, a.sexo, a.nombre, i.categoria, i.clubAtleta, c.minsByKm, c.diferenciaTiempo from Clasificacion c, Atleta a, Inscripcion i where c.idCompeticion = ? and a.email=c.emailAtleta and i.idCompeticion = c.idCompeticion and i.emailAtleta = c.emailAtleta and c.dorsal is not null and c.tiempoLlegada is null order by c.dorsal";
	private static final String OBTENER_NO_PRESENTADOS = "select a.sexo, a.nombre, i.categoria, i.clubAtleta from Clasificacion c, Atleta a, Inscripcion i where i.idCompeticion = ? and a.email=i.emailAtleta and i.idCompeticion = c.idCompeticion and i.emailAtleta not in (select cl.emailAtleta from Clasificacion cl where cl.idCompeticion = ?) group by i.emailAtleta";

	private static final String OBTENER_PUNTOS = "select * from PuntoIntermedio WHERE idCompeticion = ? ";
	private static final String OBTENER_TIEMPOS = "select p.tiempo from PuntoIntermedioClasificacion p, Inscripcion i where p.idCompeticion = ? and p.emailAtleta = i.emailAtleta and i.dorsal = ? order by tiempo";
	
	private Database db = Database.getInstance();
	private String id_competicion;

	public GetClasificacionExtendida(CompeticionDto comp) {
		this.id_competicion = comp.id;
	}

	public List<ClasificacionDto> execute() {
		List<ClasificacionDto> noPresentados = db.executeQueryPojo(ClasificacionDto.class, OBTENER_NO_PRESENTADOS,
				id_competicion, id_competicion);
		for (ClasificacionDto c : noPresentados) {
			c.presentado = false;
		}
		List<ClasificacionDto> clasificados = db.executeQueryPojo(ClasificacionDto.class, OBTENER_RESULTADOS,
				id_competicion);
		int posicion = 0;
		for (ClasificacionDto c : clasificados) {
			c.posicion = ++posicion;
		}
		List<ClasificacionDto> clasificacion = Stream.concat(
				Stream.concat(
						Stream.concat(clasificados.stream(),
								db.executeQueryPojo(ClasificacionDto.class, OBTENER_RESULTADOS_DNF, id_competicion)
										.stream()),
						db.executeQueryPojo(ClasificacionDto.class, OBTENER_RESULTADOS_DNS, id_competicion).stream()),
				noPresentados.stream()).collect(Collectors.toList());

		List<PuntoIntermedioDto> puntos = db.executeQueryPojo(PuntoIntermedioDto.class, OBTENER_PUNTOS,id_competicion);
		int nPuntos = puntos.size();
		
		if (nPuntos > 0) {
			for (ClasificacionDto clasificado : clasificacion) {
				clasificado.puntosIntermedios = new int[nPuntos];
				if (clasificado.dorsal != 0) {
					List<PuntoIntermedioClasficacionDto> tiempos =  db.executeQueryPojo(PuntoIntermedioClasficacionDto.class, OBTENER_TIEMPOS, id_competicion, clasificado.dorsal );
					for (int i = 0; i < nPuntos; i++) {
						clasificado.puntosIntermedios[i] = tiempos.get(i).getTiempo();
					}
				} else {
					for (int i = 0; i < nPuntos; i++) {
						clasificado.puntosIntermedios[i] = 0;
					}
				}
			}
		}

		return clasificacion;
	}


}
