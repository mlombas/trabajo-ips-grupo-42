package model.competicion.commands;

import java.util.List;

import model.atleta.AtletaDto;
import model.competicion.ClasificacionDto;
import model.competicion.CompeticionDto;
import model.competicion.PuntoIntermedioClasficacionDto;
import model.competicion.PuntoIntermedioDto;
import util.database.Database;

public class GetClasificacionesByNombre {

	private static String GETACLASIFICACIONESNOMBRE = "select c.dorsal, c.tiempoSalida, c.tiempoLlegada, a.sexo, a.nombre, i.categoria, c.posicion, i.clubAtleta, c.minsByKm, c.diferenciaTiempo "
			+ "from Clasificacion c, Inscripcion i, Atleta a "
			+ "where a.nombre = ? and c.emailAtleta = a.email and c.emailAtleta = i.emailAtleta  and c.idCompeticion = ?  and c.idCompeticion = i.idCompeticion and c.tiempoLlegada is not null "
			+ "order by c.tiempoLlegada-c.tiempoSalida";
	
	private static final String OBTENER_PUNTOS = "select * from PuntoIntermedio WHERE idCompeticion = ? ";
	private static final String OBTENER_TIEMPOS = "select p.tiempo from PuntoIntermedioClasificacion p, Inscripcion i where p.idCompeticion = ? and p.emailAtleta = i.emailAtleta and i.dorsal = ? order by tiempo";

	private Database db = Database.getInstance();

	private AtletaDto atleta;
	private CompeticionDto competicion;

	public GetClasificacionesByNombre(CompeticionDto competicion, AtletaDto atleta) {
		this.atleta = atleta;
		this.competicion = competicion;
		checkParams();
	}

	private void checkParams() {
		if (atleta == null) {
			throw new IllegalArgumentException("Atleta no válido");
		}
		if (competicion == null) {
			throw new IllegalArgumentException("Competicion no válida");
		}
	}

	public List<ClasificacionDto> execute() {
		List<ClasificacionDto> clasificacion = db.executeQueryPojo(ClasificacionDto.class, GETACLASIFICACIONESNOMBRE, atleta.nombre, competicion.id);
		
		List<PuntoIntermedioDto> puntos = db.executeQueryPojo(PuntoIntermedioDto.class, OBTENER_PUNTOS,competicion.getId());
		int nPuntos = puntos.size();
		
		if (nPuntos > 0) {
			for (ClasificacionDto clasificado : clasificacion) {
				clasificado.puntosIntermedios = new int[nPuntos];
				if (clasificado.dorsal != 0) {
					List<PuntoIntermedioClasficacionDto> tiempos =  db.executeQueryPojo(PuntoIntermedioClasficacionDto.class, OBTENER_TIEMPOS, competicion.getId(), clasificado.dorsal );
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
