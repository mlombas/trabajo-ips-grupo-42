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
import model.competicion.PlazoInscripcionDto;
import net.miginfocom.swing.MigLayout;
import util.Validate;
import util.exceptions.ApplicationException;
import view.util.table.PlazosToTable;

public class CrearPlazosPanel extends CrearCompeticionSubPanel {

	private static final long serialVersionUID = 1L;

	private JTable tabPlazos;
	
	private JLabel lbFechaInicio;
	private JTextField tfFechaInicio;
	private JLabel lbFechaFin;
	private JTextField tfFechaFin;
	private JLabel lbCuota;
	private JTextField tfCuota;

	private CompeticionDto comp;
	private CrearCompeticionPanel crearCompeticionPanel;

	public CrearPlazosPanel(CrearCompeticionPanel crearCompeticionPanel, CompeticionDto comp) {
		this.comp = comp;
		this.crearCompeticionPanel = crearCompeticionPanel;
		
		// Añadimos los elementos al panel
		cargarPlazos();
		addToFormulario();
		
		// Añadimos el event listener
		getBtnAñadir().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				añadirPlazo();
			}
		});
	}

	private void cargarPlazos() {
		CompeticionCrudService ccs = new CompeticionCrudServiceImpl();		
		List<PlazoInscripcionDto> plazos = ccs.getAllPlazos(comp.id);
		this.tabPlazos = new PlazosToTable(plazos);
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
		if (!checkCuota()) {
			showMessage("Cuota no válida", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		} else if (!checkFechaInicio()) {
			showMessage("Fecha de inicio no válida", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		} else if (!checkFechaFin()) {
			showMessage("Fecha de finalización no válida", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		} else {
			try {
				PlazoInscripcionDto plazo = new PlazoInscripcionDto();
				plazo.cuota = Integer.parseInt(tfCuota.getText().strip());
				
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
				List<PlazoInscripcionDto> plazos = ccs.addPlazo(comp, plazo);
				tabPlazos = new PlazosToTable(plazos);
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
		if(tfFechaFin.getText().strip().isEmpty())
			return false;
		return true;
	}

	private boolean checkFechaInicio() {
		if(tfFechaInicio.getText().strip().isEmpty())
			return false;
		return true;
	}

	private boolean checkCuota() {
		try {
			if(tfCuota.getText().strip().isEmpty() || Integer.parseInt(tfCuota.getText().strip()) < 0)
				return false;
			return true;
		}catch (RuntimeException e) {
			showMessage("Cuota no válida", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		}
		return false;
	}
	
	private void addToFormulario() {
		getPanelFormulario().setLayout(new MigLayout("", "[grow,fill]", "[][][][][][]"));
		getPanelFormulario().add(getLbFechaInicio(), "cell 0 0,alignx left,aligny center");
		getPanelFormulario().add(getTfFechaInicio(), "cell 0 1,growx,aligny top");
		getPanelFormulario().add(getLbFechaFin(), "cell 0 2,alignx left,aligny center");
		getPanelFormulario().add(getTfFechaFin(), "cell 0 3,growx,aligny top");
		getPanelFormulario().add(getLbCuota(), "cell 0 4,alignx left,aligny center");
		getPanelFormulario().add(getTfCuota(), "cell 0 5,growx,aligny top");
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

	private JLabel getLbCuota() {
		if (lbCuota == null) {
			lbCuota = new JLabel("Cuota:");
		}
		return lbCuota;
	}

	private JTextField getTfCuota() {
		if (tfCuota == null) {
			tfCuota = new JTextField();
			tfCuota.setColumns(20);
		}
		return tfCuota;
	}
	
}
