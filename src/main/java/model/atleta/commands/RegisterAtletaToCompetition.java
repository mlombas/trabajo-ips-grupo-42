package model.atleta.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Command;
import model.atleta.AtletaDto;
import model.competicion.CompeticionDto;

public class RegisterAtletaToCompetition implements Command {

	private static final String SQL = "";
	
	private AtletaDto atleta;
	private CompeticionDto competicion;
	
	public RegisterAtletaToCompetition(AtletaDto atleta, CompeticionDto competicion) {
		this.atleta = atleta;
		this.competicion = competicion;
	}

	public AtletaDto execute() {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		// TODO check: 
		//		1. No inscribirse dos veces a la misma competación.
		//		2. esté abierto el plazo de inscripción.
		//		3. existan plazas libres.
		checkCanEnroll();
		
//		try {
//			// TODO
//		}catch(SQLException e) {
//		}
		
		return atleta;
	}

	private void checkCanEnroll() {
		// TODO Auto-generated method stub
		
	}

}
