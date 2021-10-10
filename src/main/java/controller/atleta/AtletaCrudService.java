package controller.atleta;

import model.atleta.AtletaDto;
import model.competicion.CompeticionDto;

public interface AtletaCrudService {
	
	/**
	 * Permite al atleta inscribirse en una competicion para poder participar en ella.
	 * @param atleta -->  atleta que quiere inscribirse.
	 * @param competicion --> competicion en la que quiere participar.
	 * @return datos del atleta para crear el justificante.
	 */
	AtletaDto registerAtletaToCompeticion(AtletaDto atleta, CompeticionDto competicion);
	
}
