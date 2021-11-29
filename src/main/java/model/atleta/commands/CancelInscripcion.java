package model.atleta.commands;

import java.util.List;

import model.ModelFactory;
import model.competicion.CompeticionDto;
import model.inscripcion.EstadoInscripcion;
import model.inscripcion.InscripcionDto;
import util.database.Database;
import util.exceptions.ModelException;

public class CancelInscripcion {
	private static final String GETCOMPETICIONCOUTA = "select cuota from Competicion where id = ?";
	private static final String UPDATE = "UPDATE inscripcion SET estadoInscripcion = ?, devolver = ? WHERE emailAtleta = ? AND idCompeticion = ?";
	private String email;
	private String id;
	
	private Database db = Database.getInstance();

	public CancelInscripcion(String email, String idCompeticion) {
		this.email = email;
		this.id = idCompeticion;
	}
	
	public double execute() throws ModelException {
		InscripcionDto inscripcion = new InscripcionDto();
		inscripcion.idCompeticion = id;
		CompeticionDto competicion = ModelFactory.forCarreraCrudService().getCompeticionByInscripcion(inscripcion);
		double devolver = competicion.cuota;
		
		db.executeUpdate(UPDATE, EstadoInscripcion.CANCELADO, devolver, email, id);
		
		return devolver;
	}
}
