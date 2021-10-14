package model.competicion.commands;

import java.util.List;

import giis.demo.util.Database;
import model.competicion.CompeticionDto;
import model.competicion.PosicionDto;

public class GetClasificacion{

	private static final String OBTENER_RESULTADOS = "select i.posicion, a.sexo, i.nombreAtleta, i.tiempo, i.estadoInscripcion from Inscripcion i, Atleta a where idCompeticion = ? and a.email=i.emailAtleta order by i.posicion";

	private Database db = new Database();
	private String id_competicion;
		
	public GetClasificacion(CompeticionDto comp) {
		this.id_competicion = comp.id;
	}
	
	public List<PosicionDto> execute() {
		return db.executeQueryPojo(PosicionDto.class, OBTENER_RESULTADOS, id_competicion);
	}
	
	
	
}
