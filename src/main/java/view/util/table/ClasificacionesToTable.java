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

		int ncolumns = 8;
		int c = 0;
		int m = 0;
		int d = 0;
		if (club) {
			ncolumns++;
			c = 1;
		}
		if (minsByKm) {
			ncolumns++;
			m = 1;
		}
		if (diferencia) {
			ncolumns++;
			d = 1;
		}
		ncolumns += puntosCorte;

		String[] columnsPreClub = { "Posición", "Dorsal", "Nombre", "Categoría", "Sexo" };
		String[] columnsPostClub = { "Tiempo", "Salida", "Llegada" };
		String[] columnNames = new String[ncolumns];
		int k = 0;
		while (k < 5) {
			columnNames[k] = columnsPreClub[k];
			k++;
		}
		if (club) {
			columnNames[k] = "Club";
			k++;
		}
		for (int j = 0; j < 3; j++) {
			columnNames[k] = columnsPostClub[j];
			k++;
		}
		if (minsByKm) {
			columnNames[k] = "Minutos/Kilometro";
			k++;
		}
		if (diferencia) {
			columnNames[k] = "Diferencia";
			k++;
		}
		for (int i = 1; i <= puntosCorte; i++) {
			columnNames[k] = "Punto " + i;
			k++;
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
				fila[5 + c] = "-:--";
				fila[6 + c] = "-:--";
				fila[7 + c] = "-:--";
				if (minsByKm) {
					fila[8 + c] = "-:--";
				}
				if (diferencia) {
					fila[8 + m + c] = "-:--";
				}
				for (int i = 0; i < puntosCorte; i++) {
					fila[8 + d + m + c + i] = "-:--";
				}
			} else {
				if (posicion.dorsal == 0) {
					fila[0] = "---";
					fila[1] = "DNS";
					fila[5 + c] = "-:--";
					fila[6 + c] = "-:--";
					fila[7 + c] = "-:--";
					if (minsByKm) {
						fila[8 + c] = "-:--";
					}
					if (diferencia) {
						fila[8 + m + c] = "-:--";
					}
					for (int i = 0; i < puntosCorte; i++) {
						fila[8 + d + m + c + i] = "-:--";
					}
				} else {
					fila[1] = posicion.dorsal;
					fila[6 + c] = posicion.formatTime(posicion.tiempoSalida);
					if (posicion.tiempoLlegada == 0) {
						fila[0] = "---";
						fila[5 + c] = "-:--";
						fila[7 + c] = "DNF";
						if (minsByKm) {
							fila[8 + c] = "-:--";
						}
						if (diferencia) {
							fila[8 + m + c] = "-:--";
						}
						for (int i = 0; i < puntosCorte; i++) {
							fila[8 + d + m + c] = "-:--";
						}
					} else {
						fila[0] = posicion.posicion;
						fila[5 + c] = posicion.formatTime(posicion.tiempoLlegada - posicion.tiempoSalida);
						fila[7 + c] = posicion.formatTime(posicion.tiempoLlegada);
						if (minsByKm) {
							fila[8 + c] = posicion.formatTime(posicion.minsByKm);
						}
						if (diferencia) {
							fila[8 + m + c] = posicion.formatTime(posicion.diferenciaTiempo);
						}
						for (int i = 0; i < puntosCorte; i++) {
							fila[8 + d + m + c + i] = posicion.formatTime(posicion.puntosIntermedios[i]);
						}
					}
				}
			}

			model.addRow(fila);
		}

		this.setModel(model);
	}

}
