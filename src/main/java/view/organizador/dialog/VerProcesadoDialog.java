package view.organizador.dialog;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import bank_mockup.Bank;
import model.competicion.CompeticionDto;
import util.csv.CSVCreator;
import util.csv.CSVTable;

public class VerProcesadoDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private static final String fname = "contabilidad.csv";
	private static Bank bank = new Bank();
	
	private int invalid = 0;
	
	public VerProcesadoDialog(CompeticionDto dto) {
		CSVTable<String, String> table;
		try {
			table = CSVCreator.read(fname, "\s*;\s*");
		} catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(
					this, 
					"No existe el archivo 'contabilidad.csv'",
					"CarrerasPopulares APP - Error",
					JOptionPane.ERROR_MESSAGE
				);
			throw new RuntimeException(e);
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		for(List<String> data : table) {
			String dni = data.get(0);
			String code = data.get(1);
			double amount = Double.parseDouble(data.get(2));
			LocalDateTime dateTime = LocalDateTime.parse(data.get(3), formatter);
			
			if(valid(dni) && bank.isCodePending(code) && dateTime.isBefore(dateTime))
				pay(dni, code, amount, dateTime);
			else invalid++;
		}
	}

	private void pay(String dni, String code, double amount, LocalDateTime dateTime) {
		// TODO(Mario) completar esto
	}

	private boolean valid(String s) {
		return s != null && !s.isBlank();
	}
}
