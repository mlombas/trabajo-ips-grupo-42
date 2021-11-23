package view.util.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.atleta.AtletaDto;

public class AtletasToTable extends JTable {

	private static final long serialVersionUID = 1L;
	private static final String[] columnNames = {"nombre", 
												 "email",
												 "dni",
												 "sexo", 
												 "fechaNacimiento"};
	
	DefaultTableModel model;
	
	public AtletasToTable(List<AtletaDto> atletas) {
		model = new DefaultTableModel(null, columnNames);
		
		for(AtletaDto atleta : atletas)
			addAtleta(atleta);
			
		this.setModel(model);
	}
	
	public void addAtleta(AtletaDto atleta) {
		Object[] fila = new Object[columnNames.length];
		
		fila[0] = atleta.nombre;
		fila[1] = atleta.email;
		fila[2] = atleta.dni;
		fila[3] = atleta.sexo;
		fila[4] = atleta.fechaNacimiento;
		
		model.addRow(fila);
	}

	public void reset() {
		model.setRowCount(0);
	}
	
}
