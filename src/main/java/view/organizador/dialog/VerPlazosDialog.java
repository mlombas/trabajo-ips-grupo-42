package view.organizador.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import controller.competicion.CompeticionCrudService;
import controller.competicion.CompeticionCrudServiceImpl;
import model.competicion.CompeticionDto;
import model.competicion.PlazoInscripcionDto;
import view.util.table.PlazosToTable;

public class VerPlazosDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JScrollPane competicionesPane;
	private JTable tabPlazos;

	private CompeticionDto comp;

	/**
	 * Create the panel.
	 */
	public VerPlazosDialog(CompeticionDto comp) {
		setSize(new Dimension(600, 600));
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getCompeticionesPane(), BorderLayout.CENTER);
		this.comp = comp ;
		cargarPlazos();

	}

	private void cargarPlazos() {
		CompeticionCrudService ccs = new CompeticionCrudServiceImpl();		
		List<PlazoInscripcionDto> plazos = ccs.getAllPlazos(comp.id);
		
		TableModel model = new PlazosToTable(plazos).getModel();
		getTabPlazos().setModel(model);
		
	}

	private JScrollPane getCompeticionesPane() {
		if (competicionesPane == null) {
			competicionesPane = new JScrollPane();
			competicionesPane.setViewportView(getTabPlazos());
		}
		return competicionesPane;
	}

	private JTable getTabPlazos() {
		if (tabPlazos == null) {
			tabPlazos = new JTable();
		}
		return tabPlazos;
	}

}
