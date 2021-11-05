package view.organizador.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.ModelFactory;
import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;
import util.exceptions.ModelException;
import view.util.table.InscripcionesToTable;

public class VerEstadoInscripcionDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JScrollPane competicionesPane;
	private JPanel btnPane;
	private JButton btnAtras;
	
	private CompeticionDto competicion;
	InscripcionesToTable table;

	public VerEstadoInscripcionDialog(CompeticionDto competicion) {
		this.competicion = competicion;
		
		setLayout(new BorderLayout(0, 0));
		setSize(new Dimension(465, 285));
		setLocationRelativeTo(null);
		setResizable(true);
		setTitle("Carreras Populares APP - Estado de Inscripción");
		add(getCompeticionesPane(), BorderLayout.CENTER);
		add(getBtnPane(), BorderLayout.SOUTH);
	}
	
	private void closeDialog() {
		this.dispose();
	}

	private JScrollPane getCompeticionesPane() {
		if (competicionesPane == null) {
			competicionesPane = new JScrollPane();
			List<InscripcionDto> inscripciones;
			try {
				inscripciones = ModelFactory.forOrganizadorCrudService().getAtletasForCompetition(competicion);
				table = new InscripcionesToTable(inscripciones);
				competicionesPane.setViewportView(table);
			} catch (ModelException e) {
				JOptionPane.showMessageDialog(null, "Algo ha salido mal...");
			}
		}
		
		return competicionesPane;
	}
	
	private JPanel getBtnPane() {
		if (btnPane == null) {
			btnPane = new JPanel();
			btnPane.setLayout(new GridLayout(0, 1, 0, 0));
			btnPane.add(getBtnAtras());
		}
		return btnPane;
	}
	
	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atrás");
			
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					closeDialog();
				}
			});
		}
		return btnAtras;
	}

}
