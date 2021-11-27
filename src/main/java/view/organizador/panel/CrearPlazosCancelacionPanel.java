package view.organizador.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controller.competicion.CompeticionCrudService;
import controller.competicion.CompeticionCrudServiceImpl;
import model.competicion.CompeticionDto;
import model.competicion.PlazoCancelacionDto;
import model.competicion.PlazoInscripcionDto;
import net.miginfocom.swing.MigLayout;
import util.Validate;
import util.exceptions.ApplicationException;
import view.util.table.PlazosCancelacionToTable;
import view.util.table.PlazosToTable;

public class CrearPlazosCancelacionPanel extends CrearCompeticionSubPanel {

	private static final long serialVersionUID = 1L;

	private JTable tabPlazos;

	private JLabel lbFechaInicio;
	private JTextField tfFechaInicio;
	private JLabel lbFechaFin;
	private JTextField tfFechaFin;
	private JLabel lbPercent;
	private JTextField tfPercent;

	private CompeticionDto comp;

	public CrearPlazosCancelacionPanel(CompeticionDto comp) {
		this.comp = comp;

		// Añadimos los elementos al panel
		cargarPlazosCancelacion();
		addToFormulario();

		// Añadimos el event listener
		getBtnAñadir().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				añadirPlazo();
			}
		});
	}

	private void cargarPlazosCancelacion() {
		CompeticionCrudService ccs = new CompeticionCrudServiceImpl();
		List<PlazoCancelacionDto> plazos = ccs.getAllPlazosCancelacion(comp.id);
		this.tabPlazos = new PlazosCancelacionToTable(plazos);
		getScrollPaneVisualizacion().setViewportView(tabPlazos);
	}

	private void showMessage(String message, String title, int type) {
		JOptionPane pane = new JOptionPane(message, type, JOptionPane.DEFAULT_OPTION);
		pane.setOptions(new Object[] { "ACEPTAR" });
		JDialog d = pane.createDialog(pane, title);
		d.setLocationRelativeTo(null);
		d.setVisible(true);
	}

	private void añadirPlazo() {
		if (!checkPorcentaje()) {
			showMessage("Porcentaje no válido", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		} else if (!checkFechaInicio()) {
			showMessage("Fecha de inicio no válida", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		} else if (!checkFechaFin()) {
			showMessage("Fecha de finalización no válida", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		} else {
			try {
				PlazoCancelacionDto plazo = new PlazoCancelacionDto();
				plazo.porcentaje = Integer.parseInt(tfPercent.getText().strip());

				String fechaInicio = tfFechaInicio.getText();
				plazo.fechaInicio = Validate.validateFecha(fechaInicio);
				if (plazo.fechaInicio == null || plazo.fechaInicio.isBefore(LocalDate.now())) {
					showMessage("Fecha de inicio no válida", "Informacion", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				String fechaFin = tfFechaFin.getText();
				plazo.fechaFin = Validate.validateFecha(fechaFin);
				if (plazo.fechaFin == null || plazo.fechaFin.isBefore(LocalDate.now())) {
					showMessage("Fecha de finalización no válida", "Informacion", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				CompeticionCrudService ccs = new CompeticionCrudServiceImpl();
				List<PlazoCancelacionDto> plazos = ccs.addPlazoCancelacion(comp, plazo);
				tabPlazos = new PlazosCancelacionToTable(plazos);
				getScrollPaneVisualizacion().setViewportView(tabPlazos);
				this.revalidate();
			} catch (ApplicationException e) {
				showMessage(e.getMessage(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
			} catch (RuntimeException e) {
				e.printStackTrace();
				showMessage(e.toString(), "Excepcion no controlada", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private boolean checkFechaFin() {
		if (tfFechaFin.getText().strip().isEmpty())
			return false;
		return true;
	}

	private boolean checkFechaInicio() {
		if (tfFechaInicio.getText().strip().isEmpty())
			return false;
		return true;
	}

	private boolean checkPorcentaje() {
		try {
			if (tfPercent.getText().strip().isEmpty() || Integer.parseInt(tfPercent.getText().strip()) < 0
					|| Integer.parseInt(tfPercent.getText().strip()) > 100)
				return false;
			return true;
		} catch (RuntimeException e) {
			showMessage("Porcentaje no válido", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		}
		return false;
	}

	private void addToFormulario() {
		getPanelFormulario().setLayout(new MigLayout("", "[grow,fill]", "[][][][][][]"));
		getPanelFormulario().add(getLbFechaInicio(), "cell 0 0,alignx left,aligny center");
		getPanelFormulario().add(getTfFechaInicio(), "cell 0 1,growx,aligny top");
		getPanelFormulario().add(getLbFechaFin(), "cell 0 2,alignx left,aligny center");
		getPanelFormulario().add(getTfFechaFin(), "cell 0 3,growx,aligny top");
		getPanelFormulario().add(getLbPercent(), "cell 0 4,alignx left,aligny center");
		getPanelFormulario().add(getTfPercent(), "cell 0 5,growx,aligny top");
	}

	private JLabel getLbFechaInicio() {
		if (lbFechaInicio == null) {
			lbFechaInicio = new JLabel("Fecha de Inicio: ");
		}
		return lbFechaInicio;
	}

	private JTextField getTfFechaInicio() {
		if (tfFechaInicio == null) {
			tfFechaInicio = new JTextField();
			tfFechaInicio.setColumns(20);
		}
		return tfFechaInicio;
	}

	private JLabel getLbFechaFin() {
		if (lbFechaFin == null) {
			lbFechaFin = new JLabel("Fecha de Finalización: ");
		}
		return lbFechaFin;
	}

	private JTextField getTfFechaFin() {
		if (tfFechaFin == null) {
			tfFechaFin = new JTextField();
			tfFechaFin.setColumns(20);
		}
		return tfFechaFin;
	}

	private JLabel getLbPercent() {
		if (lbPercent == null) {
			lbPercent = new JLabel("Procentaje a devolver:");
		}
		return lbPercent;
	}

	private JTextField getTfPercent() {
		if (tfPercent == null) {
			tfPercent = new JTextField();
			tfPercent.setColumns(20);
		}
		return tfPercent;
	}

}
