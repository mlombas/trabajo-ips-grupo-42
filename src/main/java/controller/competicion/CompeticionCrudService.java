package controller.competicion;

import java.util.List;

import model.atleta.AtletaDto;
import model.competicion.CategoriaDto;
import model.competicion.ClasificacionDto;
import model.competicion.CompeticionDto;
import model.competicion.PlazoCancelacionDto;
import model.competicion.PlazoInscripcionDto;
import model.competicion.PuntoIntermedioClasficacionDto;
import model.competicion.PuntoIntermedioDto;
import model.inscripcion.InscripcionDto;
import util.exceptions.ModelException;

public interface CompeticionCrudService {
	
	List<Integer> cargarTiempos(CompeticionDto comp);

	List<CompeticionDto> GetAllCompeticiones() throws ModelException;
	
	List<ClasificacionDto> GetClasificacion(CompeticionDto comp);
	
	List<ClasificacionDto> GetClasificacion(CompeticionDto competicion, String categoria);
  
	List<CategoriaDto> GetCategoria(String competicionId);
	
	boolean addCompeticion(CompeticionDto competicion) throws ModelException;
  
	boolean removeCarrera(String competicionId) throws ModelException;
  
	List<Integer> generarDorsales(CompeticionDto comp);
	
	List<PlazoInscripcionDto> addPlazo (CompeticionDto comp, PlazoInscripcionDto plazo);
	
	void deletePlazosByIdCompetición(String competicionId);
	
	void checkPlazosByIdCompeticion(String competicionId);

	List<PlazoInscripcionDto> getAllPlazos (String competicionId);
  
	boolean addCategoria(CategoriaDto cat) throws ModelException;

	void deleteAllCategorias(String idCompeticion) throws ModelException;
	
	void updateCompeticion(CompeticionDto comp) throws ModelException;
	
	void addCategoriaGeneral(String idCompeticion) throws ModelException;
	
	List<CategoriaDto> getAllCategorias (String competicionId);

	int getPlazasLibres(CompeticionDto competicion) throws ModelException;

	void addPuntoIntermedio(PuntoIntermedioDto puntoIntermedio) throws ModelException;

	void deleteAllPuntosIntermedios(String id) throws ModelException;

	List<ClasificacionDto> GetClasificacionExtendida(CompeticionDto competicion);

	List<ClasificacionDto> GetClasificacionExtendida(CompeticionDto competicion, String categoria);

	int GetDistancia(CompeticionDto competicion) throws ModelException;

	int countPuntosIntermendios(CompeticionDto competicion) throws ModelException;

	List<PuntoIntermedioClasficacionDto> obtenerPuntosInt(CompeticionDto competicion, ClasificacionDto clasificado);

	CompeticionDto getCompeticionByInscripcion(InscripcionDto inscripcion);

	List<ClasificacionDto> getClasificacionUsuario(AtletaDto atleta, CompeticionDto competicion);

	List<ClasificacionDto> getClasificacionesByNombre(CompeticionDto competicion, AtletaDto atleta);

	List<ClasificacionDto> getClasificacionByDorsal(ClasificacionDto selectedAtleta, CompeticionDto competicion);


	List<PlazoCancelacionDto> getAllPlazosCancelacion(String id);

	List<PlazoCancelacionDto> addPlazoCancelacion(CompeticionDto comp, PlazoCancelacionDto plazo);

}
