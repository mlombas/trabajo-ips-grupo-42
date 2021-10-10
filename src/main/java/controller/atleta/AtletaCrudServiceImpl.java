package controller.atleta;

import model.atleta.AtletaDto;
import model.atleta.commands.RegisterAtletaToCompetition;
import model.competicion.CompeticionDto;

public class AtletaCrudServiceImpl implements AtletaCrudService {

	@Override
	public AtletaDto registerAtletaToCompeticion(AtletaDto atleta, CompeticionDto competicion) {
		return new RegisterAtletaToCompetition(atleta, competicion).execute();
	}

}
