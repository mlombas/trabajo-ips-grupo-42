package controller.atleta;

import java.util.Date;

import model.atleta.AtletaDto;
import model.atleta.commands.PayWithTarjeta;
import model.atleta.commands.RegisterAtletaToCompetition;
import model.competicion.CompeticionDto;

public class AtletaCrudServiceImpl implements AtletaCrudService {

	@Override
	public AtletaDto registerAtletaToCompeticion(AtletaDto atleta, CompeticionDto competicion) {
		return new RegisterAtletaToCompetition(atleta, competicion).execute();
	}

	@Override
	public Date payWithTarjeta(AtletaDto atleta, CompeticionDto competicion) {
		 return new PayWithTarjeta(atleta,competicion).execute();
		//TODO
	}

}
