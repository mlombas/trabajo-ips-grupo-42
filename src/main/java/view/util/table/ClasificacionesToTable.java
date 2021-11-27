package view.util.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.competicion.ClasificacionDto;

public class ClasificacionesToTable extends JTable {

	private static final long serialVersionUID = 1L;

	public ClasificacionesToTable(List<ClasificacionDto> clasificacion, boolean club, boolean minsByKm,
			boolean diferencia, int puntosCorte) {
		setDefaultEditor(Object.class, null); // hacer que no sea editable

		String[] columns = { "Posición", "Dorsal", "Nombre", "Categoría", "Sexo", "Club", "Tiempo", "Salida", "Llegada",
				"Minutos por kilometro", "Diferencia" };
		String[] columnNames = new String[columns.length + puntosCorte];
		for (int i = 0; i < columns.length; i++) {
			columnNames[i] = columns[i];
		}
		for (int i = columns.length; i < columnNames.length; i++) {
			columnNames[i] = "Punto " + ((i - columns.length) + 1);
		}

		DefaultTableModel model = new DefaultTableModel(null, columnNames);

		for (ClasificacionDto posicion : clasificacion) {
			Object[] fila = new Object[columnNames.length];
			fila[2] = posicion.nombre;
			fila[3] = posicion.categoria;
			fila[4] = posicion.sexo;
			if (club) {
				if (posicion.club != null) {
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
				if (minsByKm) {
					fila[9] = "-:--";
				}
				if (diferencia) {
					fila[10] = "-:--";
				}
				for(int i=0;i<puntosCorte;i++) {
					fila[11+i] = "-:--";
				}
			} else {
				if (posicion.dorsal == 0) {
					fila[0] = "---";
					fila[1] = "DNS";
					fila[6] = "-:--";
					fila[7] = "-:--";
					fila[8] = "-:--";
					if (minsByKm) {
						fila[9] = "-:--";
					}
					if (diferencia) {
						fila[10] = "-:--";
					}
					for(int i=0;i<puntosCorte;i++) {
						fila[11+i] = "-:--";
					}
				} else {
					fila[1] = posicion.dorsal;
					fila[7] = posicion.formatTime(posicion.tiempoSalida);
					if (posicion.tiempoLlegada == 0) {
						fila[0] = "---";
						fila[6] = "-:--";
						fila[8] = "DNF";
						if (minsByKm) {
							fila[9] = "-:--";
						}
						if (diferencia) {
							fila[10] = "-:--";
						}
						for(int i=0;i<puntosCorte;i++) {
							fila[11+i] = "-:--";
						}
					} else {
						fila[0] = posicion.posicion;
						fila[6] = posicion.formatTime(posicion.tiempoLlegada - posicion.tiempoSalida);
						fila[8] = posicion.formatTime(posicion.tiempoLlegada);
						if (minsByKm) {
							fila[9] = posicion.formatTime(posicion.minsByKm);
						}
						if (diferencia) {
							fila[10] = posicion.formatTime(posicion.diferenciaTiempo);
						}
						for(int i=0;i<puntosCorte;i++) {
							fila[11+i] = posicion.formatTime(posicion.puntosIntermedios[i]);
						}
					}
				}
			}

			model.addRow(fila);
		}

		this.setModel(model);
	}

}
