package view.organizador;

import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.inscripcion.InscripcionDto;

public class ParticipantesLista extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ParticipantePanel> participantes;

	public ParticipantesLista(List<InscripcionDto> inscripciones) {
		participantes = new LinkedList<ParticipantePanel>();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		for(InscripcionDto insc : inscripciones) {
			ParticipantePanel pp = new ParticipantePanel(insc);
			participantes.add(pp);
			add(pp);
		}
	}

}
