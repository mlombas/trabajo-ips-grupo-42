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
import view.util.table.ClasificacionesToTable;

public class VerClasficacionDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollParticipantes;
	private CompeticionDto competicion;
	private String categoria;
	private ClasificacionesToTable table;;
	private  boolean club, minsByKm, diferencia, puntosCorte;

	public VerClasficacionDialog(CompeticionDto competicion, String categoria, boolean club, boolean minsByKm, boolean diferencia, boolean puntosCorte) {
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

	private Component getCompeticionesListPane() {
		if (scrollParticipantes == null) {
			scrollParticipantes = new JScrollPane();
			List<ClasificacionExtendidaDto> clasificacion;
			if (categoria.equals("Absoluta")) {
				clasificacion = ModelFactory.forCarreraCrudService().GetClasificacionExtendida(competicion);
			} else {
				clasificacion = ModelFactory.forCarreraCrudService().GetClasificacionExtendida(competicion, categoria);
			}
			table = new ClasificacionesToTable(clasificacion, club, minsByKm, diferencia, puntosCorte);
			scrollParticipantes.setViewportView(table);
		}
		return scrollParticipantes;
	}

}
