package view.util.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.competicion.PlazoInscripcionDto;

public class PlazosToTable extends JTable {

	private static final long serialVersionUID = 1L;

	public PlazosToTable(List<PlazoInscripcionDto> plazos) {
		setDefaultEditor(Object.class, null); // hacer que no sea editable
		
		String[] columnNames = {"id_plazo", "id_competicion", "fecha_inicio", "fecha_fin", "cuota"};
		DefaultTableModel model = new DefaultTableModel(null, columnNames);
		
		for(PlazoInscripcionDto plazo : plazos) {
			Object[] fila = new Object[columnNames.length];
			
			fila[0] = plazo.id;
			fila[1] = plazo.idCompeticion;
			fila[2] = plazo.fechaInicio;
			fila[3] = plazo.fechaFin;
			fila[4] = plazo.cuota;
			
			model.addRow(fila);
		}
		
		this.setModel(model);
	}

}
