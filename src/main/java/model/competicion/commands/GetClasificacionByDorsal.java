package model.competicion.commands;

import java.util.List;

import model.competicion.ClasificacionDto;
import model.competicion.CompeticionDto;
import util.database.Database;

public class GetClasificacionByDorsal {
	
	private static String GETACLASIFICACIONBYDORSAL = "select c.dorsal, c.tiempoSalida, c.tiempoLlegada, a.sexo, a.nombre, i.categoria, c.posicion "
			 + "from Clasificacion c, Inscripcion i, Atleta a"
			 + " where c.dorsal = ? and c.idCompeticion = ? and c.emailAtleta = i.emailAtleta and c.idCompeticion = i.idCompeticion and c.emailAtleta = a.email";

	private Database db = Database.getInstance();

	private ClasificacionDto selectedAtleta;
	private CompeticionDto competicion;

	public GetClasificacionByDorsal(ClasificacionDto selectedAtleta, CompeticionDto competicion) {
		this. selectedAtleta =  selectedAtleta;
		this.competicion = competicion;
		checkParams();
	}

	private void checkParams() {
		if (selectedAtleta == null) {
			throw new IllegalArgumentException("Clasificacion no válida");
		}
		if (competicion == null) {
			throw new IllegalArgumentException("Competicion no válida");
		}
	}


	public List<ClasificacionDto> execute() {
		return db.executeQueryPojo(ClasificacionDto.class, GETACLASIFICACIONBYDORSAL, selectedAtleta.dorsal, competicion.id);
	}

}
