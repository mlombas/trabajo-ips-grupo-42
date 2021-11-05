package view.util.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.competicion.ClasificacionDto;

public class ClasificacionesToTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	public ClasificacionesToTable(List<ClasificacionDto> posiciones) {
		setDefaultEditor(Object.class, null); // hacer que no sea editable
		
		String[] columnNames = {"Posición", "Dorsal", "Nombre", "Categoría", "Sexo", "Tiempo", "Salida", "Llegada"};
		DefaultTableModel model = new DefaultTableModel(null, columnNames);
		
		for(ClasificacionDto posicion : posiciones) {
			Object[] fila = new Object[columnNames.length];
			fila[0] = posicion.posicion;
			fila[1] = posicion.dorsal;
			fila[2] = posicion.nombre;
			fila[3] = posicion.categoria;
			fila[4] = posicion.sexo;
			fila[5] = posicion.formatTime(posicion.tiempoLlegada-posicion.tiempoSalida);
			fila[6] = posicion.formatTime(posicion.tiempoSalida);
			fila[7] = posicion.formatTime(posicion.tiempoLlegada);
			model.addRow(fila);
		}
		
		this.setModel(model);
	}

}
