package view.atleta.panel;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.ModelFactory;
import model.atleta.AtletaDto;
import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import util.Validate;
import util.exceptions.AtletaNoValidoException;
import util.exceptions.ModelException;
import view.atleta.dialog.JustificanteDialog;

public class FormularioInscripcionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel panelFormulario;
	private ButtonGroup tipoDePago;
	private JButton btnValidarEInscribirse;
	private JPanel panelTipoDePago;
	private JRadioButton rdbtnTransferencia;
	private JRadioButton rdbtnTarjeta;
	private JPanel panelValidarBtn;
	private JLabel lblEmail;
	private JTextField textEmail;
	
	private AtletaDto atleta;
	private CompeticionDto competicion;
	private InscripcionDto inscripcion;

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
	
	public void setInscripcionDto(InscripcionDto inscripcion) {
		this.inscripcion = inscripcion;
	}
	
	private void showError(String arg) {
		JOptionPane.showMessageDialog(this,
				arg,
			    "ERROR - " + arg,
			    JOptionPane.ERROR_MESSAGE);
	}
	
	private void showJustificante(InscripcionDto inscripcion) {
		JustificanteDialog justificanteDialog = new JustificanteDialog(inscripcion, getRdbtnTarjeta().isSelected());
		justificanteDialog.setLocationRelativeTo(null);
		justificanteDialog.setModal(true);
		justificanteDialog.setVisible(true);
	}
	
	private JPanel getPanelFormulario() {
		if (panelFormulario == null) {
			panelFormulario = new JPanel();
			panelFormulario.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Formulario de Inscripción a la Carrera", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			panelFormulario.setLayout(new MigLayout("", "[430px,grow]", "[grow][fill][fill][fill][grow]"));
			panelFormulario.add(getLblEmail(), "flowx,cell 0 1,aligny center");
			panelFormulario.add(getTextEmail(), "cell 0 2,growx");
			panelFormulario.add(getPanelTipoDePago(), "cell 0 3,growx,aligny center");
		}
		return panelFormulario;
	}
	
	private JLabel getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new JLabel("Correo Electrónico:");
			lblEmail.setToolTipText("Inserte su email: usuario@correo.es");
			lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblEmail.setDisplayedMnemonic('E');
		}
		return lblEmail;
	}
	private JTextField getTextEmail() {
		if (textEmail == null) {
			textEmail = new JTextField();
			textEmail.setColumns(10);
		}
		return textEmail;
	}
	
	private JPanel getPanelTipoDePago() {
		if (panelTipoDePago == null) {
			panelTipoDePago = new JPanel();
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
					atleta = new AtletaDto();
						
					// Validamos el email
					String email = getTextEmail().getText();
					if(Validate.validateEmail(email))
						atleta.email = email;
					else {
						showError("Email no válido");
						getTextEmail().setText("");
						return;
					} // Show warning
					
					// TODO findAtleta
					
					try {
						competicion.id = inscripcion.idCompeticion;
						competicion.cuota = inscripcion.cuotaInscripcion;
						competicion.nombreCarrera = inscripcion.nombreCompeticion;
					} catch(ArrayIndexOutOfBoundsException aiobe) {
						JOptionPane.showMessageDialog(null, "Seleccione una carrera...");
						return;
					}
					
					InscripcionDto inscripcion = null;
					try {
						inscripcion = ModelFactory.forAtletaCrudService().registerAtletaToCompeticion(atleta, competicion);
					} catch (AtletaNoValidoException anve) { // manejamos correctamente las excepciones
						showError(anve.getMessage());
						// TODO close
						return;
					} catch (ModelException me) {
						me.printStackTrace();
						showError("Lo siento, algo ha salido mal...");
						// TODO close
						return;
					}
					
					// TODO close
					showJustificante(inscripcion);
				}
			});
		}
		return btnValidarEInscribirse;
	}

}
