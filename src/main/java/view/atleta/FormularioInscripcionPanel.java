package view.atleta;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import model.ModelFactory;
import model.atleta.AtletaDto;
import model.competicion.CompeticionDto;
import java.awt.BorderLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;

public class FormularioInscripcionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel panelFormulario;
	private ButtonGroup tipoDePago;
	private JButton btnValidarEInscribirse;
	private JTextField textNombre;
	private JLabel lblNombre;
	private JLabel lblEmail;
	private JTextField textEmail;
	private JPanel panelTipoDePago;
	private JRadioButton rdbtnTransferencia;
	private JRadioButton rdbtnTarjeta;
	private JPanel panelValidarBtn;
  
  	private AtletaDto atleta;
	private CompeticionDto competicion;

	/**
	 * Create the panel.
	 */
	public FormularioInscripcionPanel() {
		this.competicion = new CompeticionDto();
		
		setLayout(new BorderLayout(0, 0));
		add(getPanelFormulario(), BorderLayout.CENTER);
		add(getPanelValidarBtn(), BorderLayout.SOUTH);
	}
	
	public void setCompeticionDto(CompeticionDto competicion) {
		this.competicion = competicion;
	}
	
	private JPanel getPanelFormulario() {
		if (panelFormulario == null) {
			panelFormulario = new JPanel();
			panelFormulario.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Formulario de Inscripción a la Carrera", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			panelFormulario.setLayout(null);
			panelFormulario.add(getLblNombre());
			panelFormulario.add(getTextNombre());
			panelFormulario.add(getPanelTipoDePago());
			panelFormulario.add(getLblEmail());
			panelFormulario.add(getTextEmail());
		}
		return panelFormulario;
	}
	
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre:");
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNombre.setBounds(10, 10, 84, 14);
			lblNombre.setDisplayedMnemonic('N');
			lblNombre.setToolTipText("Inserte su nombre (no apellidos)");
			lblNombre.setLabelFor(getTextNombre());
		}
		return lblNombre;
	}
	
	private JTextField getTextNombre() {
		if (textNombre == null) {
			textNombre = new JTextField();
			textNombre.setBounds(10, 170, 430, 30);
			textNombre.setColumns(10);
		}
		return textNombre;
	}
	
	private JLabel getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new JLabel("Correo Electrónico:");
			lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblEmail.setBounds(10, 150, 182, 14);
			lblEmail.setDisplayedMnemonic('E');
			lblEmail.setToolTipText("Inserte su email: usuario@correo.es");
			lblEmail.setLabelFor(getTextEmail());
		}
		return lblEmail;
	}
	
	private JTextField getTextEmail() {
		if (textEmail == null) {
			textEmail = new JTextField();
			textEmail.setBounds(10, 30, 430, 30);
			textEmail.setColumns(10);
		}
		return textEmail;
	}
	
	private JPanel getPanelTipoDePago() {
		if (panelTipoDePago == null) {
			panelTipoDePago = new JPanel();
			panelTipoDePago.setBounds(10, 70, 430, 70);
			tipoDePago = new ButtonGroup();
			tipoDePago.add(getRdbtnTransferencia());
			tipoDePago.add(getRdbtnTarjeta());
			panelTipoDePago.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Tipo de Pago", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelTipoDePago.setLayout(new BoxLayout(panelTipoDePago, BoxLayout.Y_AXIS));
			panelTipoDePago.add(getRdbtnTransferencia());
			panelTipoDePago.add(getRdbtnTarjeta());
		}
		return panelTipoDePago;
	}
	
	private JRadioButton getRdbtnTransferencia() {
		if (rdbtnTransferencia == null) {
			rdbtnTransferencia = new JRadioButton("Transferencia Bancaria");
			rdbtnTransferencia.setToolTipText("La transferencia implica que vaya al banco físicamente");
			rdbtnTransferencia.setMnemonic('B');
		}
		return rdbtnTransferencia;
	}
	
	private JRadioButton getRdbtnTarjeta() {
		if (rdbtnTarjeta == null) {
			rdbtnTarjeta = new JRadioButton("Tarjeta");
			rdbtnTarjeta.setMnemonic('T');
		}
		return rdbtnTarjeta;
	}
	
	private JPanel getPanelValidarBtn() {
		if (panelValidarBtn == null) {
			panelValidarBtn = new JPanel();
			panelValidarBtn.add(getBtnValidarEInscribirse());
		}
		return panelValidarBtn;
	}
	
	private JButton getBtnValidarEInscribirse() {
		if (btnValidarEInscribirse == null) {
			btnValidarEInscribirse = new JButton("Validar e Inscribirse");
			
			btnValidarEInscribirse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					atleta.nombre = getTextNombre().getSelectedText();
					atleta.email = getTextEmail().getSelectedText();
					
					ModelFactory.forAtletaCrudService().registerAtletaToCompeticion(atleta, competicion);
				}
			});
		}
		return btnValidarEInscribirse;
	}
	
	private JButton getBtnValidarEInscribirse() {
		if (btnValidarEInscribirse == null) {
			btnValidarEInscribirse = new JButton("Validar e Inscribirse");
		}
		return btnValidarEInscribirse;
	}
  
}
