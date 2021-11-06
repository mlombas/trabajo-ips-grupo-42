package controller.competicion;

import java.util.List;

import model.competicion.CompeticionDto;
import util.exceptions.ModelException;
import model.competicion.ClasificacionDto;

public interface CompeticionCrudService {
	List<CompeticionDto> GetAllCompeticiones();
	List<PosicionDto> GetClasificacion(CompeticionDto comp);
	List<PosicionDto> GetClasificacion(CompeticionDto competicion, String categoria);
	List<Integer> cargarTiempos(CompeticionDto comp);
	List<CompeticionDto> GetAllCompeticiones() throws ModelException;
	List<ClasificacionDto> GetClasificacion(CompeticionDto comp);
	List<ClasificacionDto> GetClasificacion(CompeticionDto competicion, String categoria);
}
