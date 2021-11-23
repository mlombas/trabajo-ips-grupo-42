package view.util.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.competicion.CategoriaDto;

public class CategoriasToTable extends JTable {

	private static final long serialVersionUID = 1L;

	public CategoriasToTable(List<CategoriaDto> cats) {
		setDefaultEditor(Object.class, null); // hacer que no sea editable

		String[] columnNames = { "Nombre", "Sexo", "Edad mínima", "Edad máxima"};
		DefaultTableModel model = new DefaultTableModel(null, columnNames);

		for (CategoriaDto cat : cats) {
			Object[] fila = new Object[columnNames.length];

			fila[0] = cat.nombreCategoria;
			fila[1] = cat.sexo;
			fila[2] = cat.edadMinima;
			fila[3] = cat.edadMaxima;

			model.addRow(fila);
		}

		this.setModel(model);
	}

}
