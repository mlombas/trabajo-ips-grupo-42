package model.competicion.commands;

import java.time.LocalDate;
import java.util.List;

import model.ModelFactory;
import model.competicion.CompeticionDto;
import model.competicion.PlazoInscripcionDto;
import util.database.Database;
import util.exceptions.ApplicationException;
import util.exceptions.ModelException;

public class GetAllCompeticiones {
	
	private static final String ALL_COMPETITIONS =
			"SELECT * " +
			"FROM competicion " +
			"ORDER BY id";
	private static final String GETPLAZOACTUAL = "select * from Plazo where (fechaInicio <= ?) and ( ? < fechaFin) and idCompeticion = ?";
	private static final String GETLASTPLAZO = "select * from Plazo where fechaFin < ?  and idCompeticion = ? order by fechaFin DESC";
	private static final String GETFIRSTPLAZO = "select * from Plazo where fechaInicio > ?  and idCompeticion = ? order by fechaInicio";
		
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
			
			List<PlazoInscripcionDto> plazo = db.executeQueryPojo(PlazoInscripcionDto.class, GETPLAZOACTUAL, LocalDate.now(),LocalDate.now(),competition.id);
			if(plazo.size() == 0) {
				plazo = db.executeQueryPojo(PlazoInscripcionDto.class, GETLASTPLAZO, LocalDate.now(),competition.id);
				if(plazo.size() == 0) {
					plazo = db.executeQueryPojo(PlazoInscripcionDto.class, GETFIRSTPLAZO, LocalDate.now(),competition.id);
					if(plazo.size() == 0) {
						throw new ApplicationException("No hay plazos para esta competición " + competition.id);
					}
				}
				
			}
			competition.cuota = plazo.get(0).cuota;
			competition.fechaInicio = plazo.get(0).fechaInicio.toString() ;
			competition.fechaFin = plazo.get(0).fechaFin.toString() ;
		}
		return competitions;
	}
}
