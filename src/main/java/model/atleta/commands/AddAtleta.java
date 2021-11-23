package model.atleta.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.atleta.AtletaDto;
import util.database.Database;
import util.exceptions.AtletaNoValidoException;
import util.exceptions.ModelException;

public class AddAtleta {
		
	private static final String ADD_ATLETA = "insert into Atleta(dni, email, nombre, fechaNacimiento, sexo, club) VALUES (?, ?, ?, ?, ?, ?)";
	
	private AtletaDto atleta;	
	private Database db = Database.getInstance();
	
	public AddAtleta(AtletaDto atleta) {
		this.atleta = atleta;
	}

	public void execute() throws AtletaNoValidoException, ModelException {
		try {
			Connection c = db.getConnection();
			
			PreparedStatement pst = c.prepareStatement(ADD_ATLETA);
			
			pst.setString(1, atleta.dni);
			pst.setString(2, atleta.email);
			pst.setString(3, atleta.nombre);
			pst.setString(4, atleta.fechaNacimiento.toString());
			pst.setString(5, atleta.sexo);
			pst.setString(6, atleta.club);
			
			pst.executeUpdate();
			
			pst.close();
			c.close();
		}catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
	}
	
}
