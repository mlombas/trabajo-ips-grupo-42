package view.atleta.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.ModelFactory;
import model.atleta.AtletaDto;
import model.competicion.CompeticionDto;
import net.miginfocom.swing.MigLayout;
import util.Validate;
import util.exceptions.ModelException;
import view.atleta.AtletaMain;
import view.util.table.AtletasToTable;

public class FormularioInscripcionClubPanel extends JPanel {

	private static final long serialVersionUID = 1L;
		
	private JScrollPane scrollPaneAtletas;
	private JPanel panelFormularioAtleta;
	private JLabel lblNombre;
	private JTextField textNombre;
	private JLabel lblEmail;
	private JTextField textEmail;
	private JLabel lblFechaNacimiento;
	private JTextField textFechaNacimiento;
	private JLabel lblSexo;
	private JPanel panelBotones;
	private JButton btnAñadir;
	private JButton btnOK;
	
	AtletasToTable table;
	
	private AtletaDto atleta;
	private List<AtletaDto> atletas;
	private CompeticionDto competicion;
	
	private String nombreClub;
	private JComboBox<String> comboBoxSexo;
	private JButton btnValidarCorreo;

	public FormularioInscripcionClubPanel() {
		this.atletas = new ArrayList<>();
		
		setLayout(new MigLayout("", "[grow,fill]", "[grow][grow][grow][]"));
		add(getScrollPaneAtletas(), "cell 0 1,alignx left,aligny top");
		add(getPanelFormularioAtleta(), "cell 0 2,grow");
		add(getPanelBotones(), "cell 0 3,grow");
	}
	
	public void setCompeticionDto(CompeticionDto competicion) {
		this.competicion = competicion;
	}
	
	public void setNombreClub(String nombreClub) {
		this.nombreClub = nombreClub;
	}

	private void showError(String arg) {
		JOptionPane.showMessageDialog(this,
				arg,
			    "ERROR - " + arg,
			    JOptionPane.ERROR_MESSAGE);
	}
	
	public void reset() {
		atletas = new ArrayList<>();
		table.reset();
		resetForm();
	}
	
	private void resetForm() {
		this.atleta = new AtletaDto(); // reseteamos al atleta con el que estamos trabajando
		
		getTextNombre().setText(""); // reestablecemos todos los textos
		getTextEmail().setText("");
		getTextFechaNacimiento().setText("");
		
		getLblNombre().setVisible(false); // hacemos invisibles todos los campos adicionales del formulario
		getTextNombre().setVisible(false);
		getLblFechaNacimiento().setVisible(false);
		getTextFechaNacimiento().setVisible(false);
		getLblSexo().setVisible(false);
		getComboBoxSexo().setVisible(false);
	}
	
	private JScrollPane getScrollPaneAtletas() {
		if (scrollPaneAtletas == null) {
			scrollPaneAtletas = new JScrollPane();
			atletas = new ArrayList<>();
			table = new AtletasToTable(atletas);
			table.setEnabled(false);
			table.getColumnModel().removeColumn(table.getColumnModel().getColumn(2));
			scrollPaneAtletas.setViewportView(table);
		}
		return scrollPaneAtletas;
	}
	
	private JPanel getPanelFormularioAtleta() {
		if (panelFormularioAtleta == null) {
			panelFormularioAtleta = new JPanel();
			panelFormularioAtleta.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), 
					"Formulario de Inscripción a la Carrera", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			
			panelFormularioAtleta.setLayout(new MigLayout("", "[grow,center]", "[grow][][][][][][][][][][grow]"));
			
			panelFormularioAtleta.add(getLblEmail(), "cell 0 1,alignx left");
			panelFormularioAtleta.add(getTextEmail(), "flowy,cell 0 2,growx");
			panelFormularioAtleta.add(getBtnValidarCorreo(), "cell 0 3,growx");
			panelFormularioAtleta.add(getLblNombre(), "cell 0 4,alignx left,growy");
			panelFormularioAtleta.add(getTextNombre(), "cell 0 5,grow");
			panelFormularioAtleta.add(getLblFechaNacimiento(), "cell 0 6,alignx left,aligny center");
			panelFormularioAtleta.add(getTextFechaNacimiento(), "cell 0 7,grow");
			panelFormularioAtleta.add(getLblSexo(), "cell 0 8,alignx left,aligny top");
			panelFormularioAtleta.add(getComboBoxSexo(), "cell 0 9,growx");
		}
		return panelFormularioAtleta;
	}
	
	private JLabel getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new JLabel("E-mail:");
			lblEmail.setLabelFor(getTextEmail());
			lblEmail.setToolTipText("Inserte su email (a@a.es)");
			lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblEmail.setDisplayedMnemonic('D');
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
	
	private JButton getBtnValidarCorreo() {
		if (btnValidarCorreo == null) {
			btnValidarCorreo = new JButton("Validar correo");
			btnValidarCorreo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					atleta = new AtletaDto();
					
					// Validamos el email
					String email = getTextEmail().getText();
					if(Validate.validateEmail(email)) {
						atleta.email = email;
						
						if (atletas.contains(atleta)) {
							atletas.remove(atleta); // eliminamos el atleta que acabamos de introducir
							showError("Este atleta ya está en el lote");
							resetForm();
							return;
						}
					}else {
						showError("Email no válido");
						resetForm();
						return;
					} // Show warning
					
					try {
						Optional<AtletaDto> a = ModelFactory.forAtletaCrudService().findByAtletaEmail(atleta.email);
						
						if (a.isEmpty()) { // activamos todos los botones
							getLblNombre().setVisible(true);
							getTextNombre().setVisible(true);
							getLblFechaNacimiento().setVisible(true);
							getTextFechaNacimiento().setVisible(true);
							getLblSexo().setVisible(true);
							getComboBoxSexo().setVisible(true);
							
							// Establecemos el club
							atleta.club = nombreClub;
						} else { // lo añadimos directamente
							atleta = a.get();
							
							// Establecemos el club
							atleta.club = nombreClub;
							
							atletas.add(atleta);
							table.addAtleta(atleta);
							resetForm(); // reestablecemos el formulario para añadir más atletas
						}
					} catch(ModelException me) {
						showError(me.getMessage());
						resetForm();
						return;
					}

				}
			});
			btnValidarCorreo.setToolTipText("Validamos el correo, en caso de que el atleta esté en el sistema: añádelo");
		}
		
		return btnValidarCorreo;
	}
	
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre:");
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNombre.setDisplayedMnemonic('N');
			lblNombre.setToolTipText("Inserte su nombre (no apellidos)");
			lblNombre.setLabelFor(getTextNombre());
			lblNombre.setVisible(false);
		}
		return lblNombre;
	}
	
	private JTextField getTextNombre() {
		if (textNombre == null) {
			textNombre = new JTextField();
			textNombre.setColumns(10);
			textNombre.setVisible(false);
		}
		return textNombre;
	}
	
	private JLabel getLblFechaNacimiento() {
		if (lblFechaNacimiento == null) {
			lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
			lblFechaNacimiento.setToolTipText("Introduzca su fecha de nacimiento en el formato: DD/MM/AAAA");
			lblFechaNacimiento.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblFechaNacimiento.setDisplayedMnemonic('N');
			lblFechaNacimiento.setVisible(false);
		}
		return lblFechaNacimiento;
	}
	
	private JTextField getTextFechaNacimiento() {
		if (textFechaNacimiento == null) {
			textFechaNacimiento = new JTextField();
			textFechaNacimiento.setColumns(10);
			textFechaNacimiento.setVisible(false);
		}
		return textFechaNacimiento;
	}
	
	private JLabel getLblSexo() {
		if (lblSexo == null) {
			lblSexo = new JLabel("Sexo:");
			lblSexo.setHorizontalAlignment(SwingConstants.LEFT);
			lblSexo.setToolTipText("");
			lblSexo.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblSexo.setDisplayedMnemonic('N');
			lblSexo.setVisible(false);
		}
		return lblSexo;
	}
	
	private JComboBox<String> getComboBoxSexo() {
		if (comboBoxSexo == null) {
			comboBoxSexo = new JComboBox<>();
			comboBoxSexo.setModel(new DefaultComboBoxModel<String>(new String[] {"Hombre", "Mujer"}));
			comboBoxSexo.setVisible(false);
		}
		return comboBoxSexo;
	}
	
	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.setLayout(new GridLayout(0, 2, 0, 0));
			panelBotones.add(getBtnAñadir());
			panelBotones.add(getBtnOK());
		}
		return panelBotones;
	}
	
	private JButton getBtnAñadir() {
		if (btnAñadir == null) {
			btnAñadir = new JButton("Añadir");
			btnAñadir.setToolTipText("Añade al atleta en función de los datos aportados");
			
			btnAñadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Validamos el nombre
					String nombre = getTextNombre().getText();
					if(!nombre.trim().isEmpty())
						atleta.nombre = nombre;
					else {
						showError("Nombre no válido");
						resetForm();
						return;
					} // Show warning
					
					// Validamos la fechaDeNacimiento
					String fechaNacimiento = getTextFechaNacimiento().getText();
					atleta.fechaNacimiento = Validate.validateFecha(fechaNacimiento);
					if(atleta.fechaNacimiento == null) {
						showError("Fecha no válida");
						resetForm();
						return;
					} // Show warning
					
					// Validamos el sexo
					if (getComboBoxSexo().getSelectedItem().toString().equals("Hombre"))
						atleta.sexo = "H";
					else
						atleta.sexo = "M";
					
					// Añadimos el atleta a la tabla
					atletas.add(atleta);
					table.addAtleta(atleta);
					resetForm(); // reestablecemos el formulario para añadir más atletas
				}
			});
		}
		return btnAñadir;
	}
	
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton("OK");
			btnOK.setToolTipText("Cuando has acabado de hacer las operaciones: valídalas");
			
			btnOK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Registramos los atletas a la competición
						ModelFactory.forAtletaCrudService()
									.registerAtletasToCompetition(atletas,
											competicion);
						
						// Si todo va bien, añadimos los atletas que falten
						for (AtletaDto atleta : atletas)
							try {
								ModelFactory.forAtletaCrudService().addAtleta(atleta);
							} catch(ModelException me) { continue; }
							
						
						// Volvemos al estado principal de la aplicación
						reset();
						AtletaMain.getInstance().startPanel();
					} catch (Exception ex) {
						showError(ex.getMessage());
					}
				}
			});
		}
		return btnOK;
	}

}
