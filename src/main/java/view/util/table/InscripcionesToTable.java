package view.util.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.inscripcion.InscripcionDto;

public class InscripcionesToTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	public InscripcionesToTable(List<InscripcionDto> inscripciones) {
		setDefaultEditor(Object.class, null); // hacer que no sea editable
		
		String[] columnNames = {"nombre_competicion", "dni_atleta", "nombre_atleta", "categoria", "fechaInscripcion", "estadoInscripcion"};
		DefaultTableModel model = new DefaultTableModel(null, columnNames);
		
		for(InscripcionDto inscripcion : inscripciones) {
			Object[] fila = new Object[columnNames.length];
			
			fila[0] = inscripcion.nombreCompeticion;
			fila[1] = inscripcion.dniAtleta;
			fila[2] = inscripcion.nombreAtleta;
			fila[3] = inscripcion.categoria;
			fila[4] = inscripcion.fechaInscripcion;
			fila[5] = inscripcion.estadoInscripcion;
			
			model.addRow(fila);
		}
		
		this.setModel(model);
	}

}
