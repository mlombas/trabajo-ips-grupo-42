package view.organizador;

import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.competicion.PosicionDto;

public class ParticipantesLista extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ParticipantePanel> participantes;

	public ParticipantesLista(List<PosicionDto> posiciones) {
		participantes = new LinkedList<ParticipantePanel>();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		for(PosicionDto pos : posiciones) {
			ParticipantePanel pp = new ParticipantePanel(pos);
			participantes.add(pp);
			add(pp);
		}
	}

}
