package view.organizador.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.competicion.CompeticionCrudService;
import controller.competicion.CompeticionCrudServiceImpl;
import model.ModelFactory;
import model.competicion.CategoriaDto;
import model.competicion.CompeticionDto;
import util.exceptions.ApplicationException;
import util.exceptions.ModelException;
import view.organizador.OrganizadorMain;
import view.organizador.dialog.VerClasficacionDialog;
import view.organizador.dialog.VerEstadoInscripcionDialog;
import view.organizador.dialog.VerPlazosDialog;
import view.organizador.dialog.VerProcesadoDialog;
import view.organizador.util.AtrasOrganizadorButton;
import view.util.panel.VerCompeticionesPanel;
import javax.swing.JCheckBox;

public class GestionarCompeticionesPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private VerCompeticionesPanel verCompeticionesPane;
	private JPanel btnPane;
	private JPanel competicionManagementPane;
	private JButton btnVerEstado;
	private JButton btnVerClasificaciones;
	private AtrasOrganizadorButton btnAtras;
	private JComboBox<String> cbCategorias;
	private JButton btnCargarTiempos;
	private JButton btnGenerarDorsales;

	private JButton btnProcesar;
	private JButton btnPlazos;
	private JPanel pnCargarDatos;
	private JPanel pnClasificaciones;
	private JPanel pnVerClas;
	private JPanel pnFiltros;
	private JCheckBox chckbxPuntosControl;
	private JCheckBox chckbxClub;
	private JCheckBox chckbxTiempo;
	private JCheckBox chckbxDiferencia;

	public GestionarCompeticionesPanel() {
		setLayout(new BorderLayout(0, 0));
		add(getCompeticionesPane(), BorderLayout.CENTER);
		add(getBtnPane(), BorderLayout.SOUTH);
	}

	public void refreshCompetitions() {
		try {
			verCompeticionesPane.updateCompeticiones(ModelFactory.forCarreraCrudService().GetAllCompeticiones());
		} catch (ModelException e) {
			verCompeticionesPane.updateCompeticiones(new ArrayList<CompeticionDto>());
		}
	}

	public void updateCategorias() {
		cbCategorias.removeAllItems();
		cbCategorias.addItem("Absoluta");
		try {
			for (CategoriaDto cat : ModelFactory.forCarreraCrudService()
					.GetCategoria(verCompeticionesPane.getCompeticionId())) {
				cbCategorias.addItem(cat.nombreCategoria);
			}
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}

	private void showClasificacion(CompeticionDto competicion, String categoria) throws ModelException {
		VerClasficacionDialog clasificacionDialog = new VerClasficacionDialog(competicion, categoria,
				chckbxClub.isSelected(), chckbxTiempo.isSelected(), chckbxDiferencia.isSelected(),
				chckbxPuntosControl.isSelected());
		clasificacionDialog.setLocationRelativeTo(null);
		clasificacionDialog.setModal(true);
		clasificacionDialog.setVisible(true);

	}

	private void showEstadoInscripcion(CompeticionDto competicion) {
		VerEstadoInscripcionDialog estadoInscripcionDialog = new VerEstadoInscripcionDialog(competicion);
		estadoInscripcionDialog.setLocationRelativeTo(null);
		estadoInscripcionDialog.setModal(true);
		estadoInscripcionDialog.setVisible(true);
	}

	private void showMessage(String message, String title, int type) {
		JOptionPane pane = new JOptionPane(message, type, JOptionPane.DEFAULT_OPTION);
		pane.setOptions(new Object[] { "ACEPTAR" });
		JDialog d = pane.createDialog(pane, title);
		d.setLocationRelativeTo(null);
		d.setVisible(true);
	}

	private void cargarTiempos(CompeticionDto competicion) {
		try {
			CompeticionCrudService ccs = new CompeticionCrudServiceImpl();

			List<Integer> integers = ccs.cargarTiempos(competicion);
			showMessage(
					"Se han cargado los tiempos de la competición " + competicion.nombreCarrera
							+ ": \nTiempos cargados correctamente: " + integers.get(0)
							+ "\nTiempos que no ha sido posible cargar: " + integers.get(1),
					"\nInformacion", JOptionPane.INFORMATION_MESSAGE);
		} catch (ApplicationException e) {
			showMessage(e.getMessage(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
		} catch (RuntimeException e) {
			showMessage(e.toString(), "Excepcion no controlada", JOptionPane.ERROR_MESSAGE);
		}
	}

	private VerCompeticionesPanel getCompeticionesPane() {
		if (verCompeticionesPane == null) {
			verCompeticionesPane = new VerCompeticionesPanel();
			refreshCompetitions();
			verCompeticionesPane.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					OrganizadorMain.getInstance().getBuscarCompeticionPane().updateCategorias();
				}
			});
		}
		return verCompeticionesPane;
	}

	private JPanel getBtnPane() {
		if (btnPane == null) {
			btnPane = new JPanel();
			btnPane.setLayout(new GridLayout(0, 2, 0, 0));
			btnPane.add(getCompeticionManagementPane());
			btnPane.add(getPnCargarDatos());
			btnPane.add(getPnClasificaciones());
			btnPane.add(getBtnAtras());
		}
		return btnPane;
	}

	private JButton getBtnCargarTiempos() {
		if (btnCargarTiempos == null) {
			btnCargarTiempos = new JButton("CargarTiempos");
			btnCargarTiempos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						CompeticionDto competicion = new CompeticionDto();
						competicion.id = verCompeticionesPane.getCompeticionId();
						competicion.nombreCarrera = verCompeticionesPane.getNombreCompeticion();

						if (competicion.id.trim().isEmpty())
							JOptionPane.showMessageDialog(null, "Seleccione una carrera...");
						else
							cargarTiempos(competicion);
					} catch (ArrayIndexOutOfBoundsException aiobe) {
						JOptionPane.showMessageDialog(null, "Seleccione una carrera...");
						return;
					}
				}
			});
		}
		return btnCargarTiempos;
	}

	private JButton getBtnGenerarDorsales() {
		if (btnGenerarDorsales == null) {
			btnGenerarDorsales = new JButton("Generar Dorsales");
			btnGenerarDorsales.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						CompeticionDto competicion = new CompeticionDto();
						competicion.id = verCompeticionesPane.getCompeticionId();
						competicion.nombreCarrera = verCompeticionesPane.getNombreCompeticion();

						if (competicion.id.trim().isEmpty())
							JOptionPane.showMessageDialog(null, "Seleccione una carrera...");
						else
							generarDorsales(competicion);
					} catch (ArrayIndexOutOfBoundsException aiobe) {
						JOptionPane.showMessageDialog(null, "Seleccione una carrera...");
						return;
					}
				}
			});
		}
		return btnGenerarDorsales;
	}

	private void generarDorsales(CompeticionDto competicion) {
		try {
			CompeticionCrudService ccs = new CompeticionCrudServiceImpl();

			List<Integer> integers = ccs.generarDorsales(competicion);
			refreshCompetitions();

			showMessage(
					"Se han generado los dorsales no reservados  de la carrera" + competicion.nombreCarrera
							+ ": \nDorsales generados: " + integers.get(0),
					"\nInformacion", JOptionPane.INFORMATION_MESSAGE);

		} catch (ApplicationException e) {
			showMessage(e.getMessage(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
		} catch (RuntimeException e) {
			e.printStackTrace();
			showMessage(e.toString(), "Excepcion no controlada", JOptionPane.ERROR_MESSAGE);
		}

	}

	private AtrasOrganizadorButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new AtrasOrganizadorButton("organizadores");
		}
		return btnAtras;
	}

	private JPanel getCompeticionManagementPane() {
		if (competicionManagementPane == null) {
			competicionManagementPane = new JPanel();
			competicionManagementPane.setLayout(new GridLayout(2, 0, 0, 0));
			competicionManagementPane.add(getBtnVerEstado());
			competicionManagementPane.add(getBtnPlazos());
			competicionManagementPane.add(getBtnGenerarDorsales());
		}
		return competicionManagementPane;
	}

	private JButton getBtnProcesar() {
		if (btnProcesar == null) {
			btnProcesar = new JButton("Procesar CSV");
			btnProcesar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CompeticionDto competicion = new CompeticionDto();

					try {
						competicion.id = verCompeticionesPane.getCompeticionId();
					} catch (ArrayIndexOutOfBoundsException aiobe) {
						JOptionPane.showMessageDialog(null, "Seleccione una carrera...");
						return;
					}

					showProcesado(competicion);
				}

			});
		}
		return btnProcesar;
	}

	private void showProcesado(CompeticionDto competicion) {
		VerProcesadoDialog diag = new VerProcesadoDialog(competicion);
		diag.setLocationRelativeTo(null);
		diag.setVisible(true);
		diag.setModal(true);
	}

	private JButton getBtnVerEstado() {
		if (btnVerEstado == null) {
			btnVerEstado = new JButton("Ver Estado");

			btnVerEstado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CompeticionDto competicion = new CompeticionDto();

					try {
						competicion.id = verCompeticionesPane.getCompeticionId();
					} catch (ArrayIndexOutOfBoundsException aiobe) {
						JOptionPane.showMessageDialog(null, "Seleccione una carrera...");
						return;
					}

					showEstadoInscripcion(competicion);
				}
			});
		}
		return btnVerEstado;
	}

	private JButton getBtnVerClasificaciones() {
		if (btnVerClasificaciones == null) {
			btnVerClasificaciones = new JButton("Ver Clasificaciones");

			btnVerClasificaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CompeticionDto competicion = new CompeticionDto();

					try {
						competicion.id = verCompeticionesPane.getCompeticionId();
					} catch (ArrayIndexOutOfBoundsException aiobe) {
						JOptionPane.showMessageDialog(null, "Seleccione una carrera...");
						return;
					}
					try {
						showClasificacion(competicion, (String) getCbCategorias().getSelectedItem());
					} catch (ModelException exc) {
						JOptionPane.showMessageDialog(null, exc.getMessage());
					}
				}
			});
		}
		return btnVerClasificaciones;
	}

	private JComboBox<String> getCbCategorias() {
		if (cbCategorias == null) {
			cbCategorias = new JComboBox<String>();
			cbCategorias.addItem("Absoluta");
		}
		return cbCategorias;
	}

	private JButton getBtnPlazos() {
		if (btnPlazos == null) {
			btnPlazos = new JButton("Ver Plazos");
			btnPlazos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CompeticionDto competicion = new CompeticionDto();

					try {
						competicion.id = verCompeticionesPane.getCompeticionId();
					} catch (ArrayIndexOutOfBoundsException aiobe) {
						JOptionPane.showMessageDialog(null, "Seleccione una carrera...");
						return;
					}

					showVerPlazos(competicion);
				}
			});
		}
		return btnPlazos;
	}

	private void showVerPlazos(CompeticionDto competicion) {
		VerPlazosDialog plazosDialog = new VerPlazosDialog(competicion);
		plazosDialog.setLocationRelativeTo(null);
		plazosDialog.setModal(true);
		plazosDialog.setVisible(true);

	}

	private JPanel getPnCargarDatos() {
		if (pnCargarDatos == null) {
			pnCargarDatos = new JPanel();
			pnCargarDatos.setLayout(new GridLayout(1, 0, 0, 0));
			pnCargarDatos.add(getBtnCargarTiempos());
			pnCargarDatos.add(getBtnProcesar());
		}
		return pnCargarDatos;
	}

	private JPanel getPnClasificaciones() {
		if (pnClasificaciones == null) {
			pnClasificaciones = new JPanel();
			pnClasificaciones.setLayout(new GridLayout(0, 1, 0, 0));
			pnClasificaciones.add(getPnVerClas());
			pnClasificaciones.add(getPnFiltros());
		}
		return pnClasificaciones;
	}

	private JPanel getPnVerClas() {
		if (pnVerClas == null) {
			pnVerClas = new JPanel();
			pnVerClas.setLayout(new GridLayout(1, 1, 0, 0));
			pnVerClas.add(getBtnVerClasificaciones());
			pnVerClas.add(getCbCategorias());
		}
		return pnVerClas;
	}

	private JPanel getPnFiltros() {
		if (pnFiltros == null) {
			pnFiltros = new JPanel();
			pnFiltros.add(getChckbxPuntosControl());
			pnFiltros.add(getChckbxClub());
			pnFiltros.add(getChckbxTiempo());
			pnFiltros.add(getChckbxDiferencia());
		}
		return pnFiltros;
	}

	private JCheckBox getChckbxPuntosControl() {
		if (chckbxPuntosControl == null) {
			chckbxPuntosControl = new JCheckBox("Puntos de control");
		}
		return chckbxPuntosControl;
	}

	private JCheckBox getChckbxClub() {
		if (chckbxClub == null) {
			chckbxClub = new JCheckBox("Club");
		}
		return chckbxClub;
	}

	private JCheckBox getChckbxTiempo() {
		if (chckbxTiempo == null) {
			chckbxTiempo = new JCheckBox("Tiempo por kilómetro");
		}
		return chckbxTiempo;
	}

	private JCheckBox getChckbxDiferencia() {
		if (chckbxDiferencia == null) {
			chckbxDiferencia = new JCheckBox("Diferencia");
		}
		return chckbxDiferencia;
	}
}