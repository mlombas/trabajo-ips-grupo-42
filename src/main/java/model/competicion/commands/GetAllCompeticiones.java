package model.competicion.commands;

import java.util.List;

import giis.demo.util.Database;
import model.competicion.CompeticionDto;

public class GetAllCompeticiones {
	
	private static final String ALL_COMPETITIONS =
			"SELECT * " +
			"FROM competicion " +
			"WHERE fechaFin >= DATE('now')" +
			"ORDER BY fecha";
		
	private Database db = new Database();
	
	public List<CompeticionDto> execute() {
		List<CompeticionDto> competitions;
		competitions = db.executeQueryPojo(
				CompeticionDto.class, 
				ALL_COMPETITIONS
				);
		return competitions;
	}
}
