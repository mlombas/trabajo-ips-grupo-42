package view.organizador.panel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class ConfigurarCategoriasPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField txtNombre;
	private JButton btnValidar;
	private JPanel panelValidar;
	private JLabel lblConfCategorias;
	private JPanel pnAtrasConfirmar;
	private JButton btnConfirmar;
	private JButton btnAtras;
	private JPanel pnCentro;
	private JPanel pnConf;
	private JLabel lblNombreCat;
	private JLabel lblEdadMin;
	private JSpinner spinnerEdadMin;
	private JLabel lblTipo;
	private JLabel lblEdadMax;
	private JPanel pnBotonesTipo;
	private JRadioButton rdbtnHombre;
	private JRadioButton rdbtnMujer;
	private JRadioButton rdbtnDiscapacitados;
	private JSpinner spinnerEdadMax;

	public ConfigurarCategoriasPanel() {
		setLayout(new BorderLayout(0, 0));

		add(getLblConfCategorias(), BorderLayout.NORTH);
		add(getPnAtrasConfirmar(), BorderLayout.SOUTH);
		add(getPnCentro(), BorderLayout.CENTER);

	}

	private Component getSpinnerEdadMax() {
		if (spinnerEdadMax == null) {
			spinnerEdadMax = new JSpinner();
			spinnerEdadMax.setModel(new SpinnerNumberModel(new Integer(18), null, null, new Integer(1)));
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
		return rdbtnHombre;
	}

	private JPanel getPnBotonesTipo() {
		if (pnBotonesTipo == null) {
			pnBotonesTipo = new JPanel();
			pnBotonesTipo.setLayout(new BoxLayout(pnBotonesTipo, BoxLayout.Y_AXIS));
			pnBotonesTipo.add(getRdbtnHombre());
			pnBotonesTipo.add(getRdbtnMujer());
			pnBotonesTipo.add(getRdbtnDiscapacitados());
		}
		return pnBotonesTipo;
	}

	private Component getLblEdadMax() {
		if (lblEdadMax == null) {
			lblEdadMax = new JLabel("EdadMáxima");
		}
		return lblEdadMax;
	}

	private JLabel getlblTipo() {
		if (lblTipo == null) {
			lblTipo = new JLabel("Tipo de categoría");
		}
		return lblTipo;
	}

	private JSpinner getSpinnerEdadMin() {
		if (spinnerEdadMin == null) {
			spinnerEdadMin = new JSpinner();
			spinnerEdadMin.setModel(new SpinnerNumberModel(new Integer(18), new Integer(18), null, new Integer(1)));
		}
		return spinnerEdadMin;
	}

	private JLabel getlblEdadMin() {
		if (lblEdadMin == null) {
			lblEdadMin = new JLabel("Edad mínima");
		}
		return lblEdadMin;
	}

	private JLabel getLblNombreCat() {
		if (lblNombreCat == null) {
			lblNombreCat = new JLabel("Nombre");
			lblNombreCat.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNombreCat;
	}

	private JPanel getPnConf() {
		if (pnConf == null) {
			pnConf = new JPanel();
			pnConf.setLayout(new MigLayout("", "[225px,grow][grow]", "[bottom][grow][][grow]"));
			pnConf.add(getLblNombreCat(), "cell 0 0");
			pnConf.add(getlblEdadMin(), "cell 1 0");
			pnConf.add(getTxtNombre(), "cell 0 1,growx");
			pnConf.add(getSpinnerEdadMin(), "cell 1 1");
			pnConf.add(getlblTipo(), "cell 0 2");
			pnConf.add(getLblEdadMax(), "cell 1 2");
			pnConf.add(getPnBotonesTipo(), "cell 0 3,grow");
			pnConf.add(getSpinnerEdadMax(), "flowx,cell 1 3");
		}
		return pnConf;
	}

	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setLayout(new BorderLayout(0, 0));
			pnCentro.add(getPnConf(), BorderLayout.CENTER);
			pnCentro.add(getPanelValidar(), BorderLayout.SOUTH);
		}
		return pnCentro;
	}

	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atrás");
		}
		return btnAtras;
	}

	private JButton getBtnConfirmar() {
		if (btnConfirmar == null) {
			btnConfirmar = new JButton("Confirmar");
		}
		return btnConfirmar;
	}

	private JPanel getPnAtrasConfirmar() {
		if (pnAtrasConfirmar == null) {
			pnAtrasConfirmar = new JPanel();
			pnAtrasConfirmar.add(getBtnConfirmar());
			pnAtrasConfirmar.add(getBtnAtras());
		}
		return pnAtrasConfirmar;
	}

	private JLabel getLblConfCategorias() {
		if (lblConfCategorias == null) {
			lblConfCategorias = new JLabel("Configuración de Categorías para Carrera ");
			lblConfCategorias.setHorizontalAlignment(SwingConstants.CENTER);
			lblConfCategorias.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lblConfCategorias;
	}

	private JPanel getPanelValidar() {
		if (panelValidar == null) {
			panelValidar = new JPanel();
			panelValidar.add(getBtnValidar());
		}
		return panelValidar;
	}

	private JButton getBtnValidar() {
		if (btnValidar == null)
			btnValidar = new JButton("Validar");
		return btnValidar;
	}

	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setColumns(10);
		}
		return txtNombre;
	}

}
