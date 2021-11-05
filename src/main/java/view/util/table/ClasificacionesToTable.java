package view.util.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.competicion.ClasificacionDto;

public class ClasificacionesToTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	public ClasificacionesToTable(List<ClasificacionDto> posiciones) {
		setDefaultEditor(Object.class, null); // hacer que no sea editable
		
		String[] columnNames = {"posicion", "sexo", "nombre", "tiempo"};
		DefaultTableModel model = new DefaultTableModel(null, columnNames);
		
		for(ClasificacionDto posicion : posiciones) {
			Object[] fila = new Object[columnNames.length];
			fila[0] = posicion.posicion;
			fila[1] = posicion.sexo;
			fila[2] = posicion.nombre;
			fila[3] = posicion.tiempoLlegada-posicion.tiempoSalida;			
			model.addRow(fila);
		}
		
		this.setModel(model);
	}

}
