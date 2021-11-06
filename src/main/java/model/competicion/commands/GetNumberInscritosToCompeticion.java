package model.competicion.commands;

import util.database.Database;

public class GetNumberInscritosToCompeticion {
	
	private static final String GET_INSCRIBED_TO_COMPETITION =
			"SELECT COUNT(emailAtleta) " +
			"FROM inscripcion " +
			"WHERE idCompeticion = ?";
		
	private Database db = Database.getInstance();
	
	public int execute(String competicionId) {
		
		return db.executeQueryPojo(
				Integer.class, 
				GET_INSCRIBED_TO_COMPETITION,
				competicionId
				).get(0);
	}
}
