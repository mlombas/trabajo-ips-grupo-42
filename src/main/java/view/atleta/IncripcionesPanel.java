package view.atleta;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.competicion.CompeticionDto;

import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IncripcionesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JScrollPane competicionesPane;
	private JButton btnInscribirse;
	
	private FormularioInscripcionDialog formularioDeInscripcion;

	/**
	 * Create the panel.
	 */
	public IncripcionesPanel() {
		setLayout(new BorderLayout(0, 0));
		add(getCompeticionesPane(), BorderLayout.CENTER);
		add(getBtnInscribirse(), BorderLayout.SOUTH);

	}
	
	private void showFormularioDeInscripcion() {
		this.formularioDeInscripcion = new FormularioInscripcionDialog();
		formularioDeInscripcion.setLocationRelativeTo(null);
		formularioDeInscripcion.setModal(true);
		formularioDeInscripcion.setVisible(true);
	}

	private JScrollPane getCompeticionesPane() {
		if (competicionesPane == null) {
			competicionesPane = new JScrollPane();
		}
		return competicionesPane;
	}
	
	private JButton getBtnInscribirse() {
		if (btnInscribirse == null) {
			btnInscribirse = new JButton("Inscribirse");
			
			btnInscribirse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CompeticionDto competicion = new CompeticionDto();
					
					showFormularioDeInscripcion();
					formularioDeInscripcion.setCompeticionDto(competicion); // TODO
				}
			});
		}
		return btnInscribirse;
	}
	
}
