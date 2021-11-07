package view.util.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import model.competicion.CompeticionDto;

public class CompeticionesToTable extends JTable {

	private static final long serialVersionUID = 1L;
	private static final String[] columnNames = {"ID", "nombre", "tipo", "distancia", "cuota", 
			"fecha", "fechaInicio", "fechaFin", "plazas", "estado"};
	
	private DefaultTableModel model;
	private TableColumnModel tcm;
	private List<CompeticionDto> competiciones;
	
	public CompeticionesToTable(List<CompeticionDto> competiciones) {
		setDefaultEditor(Object.class, null); // hacer que no sea editable
		
		model = new DefaultTableModel(null, columnNames);
		this.competiciones = competiciones;
		
		for(CompeticionDto competicion : competiciones)
			addRow(competicion);
		
		setModel(model);
		
		// We hide the ID field
		tcm = getColumnModel();
		tcm.removeColumn(tcm.getColumn(0));
	}
	
	public void addRow(CompeticionDto competicion) {
		// Check not to add repeated --> in that case remove it to update...
		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getValueAt(i, 0).equals(competicion.id)) {
				model.removeRow(i);
				competiciones.remove(competicion);
				break;
			}
		}
			
		competiciones.add(competicion);
		
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

}
