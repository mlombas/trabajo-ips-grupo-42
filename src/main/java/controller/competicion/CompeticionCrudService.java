package controller.competicion;

import java.util.List;

import model.competicion.CompeticionDto;
import model.competicion.ClasificacionDto;

public interface CompeticionCrudService {
	List<CompeticionDto> GetAllCompeticiones();
	List<ClasificacionDto> GetClasificacion(CompeticionDto comp);
	List<ClasificacionDto> GetClasificacion(CompeticionDto competicion, String categoria);
}
