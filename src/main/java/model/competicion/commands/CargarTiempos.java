package model.competicion.commands;

import java.util.List;

import model.competicion.CompeticionDto;
import util.database.Database;

public class CargarTiempos {

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
	
	public List<Integer> execute(){
		
		checkCompeticion();
		
		return null;
		
	}

	private void checkCompeticion() {
		if(!competicion.estadoCarrera.equals("finalizada")) {
			throw new IllegalArgumentException("La competición no ha finalizado");
		}		
	}
	
	

}
