package view.util.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.competicion.CompeticionDto;

public class CompeticionesToTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	public CompeticionesToTable(List<CompeticionDto> competiciones) {
		setDefaultEditor(Object.class, null); // hacer que no sea editable
		
		String[] columnNames = {"ID", "nombre", "tipo", "distancia", 
				"cuota", "fecha", "fechaInicio", "fechaFin", "plazas", "estado"};
		DefaultTableModel model = new DefaultTableModel(null, columnNames);
		
		for(CompeticionDto competicion : competiciones) {
			Object[] fila = new Object[columnNames.length];
			fila[0] = competicion.id;
			fila[1] = competicion.nombreCarrera;
			fila[2] = competicion.tipoCarrera;
			fila[3] = competicion.distancia;
			fila[4] = competicion.cuota;
			fila[5] = competicion.fecha;
			fila[6] = competicion.fechaInicio;
			fila[7] = competicion.fechaFin;
			fila[8] = competicion.plazas;
			fila[9] = competicion.estadoCarrera;
			
			model.addRow(fila);
		}
		
		this.setModel(model);
	}

}
