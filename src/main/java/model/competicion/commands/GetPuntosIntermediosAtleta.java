package model.competicion.commands;

import java.util.List;

import model.competicion.ClasificacionDto;
import model.competicion.CompeticionDto;
import model.competicion.PuntoIntermedioClasficacionDto;
import util.database.Database;

public class GetPuntosIntermediosAtleta {

	private static final String OBTENER_TIEMPOS = "select p.tiempo from PuntoIntermedioClasificacion p, Inscripcion i where p.idCompeticion = ? and p.emailAtleta = i.emailAtleta and i.dorsal = ? order by tiempo";

	private Database db = Database.getInstance();

	private CompeticionDto competicion;
	private ClasificacionDto clasificado;

	public GetPuntosIntermediosAtleta(CompeticionDto competicion, ClasificacionDto clasificado) {
		this.competicion = competicion;
		this.clasificado = clasificado;

	}

	public List<PuntoIntermedioClasficacionDto> execute() {
		return db.executeQueryPojo(PuntoIntermedioClasficacionDto.class, OBTENER_TIEMPOS, competicion.id,
				clasificado.dorsal);
	}

}
