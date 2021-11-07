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
	private static final String GETPLAZOSSOLAPADOS = "select * from Plazo where idCompeticion = ? and fechaInicio <= ? < fechaFin or fechaInicio < ? <= fechaFin";
	private static final String GETPLAZOSANTERIORES = "select * from Plazo where idCompeticion = ? and fechaFin <= ?";
	private static final String GETPLAZOSPOSTERIORES = "select * from Plazo where idCompeticion = ? and fechaFin >= ?";

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
		
		List<PlazoInscripcionDto> plazosSolapados = db.executeQueryPojo(PlazoInscripcionDto.class, GETPLAZOSSOLAPADOS, competicion.id, plazo.fechaInicio, plazo.fechaFin);
		
		checkPlazosSolapados(plazosSolapados);
		
		List<PlazoInscripcionDto> plazosAnteriores = db.executeQueryPojo(PlazoInscripcionDto.class, GETPLAZOSANTERIORES, competicion.id, plazo.fechaInicio);
		
		checkPlazoAnterior(plazosAnteriores);
		
		List<PlazoInscripcionDto> plazosPosteriores = db.executeQueryPojo(PlazoInscripcionDto.class, GETPLAZOSPOSTERIORES, competicion.id, plazo.fechaFin);
		
		checkPlazoPosterior(plazosPosteriores);
		
		db.executeUpdate(CREATEPLAZO, "Plazo" +  plazos.size() + 1 , competicion.id, plazo.fechaInicio, plazo.fechaFin, plazo.cuota);
		
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
			if(  plazosPosteriores.get(0).fechaInicio != plazo.fechaFin) {
				throw new ApplicationException("El plazo deja un hueco entre él y el plazo " +  plazosPosteriores.get(0).id);
			}
			
		}
		
	}

	private void checkPlazoAnterior(List<PlazoInscripcionDto> plazosAnteriores) {
		if (plazosAnteriores.size() != 0) {
			if( plazosAnteriores.get(0).fechaFin != plazo.fechaInicio) {
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
		if (!competicion.get(0).estadoCarrera.equals("inscripción")) {
			throw new ApplicationException("La competición ya no está en estado inscripción");
		}
		this.competicion = competicion.get(0);
	}

}
