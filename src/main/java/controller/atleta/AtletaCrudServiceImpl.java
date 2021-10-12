package controller.atleta;

import model.atleta.AtletaDto;
import model.atleta.commands.RegisterAtletaToCompetition;
import model.atleta.commands.PayWithTarjeta;
import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;

import java.util.Date;

public class AtletaCrudServiceImpl implements AtletaCrudService {

	@Override
	public InscripcionDto registerAtletaToCompeticion(AtletaDto atleta, CompeticionDto competicion) {
		return new RegisterAtletaToCompetition(atleta, competicion).execute();
	}

	@Override
	public Date payWithTarjeta(AtletaDto atleta, CompeticionDto competicion) {
		 return new PayWithTarjeta(atleta,competicion).execute();
		//TODO
	}

}