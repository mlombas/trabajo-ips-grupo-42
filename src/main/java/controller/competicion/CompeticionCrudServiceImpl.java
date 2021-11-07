package controller.competicion;

import java.util.List;

import model.competicion.CompeticionDto;
import model.competicion.CategoriaDto;
import model.competicion.ClasificacionDto;
import model.competicion.commands.AddCompeticion;
import model.competicion.commands.GetAllCompeticiones;
import model.competicion.commands.GetCategorias;
import model.competicion.commands.GetClasificacion;
import model.competicion.commands.GetClasificacionByCategoria;
import model.competicion.commands.RemoveCompeticion;
import util.exceptions.ModelException;

public class CompeticionCrudServiceImpl implements CompeticionCrudService {

	@Override
	public List<CompeticionDto> GetAllCompeticiones() throws ModelException {
		return new GetAllCompeticiones().execute();
	}

	@Override
	public List<ClasificacionDto> GetClasificacion(CompeticionDto comp) {
		return new GetClasificacion(comp).execute();
	}

	@Override
	public List<ClasificacionDto> GetClasificacion(CompeticionDto competicion, String categoria) {
		return new GetClasificacionByCategoria(competicion, categoria).execute();
	}

	@Override
	public List<CategoriaDto> GetCategoria(String competicionId) {
		return new GetCategorias(competicionId).execute();
	}

  @Override
	public boolean addCompeticion(CompeticionDto competicion) throws ModelException {
		return new AddCompeticion(competicion).execute();
	}

	@Override
	public boolean removeCarrera(String competicionId) throws ModelException {
		return new RemoveCompeticion(competicionId).execute();
	}
}
