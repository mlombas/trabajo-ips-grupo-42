package controller.atleta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import model.atleta.AtletaDto;
import model.atleta.TarjetaDto;
import model.atleta.commands.AddAtleta;
import model.atleta.commands.CancelInscripcion;
import model.atleta.commands.FindAtletaByEmail;
import model.atleta.commands.GetAtletaByInscripcion;
import model.atleta.commands.GetCompeticionesInscritas;
import model.atleta.commands.PayWithTarjeta;
import model.atleta.commands.PendingPayWithTransaccion;
import model.atleta.commands.RegisterAtletaToCompetition;
import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;
import model.inscripcion.commands.ProcessTransaction;
import util.exceptions.AtletaNoValidoException;
import util.exceptions.ModelException;

public class AtletaCrudServiceImpl implements AtletaCrudService {

	@Override
	public void addAtleta(AtletaDto atleta) throws AtletaNoValidoException, ModelException {
		new AddAtleta(atleta).execute();
	}

	@Override
	public InscripcionDto registerAtletaToCompeticion(AtletaDto atleta, CompeticionDto competicion)
			throws AtletaNoValidoException, ModelException {
		return new RegisterAtletaToCompetition(competicion).execute(atleta);
	}

	@Override
	public LocalDate payWithTarjeta(InscripcionDto inscripcion, TarjetaDto tarjeta) {
		return new PayWithTarjeta(inscripcion, tarjeta).execute();
	}

	@Override
	public List<InscripcionDto> getCompetionesInscritas(AtletaDto atleta) {
		return new GetCompeticionesInscritas(atleta).execute();
	}

	@Override
	public String pendingPayWithTransaccion(InscripcionDto inscripcion) {
		return new PendingPayWithTransaccion(inscripcion).execute();
	}

	@Override
	public Optional<AtletaDto> findByAtletaEmail(String email) throws ModelException {
		return new FindAtletaByEmail(email).execute();
	}

	@Override
	public InscripcionDto processTransaction(String email, String code, double amount, LocalDateTime dt, String id)
			throws ModelException {
		return new ProcessTransaction(email, code, amount, dt, id).execute();
	}
	
	@Override
	public double cancelInscripcion(String email, String idCompeticion)
			throws ModelException {
		return new CancelInscripcion(email, idCompeticion).execute();
	}

	@Override
	public AtletaDto getAtletaByInscripcion(InscripcionDto inscripcion) {
		return new GetAtletaByInscripcion(inscripcion).execute();
	}

	@Override
	public void registerAtletasToCompetition(List<AtletaDto> atletas, CompeticionDto competicion)
			throws ModelException, AtletaNoValidoException {
		new RegisterAtletaToCompetition(competicion).execute(atletas);
	}

}