package view.organizador.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

import model.ModelFactory;
import model.competicion.ClasificacionExtendidaDto;
import model.competicion.CompeticionDto;
import model.competicion.PuntoIntermedioClasficacionDto;
import util.exceptions.ModelException;
import view.util.table.ClasificacionesToTable;

public class VerClasficacionDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollParticipantes;
	private CompeticionDto competicion;
	private String categoria;
	private ClasificacionesToTable table;;
	private boolean club, minsByKm, diferencia, puntosCorte;

	public VerClasficacionDialog(CompeticionDto competicion, String categoria, boolean club, boolean minsByKm,
			boolean diferencia, boolean puntosCorte) throws ModelException {
		this.competicion = competicion;
		this.categoria = categoria;
		this.club = club;
		this.minsByKm = minsByKm;
		this.diferencia = diferencia;
		this.puntosCorte = puntosCorte;

		setLayout(new BorderLayout(0, 0));
		setSize(new Dimension(465, 285));
		setLocationRelativeTo(null);
		setResizable(true);
		add(getCompeticionesListPane(), BorderLayout.CENTER);
	}

	private Component getCompeticionesListPane() throws ModelException {
		if (scrollParticipantes == null) {
			scrollParticipantes = new JScrollPane();

			List<ClasificacionExtendidaDto> clasificacion;
			if (categoria.equals("Absoluta")) {
				clasificacion = ModelFactory.forCarreraCrudService().GetClasificacionExtendida(competicion);
			} else {
				clasificacion = ModelFactory.forCarreraCrudService().GetClasificacionExtendida(competicion, categoria);
			}

			if (minsByKm) {
				int distancia = ModelFactory.forCarreraCrudService().GetDistancia(competicion);
				for (ClasificacionExtendidaDto clasificado : clasificacion) {
					clasificado.minsByKm = (clasificado.tiempoLlegada - clasificado.tiempoSalida) / distancia;
				}
			}

			if (diferencia) {
				int tiempoPrimero = clasificacion.get(0).tiempoLlegada - clasificacion.get(0).tiempoSalida;
				for (ClasificacionExtendidaDto clasificado : clasificacion) {
					clasificado.diferenciaTiempo = (clasificado.tiempoLlegada - clasificado.tiempoSalida)
							- tiempoPrimero;
				}

			}

			int puntos = 0;
			if (puntosCorte) {
				puntos = ModelFactory.forCarreraCrudService().countPuntosIntermendios(competicion);
				if (puntos > 0) {
					for (ClasificacionExtendidaDto clasificado : clasificacion) {
						clasificado.puntosIntermedios = new int[puntos];
						if (clasificado.dorsal != 0) {
							List<PuntoIntermedioClasficacionDto> tiempos = ModelFactory.forCarreraCrudService()
									.obtenerPuntosInt(competicion, clasificado);
							for (int i = 0; i < puntos; i++) {
								clasificado.puntosIntermedios[i] = tiempos.get(i).tiempo;
							}
						}else {for (int i = 0; i < puntos; i++) {
							clasificado.puntosIntermedios[i] = 0;
						}}
					}
				}
			}

			table = new ClasificacionesToTable(clasificacion, club, minsByKm, diferencia, puntos);
			scrollParticipantes.setViewportView(table);
		}
		return scrollParticipantes;
	}

}
