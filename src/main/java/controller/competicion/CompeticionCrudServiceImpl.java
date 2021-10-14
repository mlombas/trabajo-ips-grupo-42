package controller.competicion;

import java.util.List;

import model.competicion.CompeticionDto;
import model.competicion.PosicionDto;
import model.competicion.commands.GetAllCompeticiones;
import model.competicion.commands.GetClasificacion;
import model.competicion.commands.GetClasificacionByCategoria;

public class CompeticionCrudServiceImpl implements CompeticionCrudService {

	@Override
	public List<CompeticionDto> GetAllCompeticiones() {
		return new GetAllCompeticiones().execute();
	}

	@Override
	public List<PosicionDto> GetClasificacion(CompeticionDto comp) {
		return new GetClasificacion(comp).execute();
	}

	@Override
	public List<PosicionDto> GetClasificacion(CompeticionDto competicion, String categoria) {
		return new GetClasificacionByCategoria(competicion, categoria).execute();
	}

	
	
}
