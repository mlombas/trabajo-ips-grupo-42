package controller.atleta;

import java.sql.Date;

import model.atleta.AtletaDto;
import model.atleta.TarjetaDto;
import model.atleta.commands.RegisterAtletaToCompetition;
import model.atleta.commands.PayWithTarjeta;
import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;
import util.AtletaNoValidoException;
import util.ModelException;


public class AtletaCrudServiceImpl implements AtletaCrudService {

	@Override
	public InscripcionDto registerAtletaToCompeticion(AtletaDto atleta, CompeticionDto competicion) throws AtletaNoValidoException, ModelException {
		return new RegisterAtletaToCompetition(atleta, competicion).execute();
	}

	@Override
	public Date payWithTarjeta(AtletaDto atleta, CompeticionDto competicion, TarjetaDto tarjeta) {
		 return new PayWithTarjeta(atleta,competicion,tarjeta).execute();
	}

}