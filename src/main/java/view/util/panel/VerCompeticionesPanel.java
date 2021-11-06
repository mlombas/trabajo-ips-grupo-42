package view.util.panel;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.ModelFactory;
import model.competicion.CompeticionDto;
import util.exceptions.ModelException;
import view.organizador.panel.GestionarCompeticionesPanel;
import view.util.table.CompeticionesToTable;

public class VerCompeticionesPanel extends JPanel {

private static final long serialVersionUID = 1L;

	private JScrollPane scrollCompeticiones;
	private JTable table;
	
	private List<CompeticionDto> competiciones;

	public VerCompeticionesPanel() {
		try {
			this.competiciones = ModelFactory.forCarreraCrudService().GetAllCompeticiones();
		} catch (ModelException e) {
			this.competiciones = new ArrayList<>();
		}
		
		setLayout(new BorderLayout(0, 0));
		add(getCompeticionesListPane(), BorderLayout.CENTER);
	}
	
	public VerCompeticionesPanel(List<CompeticionDto> competiciones) {
		this.competiciones = competiciones;
		
		setLayout(new BorderLayout(0, 0));
		add(getCompeticionesListPane(), BorderLayout.CENTER);
	}

	private JScrollPane getCompeticionesListPane() {
		if(scrollCompeticiones == null) {
			table = new CompeticionesToTable(competiciones);
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
		        public void valueChanged(ListSelectionEvent event) {
		           GestionarCompeticionesPanel.getInstance().updateCategorias();
		        }
		    });
			scrollCompeticiones = new JScrollPane(table);
		}
		return scrollCompeticiones;
	}
	
	public String getCompeticionId() {
		int index = table.getSelectedRow();
		return table.getValueAt(index, 0).toString();
	}
	
	public double getCuota() {
		int index = table.getSelectedRow();
		return Double.parseDouble(table.getValueAt(index, 4).toString());
	}
	
	public String getNombreCompeticion() {
		int index = table.getSelectedRow();
		return table.getValueAt(index, 1).toString();
	}
	
}
