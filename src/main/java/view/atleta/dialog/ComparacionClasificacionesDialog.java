package view.atleta.dialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import model.ModelFactory;
import model.atleta.AtletaDto;
import model.competicion.ClasificacionDto;
import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;
import net.miginfocom.swing.MigLayout;
import util.exceptions.ApplicationException;
import view.util.table.ClasificacionesToTable;

public class ComparacionClasificacionesDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private CompeticionDto competicion;
	private AtletaDto atleta;
	private JPanel pnNombre;
	private JLabel lbNombre;
	private JTextField tfNombre;
	private JButton btnBuscarAtleta;
	private JButton btnComparar;
	private JScrollPane scrollParticipantes;
	private JTable tableParticipantes;
	private JScrollPane scrollComparacion;
	private JTable tableComparacion;

	public ComparacionClasificacionesDialog(InscripcionDto inscripcion) {
		this.competicion = ModelFactory.forCarreraCrudService().getCompeticionByInscripcion(inscripcion);
		this.atleta = ModelFactory.forAtletaCrudService().getAtletaByInscripcion(inscripcion);

		setSize(new Dimension(653, 517));
		setLocationRelativeTo(null);
		setResizable(true);
		getContentPane().setLayout(new MigLayout("", "[637px,grow]", "[][grow][][grow]"));
		getContentPane().add(getPnNombre(), "cell 0 0,growx,aligny top");
		getContentPane().add(getScrollParticipantes(), "cell 0 1,grow");
		getContentPane().add(getBtnComparar(), "cell 0 2,growx,aligny top");
		getContentPane().add(getScrollComparacion(), "cell 0 3,grow");
	}

	private JPanel getPnNombre() {
		if (pnNombre == null) {
			pnNombre = new JPanel();
			pnNombre.add(getLbNombre());
			pnNombre.add(getTfNombre());
			pnNombre.add(getBtnBuscarAtleta());
		}
		return pnNombre;
	}

	private JLabel getLbNombre() {
		if (lbNombre == null) {
			lbNombre = new JLabel("Nombre :");
		}
		return lbNombre;
	}

	private JTextField getTfNombre() {
		if (tfNombre == null) {
			tfNombre = new JTextField();
			tfNombre.setColumns(10);
		}
		return tfNombre;
	}

	private JButton getBtnBuscarAtleta() {
		if (btnBuscarAtleta == null) {
			btnBuscarAtleta = new JButton("Buscar atleta");
			btnBuscarAtleta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buscarAtleta();
				}
			});
		}
		return btnBuscarAtleta;
	}
	
	private void buscarAtleta() {
		if (!checkNombre()) {
			List<ClasificacionDto> clasificacion;
			clasificacion = ModelFactory.forCarreraCrudService().GetClasificacion(competicion);

			TableModel model = new ClasificacionesToTable(clasificacion).getModel();
			getTableParticipantes().setModel(model);

			scrollParticipantes.setViewportView(getTableParticipantes());
		} else {
			try {
				AtletaDto atleta = new AtletaDto();
				atleta.nombre = tfNombre.getText();			
				List<ClasificacionDto> clasificaciones;
				clasificaciones = ModelFactory.forCarreraCrudService().getClasificacionesByNombre(competicion,atleta);
				
				TableModel model = new ClasificacionesToTable(clasificaciones).getModel();
				getTableParticipantes().setModel(model);

				scrollParticipantes.setViewportView(getTableParticipantes());
			} catch (ApplicationException e) {
				showMessage(e.getMessage(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
			} catch (RuntimeException e) {
				showMessage(e.toString(), "Excepcion no controlada", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private boolean checkNombre() {
		if (tfNombre.getText().strip().isEmpty())
			return false;
		return true;
	}

	private void showMessage(String message, String title, int type) {
		JOptionPane pane = new JOptionPane(message, type, JOptionPane.DEFAULT_OPTION);
		pane.setOptions(new Object[] { "ACEPTAR" }); 
		JDialog d = pane.createDialog(pane, title);
		d.setLocationRelativeTo(null);
		d.setVisible(true);

	}

	private JButton getBtnComparar() {
		if (btnComparar == null) {
			btnComparar = new JButton("Comparar");
			btnComparar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getComparacion();
				}
			});
		}
		return btnComparar;
	}
	

	private void getComparacion() {
		ClasificacionDto selectedAtleta = new ClasificacionDto();
		
		try {
			
			int index = getTableParticipantes().getSelectedRow();
			
			String dorsal = getTableParticipantes().getModel().getValueAt(index, 1).toString();

			if(dorsal.equals("NP")) {
				showMessage("Este atleta no se ha presentado a la carrera", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			} else {
				
				selectedAtleta.dorsal = Integer.parseInt(dorsal);
				List<ClasificacionDto> clasificacion;
				
				clasificacion = ModelFactory.forCarreraCrudService().getClasificacionUsuario(atleta, competicion);
				clasificacion.add(ModelFactory.forCarreraCrudService().getClasificacionByDorsal(selectedAtleta, competicion).get(0));

				TableModel model = new ClasificacionesToTable(clasificacion).getModel();
				getTableComparacion().setModel(model);
			}
		} catch (ArrayIndexOutOfBoundsException aiobe) {
			JOptionPane.showMessageDialog(null, "Seleccione un atleta para poder comparar");
			return;
		}
		
	}

	private JScrollPane getScrollParticipantes() {
		if (scrollParticipantes == null) {
			scrollParticipantes = new JScrollPane();
			List<ClasificacionDto> clasificacion;
			clasificacion = ModelFactory.forCarreraCrudService().GetClasificacion(competicion);

			TableModel model = new ClasificacionesToTable(clasificacion).getModel();
			getTableParticipantes().setModel(model);

			scrollParticipantes.setViewportView(getTableParticipantes());
		}
		return scrollParticipantes;
	}

	private JTable getTableParticipantes() {
		if (tableParticipantes == null) {
			tableParticipantes = new JTable();
		}
		return tableParticipantes;
	}

	private JScrollPane getScrollComparacion() {
		if (scrollComparacion == null) {
			scrollComparacion = new JScrollPane();
			List<ClasificacionDto> clasificacion;
			clasificacion = ModelFactory.forCarreraCrudService().getClasificacionUsuario(atleta, competicion);

			TableModel model = new ClasificacionesToTable(clasificacion).getModel();
			getTableComparacion().setModel(model);

			scrollComparacion.setViewportView(getTableComparacion());
		}
		return scrollComparacion;
	}

	private JTable getTableComparacion() {
		if (tableComparacion == null) {
			tableComparacion = new JTable();
		}
		return tableComparacion;
	}
}
