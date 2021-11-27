package model.competicion.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.competicion.CompeticionDto;
import util.database.Database;
import util.exceptions.ModelException;

public class GetPlazasLibres {

	private static final String PLAZAS = "select plazas from Competicion where id = ?";
	private static final String PLAZAS_OCUPADAS = "select count(*) as plazas_ocupadas from Inscripcion WHERE idCompeticion = ? ";
	
	private Connection c = null;
	private Database db = Database.getInstance();
	
	private CompeticionDto competicion;
	
	public GetPlazasLibres(CompeticionDto competicion) {
		this.competicion = competicion;
	}

	public int execute() throws ModelException {
		try {
			c = db.getConnection();
			
			PreparedStatement pst1 = c.prepareStatement(PLAZAS);
			pst1.setString(1, competicion.id);
			ResultSet rs1 = pst1.executeQuery();
			
			PreparedStatement pst2 = c.prepareStatement(PLAZAS_OCUPADAS);
			pst2.setString(1, competicion.id);
			ResultSet rs2 = pst2.executeQuery();
			
			int plazasLibres = rs1.getInt("plazas") - rs2.getInt("plazas_ocupadas");
			
			rs1.close();
			rs2.close();
			pst1.close();
			pst2.close();
			
			c.close();
			
			return plazasLibres;
		} catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
	}
	
}