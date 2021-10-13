package view.organizador;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
