package view.organizador.panel;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.GridLayout;

public class CrearCompeticionSubPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JScrollPane scrollPaneVisualizacion;
	private JPanel panelFormulario;
	private JPanel panelBotones;
	private JButton btnAñadir;

	public CrearCompeticionSubPanel() {
		setLayout(new MigLayout("", "[grow,fill]", "[100px:n,grow][][]"));
		add(getScrollPaneVisualizacion(), "cell 0 0,grow");
		add(getPanelFormulario(), "cell 0 1,grow");
		add(getPanelBotones(), "cell 0 2,grow");
	}

	protected JScrollPane getScrollPaneVisualizacion() {
		if (scrollPaneVisualizacion == null) 
			scrollPaneVisualizacion = new JScrollPane();
		
		return scrollPaneVisualizacion;
	}
	
	protected JPanel getPanelFormulario() {
		if (panelFormulario == null) {
			panelFormulario = new JPanel();
			panelFormulario.setLayout(new MigLayout("", "[]", "[]"));
		}
		return panelFormulario;
	}
	
	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.setLayout(new GridLayout(0, 1, 0, 0));
			panelBotones.add(getBtnAñadir());
		}
		return panelBotones;
	}
	
	protected JButton getBtnAñadir() {
		if (btnAñadir == null) {
			btnAñadir = new JButton("Añadir");
			btnAñadir.setToolTipText("Añade a la carrera los datos introducidos en el formulario");
		}
		return btnAñadir;
	}
	
}