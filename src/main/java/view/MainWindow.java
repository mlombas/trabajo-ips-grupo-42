package view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import view.atleta.AtletaMain;
import view.organizador.OrganizadorMain;
import view.util.listener.BackHomeScreenAdapter;
import view.util.listener.DefaultClosingAdapter;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public static final String TITLE = "CarrerasPopulares APP";
	public static final String EXIT_DIALOG = "Estás seguro que quieres cerrar la aplicación?";
	public static final String MAIN_MENU = "home";
	private static final String ATLETAS_MENU = "atletas";
	private static final String ORGANZIADORES_MENU = "organizadores";
	
	private static MainWindow mainWindow;
	
	private JPanel cards;
	
	private MainMenu mainMenu;
	private AtletaMain atletaMain;
	private OrganizadorMain organizadorMain;
	
	/**
	 * Create the frame.
	 */
	private MainWindow() {
		// Setting-up some features of this window.
		setTitle(TITLE);
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, 1280, 720);
		setMinimumSize(new Dimension(960, 480));
		setLocationRelativeTo(null);
		
		// Main content pane.
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		// Setting-up the contentPane.
		cards = new JPanel(new CardLayout());
		
		// Create the cards.
		mainMenu = new MainMenu();
		atletaMain = AtletaMain.getInstance();
		organizadorMain = OrganizadorMain.getInstance();
		// Create the panel that contains the cards.
		cards.add(mainMenu, MAIN_MENU);
		cards.add(atletaMain, ATLETAS_MENU);
		cards.add(organizadorMain, ORGANZIADORES_MENU);
		
		// Add the card panel to the frame.
		getContentPane().add(cards);
		
		// Close operation.
		addWindowListener(DefaultClosingAdapter.getInstance());
	}
	
	public static MainWindow getInstance() {
		 if (mainWindow == null)
			 mainWindow = new MainWindow();
		 
		 return mainWindow;
	 }
	
	public void flipCard(String cardId) {
        CardLayout cl = (CardLayout) cards.getLayout();
        
        if (cardId.equals(ATLETAS_MENU) || cardId.equals(ORGANZIADORES_MENU)) {
        	removeWindowListener(DefaultClosingAdapter.getInstance());
        	addWindowListener(BackHomeScreenAdapter.getInstance());
        } else if (cardId.equals(MAIN_MENU)) {
        	removeWindowListener(BackHomeScreenAdapter.getInstance());
        	addWindowListener(DefaultClosingAdapter.getInstance());
        }
        
        cl.show(cards, cardId);
	}
	
	/** Basic implementation of the MainMenu;
	 * 		1. Here you can select which type of user you are.
	 */
	private class MainMenu extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JButton btnAtleta;
		private JButton btnOrganizador;
		/**
		 * Create the panel.
		 */
		public MainMenu() {
			setBorder(new EmptyBorder(30, 30, 30, 30));
			setLayout(new GridLayout(0, 1, 0, 20));
			add(getBtnAtleta());
			add(getBtnOrganizador());

		}
		
		private JButton getBtnAtleta() {
			if (btnAtleta == null) {
				btnAtleta = new JButton("Atleta");
				
				btnAtleta.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						flipCard(ATLETAS_MENU);
					}
				});
			}
			return btnAtleta;
		}
		
		private JButton getBtnOrganizador() {
			if (btnOrganizador == null) {
				btnOrganizador = new JButton("Organizador");
				
				btnOrganizador.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						flipCard(ORGANZIADORES_MENU);
					}
				});
			}
			return btnOrganizador;
		}
	}
	
}
