package model.competicion.commands;

import java.util.List;

import model.competicion.CompeticionDto;
import model.competicion.PlazoInscripcionDto;
import util.database.Database;
import util.exceptions.ApplicationException;

public class AddPlazo {

	private static final String GETCOMPETICION = "select * from Competicion where id = ?";
	private static final String CREATEPLAZO = "insert into Plazo(id, idCompeticion, fechaInicio, fechaFin, cuota) values (?,?,?,?,?)";
	private static final String GETALLPLAZOS = "select * from Plazo where idCompeticion = ?";
	private static final String GETPLAZOSSOLAPADOS = "select * from Plazo where idCompeticion = ? and (((fechaInicio <= ?) and ( ? < fechaFin)) or ((fechaInicio < ?) and ( ? <= fechaFin)))";
	private static final String GETPLAZOSANTERIORES = "select * from Plazo where idCompeticion = ? and fechaFin <= ? order by fechaFin desc";
	private static final String GETPLAZOSPOSTERIORES = "select * from Plazo where idCompeticion = ? and fechaFin >= ? order by fechaFin";

	private CompeticionDto competicion;
	private PlazoInscripcionDto plazo;

	private Database db = Database.getInstance();

	public AddPlazo(CompeticionDto comp, PlazoInscripcionDto plazo) {
		this.competicion = comp;
		this.plazo = plazo;
		checkArguments();
	}

	private void checkArguments() {
		if (competicion == null) {
			throw new IllegalArgumentException("La competición no es válida");
		}
		if (plazo == null) {
			throw new IllegalArgumentException("El plazo no es válido");
		}

	}

	public List<PlazoInscripcionDto> execute() {
		
		List<CompeticionDto> competi = db.executeQueryPojo(CompeticionDto.class, GETCOMPETICION, competicion.id);

		checkCompeticion(competi);
		
		List<PlazoInscripcionDto> plazos = db.executeQueryPojo(PlazoInscripcionDto.class, GETALLPLAZOS, competicion.id);
		
		checkAllPlazos(plazos);
		
		List<PlazoInscripcionDto> plazosSolapados = db.executeQueryPojo(PlazoInscripcionDto.class, GETPLAZOSSOLAPADOS, competicion.id, plazo.fechaInicio, plazo.fechaInicio, plazo.fechaFin, plazo.fechaFin);
		
		checkPlazosSolapados(plazosSolapados);
		
		List<PlazoInscripcionDto> plazosAnteriores = db.executeQueryPojo(PlazoInscripcionDto.class, GETPLAZOSANTERIORES, competicion.id, plazo.fechaInicio);
		
		checkPlazoAnterior(plazosAnteriores);
		
		List<PlazoInscripcionDto> plazosPosteriores = db.executeQueryPojo(PlazoInscripcionDto.class, GETPLAZOSPOSTERIORES, competicion.id, plazo.fechaFin);
		
		checkPlazoPosterior(plazosPosteriores);
		
		int n =  plazos.size() + 1;
		
		db.executeUpdate(CREATEPLAZO, "Plazo" + n, competicion.id, plazo.fechaInicio, plazo.fechaFin, plazo.cuota);
		
		plazos = db.executeQueryPojo(PlazoInscripcionDto.class, GETALLPLAZOS, competicion.id);
		
		return plazos;
	}

	private void checkAllPlazos(List<PlazoInscripcionDto> plazos) {
		if (plazos.size() == 4) {
			throw new ApplicationException("Has alcanzado el máximo de plazos posibles");
		}
	}

	private void checkPlazoPosterior(List<PlazoInscripcionDto> plazosPosteriores) {
		if ( plazosPosteriores.size() != 0) {
			if(  plazosPosteriores.get(0).fechaInicio.isEqual(plazo.fechaFin)) {
				throw new ApplicationException("El plazo deja un hueco entre él y el plazo " +  plazosPosteriores.get(0).id);
			}
			
		}
		
	}

	private void checkPlazoAnterior(List<PlazoInscripcionDto> plazosAnteriores) {
		if (plazosAnteriores.size() != 0) {
			if( !plazosAnteriores.get(0).fechaFin.isEqual(plazo.fechaInicio)) {
				throw new ApplicationException("El plazo deja un hueco entre él y el plazo " + plazosAnteriores.get(0).id);
			}
			
		}
		
	}

	private void checkPlazosSolapados(List<PlazoInscripcionDto> plazosSolapados) {
		if (plazosSolapados.size() != 0) {
			throw new ApplicationException("El plazo solapa con el plazo " + plazosSolapados.get(0).id);
		}
		
	}

	private void checkCompeticion(List<CompeticionDto> competicion) {
		if (competicion.size() == 0) {
			throw new ApplicationException("La competición no existe");
		}
		if (!competicion.get(0).estadoCarrera.equals("creándose")) {
			throw new ApplicationException("La competición ya no está en estado inscripción");
		}
		this.competicion = competicion.get(0);
		
		if(this.competicion.fecha.isBefore(plazo.fechaInicio) || this.competicion.fecha.isBefore(plazo.fechaFin)) {
			throw new ApplicationException("Las fecha de la competición es antes que las del plazo");
		}
	}

}
