package view.competicion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.ModelFactory;
import view.atleta.AtletaMain;

public class VerCompeticionesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private AtletaMain main;
	private JButton btnAtras;
	private JScrollPane scrollCompeticiones;
	
	public VerCompeticionesPanel(AtletaMain main) {
		this.main = main;
		
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
		if(scrollCompeticiones == null) {
			scrollCompeticiones = new JScrollPane(
					new CompeticionesLista(
							ModelFactory.forCarreraCrudService().GetAllCompeticiones()
							)
					);
		}
		return scrollCompeticiones;
	}

}
