package model.competicion.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.competicion.PuntoIntermedioDto;
import util.database.Database;
import util.exceptions.ModelException;

public class AddPuntoIntermedio {

	private static final String ADD_PUNTO_INTERMEDIO = "insert into "
													 + "PuntoIntermedio(id, idCompeticion, distanciaSalida, tiempoMaximo) "
													 + "VALUES (?, ?, ?, ?)";
	
	private Database db = Database.getInstance();
	private PuntoIntermedioDto puntoIntermedio;
	
	public AddPuntoIntermedio(PuntoIntermedioDto puntoIntermedio) {
		this.puntoIntermedio = puntoIntermedio;
	}

	public void execute() throws ModelException {
		try {
			Connection c = db.getConnection();
			
			PreparedStatement pst = c.prepareStatement(ADD_PUNTO_INTERMEDIO);
			
			pst.setString(1, puntoIntermedio.id);
			pst.setString(2, puntoIntermedio.idCompeticion);
			pst.setInt(3, puntoIntermedio.distanciaSalida);
			pst.setInt(4, puntoIntermedio.tiempoMaximo);
			
			pst.executeUpdate();
			
			pst.close();
			c.close();
		}catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
	}
	
}
