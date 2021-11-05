package view.atleta.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import model.atleta.TarjetaDto;
import model.inscripcion.InscripcionDto;
import util.Util;
import util.exceptions.ApplicationException;

public class PagoTarjetaDialog extends JDialog {


	private static final long serialVersionUID = 1L;
	private JPanel pnTarjetaCredito;
	private JButton btnValidarPago;
	private JPanel pnNumero;
	private JLabel lblNmeroDeLa;
	private JTextField tfNumero;
	private JPanel pnFecha;
	private JLabel lbFecha;
	private JTextField tfFecha;
	private JPanel pnCvc;
	private JLabel lbCvc;
	private JTextField tfCvc;
	private InscripcionDto inscripcion;
	
	/**
	 * Create the frame.
	 */
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
			pnTarjetaCredito.setLayout(new GridLayout(3, 1, 0, 0));
			pnTarjetaCredito.add(getPnNumero());
			pnTarjetaCredito.add(getPnFecha());
			pnTarjetaCredito.add(getPnCvc());
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
			showMessage("Cvc no válido", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		} else {
			try {
				TarjetaDto tarjeta = new TarjetaDto();
				tarjeta.number = tfNumero.getText();
				tarjeta.cvc = tfCvc.getText();
				tarjeta.expiration = Util.isoStringToDate(tfFecha.getText());
				AtletaCrudService acs = new AtletaCrudServiceImpl();
				LocalDate date = acs.payWithTarjeta(inscripcion, tarjeta);
				showMessage("El pago con los siguientes datos se ha confirmado:\nNombre: " + inscripcion.nombreAtleta + " \nCarrera: " + inscripcion.nombreCompeticion
						+ "\nCuota: " + inscripcion.cuotaInscripcion + "\nFecha de pago: " + date, "\nInformacion", JOptionPane.INFORMATION_MESSAGE);
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
		d.setLocation(200, 200);
		d.setVisible(true);

	}

	private boolean checkCvc() {
		if (tfCvc.getText().strip().isEmpty() || tfCvc.getText().length() != 3)
			return false;
		return true;
	}

	private boolean checkFecha() {
		if (tfFecha.getText().strip().isEmpty())
			return false;
		return true;
	}

	private boolean checkNumber() {
		if (tfNumero.getText().strip().isEmpty()) {
			return false;
		}
		return true;
	}

	private JPanel getPnNumero() {
		if (pnNumero == null) {
			pnNumero = new JPanel();
			FlowLayout fl_pnNumero = new FlowLayout(FlowLayout.CENTER, 5, 35);
			pnNumero.setLayout(fl_pnNumero);
			pnNumero.add(getLblNmeroDeLa());
			pnNumero.add(getTfNumero());
		}
		return pnNumero;
	}

	private JLabel getLblNmeroDeLa() {
		if (lblNmeroDeLa == null) {
			lblNmeroDeLa = new JLabel("Número de la tarjeta:");
			lblNmeroDeLa.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNmeroDeLa;
	}

	private JTextField getTfNumero() {
		if (tfNumero == null) {
			tfNumero = new JTextField();
			tfNumero.setColumns(20);
		}
		return tfNumero;
	}

	private JPanel getPnFecha() {
		if (pnFecha == null) {
			pnFecha = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnFecha.getLayout();
			flowLayout.setVgap(35);
			pnFecha.add(getLbFecha());
			pnFecha.add(getTfFecha());
		}
		return pnFecha;
	}

	private JLabel getLbFecha() {
		if (lbFecha == null) {
			lbFecha = new JLabel("Fecha de caducidad:");
		}
		return lbFecha;
	}

	private JTextField getTfFecha() {
		if (tfFecha == null) {
			tfFecha = new JTextField();
			tfFecha.setColumns(20);
		}
		return tfFecha;
	}

	private JPanel getPnCvc() {
		if (pnCvc == null) {
			pnCvc = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnCvc.getLayout();
			flowLayout.setVgap(35);
			pnCvc.add(getLblNewLabel_1());
			pnCvc.add(getTfCvc());
		}
		return pnCvc;
	}

	private JLabel getLblNewLabel_1() {
		if (lbCvc == null) {
			lbCvc = new JLabel("CVC:");
		}
		return lbCvc;
	}

	private JTextField getTfCvc() {
		if (tfCvc == null) {
			tfCvc = new JTextField();
			tfCvc.setColumns(20);
		}
		return tfCvc;
	}
}
