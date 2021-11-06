package view.organizador.panel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import view.organizador.OrganizadorMain;
import view.organizador.util.AtrasOrganizadorButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;
import java.awt.GridLayout;

public class CrearCompeticionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panelFormulario;
	private JButton btnValidarDatos;
	private JTextField textNombre;
	private JLabel lblNombre;
	private JLabel lblDescripcion;
	private JLabel lblTipo;
	private JTextField textTipo;
	private JPanel panelValidarBtn;
	private AtrasOrganizadorButton btnAtras;
	private JSpinner spinnerNumeroPlazas;
	private JLabel lblNumeroPlazas;
	private JLabel lblNumeroDorsalesReservados;
	private JSpinner spinnerDorsalesReservados;
	private JTextArea textAreaDescripcion;
	private JPanel panelCategorias;
	private JPanel panelPlazos;
	private JButton btnCategorias;
	private JButton btnPlazos;

	
	/**
	 * Create the panel.
	 */
	public CrearCompeticionPanel() {
		setLayout(new BorderLayout(0, 0));
		add(getPanelFormulario(), BorderLayout.CENTER);
		add(getPanelValidarBtn(), BorderLayout.SOUTH);
	}
	
	private JPanel getPanelFormulario() {
		if (panelFormulario == null) {
			panelFormulario = new JPanel();
			panelFormulario.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Formulario de Inscripción a la Carrera", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			panelFormulario.setLayout(new MigLayout("", "[grow,center][grow,center]", "[grow][][][][grow][][][][grow][grow][grow]"));
			panelFormulario.add(getLblNombre(), "flowx,cell 0 1 2 1,alignx left,growy");
			panelFormulario.add(getTextNombre(), "cell 0 2,grow");
			panelFormulario.add(getTextTipo(), "cell 1 2,grow");
			panelFormulario.add(getLblDescripcion(), "cell 0 3 2 1,alignx left");
			panelFormulario.add(getTextAreaDescripcion(), "cell 0 4 2 2,grow");
			panelFormulario.add(getLblNumeroDorsalesReservados(), "cell 0 6,alignx left");
			panelFormulario.add(getLblNumeroPlazas(), "cell 1 6,alignx left");
			panelFormulario.add(getSpinnerDorsalesReservados(), "cell 0 7,growx");
			panelFormulario.add(getSpinnerNumeroPlazas(), "cell 1 7,growx");
			panelFormulario.add(getLblTipo(), "cell 1 1,alignx left,aligny center");
			panelFormulario.add(getPanelCategorias(), "cell 0 8 2 1,grow");
			panelFormulario.add(getPanelPlazos(), "cell 0 9 2 1,grow");
		}
		return panelFormulario;
	}
	
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre:");
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNombre.setDisplayedMnemonic('N');
			lblNombre.setToolTipText("Inserte el nombre de la competición");
			lblNombre.setLabelFor(getTextNombre());
		}
		return lblNombre;
	}
	
	private JTextField getTextNombre() {
		if (textNombre == null) {
			textNombre = new JTextField();
			textNombre.setColumns(10);
		}
		return textNombre;
	}
	
	private JLabel getLblDescripcion() {
		if (lblDescripcion == null) {
			lblDescripcion = new JLabel("Descripción:");
			lblDescripcion.setLabelFor(getTextAreaDescripcion());
			lblDescripcion.setToolTipText("Escriba un breve texto explicando en qué consiste la competición");
			lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblDescripcion.setDisplayedMnemonic('D');
		}
		return lblDescripcion;
	}
	
	private JLabel getLblTipo() {
		if (lblTipo == null) {
			lblTipo = new JLabel("Tipo:");
			lblTipo.setToolTipText("");
			lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblTipo.setDisplayedMnemonic('N');
		}
		return lblTipo;
	}
	
	private JTextField getTextTipo() {
		if (textTipo == null) {
			textTipo = new JTextField();
			textTipo.setColumns(10);
		}
		return textTipo;
	}
	
	private JPanel getPanelValidarBtn() {
		if (panelValidarBtn == null) {
			panelValidarBtn = new JPanel();
			panelValidarBtn.add(getBtnValidarDatos());
			panelValidarBtn.add(getBtnAtras());
		}
		return panelValidarBtn;
	}
	
	private AtrasOrganizadorButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new AtrasOrganizadorButton(OrganizadorMain.ORGANIZADOR_MENU);
		}
		return btnAtras;
	}
	
	private JButton getBtnValidarDatos() {
		if (btnValidarDatos == null) {
			btnValidarDatos = new JButton("Validar mis Datos");
		}
		return btnValidarDatos;
	}
	
	private JSpinner getSpinnerNumeroPlazas() {
		if (spinnerNumeroPlazas == null) {
			spinnerNumeroPlazas = new JSpinner();
			spinnerNumeroPlazas.setModel(new SpinnerNumberModel(0, 0, null, 1));
		}
		return spinnerNumeroPlazas;
	}
	private JLabel getLblNumeroPlazas() {
		if (lblNumeroPlazas == null) {
			lblNumeroPlazas = new JLabel("Número de Plazas:");
			lblNumeroPlazas.setLabelFor(getSpinnerNumeroPlazas());
			lblNumeroPlazas.setToolTipText("");
			lblNumeroPlazas.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNumeroPlazas.setDisplayedMnemonic('N');
		}
		return lblNumeroPlazas;
	}
	private JLabel getLblNumeroDorsalesReservados() {
		if (lblNumeroDorsalesReservados == null) {
			lblNumeroDorsalesReservados = new JLabel("Dorsales Reservados:");
			lblNumeroDorsalesReservados.setLabelFor(getSpinnerDorsalesReservados());
			lblNumeroDorsalesReservados.setToolTipText("");
			lblNumeroDorsalesReservados.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNumeroDorsalesReservados.setDisplayedMnemonic('N');
		}
		return lblNumeroDorsalesReservados;
	}
	private JSpinner getSpinnerDorsalesReservados() {
		if (spinnerDorsalesReservados == null) {
			spinnerDorsalesReservados = new JSpinner();
			spinnerDorsalesReservados.setModel(new SpinnerNumberModel(0, 0, null, 1));
		}
		return spinnerDorsalesReservados;
	}
	private JTextArea getTextAreaDescripcion() {
		if (textAreaDescripcion == null) {
			textAreaDescripcion = new JTextArea();
		}
		return textAreaDescripcion;
	}
	private JPanel getPanelCategorias() {
		if (panelCategorias == null) {
			panelCategorias = new JPanel();
			panelCategorias.setLayout(new GridLayout(0, 1, 0, 0));
			panelCategorias.add(getBtnCategorias());
		}
		return panelCategorias;
	}
	private JPanel getPanelPlazos() {
		if (panelPlazos == null) {
			panelPlazos = new JPanel();
			panelPlazos.setLayout(new GridLayout(0, 1, 0, 0));
			panelPlazos.add(getBtnPlazos());
		}
		return panelPlazos;
	}
	private JButton getBtnCategorias() {
		if (btnCategorias == null) {
			btnCategorias = new JButton("Configurar Categorías");
		}
		return btnCategorias;
	}
	private JButton getBtnPlazos() {
		if (btnPlazos == null) {
			btnPlazos = new JButton("Configurar Plazos");
		}
		return btnPlazos;
	}
}
