package view.organizador;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.competicion.PosicionDto;

public class ParticipantePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ParticipantePanel(PosicionDto participante) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		add(new JLabel(participante.poisicion+""));
		add(Box.createHorizontalGlue());
		add(new JLabel(participante.sexo));
		add(Box.createHorizontalGlue());
		add(new JLabel(participante.nombreAtleta));
		add(Box.createHorizontalGlue());
		add(new JLabel(participante.tiempo+""));

	}
}
