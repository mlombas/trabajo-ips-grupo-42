package controller.organizador;

import java.util.List;

import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;
import model.inscripcion.commands.GetAtletasForCompetition;
import util.ModelException;

public class OrganizadorCrudServiceImpl implements OrganizadorCrudService {

	@Override
	public List<InscripcionDto> getAtletasForCompetition(CompeticionDto competicion) throws ModelException {
		return new GetAtletasForCompetition(competicion).execute();
	}

}
