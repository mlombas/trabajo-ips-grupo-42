package view.atleta;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.competicion.CompeticionDto;

import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

public class IncripcionesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JScrollPane competicionesPane;
	private JButton btnInscribirse;
	private JButton btnAtras;
	
	private AtletaMain main;
	
	private FormularioInscripcionDialog formularioDeInscripcion;
	private JPanel panelButtons;

	/**
	 * Create the panel.
	 */
	public IncripcionesPanel(AtletaMain main) {
		this.main = main;
		
		setLayout(new BorderLayout(0, 0));
		add(getCompeticionesPane(), BorderLayout.CENTER);
		add(getPanelButtons(), BorderLayout.SOUTH);

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

	
	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new GridLayout(1, 0, 0, 0));
			panelButtons.add(getBtnInscribirse());
			panelButtons.add(getBtnAtras());
		}
		return panelButtons;
	}
	
	private JButton getBtnInscribirse() {
		if (btnInscribirse == null) {
			btnInscribirse = new JButton("Inscribirse");
			
			btnInscribirse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CompeticionDto competicion = new CompeticionDto();
					
					showFormularioDeInscripcion();
					formularioDeInscripcion.setCompeticionDto(competicion);
				}
			});
		}
		return btnInscribirse;
	}
	
	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atrás");

			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					main.flipCard(AtletaMain.ATLETAS_MENU);
				}
			});
		}
		return btnAtras;
	}
	
}
