package controller.atleta;

import java.util.Date;

import model.atleta.AtletaDto;
import model.atleta.TarjetaDto;
import model.competicion.CompeticionDto;

public interface AtletaCrudService {
	
	/**
	 * Permite al atleta inscribirse en una competicion para poder participar en ella.
	 * @param atleta -->  atleta que quiere inscribirse.
	 * @param competicion --> competicion en la que quiere participar.
	 * @return datos del atleta para crear el justificante.
	 */
	AtletaDto registerAtletaToCompeticion(AtletaDto atleta, CompeticionDto competicion);
	
	
	/**
	 * Permite al atleta pagar con tarjeta la cuota de una competicion para poder participar en ella.
	 * @param atleta -->  atleta que quiere inscribirse.
	 * @param competicion --> competicion en la que quiere participar.
	 * @return la fecha de la relización del pago
	 */
	Date payWithTarjeta(AtletaDto atleta, CompeticionDto competicion, TarjetaDto tarjeta);
	//TODO
	
}
