package view.util;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.inscripcion.InscripcionDto;

public class InscripcionesToTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	public InscripcionesToTable(List<InscripcionDto> inscripciones) {
		setDefaultEditor(Object.class, null); // hacer que no sea editable
		
		String[] columnNames = {"DNI", "nombre", "categoria", "fechaInscripcion", "estadoInscripcion"};
		DefaultTableModel model = new DefaultTableModel(null, columnNames);
		
		for(InscripcionDto inscripcion : inscripciones) {
			Object[] fila = new Object[columnNames.length];
			fila[0] = inscripcion.dniAtleta;
			fila[1] = inscripcion.nombreAtleta;
			fila[2] = inscripcion.categoria;
			fila[3] = inscripcion.fechaInscripcion;
			fila[4] = inscripcion.estadoInscripcion;
			
			model.addRow(fila);
		}
		
		this.setModel(model);
	}

}
