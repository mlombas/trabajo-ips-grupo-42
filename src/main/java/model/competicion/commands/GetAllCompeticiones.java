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
			
			PlazoInscripcionDto plazo = getPlazo(competition);
			
			competition.cuota = plazo.cuota;
			competition.fechaInicio = plazo.fechaInicio.toString() ;
			competition.fechaFin = plazo.fechaFin.toString() ;
		}
		
		return competitions;
	}

	private PlazoInscripcionDto getPlazo(CompeticionDto competition) {
		List<PlazoInscripcionDto> plazoActual = db.executeQueryPojo(PlazoInscripcionDto.class, GETPLAZOACTUAL, LocalDate.now(),LocalDate.now(),competition.id);
		List<PlazoInscripcionDto> ultimoPlazo = db.executeQueryPojo(PlazoInscripcionDto.class, GETLASTPLAZO, LocalDate.now(),competition.id);
		List<PlazoInscripcionDto> primerPlazo = db.executeQueryPojo(PlazoInscripcionDto.class, GETFIRSTPLAZO, LocalDate.now(),competition.id);
		
		if(plazoActual.size() > 0) {
			return plazoActual.get(0);
		} else if (ultimoPlazo.size() > 0) {
			return ultimoPlazo.get(0);
		} else if (primerPlazo.size() > 0) {
			return primerPlazo.get(0);
		} else {
			throw new ApplicationException("No hay plazos para esta competición " + competition.id);
		}
	}
	
}
