package controller.atleta;

import java.sql.Date;
import java.util.List;

import model.atleta.AtletaDto;
import model.atleta.TarjetaDto;
import model.atleta.commands.GetCompeticionesInscritas;
import model.atleta.commands.PayWithTarjeta;
import model.atleta.commands.RegisterAtletaToCompetition;
import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;


public class AtletaCrudServiceImpl implements AtletaCrudService {

	@Override
	public InscripcionDto registerAtletaToCompeticion(AtletaDto atleta, CompeticionDto competicion) {
		return new RegisterAtletaToCompetition(atleta, competicion).execute();
	}

	@Override
	public Date payWithTarjeta(AtletaDto atleta, CompeticionDto competicion, TarjetaDto tarjeta) {
		 return new PayWithTarjeta(atleta,competicion,tarjeta).execute();
	}

	@Override
	public List<InscripcionDto> getCompetionesInscritas(AtletaDto atleta) {
		return new GetCompeticionesInscritas(atleta).execute();
	}

}