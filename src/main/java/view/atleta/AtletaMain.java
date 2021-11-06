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
import view.atleta.panel.FormularioInscripcionPanel;
import view.atleta.panel.InscribirsePanel;
import view.atleta.panel.JustificantePanel;
import view.atleta.panel.VerInscripcionesPanel;
import view.atleta.util.AtrasAtletaButton;
import view.util.panel.VerCompeticionesPanel;

public class AtletaMain extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public static final String ATLETAS_MENU = "atletas";
	public static final String INSCRIBIRSE = "inscribirse";
	public static final String VER_INSCRIPCIONES = "ver inscripciones";
	public static final String FORMULARIO_INSCRIPCION = "formulario";
	public static final String VER_CARRERAS = "carreras";
	public static final String FORMULARIO_ATLETA = "formulario atleta";
	public static final String JUSTIFICANTE = "justificante";
	
	private static AtletaMain atletaMain;
	
	private JPanel cards;
	
	private AtletaMenu atletaMenu;
	private InscribirsePanel inscribirsePanel;
	private VerInscripcionesPanel verInscripcionesPanel;
	private VerCompeticionesPanel verCompeticionesPanel;
	private FormularioInscripcionPanel formularioInscripcionPanel;
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
		verCompeticionesPanel = getVerCompeticionesPanel();
		formularioInscripcionPanel = new FormularioInscripcionPanel();
		formularioAtletaPanel = new FormularioAtletaPanel();
		justificantePanel = new JustificantePanel();
		
		// Create the panel that contains the cards.
		cards.add(atletaMenu, ATLETAS_MENU);
		cards.add(inscribirsePanel, INSCRIBIRSE);
		cards.add(verInscripcionesPanel, VER_INSCRIPCIONES);
		cards.add(verCompeticionesPanel, VER_CARRERAS);
		cards.add(formularioInscripcionPanel, FORMULARIO_INSCRIPCION);
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
	 * 			a. Inscribirse
	 * 			b. Ver inscripciones
	 * 			c. Ver competiciones
	 */
	private class AtletaMenu extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JButton btnInscribirse;
		private JButton btnVerInscripciones;
		private JButton btnVerCompeticiones;
		/**
		 * Create the panel.
		 */
		public AtletaMenu() {
			setBorder(new EmptyBorder(30, 30, 30, 30));
			setLayout(new GridLayout(0, 1, 0, 20));
			add(getBtnInscribirse());
			add(getBtnVerInscripciones());
			add(getBtnVerCompeticiones());
		}
		
		private JButton getBtnVerCompeticiones() {
			if(btnVerCompeticiones == null) {
				btnVerCompeticiones = new JButton("Ver Competiciones");
				btnVerCompeticiones.addActionListener(
						new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								flipCard(VER_CARRERAS);
							}
						});
			}
			return btnVerCompeticiones;
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
	
	private VerCompeticionesPanel getVerCompeticionesPanel() {
		if (verCompeticionesPanel == null) {
			verCompeticionesPanel = new VerCompeticionesPanel();
			verCompeticionesPanel.add(new AtrasAtletaButton(AtletaMain.ATLETAS_MENU), BorderLayout.SOUTH);
		}
		return verCompeticionesPanel;
	}
	
	public FormularioInscripcionPanel getFormularioInscripcion() {
		return formularioInscripcionPanel;
	}

	public FormularioAtletaPanel getFormularioAtletaPanel() {
		return formularioAtletaPanel;
	}
	
	public JustificantePanel getJustificantePanel() {
		return justificantePanel;
	}

}