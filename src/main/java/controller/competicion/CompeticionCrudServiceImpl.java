package controller.competicion;

import java.util.List;

import model.competicion.CompeticionDto;
import model.competicion.commands.GetAllCompeticiones;
import model.competicion.commands.GetClasificacion;
import model.inscripcion.InscripcionDto;

public class CompeticionCrudServiceImpl implements CompeticionCrudService {

	@Override
	public List<CompeticionDto> GetAllCompeticiones() {
		return new GetAllCompeticiones().execute();
	}

	@Override
	public List<InscripcionDto> GetClasificacion(CompeticionDto comp) {
		return new GetClasificacion(comp).execute();
	}

	
	
}
