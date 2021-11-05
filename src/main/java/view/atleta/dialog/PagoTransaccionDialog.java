package view.atleta.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import model.ModelFactory;
import model.inscripcion.InscripcionDto;

public class PagoTransaccionDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnCerrar;
	private JLabel codeLabel;
	private InscripcionDto inscripcion;

	public PagoTransaccionDialog(InscripcionDto inscripcion) {
		this.inscripcion = inscripcion;
		
		setResizable(false);
		setSize(new Dimension(465, 285));
		setTitle("Carreras Populares APP - Pago con Transacción");
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getCodeLabel(), BorderLayout.CENTER);
		getContentPane().add(getBtnCerrar(), BorderLayout.SOUTH);	
	}
	
	private JLabel getCodeLabel() {
		if(codeLabel == null) {
			String code = ModelFactory.forAtletaCrudService().pendingPayWithTransaccion(inscripcion);

			codeLabel = new JLabel("Debes de hacer una transferencia en tu banco con el codigo " + code + 
					" antes de 48h");
		}
		
		return codeLabel;
	}

	private JButton getBtnCerrar() {
		if (btnCerrar == null) {
			btnCerrar = new JButton("Validar Pago");
			btnCerrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					PagoTransaccionDialog.this.dispose();
				}
			});
		}
		return btnCerrar;
	}
}

