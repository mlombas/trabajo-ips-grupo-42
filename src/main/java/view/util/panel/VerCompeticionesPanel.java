package view.util.panel;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.ModelFactory;
import model.competicion.CompeticionDto;
import view.util.table.CompeticionesToTable;

public class VerCompeticionesPanel extends JPanel {

private static final long serialVersionUID = 1L;

	private JScrollPane scrollCompeticiones;
	private JTable table;
	
	private List<CompeticionDto> competiciones;

	public VerCompeticionesPanel() {
		this.competiciones = ModelFactory.forCarreraCrudService().GetAllCompeticiones();
		
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
		return Double.parseDouble(table.getValueAt(index, 5).toString());
	}
	
	public String getNombreCompeticion() {
		int index = table.getSelectedRow();
		return table.getValueAt(index, 1).toString();
	}
	
}
