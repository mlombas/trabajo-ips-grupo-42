package model.inscripcion.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import bank_mockup.Bank;
import model.inscripcion.InscripcionDto;
import util.database.Database;
import util.exceptions.ModelException;

public class ProcessTransaction {

	private static final String GET_INSCRIPCION = "SELECT * FROM inscripcion WHERE emailAtleta = ? AND idCompeticion = ?";
	private static final String UPDATE = "UPDATE inscripcion SET estadoInscripcion = ?, devolver = ? WHERE emailAtleta = ? AND idCompeticion = ?";
	
	private String email;
	private String code;
	private double amount;
	private String id;
	
	private Database db = Database.getInstance();

	private InscripcionDto inscripcion;

	private LocalDate date;
	
	private Bank bank = new Bank();

	public ProcessTransaction(String email, String code, double amount, LocalDateTime date, String id) {
		this.email = email;
		this.code = code;
		this.amount = amount;
		this.date = date.toLocalDate();
		this.id = id;
	}

	public InscripcionDto execute() throws ModelException {
		List<InscripcionDto> l = db.executeQueryPojo(InscripcionDto.class, GET_INSCRIPCION, email, id);
		if(l.isEmpty())
			throw new ModelException("La inscripcion no existe");
		
		inscripcion = l.get(0);
		if(date.isAfter(inscripcion.fechaCambioEstado.plusDays(2)))
			throw new ModelException("Han pasado mas de 48h desde la preisncripcion");
		
		double to_pay = bank.getPendingAmount(code);
		if(amount >= to_pay)
			update("INSCRITO", amount - to_pay);
		else
			update("RECHAZADO", amount);
		
		return db.executeQueryPojo(InscripcionDto.class, GET_INSCRIPCION, email, id).get(0);
	}

	private void update(String state, double devolver) {
		db.executeUpdate(UPDATE, state, devolver, email, id);
	}
}
