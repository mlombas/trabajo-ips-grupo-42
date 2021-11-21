package model.competicion.commands;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import model.competicion.CompeticionDto;
import model.competicion.PuntoIntermedioDto;
import model.inscripcion.InscripcionDto;
import util.csv.CSVCreator;
import util.csv.CSVTable;
import util.database.Database;
import util.exceptions.ApplicationException;

public class CargarTiempos {
	
	private static final String GETINSCRIPCIONBYDORSAL = "select * from Inscripcion where idCompeticion = ? and dorsal = ? ";
	private static final String UPDATECLASIFICACION = "update Clasificacion set tiempoSalida = ?, tiempoLlegada = ? where idCompeticion = ? and emailAtleta = ? ";
	private static final String GETCOMPETICION = "select * from Competicion where id = ?";
	private static final String GETPUNTOSINTERMEDIOS = "select * from PuntoIntermedio where idCompeticion = ?";
	private static final String UPDATETIEMPOINTERMEDIOCLASIFICACION = "update PuntoIntermedioClasificacion set tiempo = ? where idCompeticion = ? and emailAtleta = ? and idPuntoIntermedio = ?" ;

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
		List<Integer> lista = new ArrayList<Integer>();
		
		int[] result = new int[2];
		
		result[0] = 0;
		result[1] = 0;
		
		List<CompeticionDto> competi = db.executeQueryPojo(CompeticionDto.class, GETCOMPETICION, competicion.id);
		
		checkCompeticion(competi);
		
		try {
			
			CSVTable<String, String> tabla = CSVCreator.read("src/main/java/util/Tiempos" + competicion.id +".csv",";");
			for (List<String> row : tabla) {
				String dorsal = row.get(0);
				
				List<InscripcionDto> inscripcion =  db.executeQueryPojo(InscripcionDto.class, GETINSCRIPCIONBYDORSAL, competicion.id, dorsal);
				
				List<PuntoIntermedioDto> puntosIntermedios = db.executeQueryPojo(PuntoIntermedioDto.class, GETPUNTOSINTERMEDIOS, competicion.id);
				
				if(inscripcion.size() > 0) {
					
					Integer tiempoSalida = checkTiempo(row.get(1));
					Integer tiempoLlegada = checkTiempo(row.get(2));
					
					db.executeUpdate(UPDATECLASIFICACION, tiempoSalida, tiempoLlegada, competicion.id, inscripcion.get(0).emailAtleta);
					
					int cont = 3;
					for(PuntoIntermedioDto puntoIntermedio : puntosIntermedios) {
						Integer tiempo = checkTiempo(row.get(cont));
						
						db.executeUpdate(UPDATETIEMPOINTERMEDIOCLASIFICACION, tiempo, competicion.id, inscripcion.get(0).emailAtleta, puntoIntermedio.id);
						
						cont++;
					}
					
					result[0] += 1;
					
				} else {
					
					result[1] += 1;
					
				}
				
				
			}
			
		} catch (FileNotFoundException e) {
			throw new ApplicationException("Fichero de tiempos no encontrado");
		}
		
		lista.add(result[0]);
		lista.add(result[1]);
		
		return lista;
		
	}

	private Integer checkTiempo(String tiempo) {
		if(tiempo.equals("-")) {
			return null;
		} 
		return Integer.valueOf(tiempo);
	}

	private void checkCompeticion(List<CompeticionDto> competicion) {
		if(competicion.size() == 0) {
			throw new ApplicationException("La competición no existe");
		}
		if(!competicion.get(0).estadoCarrera.equals("finalizada")) {
			throw new ApplicationException("La competición no ha finalizado");
		}		
		this.competicion = competicion.get(0);
	}
	
	

}
