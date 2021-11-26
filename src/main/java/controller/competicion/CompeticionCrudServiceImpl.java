package controller.competicion;

import java.util.List;

import model.competicion.CategoriaDto;
import model.competicion.ClasificacionDto;
import model.competicion.ClasificacionExtendidaDto;
import model.competicion.CompeticionDto;
import model.competicion.PlazoInscripcionDto;
import model.competicion.PuntoIntermedioDto;
import model.competicion.commands.AddCategoria;
import model.competicion.commands.AddCategoriaGeneral;
import model.competicion.commands.AddCompeticion;
import model.competicion.commands.AddPlazo;
import model.competicion.commands.AddPuntoIntermedio;
import model.competicion.commands.CargarTiempos;
import model.competicion.commands.CheckPlazosByIdCompeticion;
import model.competicion.commands.DeletePlazosByIdCompetición;
import model.competicion.commands.DeleteAllCategorias;
import model.competicion.commands.DeleteAllPuntosIntermedios;
import model.competicion.commands.GenerarDorsales;
import model.competicion.commands.GetAllCategorias;
import model.competicion.commands.GetAllCompeticiones;
import model.competicion.commands.GetAllPlazos;
import model.competicion.commands.GetNombresCategorias;
import model.competicion.commands.GetClasificacion;
import model.competicion.commands.GetClasificacionByCategoria;
import model.competicion.commands.GetClasificacionExtendida;
import model.competicion.commands.GetClasificacionExtendidaByCategoria;
import model.competicion.commands.GetDistancia;
import model.competicion.commands.GetPlazasLibres;
import model.competicion.commands.RemoveCompeticion;
import model.competicion.commands.UpdateCompeticion;
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
		return new GetNombresCategorias(competicionId).execute();
	}

	@Override
	public boolean addCompeticion(CompeticionDto competicion) throws ModelException {
		return new AddCompeticion(competicion).execute();
	}

	@Override
	public boolean removeCarrera(String competicionId) throws ModelException {
		return new RemoveCompeticion(competicionId).execute();
  }
  
	@Override
	public List<Integer> cargarTiempos(CompeticionDto comp) {
		return new CargarTiempos(comp).execute();
	}

	@Override
	public List<Integer> generarDorsales(CompeticionDto comp) {
		return new GenerarDorsales(comp).execute();
	}

	@Override
	public List<PlazoInscripcionDto> addPlazo(CompeticionDto comp, PlazoInscripcionDto plazo) {
		return new AddPlazo(comp,plazo).execute();
	}

	@Override
	public void deletePlazosByIdCompetición(String competicionId) {
		new DeletePlazosByIdCompetición(competicionId).execute();
	}

	@Override
	public void checkPlazosByIdCompeticion(String competicionId) {
		new CheckPlazosByIdCompeticion(competicionId).execute();
		
	}

	@Override
	public List<PlazoInscripcionDto> getAllPlazos(String competicionId) {
		return new GetAllPlazos(competicionId).execute();
	}
	
	@Override
	public boolean addCategoria(CategoriaDto cat) throws ModelException {
		return new AddCategoria(cat).execute();
	}

	@Override
	public void deleteAllCategorias(String idCompeticion) throws ModelException {
		 new DeleteAllCategorias(idCompeticion).execute();
		
	}

	@Override
	public void updateCompeticion(CompeticionDto comp) throws ModelException {
		new UpdateCompeticion(comp).execute();
	}

	@Override
	public void addCategoriaGeneral(String idCompeticion) throws ModelException {
		new AddCategoriaGeneral(idCompeticion).execute();
		
	}

	@Override
	public int getPlazasLibres(CompeticionDto competicion) throws ModelException {
		return new GetPlazasLibres(competicion).execute();
  }

  @Override
	public List<CategoriaDto> getAllCategorias(String competicionId) {
		return new GetAllCategorias(competicionId).execute();
	}

	@Override
	public void addPuntoIntermedio(PuntoIntermedioDto puntoIntermedio) throws ModelException {
		new AddPuntoIntermedio(puntoIntermedio).execute();
	}
	
	@Override
	public void deleteAllPuntosIntermedios(String id) throws ModelException {
		new DeleteAllPuntosIntermedios(id).execute();

	}

	@Override
	public List<ClasificacionExtendidaDto> GetClasificacionExtendida(CompeticionDto competicion) {
		return new GetClasificacionExtendida(competicion).execute();
	}

	@Override
	public List<ClasificacionExtendidaDto> GetClasificacionExtendida(CompeticionDto competicion, String categoria) {
		return new GetClasificacionExtendidaByCategoria(competicion, categoria).execute();
	}

	@Override
	public int GetDistancia(CompeticionDto competicion) throws ModelException {
		return new GetDistancia(competicion).execute();
	}
  
}
