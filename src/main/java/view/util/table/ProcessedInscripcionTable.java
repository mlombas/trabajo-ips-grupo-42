package view.util.table;

import java.awt.Component;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.inscripcion.InscripcionDto;

public class ProcessedInscripcionTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	public ProcessedInscripcionTable(List<InscripcionDto> inscripciones) {
		setDefaultEditor(Object.class, null); // hacer que no sea editable
		
		String[] columnNames = {"email_atleta", "nombre_atleta", "dinero_a_devolver", "estado_inscripcion"};
		DefaultTableModel model = new DefaultTableModel(null, columnNames);
		
		for(InscripcionDto inscripcion : inscripciones) {
			Object[] fila = new Object[columnNames.length];
			
			fila[0] = inscripcion.emailAtleta;
			fila[1] = inscripcion.nombreAtleta;
			fila[2] = inscripcion.devolver;
			fila[3] = inscripcion.estadoInscripcion;
			
			model.addRow(fila);
		}
		
		this.setModel(model);
	}

}
