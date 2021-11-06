package controller.competicion;

import java.util.List;

import model.competicion.CompeticionDto;
<<<<<<< HEAD
import model.competicion.PosicionDto;
import model.competicion.commands.CargarTiempos;
=======
import model.competicion.ClasificacionDto;
>>>>>>> refs/heads/main
import model.competicion.commands.GetAllCompeticiones;
import model.competicion.commands.GetClasificacion;
import model.competicion.commands.GetClasificacionByCategoria;
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
<<<<<<< HEAD

	@Override
	public List<Integer> cargarTiempos(CompeticionDto comp) {
		return new CargarTiempos(comp).execute();
	}

	
	
=======
>>>>>>> refs/heads/main
}
