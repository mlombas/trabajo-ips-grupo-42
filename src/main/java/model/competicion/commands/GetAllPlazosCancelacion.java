package model.competicion.commands;

import java.util.List;

import model.competicion.CompeticionDto;
import model.competicion.PlazoCancelacionDto;
import util.database.Database;
import util.exceptions.ApplicationException;

public class GetAllPlazosCancelacion {

	private static final String GETCOMPETICION = "select * from Competicion where id = ?";
	private static final String GETALLPLAZOS = "select * from PlazoCancelacion where idCompeticion = ?";

	private String competicionId;
	private Database db = Database.getInstance();

	public GetAllPlazosCancelacion(String competicionId) {
		this.competicionId = competicionId;
		checkArguments();
	}

	private void checkArguments() {
		if (competicionId == null || competicionId.trim().length() == 0) {
			throw new IllegalArgumentException("La competición no es válida");
		}

	}

	public List<PlazoCancelacionDto> execute() {

		List<CompeticionDto> competi = db.executeQueryPojo(CompeticionDto.class, GETCOMPETICION, competicionId);

		checkCompeticion(competi);

		List<PlazoCancelacionDto> plazos = db.executeQueryPojo(PlazoCancelacionDto.class, GETALLPLAZOS, competicionId);

		return plazos;

	}

	private void checkCompeticion(List<CompeticionDto> competicion) {
		if (competicion.size() == 0) {
			throw new ApplicationException("La competición no existe");
		}
	}

}
