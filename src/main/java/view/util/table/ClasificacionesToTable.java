package view.util.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.competicion.PosicionDto;

public class ClasificacionesToTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	public ClasificacionesToTable(List<PosicionDto> posiciones) {
		setDefaultEditor(Object.class, null); // hacer que no sea editable
		
		String[] columnNames = {"posicion", "sexo", "nombre", "tiempo"};
		DefaultTableModel model = new DefaultTableModel(null, columnNames);
		
		for(PosicionDto posicion : posiciones) {
			Object[] fila = new Object[columnNames.length];
			fila[0] = posicion.posicion;
			fila[1] = posicion.sexo;
			fila[2] = posicion.nombreAtleta;
			if (posicion.estadoInscripcion == "Finalizado")
				fila[3] = posicion.tiempo + "";
			else
				fila[3] ="-:--";			
			model.addRow(fila);
		}
		
		this.setModel(model);
	}

}
