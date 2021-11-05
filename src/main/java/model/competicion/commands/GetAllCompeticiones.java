package model.competicion.commands;

import java.util.List;

import model.competicion.CompeticionDto;
import util.database.Database;

public class GetAllCompeticiones {
	
	private static final String ALL_COMPETITIONS =
			"SELECT * " +
			"FROM competicion " +
			"ORDER BY id";
		
	private Database db = Database.getInstance();
	
	public List<CompeticionDto> execute() {
		List<CompeticionDto> competitions;
		competitions = db.executeQueryPojo(
				CompeticionDto.class, 
				ALL_COMPETITIONS
				);
		return competitions;
	}
}
