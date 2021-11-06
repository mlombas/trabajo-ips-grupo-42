package view.organizador.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

import model.ModelFactory;
import model.competicion.CompeticionDto;
import model.competicion.ClasificacionDto;
import view.util.table.ClasificacionesToTable;

public class VerClasficacionDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollParticipantes;
	private CompeticionDto competicion;
	private String categoria;
	ClasificacionesToTable table;;

	public VerClasficacionDialog(CompeticionDto competicion, String categoria) {
		this.competicion = competicion;
		this.categoria = categoria;

		setLayout(new BorderLayout(0, 0));
		setSize(new Dimension(465, 285));
		setLocationRelativeTo(null);
		setResizable(true);
		add(getCompeticionesListPane(), BorderLayout.CENTER);
	}

	private Component getCompeticionesListPane() {
		if (scrollParticipantes == null) {
			scrollParticipantes = new JScrollPane();
			List<ClasificacionDto> clasificacion;
			if (categoria.equals("Absoluta")) {
				clasificacion = ModelFactory.forCarreraCrudService().GetClasificacion(competicion);
				table = new ClasificacionesToTable(clasificacion);
				scrollParticipantes.setViewportView(table);
			} else {
				clasificacion = ModelFactory.forCarreraCrudService().GetClasificacion(competicion, categoria);
				table = new ClasificacionesToTable(clasificacion);
				scrollParticipantes.setViewportView(table);
			}
		}
		return scrollParticipantes;
	}

}
