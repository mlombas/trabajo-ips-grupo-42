package controller.atleta;

import model.atleta.AtletaDto;
import model.atleta.commands.RegisterAtletaToCompetition;
import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;

public class AtletaCrudServiceImpl implements AtletaCrudService {

	@Override
	public InscripcionDto registerAtletaToCompeticion(AtletaDto atleta, CompeticionDto competicion) {
		return new RegisterAtletaToCompetition(atleta, competicion).execute();
	}

}
