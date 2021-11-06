package model.competicion.commands;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;
import util.csv.CSVCreator;
import util.csv.CSVTable;
import util.database.Database;
import util.exceptions.ApplicationException;

public class CargarTiempos {
	
	private static final String GETINSCRIPCIONBYDORSAL = "select * from Inscripcion where idCompeticion = ? and dorsal = ? ";
	private static final String UPDATECLASIFICACION = "update Clasificacion set tiempoSalida = ?, tiempoLlegada = ? where idCompeticion = ? and emailAtleta = ? ";
	private static final String GETCOMPETICION = "select * from Competicion where id = ?";

	private CompeticionDto competicion;
	
	private Database db = Database.getInstance();

	public CargarTiempos(CompeticionDto comp) {
		this.competicion = comp;
		checkArguments();
	}

	private void checkArguments() {
		if(competicion == null) {
			throw new IllegalArgumentException("La competición no es válida");
		}
	}
	
	public List<Integer> execute() {
		
		List<Integer> result = new ArrayList<Integer>();
		
		result.add(0, 0);
		result.add(1, 0);
		
		List<CompeticionDto> competi = db.executeQueryPojo(CompeticionDto.class, GETCOMPETICION, competicion.id);
		
		checkCompeticion(competi);
		
		try {
			
			CSVTable<String, String> tabla = CSVCreator.read("src/main/java/util/Tiempos" + competicion.id +".csv");
			for (List<String> row : tabla) {
				String dorsal = row.get(0);
				
				List<InscripcionDto> inscripcion =  db.executeQueryPojo(InscripcionDto.class, GETINSCRIPCIONBYDORSAL, competicion.id, dorsal);
				
				if(inscripcion.size() > 0) {
					int tiempoSalida = Integer.valueOf(row.get(1));
					int tiempoLlegada = Integer.valueOf(row.get(2));
					
					db.executeUpdate(UPDATECLASIFICACION, tiempoSalida, tiempoLlegada, competicion.id, inscripcion.get(0).emailAtleta);
					
					result.add(0, result.get(0) + 1);
				} else {
					result.add(1, result.get(1) + 1);
				}
				
				
			}
			
		} catch (FileNotFoundException e) {
			throw new ApplicationException("Fichero de tiempos no encontrado");
		}
		
		return result;
		
	}

	private void checkCompeticion(List<CompeticionDto> competicion) {
		if(competicion.size() == 0) {
			throw new IllegalArgumentException("La competición no existe");
		}
		if(!competicion.get(0).estadoCarrera.equals("finalizada")) {
			throw new IllegalArgumentException("La competición no ha finalizado");
		}		
		this.competicion = competicion.get(0);
	}
	
	

}
