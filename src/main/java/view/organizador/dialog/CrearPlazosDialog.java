package view.organizador.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import controller.competicion.CompeticionCrudService;
import controller.competicion.CompeticionCrudServiceImpl;
import model.competicion.CompeticionDto;
import model.competicion.PlazoInscripcionDto;
import util.Validate;
import util.exceptions.ApplicationException;
import view.util.table.PlazosToTable;

public class CrearPlazosDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JScrollPane competicionesPane;
	private JTable tabPlazos;
	private JPanel panel;
	private JPanel pnDatosPlazos;
	private JPanel pnBotonesPlazos;
	private JButton btnAñadir;
	private JPanel pnFechaInicio;
	private JPanel pnFechaFin;
	private JPanel pnCuota;
	private JLabel lbFechaInicio;
	private JTextField tfFechaInicio;
	private JLabel lbFechaFin;
	private JTextField tfFechaFin;
	private JLabel lbCuota;
	private JTextField tfCuota;
	private JButton btnConfirmar;

	private CompeticionDto comp;

	/**
	 * Create the panel.
	 */
	public CrearPlazosDialog(CompeticionDto comp) {
		setSize(new Dimension(600, 600));
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getCompeticionesPane(), BorderLayout.CENTER);
		getContentPane().add(getPanel(), BorderLayout.SOUTH);
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

	private void showMessage(String message, String title, int type) {
		JOptionPane pane = new JOptionPane(message, type, JOptionPane.DEFAULT_OPTION);
		pane.setOptions(new Object[] { "ACEPTAR" });
		JDialog d = pane.createDialog(pane, title);
		d.setLocationRelativeTo(null);
		d.setVisible(true);

	}

	private JTable getTabPlazos() {
		if (tabPlazos == null) {
			tabPlazos = new JTable();
		}
		return tabPlazos;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new GridLayout(0, 1, 0, 0));
			panel.add(getPnDatosPlazos());
			panel.add(getPnBotonesPlazos());
		}
		return panel;
	}

	private JPanel getPnDatosPlazos() {
		if (pnDatosPlazos == null) {
			pnDatosPlazos = new JPanel();
			pnDatosPlazos.setLayout(new GridLayout(3, 0, 0, 0));
			pnDatosPlazos.add(getPnFechaInicio());
			pnDatosPlazos.add(getPnFechaFin());
			pnDatosPlazos.add(getPnCuota());
		}
		return pnDatosPlazos;
	}

	private JPanel getPnBotonesPlazos() {
		if (pnBotonesPlazos == null) {
			pnBotonesPlazos = new JPanel();
			pnBotonesPlazos.setLayout(new GridLayout(0, 2, 0, 0));
			pnBotonesPlazos.add(getBtnAñadir());
			pnBotonesPlazos.add(getBtnConfirmar());
		}
		return pnBotonesPlazos;
	}

	private JButton getBtnAñadir() {
		if (btnAñadir == null) {
			btnAñadir = new JButton("Añadir");
			btnAñadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					añadirPlazo();
				}
			});
		}
		return btnAñadir;
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
				
				TableModel model = new PlazosToTable(plazos).getModel();
				getTabPlazos().setModel(model);
				

				
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

	private JPanel getPnFechaInicio() {
		if (pnFechaInicio == null) {
			pnFechaInicio = new JPanel();
			pnFechaInicio.add(getLbFechaInicio());
			pnFechaInicio.add(getTfFechaInicio());
		}
		return pnFechaInicio;
	}

	private JPanel getPnFechaFin() {
		if (pnFechaFin == null) {
			pnFechaFin = new JPanel();
			pnFechaFin.add(getLbFechaFin());
			pnFechaFin.add(getTfFechaFin());
		}
		return pnFechaFin;
	}

	private JPanel getPnCuota() {
		if (pnCuota == null) {
			pnCuota = new JPanel();
			pnCuota.add(getLbCuota());
			pnCuota.add(getTfCuota());
		}
		return pnCuota;
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

	private JButton getBtnConfirmar() {
		if (btnConfirmar == null) {
			btnConfirmar = new JButton("Confirmar");
			btnConfirmar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					checkPlazos();
				}
			});
		}
		return btnConfirmar;
	}
	

	private void checkPlazos() {
		try {
			CompeticionCrudService ccs = new CompeticionCrudServiceImpl();		
			ccs.checkPlazosByIdCompeticion(comp.id);
			
			dispose();
		} catch (ApplicationException e) {
			showMessage(e.getMessage(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
		} catch (RuntimeException e) { 
			e.printStackTrace(); 
			showMessage(e.toString(), "Excepcion no controlada", JOptionPane.ERROR_MESSAGE);
		}
	}
}
