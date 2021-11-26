package view.util.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.competicion.ClasificacionExtendidaDto;

public class ClasificacionesToTable extends JTable {

	private static final long serialVersionUID = 1L;

	public ClasificacionesToTable(List<ClasificacionExtendidaDto> clasificacion, boolean club, boolean minsByKm, boolean diferencia, int puntosCorte) {
		setDefaultEditor(Object.class, null); // hacer que no sea editable

		String[] columnNames = { "Posición", "Dorsal", "Nombre", "Categoría", "Sexo", "Club", "Tiempo", "Salida", "Llegada", "Minutos por kilometro", "Diferencia" };
		DefaultTableModel model = new DefaultTableModel(null, columnNames);

		for (ClasificacionExtendidaDto posicion : clasificacion) {
			Object[] fila = new Object[columnNames.length];
			fila[2] = posicion.nombre;
			fila[3] = posicion.categoria;
			fila[4] = posicion.sexo;
			if(club) {
			if(posicion.club!=null) {
				fila[5] = posicion.club;
			} else {
				fila[5] = "---";
			} 
			}
			if (!posicion.presentado) {
				fila[0] = "---";
				fila[1] = "NP";
				fila[6] = "-:--";
				fila[7] = "-:--";
				fila[8] = "-:--";
				if(minsByKm) {
					fila[9] = "-:--";
				}
				if(diferencia) {
					fila[10] = "-:--";
				}
			} else {
				if (posicion.dorsal == 0) {
					fila[0] = "---";
					fila[1] = "DNS";
					fila[6] = "-:--";
					fila[7] = "-:--";
					fila[8] = "-:--";
					if(minsByKm) {
						fila[9] = "-:--";
					}
					if(diferencia) {
						fila[10] = "-:--";
					}
				} else {
					fila[1] = posicion.dorsal;
					fila[7] = posicion.formatTime(posicion.tiempoSalida);
					if (posicion.tiempoLlegada == 0) {
						fila[0] = "---";
						fila[6] = "-:--";
						fila[8] = "DNF";
						if(minsByKm) {
							fila[9] = "-:--";
						}
						if(diferencia) {
							fila[10] = "-:--";
						}
					} else {
						fila[0] = posicion.posicion;
						fila[6] = posicion.formatTime(posicion.tiempoLlegada - posicion.tiempoSalida);
						fila[8] = posicion.formatTime(posicion.tiempoLlegada);
						if(minsByKm) {
							fila[9] = posicion.formatTime(posicion.minsByKm);
						}
						if(diferencia) {
							fila[10] = posicion.formatTime(posicion.diferenciaTiempo);
						}
					}
				}
			}
			
			model.addRow(fila);
		}

		this.setModel(model);
	}

}
