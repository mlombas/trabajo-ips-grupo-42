package view.organizador;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.ModelFactory;
import model.competicion.CompeticionDto;

public class VerClasficacionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrganizadorMain main;
	private JButton btnAtras;
	private JScrollPane scrollParticipantes;
	private CompeticionDto competicion;
	
	public VerClasficacionPanel(OrganizadorMain main, CompeticionDto competicion) {
		this.main = main;
		this.competicion = competicion;
		
		setLayout(new BorderLayout(0, 0));
		add(getCompeticionesListPane(), BorderLayout.CENTER);
		add(getBtnAtras(), BorderLayout.SOUTH);
	}

	private Component getBtnAtras() {
		if(btnAtras == null) {
			btnAtras = new JButton("Atras");
			btnAtras.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					main.startPanel();
				}
			});
		}
		return btnAtras;
	}

	private Component getCompeticionesListPane() {
		if(scrollParticipantes == null) {
			scrollParticipantes = new JScrollPane(
					new ParticipantesLista(
							ModelFactory.forCarreraCrudService().GetClasificacion(competicion)
							)
					);
		}
		return scrollParticipantes;
	}

}
