package model.atleta.commands;

import java.time.LocalDate;
import java.util.Optional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.atleta.AtletaDto;
import util.database.Database;
import util.exceptions.ModelException;

public class FindAtletaByEmail {

	private static final String FIND_BY_EMAIL =
			"SELECT * " +
			"FROM atleta " +
			"WHERE email = ?";
		
	private Database db = Database.getInstance();
	private String email;
	
	public FindAtletaByEmail(String email) {
		this.email = email;
	}
	
	public Optional<AtletaDto> execute() throws ModelException {
		AtletaDto atleta = new AtletaDto();

		try {
			Connection c = db.getConnection();
			
			PreparedStatement pst = c.prepareStatement(FIND_BY_EMAIL);
			pst.setString(1, email);
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				atleta.dni = rs.getString("dni");
				atleta.email = rs.getString("email");
				atleta.fechaNacimiento = LocalDate.parse(rs.getString("fechaNacimiento"));
				atleta.nombre = rs.getString("nombre");
				atleta.sexo = rs.getString("sexo");
			} else {
				return Optional.empty();
			}
			
			rs.close();
			pst.close();
			c.close();
		}catch(SQLException e) {
			throw new ModelException(e.getMessage());
		}
		
		return Optional.ofNullable(atleta);
	}
	
}
