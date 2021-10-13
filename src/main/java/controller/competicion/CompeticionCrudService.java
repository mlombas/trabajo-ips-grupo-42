package controller.competicion;

import java.util.List;

import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;

public interface CompeticionCrudService {
	List<CompeticionDto> GetAllCompeticiones();
	List<InscripcionDto> GetClasificacion(CompeticionDto comp);
	List<InscripcionDto> GetClasificacion(CompeticionDto competicion, String categoria);
}
