package view.atleta;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class FormularioInscripcionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel panelFormulario;
	private ButtonGroup tipoDePago;
	private JRadioButton rdbtnTransferencia;
	private JRadioButton rdbtnTarjeta;
	private JButton btnValidarEInscribirse;

	/**
	 * Create the panel.
	 */
	public FormularioInscripcionPanel() {
		setLayout(new BorderLayout(0, 0));
		add(getPanelFormulario());
		add(getBtnValidarEInscribirse(), BorderLayout.SOUTH);

	}
	
	private JPanel getPanelFormulario() {
		if (panelFormulario == null) {
			panelFormulario = new JPanel();
			tipoDePago = new ButtonGroup();
			
			tipoDePago.add(getRdbtnTransferencia());
			tipoDePago.add(getRdbtnTarjeta());
			
			panelFormulario.add(getRdbtnTransferencia());
			panelFormulario.add(getRdbtnTarjeta());
		}
		return panelFormulario;
	}
	
	private JRadioButton getRdbtnTransferencia() {
		if (rdbtnTransferencia == null) {
			rdbtnTransferencia = new JRadioButton("Transferencia Bancaria");
		}
		return rdbtnTransferencia;
	}
	
	private JRadioButton getRdbtnTarjeta() {
		if (rdbtnTarjeta == null) {
			rdbtnTarjeta = new JRadioButton("Tarjeta");
		}
		return rdbtnTarjeta;
	}
	
	private JButton getBtnValidarEInscribirse() {
		if (btnValidarEInscribirse == null) {
			btnValidarEInscribirse = new JButton("Validar e Inscribirse");
		}
		return btnValidarEInscribirse;
	}
}
