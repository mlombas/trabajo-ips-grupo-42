package view.organizador;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class OrganizadorMain extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	static final String ORGANIZADOR_MENU = "organizadores";
	static final String CREAR_CARRERA = "crear carrera";
	static final String BUSCAR_CARRERAS = "buscar carrera";
	
	private JPanel cards;
	
	private OrganizadorMenu organizadorMenu;
	private CrearCarreraPanel crearCarreraPanel;
	private VerCarrerasPanel buscarCarreraPanel;
	/**
	 * Create the frame.
	 */
	public OrganizadorMain() {
		// Setting-up some features of this window.
		setBounds(0, 0, 1366, 768);
		
		// Main content pane.
		this.setLayout(new BorderLayout(0, 0));
		
		// Setting-up the contentPane.
		cards = new JPanel(new CardLayout());
		
		// Create the cards.
		organizadorMenu = new OrganizadorMenu();
		crearCarreraPanel = new CrearCarreraPanel(this);
		buscarCarreraPanel = new VerCarrerasPanel(this);
		
		// Create the panel that contains the cards.
		cards.add(organizadorMenu, ORGANIZADOR_MENU);
		cards.add(crearCarreraPanel, CREAR_CARRERA);
		cards.add(buscarCarreraPanel, BUSCAR_CARRERAS);
		
		// Add the card panel to the frame.
		this.add(cards);
		
		// Default button.
		//	getRootPane().setDefaultButton(); TODO
	}
	
	protected void flipCard(String cardId) {
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, cardId);
	}
	

	/** Basic implementation of the AtletasMenu;
	 * 		1. Here you can select what you want to do:
	 * 			a. Inscribirse
	 * 			b. Ver inscripciones
	 */
	private class OrganizadorMenu extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JButton btnCrearCarrera;
		private JButton btnVerCarreras;
		/**
		 * Create the panel.
		 */
		public OrganizadorMenu() {
			setBorder(new EmptyBorder(30, 30, 30, 30));
			setLayout(new GridLayout(0, 1, 0, 20));
			add(getBtnCrearCarrera());
			add(getBtnVerCarreras());
		
		}
		
		private JButton getBtnCrearCarrera() {
			if (btnCrearCarrera == null) {
				btnCrearCarrera = new JButton("Crear Carrera");
				
				btnCrearCarrera.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//flipCard(CREAR_CARRERA);
					}
				});
			}
			return btnCrearCarrera;
		}
		
		private JButton getBtnVerCarreras() {
			if (btnVerCarreras == null) {
				btnVerCarreras = new JButton("Ver Carreras");
				
				btnVerCarreras.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						flipCard(BUSCAR_CARRERAS);
					}
				});
			}
			return btnVerCarreras;
		}
	}


	public void startPanel() {
		flipCard(ORGANIZADOR_MENU);
	}

}