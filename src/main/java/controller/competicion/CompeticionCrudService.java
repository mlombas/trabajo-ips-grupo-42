package controller.competicion;

import java.util.List;

import model.competicion.CompeticionDto;
import util.exceptions.ModelException;
import model.competicion.CategoriaDto;
import model.competicion.ClasificacionDto;

public interface CompeticionCrudService {
	
	List<CompeticionDto> GetAllCompeticiones() throws ModelException;
	
	List<ClasificacionDto> GetClasificacion(CompeticionDto comp);
	
	List<ClasificacionDto> GetClasificacion(CompeticionDto competicion, String categoria);
  
	List<CategoriaDto> GetCategoria(String competicionId);
	
	boolean addCompeticion(CompeticionDto competicion) throws ModelException;

}
