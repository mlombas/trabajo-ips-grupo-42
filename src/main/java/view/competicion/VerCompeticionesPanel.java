package view.competicion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import giis.demo.util.SwingUtil;
import model.ModelFactory;
import model.competicion.CompeticionDto;
import view.atleta.AtletaMain;

public class VerCompeticionesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private AtletaMain main;
	private JButton btnAtras;
	private JScrollPane scrollCompeticiones;
	private JTable table;
	
	public VerCompeticionesPanel(AtletaMain main) {
		this(main, true);
	}
	public VerCompeticionesPanel(AtletaMain main, boolean botonAtras) {
		this.main = main;
		
		setLayout(new BorderLayout(0, 0));
		add(getCompeticionesListPane(), BorderLayout.CENTER);
		if(botonAtras) add(getBtnAtras(), BorderLayout.SOUTH);
	}

	private Component getBtnAtras() {
		if(btnAtras == null) {
			btnAtras = new JButton("Atras");
			btnAtras.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					main.startPanel();
				}
			});
		}
		return btnAtras;
	}

	private Component getCompeticionesListPane() {
		if(scrollCompeticiones == null) {

			List<CompeticionDto> competiciones = ModelFactory.forCarreraCrudService().GetAllCompeticiones();
			TableModel tmodel = SwingUtil.getTableModelFromPojos(competiciones, new String[] {
					"nombreCarrera",
					"tipoCarrera",
					"fecha",
					"distancia",
					"cuota",
					"fechaFin",
					"plazas"
					});
			
			table = new JTable(tmodel);
			SwingUtil.autoAdjustColumns(table);
			
			scrollCompeticiones = new JScrollPane(table);
		}
		return scrollCompeticiones;
	}
	
	public String getCompeticionId() {
		int index = table.getSelectedRow();
		return table.getComponentAt(index, 0).toString();
	}

}
