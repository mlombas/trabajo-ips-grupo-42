package view.atleta.panel;

import javax.swing.JPanel;

import model.ModelFactory;
import model.competicion.CompeticionDto;
import util.exceptions.ModelException;
import view.atleta.AtletaMain;
import view.atleta.util.AtrasAtletaButton;
import view.util.panel.VerCompeticionesPanel;

import javax.swing.JButton;
import javax.swing.JOptionPane;

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

	private JPanel panelButtons;

	/**
	 * Create the panel.
	 * @throws ModelException 
	 */
	public InscribirsePanel() {
		setLayout(new BorderLayout(0, 0));
		add(getCompeticionesPane(), BorderLayout.CENTER);
		add(getPanelButtons(), BorderLayout.SOUTH);

	}

	VerCompeticionesPanel getCompeticionesPane() {
		if (competicionesPane == null) {
			List<CompeticionDto> competiciones;
			try {
				competiciones = ModelFactory
						.forCarreraCrudService()
						.GetAllCompeticiones()
						.stream()
						.filter(competicion -> LocalDate.parse(competicion.getFecha())
												.compareTo(LocalDate.now()) > 0)
						.collect(Collectors.toList());
				competicionesPane = new VerCompeticionesPanel(competiciones);
			} catch (ModelException e) {
				JOptionPane.showMessageDialog(null, "No se han podido cargar las competiciones");
				AtletaMain.getInstance().startPanel();
			}
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
					
					try {
						competicion.id = competicionesPane.getCompeticionId();
						competicion.cuota = competicionesPane.getCuota();
						competicion.nombreCarrera = competicionesPane.getNombreCompeticion();
					} catch (ArrayIndexOutOfBoundsException aiobe) {
						JOptionPane.showMessageDialog(null, "Seleccione una carrera...");
						return;
					}

					// Move to the other card
					AtletaMain.getInstance().getFormularioInscripcion().setCompeticionDto(competicion);
					AtletaMain.getInstance().flipCard(AtletaMain.FORMULARIO_INSCRIPCION);
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
