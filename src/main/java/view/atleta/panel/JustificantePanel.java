package view.atleta.panel;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import bank_mockup.Bank;
import model.inscripcion.InscripcionDto;
import view.atleta.dialog.PagoTarjetaDialog;
import view.atleta.dialog.PagoTransaccionDialog;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

public class JustificantePanel extends JPanel {

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
	public JustificantePanel() {
		setLayout(new BorderLayout(0, 0));
		add(getLblAgradecimiento(), BorderLayout.NORTH);
		add(getPanelOkBtn(), BorderLayout.SOUTH);
	}
	
	public void setInscripcionDto(InscripcionDto inscripcion) {
		this.inscripcion = inscripcion;
		
		// Cuando se establezca la inscripción...
		add(getPanelFormulario(), BorderLayout.CENTER);
	}
	
	public void setIsPagoTarjeta(boolean isPagoTarjeta) {
		this.isPagoTarjeta = isPagoTarjeta;
	}
	
	private void showPagoTarjeta(InscripcionDto inscripcion) {
		PagoTarjetaDialog pagoTarjetaDialog = new PagoTarjetaDialog(inscripcion);
		pagoTarjetaDialog.setLocationRelativeTo(null);
		pagoTarjetaDialog.setModal(true);
		pagoTarjetaDialog.setVisible(true);
	}
	
	private void showPagoTransacción(InscripcionDto inscripcion) {
		PagoTransaccionDialog diag = new PagoTransaccionDialog(inscripcion);
		diag.setLocationRelativeTo(null);
		diag.setModal(true);
		diag.setVisible(true);
	}
	
	private JLabel getLblAgradecimiento() {
		if (lblAgradecimiento == null) {
			lblAgradecimiento = new JLabel("Gracias por realizar la Inscripción, nos vemos en la carrera!");
			lblAgradecimiento.setHorizontalAlignment(SwingConstants.CENTER);
			lblAgradecimiento.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblAgradecimiento.setDisplayedMnemonic('N');
		}
		return lblAgradecimiento;
	}
	
	private JPanel getPanelFormulario() {
		if (panelFormulario == null) {
			panelFormulario = new JPanel();
			panelFormulario.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Justificante de Inscripción", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelFormulario.setLayout(new MigLayout("", "[429px]", "[14px][14px][14px][14px][14px]"));
			panelFormulario.add(getLblNombre(), "cell 0 0,grow");
			panelFormulario.add(getLblCompeticion(), "cell 0 1,grow");
			panelFormulario.add(getLblCategoria(), "cell 0 2,grow");
			panelFormulario.add(getLblFechaInscripcion(), "cell 0 3,grow");
			panelFormulario.add(getLblCantidadAbonar(), "cell 0 4,grow");
		}
		return panelFormulario;
	}
	
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre: " + inscripcion.nombreAtleta);
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lblNombre;
	}
	
	private JLabel getLblCompeticion() {
		if (lblCompeticion == null) {
			lblCompeticion = new JLabel("Competición: " + inscripcion.idCompeticion);
			lblCompeticion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lblCompeticion;
	}
	
	private JLabel getLblCategoria() {
		if (lblCategoria == null) {
			lblCategoria = new JLabel("Categoría: " + inscripcion.categoria);
			lblCategoria.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lblCategoria;
	}
	
	private JLabel getLblFechaInscripcion() {
		if (lblFechaInscripcion == null) {
			lblFechaInscripcion = new JLabel("Fecha de Inscripción: " + inscripcion.fechaInscripcion);
			lblFechaInscripcion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lblFechaInscripcion;
	}
	
	private JLabel getLblCantidadAbonar() {
		if (lblCantidadAbonar == null) {
			lblCantidadAbonar = new JLabel("Cantidad a abonar: " + inscripcion.cuotaInscripcion);
			lblCantidadAbonar.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
