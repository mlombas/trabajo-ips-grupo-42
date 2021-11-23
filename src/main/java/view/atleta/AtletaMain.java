package view.atleta;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import util.exceptions.ModelException;
import view.atleta.panel.FormularioAtletaPanel;
import view.atleta.panel.FormularioInscripcionAtletaPanel;
import view.atleta.panel.FormularioInscripcionClubPanel;
import view.atleta.panel.InscribirsePanel;
import view.atleta.panel.JustificantePanel;
import view.atleta.panel.VerInscripcionesPanel;

public class AtletaMain extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public static final String ATLETAS_MENU = "atletas";
	public static final String INSCRIBIRSE = "inscribirse";
	public static final String VER_INSCRIPCIONES = "ver inscripciones";
	public static final String FORMULARIO_INSCRIPCION_ATLETA = "formulario inscripcion atleta";
	public static final String FORMULARIO_INSCRIPCION_CLUB = "formulario inscripcion club";
	public static final String FORMULARIO_ATLETA = "formulario atleta";
	public static final String JUSTIFICANTE = "justificante";
	
	private static AtletaMain atletaMain;
	
	private JPanel cards;
	
	private AtletaMenu atletaMenu;
	private InscribirsePanel inscribirsePanel;
	private VerInscripcionesPanel verInscripcionesPanel;
	private FormularioInscripcionAtletaPanel formularioInscripcionAtletaPanel;
	private FormularioInscripcionClubPanel formularioInscripcionClubPanel;
	private FormularioAtletaPanel formularioAtletaPanel;
	private JustificantePanel justificantePanel;
	
	/**
	 * Create the frame.
	 * @throws ModelException 
	 */
	private AtletaMain() {
		// Setting-up some features of this window.
		setBounds(0, 0, 1366, 768);
		
		// Main content pane.
		this.setLayout(new BorderLayout(0, 0));
		
		// Setting-up the contentPane.
		cards = new JPanel(new CardLayout());
		
		// Create the cards.
		atletaMenu = new AtletaMenu();
		inscribirsePanel = new InscribirsePanel();
		verInscripcionesPanel = new VerInscripcionesPanel();
		formularioInscripcionAtletaPanel = new FormularioInscripcionAtletaPanel();
		formularioInscripcionClubPanel = new FormularioInscripcionClubPanel();
		formularioAtletaPanel = new FormularioAtletaPanel();
		justificantePanel = new JustificantePanel();
		
		// Create the panel that contains the cards.
		cards.add(atletaMenu, ATLETAS_MENU);
		cards.add(inscribirsePanel, INSCRIBIRSE);
		cards.add(verInscripcionesPanel, VER_INSCRIPCIONES);
		cards.add(formularioInscripcionAtletaPanel, FORMULARIO_INSCRIPCION_ATLETA);
		cards.add(formularioInscripcionClubPanel, FORMULARIO_INSCRIPCION_CLUB);
		cards.add(formularioAtletaPanel, FORMULARIO_ATLETA);
		cards.add(justificantePanel, JUSTIFICANTE);
		
		// Add the card panel to the frame.
		this.add(cards);
	}

	public static AtletaMain getInstance() {
		 if (atletaMain == null)
			 atletaMain = new AtletaMain();
		 
		 return atletaMain;
	 }
	
	public void startPanel() {
		flipCard(ATLETAS_MENU);
	}
	
	public void flipCard(String cardId) {
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, cardId);
	}
	
	/** Basic implementation of the AtletasMenu;
	 * 		1. Here you can select what you want to do:
	 * 			a. Inscribe
	 * 			b. See inscriptions
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
						inscribirsePanel.refreshCompetitions();
						flipCard(INSCRIBIRSE);
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
	
	public FormularioInscripcionAtletaPanel getFormularioInscripcion() {
		return formularioInscripcionAtletaPanel;
	}
	
	public FormularioInscripcionClubPanel getFormularioInscripcionClub() {
		return formularioInscripcionClubPanel;
	}

	public FormularioAtletaPanel getFormularioAtletaPanel() {
		return formularioAtletaPanel;
	}
	
	public JustificantePanel getJustificantePanel() {
		return justificantePanel;
	}

}