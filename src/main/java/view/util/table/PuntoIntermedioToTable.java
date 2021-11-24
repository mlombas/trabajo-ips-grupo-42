package view.util.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.competicion.PuntoIntermedioDto;

public class PuntoIntermedioToTable extends JTable {

	private static final long serialVersionUID = 1L;
	private static final String[] columnNames = { "distanciaSalida"};

	public PuntoIntermedioToTable(List<PuntoIntermedioDto> puntosIntermedios) {
		setDefaultEditor(Object.class, null); // hacer que no sea editable

		DefaultTableModel model = new DefaultTableModel(null, columnNames);

		for (PuntoIntermedioDto pi : puntosIntermedios) {
			Object[] fila = new Object[columnNames.length];

			fila[0] = pi.distanciaSalida;

			model.addRow(fila);
		}

		this.setModel(model);
	}

}
