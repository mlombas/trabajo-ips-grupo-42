package view.organizador;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

import model.competicion.CompeticionDto;
import view.atleta.FormularioInscripcionDialog;

import java.awt.CardLayout;

public class BuscarCarreraPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JScrollPane competicionesPane;
	private JButton btnAtras;
	
	private OrganizadorMain main;
	private JTextField textFieldIdCarrera;
	private JTextField textFieldCategoria;

	private VerClasficacionDialog clasDial;
	
	/**
	 * Create the panel.
	 */
	public BuscarCarreraPanel(OrganizadorMain main) {
		this.main = main;
		
		setLayout(new BorderLayout(0, 0));
		add(getCompeticionesPane(), BorderLayout.CENTER);
		add(getBtnAtras(), BorderLayout.SOUTH);
		
		JPanel pnCard = new JPanel();
		add(pnCard, BorderLayout.NORTH);
		pnCard.setLayout(new CardLayout(0, 0));

	}

	private JScrollPane getCompeticionesPane() {
		if (competicionesPane == null) {
			competicionesPane = new JScrollPane();
			
			JPanel panel = new JPanel();
			competicionesPane.setViewportView(panel);
			panel.setLayout(new BorderLayout(0, 0));
			
			JButton btnVerResultados = new JButton("Ver resultados");
			btnVerResultados.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CompeticionDto comp = new CompeticionDto();
					comp.id = textFieldIdCarrera.getText();
					showClasificacion(comp);
				}
			});
			panel.add(btnVerResultados, BorderLayout.SOUTH);
			
			JPanel panel_1 = new JPanel();
			panel.add(panel_1, BorderLayout.CENTER);
			panel_1.setLayout(new GridLayout(0, 2, 0, 0));
			
			JLabel lblIdCarrera = new JLabel("IdCarrera");
			lblIdCarrera.setHorizontalAlignment(SwingConstants.CENTER);
			panel_1.add(lblIdCarrera);
			
			JLabel lblCategoria = new JLabel("Categoria");
			lblCategoria.setHorizontalAlignment(SwingConstants.CENTER);
			panel_1.add(lblCategoria);
			
			textFieldIdCarrera = new JTextField();
			panel_1.add(textFieldIdCarrera);
			textFieldIdCarrera.setColumns(20);
			
			textFieldCategoria = new JTextField();
			panel_1.add(textFieldCategoria);
			textFieldCategoria.setColumns(6);
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
	
	private void showClasificacion(CompeticionDto comp) {
		this.clasDial = new VerClasficacionDialog(main, comp);
		clasDial.setLocationRelativeTo(null);
		clasDial.setModal(true);
		clasDial.setVisible(true);

}
}