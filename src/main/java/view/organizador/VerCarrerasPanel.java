package view.organizador;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VerCarrerasPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JScrollPane competicionesPane;
	private JButton btnAtras;
	
	private OrganizadorMain main;

	/**
	 * Create the panel.
	 */
	public VerCarrerasPanel(OrganizadorMain main) {
		this.main = main;
		
		setLayout(new BorderLayout(0, 0));
		add(getCompeticionesPane(), BorderLayout.CENTER);
		add(getBtnAtras(), BorderLayout.SOUTH);

	}

	private JScrollPane getCompeticionesPane() {
		if (competicionesPane == null) {
			competicionesPane = new JScrollPane();
		}
		return competicionesPane;
	}
	
	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atrás");
			
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					main.flipCard(OrganizadorMain.ORGANIZADOR_MENU);
				}
			});
		}
		return btnAtras;
	}

}
