package view.atleta.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.atleta.AtletaCrudService;
import controller.atleta.AtletaCrudServiceImpl;
import model.ModelFactory;
import model.atleta.TarjetaDto;
import model.inscripcion.InscripcionDto;
import util.Util;
import util.exceptions.ApplicationException;
import view.atleta.AtletaMain;
import net.miginfocom.swing.MigLayout;

public class PagoTarjetaDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JPanel pnTarjetaCredito;
	private JButton btnValidarPago;
	private InscripcionDto inscripcion;
	private JLabel lblNumeroTarjeta;
	private JTextField textFieldNumeroTarjeta;
	private JLabel lblCVC;
	private JTextField textFieldCVC;
	private JLabel lbFechaCaducidad;
	private JTextField textFieldFechaCaducidad;
	
	public PagoTarjetaDialog(InscripcionDto inscripcion) {
		setResizable(false);
		setSize(new Dimension(465, 285));
		setTitle("Carreras Populares APP - Pago con Tarjeta");
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPnTarjetaCredito(), BorderLayout.CENTER);
		getContentPane().add(getBtnValidarPago(), BorderLayout.SOUTH);
		this.inscripcion = inscripcion;
	}

	private JPanel getPnTarjetaCredito() {
		if (pnTarjetaCredito == null) {
			pnTarjetaCredito = new JPanel();
			pnTarjetaCredito.setLayout(new MigLayout("", "[grow,fill]", "[grow][][][][][][][grow]"));
			pnTarjetaCredito.add(getLblNumeroTarjeta(), "flowy,cell 0 1,grow");
			pnTarjetaCredito.add(getTextFieldNumeroTarjeta(), "cell 0 2,grow");
			pnTarjetaCredito.add(getLbFechaCaducidad(), "cell 0 3,grow");
			pnTarjetaCredito.add(getTextFieldFechaCaducidad(), "cell 0 4,grow");
			pnTarjetaCredito.add(getLblCVC(), "flowy,cell 0 5,grow");
			pnTarjetaCredito.add(getTextFieldCVC(), "cell 0 6,grow");
		}
		return pnTarjetaCredito;
	}

	private JButton getBtnValidarPago() {
		if (btnValidarPago == null) {
			btnValidarPago = new JButton("Validar Pago");
			btnValidarPago.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					validarPago();
				}
			});
		}
		return btnValidarPago;
	}

	private void validarPago() {
		if (!checkNumber()) {
			showMessage("Número de tarjeta no válido", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		} else if (!checkFecha()) {
			showMessage("Fecha de caducidad no válida", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		} else if (!checkCvc()) {
			showMessage("CVC no válido", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		} else {
			try {
				TarjetaDto tarjeta = new TarjetaDto();
				tarjeta.number = textFieldNumeroTarjeta.getText();
				tarjeta.cvc = textFieldCVC.getText();
				tarjeta.expiration = Util.isoStringToDate(textFieldFechaCaducidad.getText());
				AtletaCrudService acs = ModelFactory.forAtletaCrudService();
				LocalDate date = acs.payWithTarjeta(inscripcion, tarjeta);
				showMessage("El pago con los siguientes datos se ha confirmado:\nNombre: " + inscripcion.nombreAtleta + 
							" \nCarrera: " + inscripcion.nombreCompeticion + 
							"\nCuota: " + inscripcion.cuotaInscripcion + 
							"\nFecha de pago: " + date, 
								"\nInformacion", 
								JOptionPane.INFORMATION_MESSAGE);
				AtletaMain.getInstance().startPanel();
				this.dispose();
			} catch (ApplicationException e) {
				showMessage(e.getMessage(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
			} catch (RuntimeException e) { 
				e.printStackTrace(); 
				showMessage(e.toString(), "Excepcion no controlada", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}

	private void showMessage(String message, String title, int type) {
		JOptionPane pane = new JOptionPane(message, type, JOptionPane.DEFAULT_OPTION);
		pane.setOptions(new Object[] { "ACEPTAR" }); 
		JDialog d = pane.createDialog(pane, title);
		d.setLocationRelativeTo(null);
		d.setVisible(true);

	}

	private boolean checkCvc() {
		if (textFieldCVC.getText().strip().isEmpty() || textFieldCVC.getText().length() != 3)
			return false;
		return true;
	}

	private boolean checkFecha() {
		if (textFieldFechaCaducidad.getText().strip().isEmpty())
			return false;
		return true;
	}

	private boolean checkNumber() {
		if (textFieldNumeroTarjeta.getText().strip().isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	private JLabel getLblNumeroTarjeta() {
		if (lblNumeroTarjeta == null) {
			lblNumeroTarjeta = new JLabel("Número de la tarjeta:");
			lblNumeroTarjeta.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return lblNumeroTarjeta;
	}
	
	private JTextField getTextFieldNumeroTarjeta() {
		if (textFieldNumeroTarjeta == null) {
			textFieldNumeroTarjeta = new JTextField();
			textFieldNumeroTarjeta.setColumns(20);
		}
		return textFieldNumeroTarjeta;
	}
	
	private JLabel getLbFechaCaducidad() {
		if (lbFechaCaducidad == null) {
			lbFechaCaducidad = new JLabel("Fecha de caducidad:");
		}
		return lbFechaCaducidad;
	}
	
	private JTextField getTextFieldFechaCaducidad() {
		if (textFieldFechaCaducidad == null) {
			textFieldFechaCaducidad = new JTextField();
			textFieldFechaCaducidad.setColumns(20);
		}
		return textFieldFechaCaducidad;
	}
	
	private JLabel getLblCVC() {
		if (lblCVC == null) {
			lblCVC = new JLabel("CVC:");
		}
		return lblCVC;
	}
	
	private JTextField getTextFieldCVC() {
		if (textFieldCVC == null) {
			textFieldCVC = new JTextField();
			textFieldCVC.setColumns(20);
		}
		return textFieldCVC;
	}

}
