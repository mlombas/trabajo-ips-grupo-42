package model.atleta.commands;

import java.time.LocalDate;
import java.util.List;

import model.ModelFactory;
import model.competicion.CompeticionDto;
import model.competicion.PlazoCancelacionDto;
import model.competicion.PlazoInscripcionDto;
import model.inscripcion.EstadoInscripcion;
import model.inscripcion.InscripcionDto;
import util.database.Database;
import util.exceptions.ModelException;

public class CancelInscripcion {
	private static final String GETINSCRIPCION = "select * from Inscripcion where emailAtleta = ? and idCompeticion = ?";
	private static final String GETPLAZO = "select * from Plazo where idCompeticion = ? and fechaInicio <= ? and fechaFin > ?";
	private static final String GETPLAZOCANCELACION = "select * from PlazoCancelacion where idCompeticion = ? and fechaInicio <= ? and fechaFin > ?";
	private static final String UPDATE = "UPDATE inscripcion SET estadoInscripcion = ?, devolver = ? WHERE emailAtleta = ? AND idCompeticion = ?";
	private String email;
	private String id;
	
	private Database db = Database.getInstance();

	public CancelInscripcion(String email, String idCompeticion) {
		this.email = email;
		this.id = idCompeticion;
	}
	
	public double execute() throws ModelException {
		InscripcionDto inscripcion = getInscripcion();
		if(inscripcion.estadoInscripcion == EstadoInscripcion.PARTICIPADO)
			throw new ModelException("Esta competición ya se ha disputado");
		
		double devolver = getDevolver(inscripcion.fechaInscripcion);
		
		db.executeUpdate(UPDATE, EstadoInscripcion.CANCELADO, devolver, email, id);
		
		return devolver;
	}
	
	private InscripcionDto getInscripcion() throws ModelException {
		List<InscripcionDto> list = db.executeQueryPojo(InscripcionDto.class, GETINSCRIPCION, email, id);
		if(list.size() == 0)
			throw new ModelException("La inscripción no existe");
		
		return list.get(0);
	}
	
	private double getDevolver(LocalDate date) throws ModelException {
		List<PlazoInscripcionDto> lpi = db.executeQueryPojo(PlazoInscripcionDto.class, GETPLAZO, id, date, date);
		if(lpi.size() == 0)
			throw new ModelException("No hay plazo");
		
		PlazoInscripcionDto plazoInscripcion = lpi.get(0);
		
		List<PlazoCancelacionDto> lpc = db.executeQueryPojo(PlazoCancelacionDto.class, GETPLAZOCANCELACION, id, date, date);
		if(lpc.size() == 0)
			throw new ModelException("No hay plazo de cancelacion");
		
		PlazoCancelacionDto plazoCancelacion = lpc.get(0);
		
		return plazoInscripcion.cuota * plazoCancelacion.porcentaje / 100.0;
	}
}
