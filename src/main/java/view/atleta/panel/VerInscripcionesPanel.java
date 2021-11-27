package view.atleta.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.TableColumnModel;

import controller.atleta.AtletaCrudServiceImpl;
import model.atleta.AtletaDto;
import model.inscripcion.InscripcionDto;
import util.exceptions.ApplicationException;
import view.atleta.AtletaMain;
import view.atleta.dialog.ComparacionClasificacionesDialog;
import view.atleta.util.AtrasAtletaButton;
import view.util.table.InscripcionesToTable;

public class VerInscripcionesPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JScrollPane competicionesPane;

	private JPanel pnEmail;
	private JLabel lbEmail;
	private JTextField tfEmail;
	private JButton btnBuscarCompeticion;
	private JPanel pnBotones;
	private InscripcionesToTable inscripcionesTable;
	private AtrasAtletaButton btnAtras;
	private JButton btnClasificacion;
	private List<InscripcionDto> inscripciones = new ArrayList<>();

	/**
	 * Create the panel.
	 */
	public VerInscripcionesPanel() {
		setLayout(new BorderLayout(0, 0));
		add(getPnEmail(), BorderLayout.NORTH);
		add(getCompeticionesPane(), BorderLayout.CENTER);
		add(getPnBotones(), BorderLayout.SOUTH);

	}

	private JScrollPane getCompeticionesPane() {
		if (competicionesPane == null) {
			competicionesPane = new JScrollPane();
			competicionesPane.setViewportView(getInscripcionesTable());
		}
		return competicionesPane;
	}

	private JPanel getPnEmail() {
		if (pnEmail == null) {
			pnEmail = new JPanel();
			pnEmail.add(getLbEmail());
			pnEmail.add(getTfEmail());
			pnEmail.add(getBtnBuscarCompeticion());
		}
		return pnEmail;
	}

	private JLabel getLbEmail() {
		if (lbEmail == null) {
			lbEmail = new JLabel("Email:");
		}
		return lbEmail;
	}

	private JTextField getTfEmail() {
		if (tfEmail == null) {
			tfEmail = new JTextField();
			tfEmail.setColumns(10);
		}
		return tfEmail;
	}

	private JButton getBtnBuscarCompeticion() {
		if (btnBuscarCompeticion == null) {
			btnBuscarCompeticion = new JButton("Buscar competiciones");
			btnBuscarCompeticion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buscarCompeticiones();
				}
			});
		}
		return btnBuscarCompeticion;
	}

	private void buscarCompeticiones() {
		if (!checkEmail()) {
			showMessage("No hay ningún email con el que iniciar la búsqueda", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		} else {
			try {
				// Establecemos el atleta sobre el que queremos ver las inscripciones
				AtletaDto atleta = new AtletaDto();
				atleta.email = tfEmail.getText();
				
				// Obtenemos las inscripciones
				inscripciones =  new AtletaCrudServiceImpl().getCompetionesInscritas(atleta);
				inscripcionesTable = new InscripcionesToTable(inscripciones);
				
				// Eliminamos columnas
				TableColumnModel tcm = inscripcionesTable.getColumnModel();
				tcm.removeColumn(tcm.getColumn(0)); 
				tcm.removeColumn(tcm.getColumn(1)); 
				tcm.removeColumn(tcm.getColumn(1));
				
				// Re-actualizamos la tabla
				competicionesPane.setViewportView(inscripcionesTable);
				competicionesPane.revalidate();
			} catch (ApplicationException e) {
				showMessage(e.getMessage(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
			} catch (RuntimeException e) {
				showMessage(e.toString(), "Excepcion no controlada", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void showMessage(String message, String title, int type) {
		JOptionPane pane = new JOptionPane(message, type, JOptionPane.DEFAULT_OPTION);
		pane.setOptions(new Object[] { "ACEPTAR" });
		JDialog d = pane.createDialog(pane, title);
		d.setLocationRelativeTo(null);
		d.setVisible(true);

	}

	private boolean checkEmail() {
		if (tfEmail.getText().strip().isEmpty())
			return false;
		return true;
	}

	private InscripcionesToTable getInscripcionesTable() {
		if (inscripcionesTable == null) {
			inscripcionesTable = new InscripcionesToTable(inscripciones);

			// Eliminamos columnas que no nos interesan
			TableColumnModel tcm = inscripcionesTable.getColumnModel();
			tcm.removeColumn(tcm.getColumn(0)); 
			tcm.removeColumn(tcm.getColumn(1)); 
			tcm.removeColumn(tcm.getColumn(1));
		}
		return inscripcionesTable;
	}

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new GridLayout(0, 2, 0, 0));
			pnBotones.add(getBtnClasificacion());
			pnBotones.add(getBtnAtras());
		}
		return pnBotones;
	}

	private AtrasAtletaButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new AtrasAtletaButton(AtletaMain.ATLETAS_MENU);
		}
		return btnAtras;
	}

	private JButton getBtnClasificacion() {
		if (btnClasificacion == null) {
			btnClasificacion = new JButton("Ver Clasificación");
			btnClasificacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getClasificacion();
				}
			});
		}
		return btnClasificacion;
	}

	private void getClasificacion() {
		InscripcionDto inscripcion = new InscripcionDto();

		try {

			int index = inscripcionesTable.getSelectedRow();

			inscripcion.idCompeticion = inscripcionesTable.getModel().getValueAt(index, 0).toString();
			inscripcion.emailAtleta = inscripcionesTable.getModel().getValueAt(index, 3).toString();

			if (!inscripcionesTable.getModel().getValueAt(index, 8).toString().equals("PARTICIPADO")) {
				JOptionPane.showMessageDialog(null, "Seleccione una carrera que haya acabado");
				return;
			}

		} catch (ArrayIndexOutOfBoundsException aiobe) {
			JOptionPane.showMessageDialog(null, "Seleccione una carrera...");
			return;
		}

		showCreaComparacion(inscripcion);

	}

	private void showCreaComparacion(InscripcionDto inscripcion) {
		ComparacionClasificacionesDialog comparacionesDialog = new ComparacionClasificacionesDialog(inscripcion);
		comparacionesDialog.setLocationRelativeTo(null);
		comparacionesDialog.setModal(true);
		comparacionesDialog.setVisible(true);
	}

}
