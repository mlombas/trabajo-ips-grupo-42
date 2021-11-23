package view.organizador.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import controller.competicion.CompeticionCrudService;
import controller.competicion.CompeticionCrudServiceImpl;
import model.ModelFactory;
import model.competicion.CategoriaDto;
import model.competicion.CompeticionDto;
import net.miginfocom.swing.MigLayout;
import util.exceptions.ModelException;
import view.util.table.CategoriasToTable;

public class CrearCategoriasPanel extends CrearCompeticionSubPanel {

	private static final long serialVersionUID = 1L;

	private JTable tabCategorias;
	private JTextField txtNombre;
	private JLabel lblTipo;
	private JLabel lblEdadMin;

	private CompeticionDto comp;
	private JLabel lblNombreCat;
	private JSpinner spinnerEdadMin;
	private JLabel lblEdadMax;
	private JSpinner spinnerEdadMax;
	private JPanel pnBotonesTipo;
	private JRadioButton rdbtnHombre;
	private JRadioButton rdbtnMujer;
	private JRadioButton rdbtnDiscapacitados;

	CompeticionCrudService ccs = new CompeticionCrudServiceImpl();

	public CrearCategoriasPanel(CompeticionDto comp) {
		this.comp = comp;

		// Añadimos los elementos al panel
		cargarCategorias();
		addToFormulario();

		// Añadimos el event listener
		getBtnAñadir().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				añadirCategoria();
			}
		});
	}

	private void cargarCategorias() {
		List<CategoriaDto> cats = ccs.getAllCategorias(comp.id);
		this.tabCategorias = new CategoriasToTable(cats);
		getScrollPaneVisualizacion().setViewportView(tabCategorias);
	}

	private void añadirCategoria() {
		CategoriaDto cat = new CategoriaDto();
		cat.idCompeticion = comp.id;
		cat.nombreCategoria = txtNombre.getText();
		cat.edadMinima = (int) spinnerEdadMin.getValue();
		cat.edadMaxima = (int) spinnerEdadMax.getValue();
		if (rdbtnHombre.isSelected()) {
			cat.sexo = "H";
		}
		if (rdbtnMujer.isSelected()) {
			cat.sexo = "M";
		}
		if (rdbtnDiscapacitados.isSelected()) {
			cat.sexo = "D";
		}
		try {
			ModelFactory.forCarreraCrudService().addCategoria(cat);
			JOptionPane.showMessageDialog(null, "Categoría creada con éxito");
			List<CategoriaDto> cats = ccs.getAllCategorias(comp.id);
			this.tabCategorias = new CategoriasToTable(cats);
			getScrollPaneVisualizacion().setViewportView(tabCategorias);
			this.revalidate();
		} catch (ModelException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	private void addToFormulario() {
		getPanelFormulario().setLayout(new MigLayout("", "[grow,fill][grow,fill]", "[][][][][grow][]"));
		getPanelFormulario().add(getLblNombreCat(), "cell 0 1");
		getPanelFormulario().add(getLblEdadMin(), "cell 1 1,alignx left,aligny center");
		getPanelFormulario().add(getTxtNombre(), "cell 0 2,growx,aligny top");
		getPanelFormulario().add(getSpinnerEdadMin(), "cell 1 2");
		getPanelFormulario().add(getLblTipo(), "cell 0 3,alignx left,aligny center");
		getPanelFormulario().add(getLblEdadMax(), "cell 1 3");
		getPanelFormulario().add(getPnBotonesTipo(), "cell 0 4,grow");
		getPanelFormulario().add(getSpinnerEdadMax(), "cell 1 4");
	}

	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setColumns(20);
		}
		return txtNombre;
	}

	private JLabel getLblTipo() {
		if (lblTipo == null) {
			lblTipo = new JLabel("Tipo de categoría:");
		}
		return lblTipo;
	}

	private JLabel getLblEdadMin() {
		if (lblEdadMin == null) {
			lblEdadMin = new JLabel("Edad mínima:");
		}
		return lblEdadMin;
	}

	private JLabel getLblNombreCat() {
		if (lblNombreCat == null) {
			lblNombreCat = new JLabel("Nombre:");
		}
		return lblNombreCat;
	}

	private JPanel getPnBotonesTipo() {
		if (pnBotonesTipo == null) {
			pnBotonesTipo = new JPanel();
			pnBotonesTipo.setLayout(new BoxLayout(pnBotonesTipo, BoxLayout.Y_AXIS));
			pnBotonesTipo.add(getRdbtnHombre());
			pnBotonesTipo.add(getRdbtnMujer());
			pnBotonesTipo.add(getRdbtnDiscapacitados());
			ButtonGroup group = new ButtonGroup();
			group.add(getRdbtnHombre());
			group.add(getRdbtnMujer());
			group.add(getRdbtnDiscapacitados());
		}
		return pnBotonesTipo;
	}

	private JSpinner getSpinnerEdadMin() {
		if (spinnerEdadMin == null) {
			spinnerEdadMin = new JSpinner();
			spinnerEdadMin.setPreferredSize(new Dimension(60, 20));
			spinnerEdadMin.setModel(new SpinnerNumberModel(18, 18, null, 1));
		}
		return spinnerEdadMin;
	}

	private JLabel getLblEdadMax() {
		if (lblEdadMax == null) {
			lblEdadMax = new JLabel("Edad máxima:");
		}
		return lblEdadMax;
	}

	private JSpinner getSpinnerEdadMax() {
		if (spinnerEdadMax == null) {
			spinnerEdadMax = new JSpinner();
			spinnerEdadMax.setPreferredSize(new Dimension(60, 20));
			spinnerEdadMax.setModel(new SpinnerNumberModel(18, 18, null, 1));
		}
		return spinnerEdadMax;
	}

	private JRadioButton getRdbtnDiscapacitados() {
		if (rdbtnDiscapacitados == null)
			rdbtnDiscapacitados = new JRadioButton("Discapacitados");
		return rdbtnDiscapacitados;
	}

	private JRadioButton getRdbtnMujer() {
		if (rdbtnMujer == null)
			rdbtnMujer = new JRadioButton("Mujer");
		return rdbtnMujer;
	}

	private JRadioButton getRdbtnHombre() {
		if (rdbtnHombre == null)
			rdbtnHombre = new JRadioButton("Hombre");
		rdbtnHombre.setSelected(true);
		return rdbtnHombre;
	}
}
