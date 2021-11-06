package view.util.panel;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.competicion.CompeticionDto;
import view.util.table.CompeticionesToTable;

public class VerCompeticionesPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollCompeticiones;
	private CompeticionesToTable table;

	public VerCompeticionesPanel() {
		setLayout(new BorderLayout(0, 0));
		add(getCompeticionesListPane(), BorderLayout.CENTER);
	}
	
	public void updateCompeticiones(List<CompeticionDto> competiciones) {
		for (CompeticionDto competicion : competiciones)
			table.addRow(competicion);
		scrollCompeticiones.revalidate();
  }

	public String getCompeticionId() {
		int index = table.getSelectedRow();
		return table.getModel().getValueAt(index, 0).toString();
	}
	
	public double getCuota() {
		int index = table.getSelectedRow();
		return Double.parseDouble(table.getModel().getValueAt(index, 4).toString());
	}
	
	public String getNombreCompeticion() {
		int index = table.getSelectedRow();
		return table.getModel().getValueAt(index, 1).toString();
	}
	
	public String getEstadoCompeticion() {
		int index = table.getSelectedRow();
		return table.getModel().getValueAt(index, 9).toString();
	}
  
    private JScrollPane getCompeticionesListPane() {
        if(scrollCompeticiones == null) {
          table = new CompeticionesToTable(new ArrayList<CompeticionDto>());

        scrollCompeticiones = new JScrollPane();
        scrollCompeticiones.setViewportView(table);
      }
      
      return scrollCompeticiones;
    }
  
    public CompeticionesToTable getTable(){
	    return table;
    }
	
}