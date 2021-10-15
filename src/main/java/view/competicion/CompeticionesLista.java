package view.competicion;

import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.competicion.CompeticionDto;

public class CompeticionesLista extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private List<CompeticionPanel> competiciones;
	
	public CompeticionesLista(List<CompeticionDto> competiciones) {
		this.competiciones = new LinkedList<CompeticionPanel>();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		for(CompeticionDto competicion : competiciones) {
			CompeticionPanel cp = new CompeticionPanel(competicion);
			this.competiciones.add(cp);
			add(cp);
		}
	}
}
