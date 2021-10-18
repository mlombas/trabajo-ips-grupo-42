package view.atleta;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.ModelFactory;
import model.atleta.AtletaDto;
import model.competicion.CompeticionDto;
import model.inscripcion.InscripcionDto;
import util.AtletaNoValidoException;
import util.ModelException;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;

public class FormularioInscripcionDialog extends JDialog {

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
	private IncripcionesPanel inscripciones;
	
	private AtletaDto atleta;
	private CompeticionDto competicion;
	private JLabel lblFechaNacimiento;
	private JTextField textFechaNacimiento;
	private JLabel lblSexo;
	private JRadioButton rdbtnHombre;
	private JRadioButton rdbtnMujer;

	/**
	 * Create the panel.
	 */
	public FormularioInscripcionDialog(IncripcionesPanel incripcionesPanel) {
		this.competicion = new CompeticionDto();
		this.inscripciones = incripcionesPanel;
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		setSize(new Dimension(465, 350));
		setResizable(false);
		setTitle("Carreras Populares APP - Formulario de Inscripción");
		getContentPane().add(getPanelFormulario(), BorderLayout.CENTER);
		getContentPane().add(getPanelValidarBtn(), BorderLayout.SOUTH);
	}
	
	public void setCompeticionDto(CompeticionDto competicion) {
		this.competicion = competicion;
	}
	
	private boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile("^(.+)@(.+)$");
		return pattern.matcher(email).matches();
	}
	
	private LocalDate validateFecha(String email) {
		LocalDate date;
		
		try {
			date = LocalDate.parse(getTextFechaNacimiento().getText(),
					DateTimeFormatter.ofPattern("d/M/yyyy"));
		} catch(DateTimeParseException dfe) {
			return null;
		}
		
		return date;
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

	private void closeDialog() {
		dispose();
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
			panelFormulario.add(getLblFechaNacimiento());
			panelFormulario.add(getTextFechaNacimiento());
			panelFormulario.add(getLblSexo());
			panelFormulario.add(getRdbtnHombre());
			panelFormulario.add(getRdbtnMujer());
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
			textNombre.setBounds(10, 30, 430, 30);
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
			textEmail.setBounds(10, 170, 430, 30);
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
	
	private JLabel getLblFechaNacimiento() {
		if (lblFechaNacimiento == null) {
			lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
			lblFechaNacimiento.setToolTipText("Introduzca su fecha de nacimiento en el formato: DD/MM/AAAA");
			lblFechaNacimiento.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblFechaNacimiento.setDisplayedMnemonic('N');
			lblFechaNacimiento.setBounds(10, 211, 143, 14);
		}
		return lblFechaNacimiento;
	}
	
	private JTextField getTextFechaNacimiento() {
		if (textFechaNacimiento == null) {
			textFechaNacimiento = new JTextField();
			textFechaNacimiento.setColumns(10);
			textFechaNacimiento.setBounds(10, 231, 210, 30);
		}
		return textFechaNacimiento;
	}
	
	private JLabel getLblSexo() {
		if (lblSexo == null) {
			lblSexo = new JLabel("Sexo:");
			lblSexo.setHorizontalAlignment(SwingConstants.CENTER);
			lblSexo.setToolTipText("");
			lblSexo.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblSexo.setDisplayedMnemonic('N');
			lblSexo.setBounds(264, 214, 139, 14);
		}
		return lblSexo;
	}
	
	private JRadioButton getRdbtnHombre() {
		if (rdbtnHombre == null) {
			rdbtnHombre = new JRadioButton("Hombre");
			rdbtnHombre.setBounds(264, 238, 74, 23);
		}
		return rdbtnHombre;
	}
	
	private JRadioButton getRdbtnMujer() {
		if (rdbtnMujer == null) {
			rdbtnMujer = new JRadioButton("Mujer");
			rdbtnMujer.setBounds(340, 238, 63, 23);
		}
		return rdbtnMujer;
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
			getRootPane().setDefaultButton(btnValidarEInscribirse);
			
			btnValidarEInscribirse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					atleta = new AtletaDto();
					
					// Validamos el nombre
					String nombre = getTextNombre().getText();
					if(!nombre.trim().isEmpty())
						atleta.nombre = nombre;
					else {
						showError("Nombre no válido");
						return;
					} // Show warning
						
					// Validamos el email
					String email = getTextEmail().getText();
					if(validateEmail(email))
						atleta.email = email;
					else {
						showError("Email no válido");
						getTextEmail().setText("");
						return;
					} // Show warning
					
					// Validamos la fechaDeNacimiento
					String fechaNacimiento = getTextFechaNacimiento().getText();
					atleta.fechaNacimiento = validateFecha(fechaNacimiento);
					if(atleta.fechaNacimiento == null) {
						showError("Fecha no válida");
						return;
					} // Show warning
					
					// Validamos el sexo
					if (getRdbtnHombre().isSelected())
						atleta.sexo = "H";
					else if (getRdbtnMujer().isSelected())
						atleta.sexo = "M";
					else {
						showError("Sexo no seleccionado");
						return;
					}
					
					try {
						competicion.id = inscripciones.getCompeticionId();
					} catch(ArrayIndexOutOfBoundsException aiobe) {
						JOptionPane.showMessageDialog(null, "Seleccione una carrera...");
						return;
					}
					
					InscripcionDto inscripcion = null;
					try {
						inscripcion = ModelFactory.forAtletaCrudService().registerAtletaToCompeticion(atleta, competicion);
					} catch (AtletaNoValidoException anve) { // manejamos correctamente las excepciones
						showError(anve.getMessage());
						closeDialog();
						return;
					} catch (ModelException me) {
						showError("Lo siento, algo ha salido mal...");
						closeDialog();
						return;
					}
					
					closeDialog();
					showJustificante(inscripcion);
				}
			});
		}
		return btnValidarEInscribirse;
	}

}
