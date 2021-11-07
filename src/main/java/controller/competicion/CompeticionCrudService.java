package controller.competicion;

import java.util.List;

import model.competicion.CategoriaDto;
import model.competicion.ClasificacionDto;
import model.competicion.CompeticionDto;
import model.competicion.PlazoInscripcionDto;
import util.exceptions.ModelException;


public interface CompeticionCrudService {
	List<Integer> cargarTiempos(CompeticionDto comp);

	List<CompeticionDto> GetAllCompeticiones() throws ModelException;
	
	List<ClasificacionDto> GetClasificacion(CompeticionDto comp);
	
	List<ClasificacionDto> GetClasificacion(CompeticionDto competicion, String categoria);
  
	List<CategoriaDto> GetCategoria(String competicionId);
	
	boolean addCompeticion(CompeticionDto competicion) throws ModelException;
	
	List<Integer> generarDorsales(CompeticionDto comp);
	
	List<PlazoInscripcionDto> addPlazo (CompeticionDto comp, PlazoInscripcionDto plazo);

}
