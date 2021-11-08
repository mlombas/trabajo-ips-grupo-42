package model.competicion.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.competicion.CompeticionDto;
import util.database.Database;
import util.exceptions.ModelException;

public class AddCompeticion {

	private static final String ADD_COMPETICION = "insert into "
			+ "Competicion(id, nombreCarrera, tipoCarrera, distancia, fecha, plazas, dorsalesReservados, descripcion, estadoCarrera) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private CompeticionDto competicion;
	private Database db = Database.getInstance();
	
	public AddCompeticion(CompeticionDto competicion) {
		this.competicion = competicion;
	}

	public boolean execute() throws ModelException {
		try {
			Connection c = db.getConnection();
			
			PreparedStatement pst = c.prepareStatement(ADD_COMPETICION);
			
			pst.setString(1, competicion.id);
			pst.setString(2, competicion.nombreCarrera);
			pst.setString(3, competicion.tipoCarrera);
			pst.setInt(4, competicion.distancia);
			pst.setString(5, competicion.fecha.toString());
			pst.setInt(6, competicion.plazas);
			pst.setInt(7, competicion.dorsalesReservados);
			pst.setString(8, competicion.descripcion);
			pst.setString(9, competicion.estadoCarrera);
			
			int ans = pst.executeUpdate();
			
			pst.close();
			c.close();
			
			return ans == 1;
		}catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
	}
	
}
