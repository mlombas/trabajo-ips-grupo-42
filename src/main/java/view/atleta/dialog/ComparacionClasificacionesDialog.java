package view.atleta.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
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
import view.util.table.ClasificacionesToTable;



//if (scrolComparación == null) {
//scrolComparación = new JScrollPane();
//List<ClasificacionDto> clasificacion;
//clasificacion = ModelFactory.forCarreraCrudService().getClasificacionUsuario(atleta, competicion);
//
//TableModel model = new ClasificacionesToTable(clasificacion).getModel();
//getTableParticipantes().setModel(model);
//}
//return scrolComparación;

//private JScrollPane getScrollParticipantes() {
//	if (scrollParticipantes == null) {
//		scrollParticipantes = new JScrollPane();
//		List<ClasificacionDto> clasificacion;
//		clasificacion = ModelFactory.forCarreraCrudService().GetClasificacion(competicion);
//		
//		TableModel model = new ClasificacionesToTable(clasificacion).getModel();
//		getTableParticipante().setModel(model);
//
//		scrollParticipantes.setViewportView(getTableParticipante());
//	}
//	return scrollParticipantes;
//}

public class ComparacionClasificacionesDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private CompeticionDto competicion;
	private AtletaDto atleta;
	private JPanel pnNombre;
	private JLabel lbNombre;
	private JTextField tfNombre;
	private JButton btnBuscarAtleta;
	private JTable tableParticipantes;
	private JTable tableComparacion;
	private JButton btnComparar;
	private JScrollPane scrollParticipantes;
	private JTable tableParticipante;

	public ComparacionClasificacionesDialog(InscripcionDto inscripcion) {
		this.competicion = ModelFactory.forCarreraCrudService().getCompeticionByInscripcion(inscripcion);
		this.atleta = ModelFactory.forAtletaCrudService().getAtletaByInscripcion(inscripcion);

		setSize(new Dimension(653, 517));
		setLocationRelativeTo(null);
		setResizable(true);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPnNombre(), BorderLayout.NORTH);
		getContentPane().add(getScrollParticipantes(), BorderLayout.CENTER);
		getContentPane().add(getBtnComparar(), BorderLayout.SOUTH);
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
					// TODO
				}
			});
		}
		return btnBuscarAtleta;
	}
	private JButton getBtnComparar() {
		if (btnComparar == null) {
			btnComparar = new JButton("Comparar");
			btnComparar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//TODO
				}
			});
		}
		return btnComparar;
	}
	private JScrollPane getScrollParticipantes() {
		if (scrollParticipantes == null) {
			scrollParticipantes = new JScrollPane();
			List<ClasificacionDto> clasificacion;
			clasificacion = ModelFactory.forCarreraCrudService().GetClasificacion(competicion);
			
			TableModel model = new ClasificacionesToTable(clasificacion).getModel();
			getTableParticipante().setModel(model);

			scrollParticipantes.setViewportView(getTableParticipante());
		}
		return scrollParticipantes;
	}
	
	private JTable getTableParticipante() {
		if (tableParticipante == null) {
			tableParticipante = new JTable();
		}
		return tableParticipante;
	}
}
