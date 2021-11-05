package controller.competicion;

import java.util.List;

import model.competicion.CompeticionDto;
import model.competicion.PosicionDto;

public interface CompeticionCrudService {
	List<CompeticionDto> GetAllCompeticiones();
	List<PosicionDto> GetClasificacion(CompeticionDto comp);
	List<PosicionDto> GetClasificacion(CompeticionDto competicion, String categoria);
	List<Integer> cargarTiempos(CompeticionDto comp);
}
