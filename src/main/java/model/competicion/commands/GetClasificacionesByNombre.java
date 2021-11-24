package model.competicion.commands;

import java.util.List;

import model.atleta.AtletaDto;
import model.competicion.ClasificacionDto;
import model.competicion.CompeticionDto;
import util.database.Database;

public class GetClasificacionesByNombre {

	private static String GETACLASIFICACIONESNOMBRE = "select c.dorsal, c.tiempoSalida, c.tiempoLlegada, a.sexo, a.nombre, i.categoria, c.posicion "
			+ "from Clasificacion c, Inscripcion i, Atleta a "
			+ "where a.nombre = ? and c.emailAtleta = a.email and c.emailAtleta = i.emailAtleta  and c.idCompeticion = ?  and c.idCompeticion = i.idCompeticion and c.tiempoLlegada is not null "
			+ "order by c.tiempoLlegada-c.tiempoSalida";

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
		return db.executeQueryPojo(ClasificacionDto.class, GETACLASIFICACIONESNOMBRE, atleta.nombre, competicion.id);
	}

}
