package controller.organizador;

import java.util.List;

import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;
import util.exceptions.ModelException;

public interface OrganizadorCrudService {
	
	/**
	 * Permite al organizador saber cuál es el estado de las inscripciones.
	 * @param competicion --> competicion en la que queremos ver los datos.
	 * @return datos de los atletas registrados en la competición determinada.
	 * @throws ModelException --> cuando sale algo mal en el modelo...
	 */
	List<InscripcionDto> getAtletasForCompetition(CompeticionDto competicion) throws ModelException;
	
}
