package model.competicion.commands;

import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;
import util.database.Database;

public class GetCompeticionByInscripcion {

	private static String  GETCOMPETICIONBYINSCRIPCION = "select * from Competicion where id = ?";

	private Database db = Database.getInstance();

	private InscripcionDto inscripcion;

	public GetCompeticionByInscripcion(InscripcionDto inscripcion) {
		this.inscripcion = inscripcion;
		checkParams();
	}

	private void checkParams() {
		if (inscripcion == null) {
			throw new IllegalArgumentException("La inscripcion no es válida");
		}
		if (inscripcion.idCompeticion == null || inscripcion.idCompeticion.trim().length() == 0) {
			throw new IllegalArgumentException("La id de la competición no es válida");
		}
	}

	public CompeticionDto execute() {
		return db.executeQueryPojo(CompeticionDto.class, GETCOMPETICIONBYINSCRIPCION, inscripcion.idCompeticion).get(0);
	}

}
