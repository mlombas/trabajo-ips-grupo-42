package view.organizador;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

import model.ModelFactory;
import model.competicion.CompeticionDto;
import view.util.CompeticionesToTable;
import javax.swing.JTextField;

public class VerCarrerasPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JScrollPane competicionesPane;
	private JPanel btnPane;
	private JPanel competicionManagementPane;
	private JButton btnVerEstado;
	private JButton btnVerClasificaciones;
	private JButton btnAtras;
	
	private OrganizadorMain main;
	
	CompeticionesToTable table;
	private JPanel verClasificacionesPane;
	private JTextField textCategoria;

	public VerCarrerasPanel(OrganizadorMain main) {
		this.main = main;
		
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
	
	private String getCompeticionId() {
		String competicionId = "";
		
		try {
			int row = table.getSelectedRow();
			int column = 0;
			
			competicionId = table.getValueAt(row, column) + "";
		} catch (ArrayIndexOutOfBoundsException aiaobe) {
			return "";
		}
		
		return competicionId;
	}

	private JScrollPane getCompeticionesPane() {
		if (competicionesPane == null) {
			competicionesPane = new JScrollPane();
			List<CompeticionDto> competiciones = ModelFactory.forCarreraCrudService().GetAllCompeticiones();
			table = new CompeticionesToTable(competiciones);
			competicionesPane.setViewportView(table);
		}
		
		return competicionesPane;
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
	
	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atrás");
			
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					main.flipCard(OrganizadorMain.ORGANIZADOR_MENU);
				}
			});
		}
		return btnAtras;
	}
	
	private JPanel getCompeticionManagementPane() {
		if (competicionManagementPane == null) {
			competicionManagementPane = new JPanel();
			competicionManagementPane.setLayout(new GridLayout(2, 0, 0, 0));
			competicionManagementPane.add(getVerClasificacionesPane());
			competicionManagementPane.add(getBtnVerEstado());
		}
		return competicionManagementPane;
	}
	
	private JButton getBtnVerEstado() {
		if (btnVerEstado == null) {
			btnVerEstado = new JButton("Ver Estado");
			
			btnVerEstado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CompeticionDto competicion = new CompeticionDto();
					competicion.id = getCompeticionId();
					
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
			verClasificacionesPane.add(getTextCategoria());
		}
		return verClasificacionesPane;
	}
	
	private JButton getBtnVerClasificaciones() {
		if (btnVerClasificaciones == null) {
			btnVerClasificaciones = new JButton("Ver Clasificaciones");
			
			btnVerClasificaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CompeticionDto competicion = new CompeticionDto();
					competicion.id = getCompeticionId();
					
					if(competicion.id.trim().isEmpty())
						JOptionPane.showMessageDialog(null, "Seleccione una carrera...");
					else
						showClasificacion(competicion, getTextCategoria().getText());
				}
			});
		}
		return btnVerClasificaciones;
	}
	
	private JTextField getTextCategoria() {
		if (textCategoria == null) {
			textCategoria = new JTextField();
			textCategoria.setToolTipText("Indique la categoría que quiere buscar");
			textCategoria.setColumns(10);
		}
		return textCategoria;
	}
	
}