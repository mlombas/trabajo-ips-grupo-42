package view.organizador.panel;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

import model.competicion.CompeticionDto;
import view.organizador.OrganizadorMain;
import view.organizador.dialog.VerClasficacionDialog;
import view.organizador.dialog.VerEstadoInscripcionDialog;
import view.organizador.util.AtrasOrganizadorButton;
import view.util.panel.VerCompeticionesPanel;

import javax.swing.JComboBox;

public class GestionarCompeticionesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private VerCompeticionesPanel verCompeticionesPane;
	private JPanel btnPane;
	private JPanel competicionManagementPane;
	private JButton btnVerEstado;
	private JButton btnVerClasificaciones;
	private AtrasOrganizadorButton btnAtras;
	
	private JPanel verClasificacionesPane;
	private JComboBox<String> cbCategorias;

	public GestionarCompeticionesPanel() {
		setLayout(new BorderLayout(0, 0));
		add(getCompeticionesPane(), BorderLayout.CENTER);
		add(getBtnPane(), BorderLayout.SOUTH);

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
		if (verCompeticionesPane == null)
			verCompeticionesPane = new VerCompeticionesPanel();
		
		return verCompeticionesPane;
	}
	
	private JPanel getBtnPane() {
		if (btnPane == null) {
			btnPane = new JPanel();
			btnPane.setLayout(new GridLayout(0, 2, 0, 0));
			btnPane.add(getCompeticionManagementPane());
			btnPane.add(getBtnAtras());
		}
		return btnPane;
	}
	
	private AtrasOrganizadorButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new AtrasOrganizadorButton(OrganizadorMain.ORGANIZADOR_MENU);
		}
		return btnAtras;
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
					competicion.id = verCompeticionesPane.getCompeticionId();
					
					if(competicion.id.trim().isEmpty())
						return;
					else
						showEstadoInscripcion(competicion);
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
					competicion.id = verCompeticionesPane.getCompeticionId();
					
					if(competicion.id.trim().isEmpty())
						JOptionPane.showMessageDialog(null, "Seleccione una carrera...");
					else
						showClasificacion(competicion, getCbCategorias().getSelectedItem().toString());
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
}