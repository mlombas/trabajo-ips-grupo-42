package view.competicion;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.competicion.CompeticionDto;

public class CompeticionPanel extends JPanel {

	public CompeticionPanel(CompeticionDto competicion) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		add(new JLabel(competicion.nombreCarrera));
		add(Box.createHorizontalGlue());
		add(new JLabel(competicion.distancia + "km"));
		add(Box.createHorizontalGlue());
		add(new JLabel("Tipo: " + competicion.tipoCarrera));
		add(Box.createHorizontalGlue());
		add(new JLabel("Cuota: " + competicion.cuota + "€"));
		//TODO(Mario) añadir la fecha
		//add(new JLabel("Fecha: " + competicion.fecha.toLocalDate().toString()));
	}

}
