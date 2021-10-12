package model.competicion.commands;

import java.util.LinkedList;
import java.util.List;

import bank_mockup.Bank;
import giis.demo.util.Database;
import model.atleta.TarjetaDto;
import model.competicion.CompeticionDto;

public class GetAllCompeticiones {
	
	private static final String ALL_COMPETITIONS =
			"SELECT * " +
			"FROM competicion " +
			"WHERE fechaFin >= DATE('now')";
		
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
