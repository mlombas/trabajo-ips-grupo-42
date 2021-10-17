
package controller.atleta;

import java.sql.Date;
import java.util.List;

import model.atleta.AtletaDto;
import model.atleta.TarjetaDto;
import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;
import util.AtletaNoValidoException;
import util.ModelException;


public interface AtletaCrudService {
	
	/**
	 * Permite al atleta inscribirse en una competicion para poder participar en ella.
	 * @param atleta -->  atleta que quiere inscribirse.
	 * @param competicion --> competicion en la que quiere participar.
	 * @return datos del atleta para crear el justificante.
	 * @throws AtletaNoValidoException --> si el atleta no es valido...
	 * @throws ModelException --> cuando sale algo mal en el modelo...
	 */
	InscripcionDto registerAtletaToCompeticion(AtletaDto atleta, CompeticionDto competicion) throws AtletaNoValidoException, ModelException;
	
	/**
	 * Permite al atleta pagar con tarjeta la cuota de una competicion para poder participar en ella.
	 * @param atleta -->  atleta que quiere inscribirse.
	 * @param competicion --> competicion en la que quiere participar.
	 * @return la fecha de la relización del pago
	 */
	Date payWithTarjeta(InscripcionDto inscripcion, TarjetaDto tarjeta);

	/**
	 * Permite al atleta visualizar todas las competiciones en las que se ha inscrito
	 * @param atleta -->  atleta que quiere ver las competiciones.
	 * @return unas lista con las competiciones en las que está inscrito.
	 */
	List<InscripcionDto> getCompetionesInscritas(AtletaDto atleta);
	
}