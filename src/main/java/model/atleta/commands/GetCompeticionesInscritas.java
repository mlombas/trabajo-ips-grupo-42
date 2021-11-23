package model.atleta.commands;

import java.util.List;

import model.atleta.AtletaDto;
import model.inscripcion.InscripcionDto;
import util.database.Database;
import util.exceptions.ApplicationException;

public class GetCompeticionesInscritas {
	
	private static final String EXISTSEMAIL = "select * from Atleta where email = ?";
	private static final String COMPETICIONESINSCRITAS = "select * from Inscripcion where emailAtleta = ? order by fechaCambioEstado DESC";

	private AtletaDto atleta;
	private Database db = Database.getInstance();

	public GetCompeticionesInscritas(AtletaDto atleta) {
		this.atleta = atleta;
	}

	public List<InscripcionDto> execute() {
		checkArguments();
		checkExistsEmail();
		
		List<InscripcionDto> inscripciones = db.executeQueryPojo(InscripcionDto.class, COMPETICIONESINSCRITAS, atleta.email);
		return inscripciones;
	}

	private void checkExistsEmail() {
		List<AtletaDto> ad = db.executeQueryPojo(AtletaDto.class, EXISTSEMAIL, atleta.email);
		if(ad.size() <= 0) {
			throw new ApplicationException("No existe ningún atleta con ese email");
		}
	}

	private void checkArguments() {
		if (atleta == null) {
			throw new IllegalArgumentException("El AtletaDto es nulo");
		}
	}

}
