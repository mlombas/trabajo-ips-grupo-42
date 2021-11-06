package model.competicion.commands;

import java.util.List;

import model.ModelFactory;
import model.competicion.CompeticionDto;
import util.database.Database;
import util.exceptions.ModelException;

public class GetAllCompeticiones {
	
	private static final String ALL_COMPETITIONS =
			"SELECT * " +
			"FROM competicion " +
			"ORDER BY id";
		
	private Database db = Database.getInstance();
	
	public List<CompeticionDto> execute() throws ModelException {
		List<CompeticionDto> competitions;
		competitions = db.executeQueryPojo(
				CompeticionDto.class, 
				ALL_COMPETITIONS
				);
		
		for(CompeticionDto competition : competitions) {
			int nAtletas = ModelFactory.forOrganizadorCrudService()
					.getAtletasForCompetition(competition).size();
			competition.plazas -= nAtletas;
		}
		return competitions;
	}
}
