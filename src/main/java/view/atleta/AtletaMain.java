package view.atleta;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AtletaMain extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	static final String ATLETAS_MENU = "atletas";
	static final String INSCRIPCIONES = "inscripciones";
	static final String VER_INSCRIPCIONES = "ver inscripciones";
	static final String FORMULARIO_INSCRIPCION = "formulario";
	
	private JPanel cards;
	
	private AtletaMenu atletaMenu;
	private IncripcionesPanel inscripcionesPanel;
	private VerInscripcionesPanel verInscripcionesPanel;
	
	/**
	 * Create the frame.
	 */
	public AtletaMain() {
		// Setting-up some features of this window.
		setBounds(0, 0, 1366, 768);
		
		// Main content pane.
		this.setLayout(new BorderLayout(0, 0));
		
		// Setting-up the contentPane.
		cards = new JPanel(new CardLayout());
		
		// Create the cards.
		atletaMenu = new AtletaMenu();
		inscripcionesPanel = new IncripcionesPanel(this);
		verInscripcionesPanel = new VerInscripcionesPanel(this);
		
		// Create the panel that contains the cards.
		cards.add(atletaMenu, ATLETAS_MENU);
		cards.add(inscripcionesPanel, INSCRIPCIONES);
		cards.add(verInscripcionesPanel, VER_INSCRIPCIONES);
		
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
	private class AtletaMenu extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JButton btnInscribirse;
		private JButton btnVerInscripciones;
		/**
		 * Create the panel.
		 */
		public AtletaMenu() {
			setBorder(new EmptyBorder(30, 30, 30, 30));
			setLayout(new GridLayout(0, 1, 0, 20));
			add(getBtnInscribirse());
			add(getBtnVerInscripciones());
		
		}
		
		private JButton getBtnInscribirse() {
			if (btnInscribirse == null) {
				btnInscribirse = new JButton("Inscribirse");
				
				btnInscribirse.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						flipCard(INSCRIPCIONES);
					}
				});
			}
			return btnInscribirse;
		}
		
		private JButton getBtnVerInscripciones() {
			if (btnVerInscripciones == null) {
				btnVerInscripciones = new JButton("Ver Inscripciones");
				
				btnVerInscripciones.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						flipCard(VER_INSCRIPCIONES);
					}
				});
			}
			return btnVerInscripciones;
		}
	}

}