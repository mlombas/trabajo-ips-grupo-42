package controller.atleta;

import java.time.LocalDate;
import java.util.List;

import model.atleta.AtletaDto;
import model.atleta.TarjetaDto;
import model.atleta.commands.GetCompeticionesInscritas;
import model.atleta.commands.PayWithTarjeta;
import model.atleta.commands.PendingPayWithTransaccion;
import model.atleta.commands.RegisterAtletaToCompetition;
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
	public LocalDate payWithTarjeta(InscripcionDto inscripcion, TarjetaDto tarjeta) {
		 return new PayWithTarjeta(inscripcion,tarjeta).execute();
	}

	@Override
	public List<InscripcionDto> getCompetionesInscritas(AtletaDto atleta) {
		return new GetCompeticionesInscritas(atleta).execute();
	}

	@Override
	public String pendingPayWithTransaccion(InscripcionDto inscripcion) {
		return new PendingPayWithTransaccion(inscripcion).execute();
	}

}