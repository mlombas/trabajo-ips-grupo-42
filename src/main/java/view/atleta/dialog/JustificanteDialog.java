package view.atleta.dialog;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import bank_mockup.Bank;
import model.inscripcion.InscripcionDto;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class JustificanteDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JPanel panelFormulario;
	private JLabel lblNombre;
	private JPanel panelOkBtn;
	private JButton btnOk;
	private JLabel lblCompeticion;
	private JLabel lblCategoria;
	private JLabel lblFechaInscripcion;
	private JLabel lblCantidadAbonar;
	private JLabel lblAgradecimiento;
	
	private InscripcionDto inscripcion;
	
	private boolean isPagoTarjeta;
	
	private Bank bank = new Bank();
	
	/**
	 * Create the panel.
	 */
	public JustificanteDialog(InscripcionDto inscripcion, boolean isPagoTarjeta) {
		this.inscripcion = inscripcion;
		this.isPagoTarjeta = isPagoTarjeta;
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		setSize(new Dimension(465, 285));
		setResizable(false);
		setTitle("Carreras Populares APP - Justificante de Inscripción");
		getContentPane().add(getLblAgradecimiento(), BorderLayout.NORTH);
		getContentPane().add(getPanelFormulario(), BorderLayout.CENTER);
		getContentPane().add(getPanelOkBtn(), BorderLayout.SOUTH);
	}
	
	private void showPagoTarjeta(InscripcionDto inscripcion) {
		PagoTarjetaDialog pagoTarjetaDialog = new PagoTarjetaDialog(inscripcion);
		pagoTarjetaDialog.setLocationRelativeTo(null);
		pagoTarjetaDialog.setModal(true);
		pagoTarjetaDialog.setVisible(true);
		this.dispose();
	}
	
	private void showPagoTransacción(InscripcionDto inscripcion) {
		PagoTransaccionDialog diag = new PagoTransaccionDialog(inscripcion);
		diag.setLocationRelativeTo(null);
		diag.setModal(true);
		diag.setVisible(true);
		closeDialog();
	}
	
	private void closeDialog() {
		this.dispose();
	}
	
	private JLabel getLblAgradecimiento() {
		if (lblAgradecimiento == null) {
			lblAgradecimiento = new JLabel("Gracias por realizar la Inscripción, nos vemos en la carrera!");
			lblAgradecimiento.setHorizontalAlignment(SwingConstants.CENTER);
			lblAgradecimiento.setToolTipText("");
			lblAgradecimiento.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblAgradecimiento.setDisplayedMnemonic('N');
		}
		return lblAgradecimiento;
	}
	
	private JPanel getPanelFormulario() {
		if (panelFormulario == null) {
			panelFormulario = new JPanel();
			panelFormulario.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Justificante de Inscripción", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelFormulario.setLayout(null);
			panelFormulario.add(getLblNombre());
			panelFormulario.add(getLblCompeticion());
			panelFormulario.add(getLblCategoria());
			panelFormulario.add(getLblFechaInscripcion());
			panelFormulario.add(getLblCantidadAbonar());
		}
		return panelFormulario;
	}
	
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre: " + inscripcion.nombreAtleta);
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNombre.setBounds(10, 10, 429, 14);
			lblNombre.setDisplayedMnemonic('N');
			lblNombre.setToolTipText("");
		}
		return lblNombre;
	}
	
	private JLabel getLblCompeticion() {
		if (lblCompeticion == null) {
			lblCompeticion = new JLabel("Competición: " + inscripcion.idCompeticion);
			lblCompeticion.setToolTipText("");
			lblCompeticion.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblCompeticion.setDisplayedMnemonic('N');
			lblCompeticion.setBounds(10, 40, 429, 14);
		}
		return lblCompeticion;
	}
	
	private JLabel getLblCategoria() {
		if (lblCategoria == null) {
			lblCategoria = new JLabel("Categoría: " + inscripcion.categoria);
			lblCategoria.setToolTipText("");
			lblCategoria.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblCategoria.setDisplayedMnemonic('N');
			lblCategoria.setBounds(10, 70, 429, 14);
		}
		return lblCategoria;
	}
	
	private JLabel getLblFechaInscripcion() {
		if (lblFechaInscripcion == null) {
			lblFechaInscripcion = new JLabel("Fecha de Inscripción: " + inscripcion.fechaInscripcion);
			lblFechaInscripcion.setToolTipText("");
			lblFechaInscripcion.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblFechaInscripcion.setDisplayedMnemonic('N');
			lblFechaInscripcion.setBounds(10, 100, 429, 14);
		}
		return lblFechaInscripcion;
	}
	
	private JLabel getLblCantidadAbonar() {
		if (lblCantidadAbonar == null) {
			lblCantidadAbonar = new JLabel("Cantidad a abonar: " + inscripcion.cuotaInscripcion);
			lblCantidadAbonar.setToolTipText("");
			lblCantidadAbonar.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblCantidadAbonar.setDisplayedMnemonic('N');
			lblCantidadAbonar.setBounds(10, 130, 429, 14);
		}
		return lblCantidadAbonar;
	}
	
	private JPanel getPanelOkBtn() {
		if (panelOkBtn == null) {
			panelOkBtn = new JPanel();
			panelOkBtn.add(getBtnOk());
		}
		return panelOkBtn;
	}
	
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("OK");
			btnOk.setMnemonic('O');
			
			btnOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(isPagoTarjeta)
						showPagoTarjeta(inscripcion);
					else
						showPagoTransacción(inscripcion);
				}
			});
		}
		return btnOk;
	}
	
}
