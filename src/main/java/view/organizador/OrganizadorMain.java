package view.organizador;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.exceptions.ModelException;
import view.organizador.panel.CrearCompeticionPanel;
import view.organizador.panel.GestionarCompeticionesPanel;

public class OrganizadorMain extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public static final String ORGANIZADOR_MENU = "organizadores";
	public static final String CREAR_COMPETICION = "crear competicion";
	public static final String GESTIONAR_COMPETICIONES = "buscar competicion";
	
	private static OrganizadorMain organizadorMain;
	
	private JPanel cards;
	
	private OrganizadorMenu organizadorMenu;
	private CrearCompeticionPanel crearCompeticionPane;
	private GestionarCompeticionesPanel buscarCompeticionPane;
	
	/**
	 * Create the frame.
	 * @throws ModelException 
	 */
	private OrganizadorMain() {
		// Setting-up some features of this window.
		setBounds(0, 0, 1366, 768);
		
		// Main content pane.
		this.setLayout(new BorderLayout(0, 0));
		
		// Setting-up the contentPane.
		cards = new JPanel(new CardLayout());
		
		// Create the cards.
		organizadorMenu = new OrganizadorMenu();
		crearCompeticionPane = new CrearCompeticionPanel();
		buscarCompeticionPane = new GestionarCompeticionesPanel();
		
		// Create the panel that contains the cards.
		cards.add(organizadorMenu, ORGANIZADOR_MENU);
		cards.add(crearCompeticionPane, CREAR_COMPETICION);
		cards.add(buscarCompeticionPane, GESTIONAR_COMPETICIONES);
		
		// Add the card panel to the frame.
		this.add(cards);
	}
	
	public static OrganizadorMain getInstance() {
		 if (organizadorMain == null)
			 organizadorMain = new OrganizadorMain();
		 
		 return organizadorMain;
	 }
	
	public void flipCard(String cardId) {
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
				btnCrearCarrera = new JButton("Crear Competición");
				
				btnCrearCarrera.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						flipCard(CREAR_COMPETICION);
					}
				});
			}
			return btnCrearCarrera;
		}
		
		private JButton getBtnVerCarreras() {
			if (btnVerCarreras == null) {
				btnVerCarreras = new JButton("Gestionar Competiciones");
				
				btnVerCarreras.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						flipCard(GESTIONAR_COMPETICIONES);
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