package view.organizador.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.competicion.CompeticionCrudService;
import controller.competicion.CompeticionCrudServiceImpl;
import model.ModelFactory;
import java.util.ArrayList;
import model.competicion.CategoriaDto;
import model.competicion.CompeticionDto;
import util.exceptions.ApplicationException;
import view.organizador.OrganizadorMain;
import util.exceptions.ModelException;

import view.organizador.dialog.VerClasficacionDialog;
import view.organizador.dialog.VerEstadoInscripcionDialog;
import view.organizador.util.AtrasOrganizadorButton;
import view.util.panel.VerCompeticionesPanel;

public class GestionarCompeticionesPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private VerCompeticionesPanel verCompeticionesPane;
	private JPanel btnPane;
	private JPanel competicionManagementPane;
	private JButton btnVerEstado;
	private JButton btnVerClasificaciones;
	private AtrasOrganizadorButton btnAtras;
	
	private static GestionarCompeticionesPanel instance;
	
	private JPanel verClasificacionesPane;
	private JComboBox<String> cbCategorias;
	private JButton btnCargarTiempos;
	private JButton btnGenerarDorsales;

	private GestionarCompeticionesPanel() {
		setLayout(new BorderLayout(0, 0));
		add(getCompeticionesPane(), BorderLayout.CENTER);
		add(getBtnPane(), BorderLayout.SOUTH);
	}
	
	public void refreshCompetitions() {
		try {
			verCompeticionesPane.updateCompeticiones(ModelFactory
													.forCarreraCrudService()
													.GetAllCompeticiones());
		} catch (ModelException e) {
			verCompeticionesPane.updateCompeticiones(new ArrayList<CompeticionDto>());
		}
	}

	private void showClasificacion(CompeticionDto competicion, String categoria) {
		VerClasficacionDialog clasificacionDialog = new VerClasficacionDialog(competicion, categoria);
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

	private VerCompeticionesPanel getCompeticionesPane() {
		if (verCompeticionesPane == null) {
			verCompeticionesPane = new VerCompeticionesPanel();
		return verCompeticionesPane;
	}

	private JPanel getBtnPane() {
		if (btnPane == null) {
			btnPane = new JPanel();
			btnPane.setLayout(new GridLayout(0, 2, 0, 0));
			btnPane.add(getCompeticionManagementPane());
			btnPane.add(getBtnCargarTiempos());
			btnPane.add(getBtnGenerarDorsales());
			btnPane.add(getBtnAtras_1());
		}
		return btnPane;
	}

	private JPanel getCompeticionManagementPane() {
		if (competicionManagementPane == null) {
			competicionManagementPane = new JPanel();
			competicionManagementPane.setLayout(new GridLayout(2, 0, 0, 0));
			competicionManagementPane.add(getBtnVerEstado());
			competicionManagementPane.add(getVerClasificacionesPane());
		}
		return competicionManagementPane;
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
					
					if(competicion.id.trim().isEmpty())
						return;
					}
					
				}
			});
		}
		return btnVerEstado;
	}

	private JPanel getVerClasificacionesPane() {
		if (verClasificacionesPane == null) {
			verClasificacionesPane = new JPanel();
			verClasificacionesPane.setLayout(new GridLayout(0, 2, 0, 0));
			verClasificacionesPane.add(getBtnVerClasificaciones());
			verClasificacionesPane.add(getCbCategorias());
		}
		return verClasificacionesPane;
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
					
					if(competicion.id.trim().isEmpty())
						JOptionPane.showMessageDialog(null, "Seleccione una carrera...");
						return;
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

	private AtrasOrganizadorButton getBtnAtras_1() {
		if (btnAtras == null) {
			btnAtras = new AtrasOrganizadorButton("organizadores");
		}
		return btnAtras;
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
			e.printStackTrace();
			showMessage(e.toString(), "Excepcion no controlada", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void showMessage(String message, String title, int type) {
		JOptionPane pane = new JOptionPane(message, type, JOptionPane.DEFAULT_OPTION);
		pane.setOptions(new Object[] { "ACEPTAR" });
		JDialog d = pane.createDialog(pane, title);
		d.setLocation(200, 200);
		d.setVisible(true);

	}

	private JButton getBtnGenerarDorsales() {
		if (btnGenerarDorsales == null) {
			btnGenerarDorsales = new JButton("Generar Dorsales");
		}
		return btnGenerarDorsales;
	}

	public void updateCategorias() {
		cbCategorias.removeAllItems();
		cbCategorias.addItem("Absoluta");
		for(CategoriaDto cat : ModelFactory.forCarreraCrudService().GetCategoria(verCompeticionesPane.getCompeticionId())) {
			cbCategorias.addItem(cat.nombreCategoria);
			System.out.println(cat.nombreCategoria);
		}
		
		System.out.println("updated");
	}

	public static GestionarCompeticionesPanel getInstance() {
		if(instance == null) {
			instance = new GestionarCompeticionesPanel();
		}
		return instance;
	}
}