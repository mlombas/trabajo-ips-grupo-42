package controller.competicion;

import java.util.List;

import model.competicion.ClasificacionDto;
import model.competicion.CompeticionDto;
import util.exceptions.ModelException;

public interface CompeticionCrudService {
	List<Integer> cargarTiempos(CompeticionDto comp);
	List<CompeticionDto> GetAllCompeticiones() throws ModelException;
	List<ClasificacionDto> GetClasificacion(CompeticionDto comp);
	List<ClasificacionDto> GetClasificacion(CompeticionDto competicion, String categoria);
}
