package model.competicion.commands;

import java.util.ArrayList;
import java.util.List;

import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;
import util.database.Database;
import util.exceptions.ApplicationException;

public class GenerarDorsales {
	
	private static final String GETINSCRIPCIONBYCOMPETICIONINCSRITO = "select * from Inscripcion where idCompeticion = ? and estadoInscripcion = 'INSCRITO' order by fechaInscripcion";
	private static final String GETCOMPETICION = "select * from Competicion where id = ?";
	private static final String UPDATEINSCRIPCIONMATRICULA = "update Inscripcion set dorsal = ? where idCompeticion = ? and emailAtleta = ? ";
	private static final String CREATECLASIFICACION = "insert into Clasificacion(idCompeticion, emailAtleta, dorsal) values (?,?,?)";
	private static final String UPDATEESTADOCARRERACOMPETICION = "update Competicion set estadoCarrera = ? where id = ?";

	private CompeticionDto competicion;
	
	private Database db = Database.getInstance();

	public GenerarDorsales(CompeticionDto comp) {
		this.competicion = comp;
		checkArguments();
	}

	private void checkArguments() {
		if(competicion == null) {
			throw new IllegalArgumentException("La competición no es válida");
		}
	}
	
	public List<Integer> execute() {
		List<Integer> lista = new ArrayList<Integer>();
		
		int count = 0;
		
		List<CompeticionDto> competi = db.executeQueryPojo(CompeticionDto.class, GETCOMPETICION, competicion.id);
		
		checkCompeticion(competi);
		
		List<InscripcionDto> inscripciones = db.executeQueryPojo(InscripcionDto.class, GETINSCRIPCIONBYCOMPETICIONINCSRITO, competicion.id);
		
		int numeroDorsal = competicion.dorsalesReservados + 1;
		
		for(InscripcionDto inscripcion : inscripciones) {
			
			db.executeUpdate(UPDATEINSCRIPCIONMATRICULA, numeroDorsal , inscripcion.idCompeticion, inscripcion.emailAtleta);

			db.executeUpdate(CREATECLASIFICACION, inscripcion.idCompeticion, inscripcion.emailAtleta, numeroDorsal);
			numeroDorsal++;
			count++;
			
		}
		
		db.executeUpdate(UPDATEESTADOCARRERACOMPETICION, "pre-carrera", competicion.id);
		
		lista.add(count);
		
		return lista;
		
	}

	private void checkCompeticion(List<CompeticionDto> competicion) {
		if(competicion.size() == 0) {
			throw new ApplicationException("La competición no existe");
		}
		if(!competicion.get(0).estadoCarrera.equals("inscripcion_cerrada")) {
			throw new ApplicationException("La competición no acaba de cerrar sus periodos de inscripción");
		}		
		this.competicion = competicion.get(0);
	}
	
	

}
