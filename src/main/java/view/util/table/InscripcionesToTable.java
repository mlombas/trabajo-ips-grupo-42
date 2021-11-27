package view.util.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.inscripcion.InscripcionDto;

public class InscripcionesToTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	public InscripcionesToTable(List<InscripcionDto> inscripciones) {
		setDefaultEditor(Object.class, null); // hacer que no sea editable
		
		String[] columnNames = {"id_competicion","nombre_competicion", "dni_atleta", "email_atleta", "nombre_atleta", "club_atleta","categoria", "fechaInscripcion", "estadoInscripcion"};

		DefaultTableModel model = new DefaultTableModel(null, columnNames);
		
		for(InscripcionDto inscripcion : inscripciones) {
			Object[] fila = new Object[columnNames.length];
			
			fila[0] = inscripcion.idCompeticion;
			fila[1] = inscripcion.nombreCompeticion;
			fila[2] = inscripcion.dniAtleta;
			fila[3] = inscripcion.emailAtleta;
			fila[4] = inscripcion.nombreAtleta;
			fila[5] = inscripcion.clubAtleta;
			fila[6] = inscripcion.categoria;
			fila[7] = inscripcion.fechaInscripcion;
			fila[8] = inscripcion.estadoInscripcion;
			
			model.addRow(fila);
		}
		
		this.setModel(model);
	}

}
