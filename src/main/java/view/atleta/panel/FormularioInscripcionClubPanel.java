package view.atleta.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
	private JLabel lblDni;
	private JTextField textDni;
	private JLabel lblEmail;
	private JTextField textEmail;
	private JLabel lblFechaNacimiento;
	private JTextField textFechaNacimiento;
	private JLabel lblSexo;
	private ButtonGroup sexo = new ButtonGroup();
	private JRadioButton rdbtnHombre;
	private JRadioButton rdbtnMujer;
	private JPanel panelBotones;
	private JButton btnAñadir;
	private JButton btnOK;
	
	AtletasToTable table;
	
	private List<AtletaDto> atletas;
	private CompeticionDto competicion;
	
	private String nombreClub;

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
		getTextNombre().setText("");
		getTextDni().setText("");
		getTextEmail().setText("");
		getTextFechaNacimiento().setText("");
		sexo.clearSelection();
	}
	
	private JScrollPane getScrollPaneAtletas() {
		if (scrollPaneAtletas == null) {
			scrollPaneAtletas = new JScrollPane();
			atletas = new ArrayList<>();
			table = new AtletasToTable(atletas);
			scrollPaneAtletas.setViewportView(table);
		}
		
		return scrollPaneAtletas;
	}
	
	private JPanel getPanelFormularioAtleta() {
		if (panelFormularioAtleta == null) {
			panelFormularioAtleta = new JPanel();
			panelFormularioAtleta.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), 
					"Formulario de Inscripción a la Carrera", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			panelFormularioAtleta.setLayout(new MigLayout("", "[grow,center][grow,center]", "[grow][][][][][][][][][][][grow]"));
			panelFormularioAtleta.add(getLblNombre(), "cell 0 1 2 1,alignx left,growy");
			panelFormularioAtleta.add(getTextNombre(), "cell 0 2 2 1,grow");
			panelFormularioAtleta.add(getLblDni(), "cell 0 3 2 1,alignx left");
			panelFormularioAtleta.add(getTextDni(), "cell 0 4 2 1,growx");
			panelFormularioAtleta.add(getLblEmail(), "cell 0 5 2 1,alignx left");
			panelFormularioAtleta.add(getTextEmail(), "cell 0 6 2 1,growx");
			panelFormularioAtleta.add(getLblFechaNacimiento(), "cell 0 7,alignx left,aligny center");
			panelFormularioAtleta.add(getTextFechaNacimiento(), "cell 0 8 2 1,grow");
			panelFormularioAtleta.add(getLblSexo(), "cell 0 9 2 1,alignx center,aligny top");
			panelFormularioAtleta.add(getRdbtnMujer(), "flowx,cell 0 10 2 1");
			panelFormularioAtleta.add(getRdbtnHombre(), "cell 0 10 2 1,alignx right,aligny bottom");
		}
		return panelFormularioAtleta;
	}
	
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre:");
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNombre.setDisplayedMnemonic('N');
			lblNombre.setToolTipText("Inserte su nombre (no apellidos)");
			lblNombre.setLabelFor(getTextNombre());
		}
		return lblNombre;
	}
	
	private JTextField getTextNombre() {
		if (textNombre == null) {
			textNombre = new JTextField();
			textNombre.setColumns(10);
		}
		return textNombre;
	}
	
	private JLabel getLblDni() {
		if (lblDni == null) {
			lblDni = new JLabel("DNI:");
			lblDni.setToolTipText("Inserte su DNI (99999999X)");
			lblDni.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblDni.setDisplayedMnemonic('N');
		}
		return lblDni;
	}
	private JTextField getTextDni() {
		if (textDni == null) {
			textDni = new JTextField();
			textDni.setColumns(10);
		}
		return textDni;
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
	
	private JLabel getLblFechaNacimiento() {
		if (lblFechaNacimiento == null) {
			lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
			lblFechaNacimiento.setToolTipText("Introduzca su fecha de nacimiento en el formato: DD/MM/AAAA");
			lblFechaNacimiento.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblFechaNacimiento.setDisplayedMnemonic('N');
		}
		return lblFechaNacimiento;
	}
	
	private JTextField getTextFechaNacimiento() {
		if (textFechaNacimiento == null) {
			textFechaNacimiento = new JTextField();
			textFechaNacimiento.setColumns(10);
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
		}
		return lblSexo;
	}
	
	private JRadioButton getRdbtnHombre() {
		if (rdbtnHombre == null) {
			rdbtnHombre = new JRadioButton("Hombre");
			rdbtnHombre.setToolTipText("H");
			sexo.add(rdbtnHombre);
		}
		return rdbtnHombre;
	}
	
	private JRadioButton getRdbtnMujer() {
		if (rdbtnMujer == null) {
			rdbtnMujer = new JRadioButton("Mujer");
			rdbtnMujer.setToolTipText("M");
			sexo.add(rdbtnMujer);
		}
		return rdbtnMujer;
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
					AtletaDto atleta = new AtletaDto();
					
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
					if(Validate.validateEmail(email))
						atleta.email = email;
					else {
						showError("Email no válido");
						reset();
						return;
					} // Show warning
					
					// Validamos el dni
					String dni = getTextDni().getText();
					if(!dni.trim().isEmpty())
						atleta.dni = dni;
					else {
						showError("DNI no válido");
						return;
					} // Show warning
					
					// Validamos la fechaDeNacimiento
					String fechaNacimiento = getTextFechaNacimiento().getText();
					atleta.fechaNacimiento = Validate.validateFecha(fechaNacimiento);
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
					
					// Establecemos el club
					atleta.club = nombreClub;
					
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
						try {
							for (AtletaDto atleta : atletas)
								ModelFactory.forAtletaCrudService().addAtleta(atleta);
						} catch (ModelException me) { } // no hagas nada, sencillamente el atleta ya había sido añadido
						
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
