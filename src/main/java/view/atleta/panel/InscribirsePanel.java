package view.atleta.panel;

import javax.swing.JPanel;

import model.ModelFactory;
import model.competicion.CompeticionDto;
import view.atleta.AtletaMain;
import view.atleta.util.AtrasAtletaButton;
import view.util.panel.VerCompeticionesPanel;

import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.GridLayout;

public class InscribirsePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private VerCompeticionesPanel competicionesPane;
	private JButton btnInscribirse;
	private AtrasAtletaButton btnAtras;
	
	private FormularioInscripcionPanel formularioDeInscripcion;
	private JPanel panelButtons;

	/**
	 * Create the panel.
	 */
	public InscribirsePanel() {
		setLayout(new BorderLayout(0, 0));
		add(getCompeticionesPane(), BorderLayout.CENTER);
		add(getPanelButtons(), BorderLayout.SOUTH);

	}

	VerCompeticionesPanel getCompeticionesPane() {
		if (competicionesPane == null) {
			List<CompeticionDto> competiciones = ModelFactory
					.forCarreraCrudService()
					.GetAllCompeticiones()
					.stream()
					.filter(competicion -> LocalDate.parse(competicion.getFecha())
											.compareTo(LocalDate.now()) > 0)
					.collect(Collectors.toList());
			competicionesPane = new VerCompeticionesPanel(competiciones);
		}
		return competicionesPane;
	}

	
	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new GridLayout(1, 0, 0, 0));
			panelButtons.add(getBtnInscribirse());
			panelButtons.add(getBtnAtras());
		}
		return panelButtons;
	}
	
	private JButton getBtnInscribirse() {
		if (btnInscribirse == null) {
			btnInscribirse = new JButton("Inscribirse");
			
			btnInscribirse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CompeticionDto competicion = new CompeticionDto();
					
					//showFormularioDeInscripcion(); // TODO flip card
					formularioDeInscripcion.setCompeticionDto(competicion);
				}
			});
		}
		return btnInscribirse;
	}
	
	private AtrasAtletaButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new AtrasAtletaButton(AtletaMain.ATLETAS_MENU);
		}
		return btnAtras;
	}
	
}
