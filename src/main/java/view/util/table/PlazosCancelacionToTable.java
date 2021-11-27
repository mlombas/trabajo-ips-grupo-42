package view.util.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.competicion.PlazoCancelacionDto;

public class PlazosCancelacionToTable extends JTable {

	private static final long serialVersionUID = 1L;

	public PlazosCancelacionToTable(List<PlazoCancelacionDto> plazos) {
		setDefaultEditor(Object.class, null); // hacer que no sea editable
		
		String[] columnNames = {"Plazo", "Fecha de inicio", "Fecha de fin", "Devolución"};
		DefaultTableModel model = new DefaultTableModel(null, columnNames);
		
		for(PlazoCancelacionDto plazo : plazos) {
			Object[] fila = new Object[columnNames.length];
			
			fila[0] = plazo.id;
			fila[1] = plazo.fechaInicio;
			fila[2] = plazo.fechaFin;
			fila[3] = plazo.porcentaje + "%";
			
			model.addRow(fila);
		}
		
		this.setModel(model);
	}

}
