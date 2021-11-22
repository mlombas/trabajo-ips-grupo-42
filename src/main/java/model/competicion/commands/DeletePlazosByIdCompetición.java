package model.competicion.commands;

import java.util.List;

import model.competicion.CompeticionDto;
import util.database.Database;
import util.exceptions.ApplicationException;

public class DeletePlazosByIdCompetición {
	
	private static final String GETCOMPETICION = "select * from Competicion where id = ?";
	private static final String DELETEPLAZOS = "DELETE FROM Plazo WHERE idCompeticion = ?";
	
	private String competicionId;
	private Database db = Database.getInstance();
	
	public DeletePlazosByIdCompetición(String competicionId) {
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
		
		db.executeUpdate(DELETEPLAZOS, competicionId);
	}
	
	private void checkCompeticion(List<CompeticionDto> competicion) {
		if (competicion.size() == 0) {
			throw new ApplicationException("La competición no existe");
		}
	}
	

}
