package view.organizador.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import bank_mockup.Bank;
import model.ModelFactory;
import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;
import util.csv.CSVCreator;
import util.csv.CSVTable;
import util.exceptions.ModelException;
import view.util.table.ProcessedInscripcionTable;

public class VerProcesadoDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private static final String fname = "contabilidad.csv";
	private static Bank bank = new Bank();
	
	private int invalid = 0;

	private CompeticionDto dto;

	private List<InscripcionDto> processed;
	
	public VerProcesadoDialog(CompeticionDto dto) {
		this.dto = dto;
		
		process();
		
		setLayout(new BorderLayout(0, 0));
		setSize(new Dimension(465, 285));
		setLocationRelativeTo(null);
		setResizable(true);
		getContentPane().add(getValidInvalidLbl(), BorderLayout.NORTH);
		getContentPane().add(getProcessedTable(), BorderLayout.CENTER);
		getContentPane().add(getBackBtn(), BorderLayout.SOUTH);
	}

	private Component getBackBtn() {
		JButton b = new JButton("Back");
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VerProcesadoDialog.this.dispose();
			}
		});
		return b;
	}

	private Component getProcessedTable() {
		return new JScrollPane(
				new ProcessedInscripcionTable(processed)
				);
	}

	private JLabel getValidInvalidLbl() {
		return new JLabel("Valid: " + processed.size() + " Invalid: " + invalid);
	}

	private void process() {
		processed = new LinkedList<InscripcionDto>();
		
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
			String email = data.get(0);
			String code = data.get(1);
			double amount = Double.parseDouble(data.get(2));
			LocalDateTime dateTime = LocalDateTime.parse(data.get(3), formatter);
			
			try {
				if(valid(email) && bank.isCodePending(code))
					pay(email, code, amount, dateTime);
				else invalid++;
			} catch(ModelException e) {
				invalid++;
			}
		}
	}
	
	private void pay(String email, String code, double amount, LocalDateTime dateTime) throws ModelException {
		processed.add(ModelFactory.forAtletaCrudService().processTransaction(email, code, amount, dateTime, dto.id));
	}

	private boolean valid(String s) {
		return s != null && !s.isBlank();
	}
}
