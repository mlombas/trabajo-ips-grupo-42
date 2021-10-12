package controller.atleta;

import java.util.Date;

import model.atleta.AtletaDto;
import model.atleta.TarjetaDto;
import model.atleta.commands.PayWithTarjeta;
import model.atleta.commands.RegisterAtletaToCompetition;
import model.competicion.CompeticionDto;

public class AtletaCrudServiceImpl implements AtletaCrudService {

	@Override
	public AtletaDto registerAtletaToCompeticion(AtletaDto atleta, CompeticionDto competicion) {
		return new RegisterAtletaToCompetition(atleta, competicion).execute();
	}

	@Override
	public Date payWithTarjeta(AtletaDto atleta, CompeticionDto competicion, TarjetaDto tarjeta) {
		 return new PayWithTarjeta(atleta,competicion,tarjeta).execute();
	}

}
