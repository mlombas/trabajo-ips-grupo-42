package model.competicion.commands;

import java.util.List;

import model.competicion.CompeticionDto;
import model.competicion.PlazoInscripcionDto;
import util.database.Database;
import util.exceptions.ApplicationException;

public class CheckPlazosByIdCompeticion {
	
	private static final String GETCOMPETICION = "select * from Competicion where id = ?";
	private static final String GETALLPLAZOS = "select * from Plazo where idCompeticion = ?";
	
	private String competicionId;
	private Database db = Database.getInstance();
	
	public  CheckPlazosByIdCompeticion(String competicionId) {
		this.competicionId = competicionId;
		checkArguments();
	}
	
	private void checkArguments() {
		if (competicionId == null || competicionId.trim().length() == 0) {
			throw new IllegalArgumentException("La competición no es válida");
		}

	}
	
	public void execute() {
		
		List<CompeticionDto> competi = db.executeQueryPojo(CompeticionDto.class, GETCOMPETICION, competicionId);

		checkCompeticion(competi);
		
		List<PlazoInscripcionDto> plazos = db.executeQueryPojo(PlazoInscripcionDto.class, GETALLPLAZOS, competicionId);
		
		if (plazos.size() == 0) {
			throw new ApplicationException("No hay ningún plazo de inscripción");
		}
	}
	
	private void checkCompeticion(List<CompeticionDto> competicion) {
		if (competicion.size() == 0) {
			throw new ApplicationException("La competición no existe");
		}
	}

}
