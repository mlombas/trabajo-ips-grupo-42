package model.atleta.commands;

import model.atleta.AtletaDto;
import model.inscripcion.InscripcionDto;
import util.database.Database;

public class GetAtletaByInscripcion {

	private static String  GETATLETABYINSCRIPCION = "select * from Atleta where email = ?";

	private Database db = Database.getInstance();

	private InscripcionDto inscripcion;
	
	public GetAtletaByInscripcion(InscripcionDto inscripcion) {
		this.inscripcion = inscripcion;
		checkParams();
	}

	private void checkParams() {
		if (inscripcion == null) {
			throw new IllegalArgumentException("La inscripcion no es válida");
		}
		if (inscripcion.emailAtleta == null || inscripcion.emailAtleta.trim().length() == 0) {
			throw new IllegalArgumentException("El email del atleta no es válido");
		}
	}

	public AtletaDto execute() {
		return db.executeQueryPojo(AtletaDto.class,  GETATLETABYINSCRIPCION, inscripcion.emailAtleta).get(0);
	}

}
