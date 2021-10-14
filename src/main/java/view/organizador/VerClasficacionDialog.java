package view.organizador;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

import model.ModelFactory;
import model.competicion.CompeticionDto;

public class VerClasficacionDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollParticipantes;
	private CompeticionDto competicion;
	private String categoria;

	public VerClasficacionDialog(CompeticionDto competicion, String categoria) {
		this.competicion = competicion;
		this.categoria = categoria;

		setLayout(new BorderLayout(0, 0));
		setSize(new Dimension(465, 285));
		setResizable(true);
		add(getCompeticionesListPane(), BorderLayout.CENTER);
	}

	private Component getCompeticionesListPane() {
		if (scrollParticipantes == null) {
			if (categoria.isBlank()) {
				scrollParticipantes = new JScrollPane(
						new ParticipantesLista(ModelFactory.forCarreraCrudService().GetClasificacion(competicion)));
			}else {
				scrollParticipantes = new JScrollPane(
						new ParticipantesLista(ModelFactory.forCarreraCrudService().GetClasificacion(competicion, categoria)));
			}
		}
		return scrollParticipantes;
	}

}
