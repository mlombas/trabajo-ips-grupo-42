package controller.competicion;

import java.util.List;

import model.competicion.CompeticionDto;
import model.competicion.commands.GetAllCompeticiones;

public class CompeticionCrudServiceImpl implements CompeticionCrudService {

	@Override
	public List<CompeticionDto> GetAllCompeticiones() {
		return new GetAllCompeticiones().execute();
	}

}
