package view.organizador;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class CrearCarreraPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private OrganizadorMain main;

	/**
	 * Create the panel.
	 */
	public CrearCarreraPanel(OrganizadorMain main) {
		this.main = main;
		
		setLayout(new BorderLayout(0, 0));

	}
	
}
