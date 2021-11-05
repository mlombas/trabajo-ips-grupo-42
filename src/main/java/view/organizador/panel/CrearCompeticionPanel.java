package view.organizador.panel;

import javax.swing.ButtonGroup;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import view.organizador.OrganizadorMain;
import view.organizador.util.AtrasOrganizadorButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

public class CrearCompeticionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panelFormulario;
	private JButton btnValidarDatos;
	private JTextField textNombre;
	private JLabel lblNombre;
	private JLabel lblDni;
	private JTextField textDni;
	private JLabel lblFechaNacimiento;
	private JTextField textFechaNacimiento;
	private JLabel lblSexo;
	private ButtonGroup sexo = new ButtonGroup();
	private JRadioButton rdbtnHombre;
	private JRadioButton rdbtnMujer;
	private JPanel panelValidarBtn;
	private AtrasOrganizadorButton btnAtras;

	
	/**
	 * Create the panel.
	 */
	public CrearCompeticionPanel() {
		setLayout(new BorderLayout(0, 0));
		add(getPanelFormulario(), BorderLayout.CENTER);
		add(getPanelValidarBtn(), BorderLayout.SOUTH);
	}
	
	private JPanel getPanelFormulario() {
		if (panelFormulario == null) {
			panelFormulario = new JPanel();
			panelFormulario.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Formulario de Inscripción a la Carrera", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			panelFormulario.setLayout(new MigLayout("", "[grow,center][grow,center]", "[grow][][][][][][][][][grow]"));
			panelFormulario.add(getLblNombre(), "cell 0 1 2 1,alignx left,growy");
			panelFormulario.add(getTextNombre(), "cell 0 2 2 1,grow");
			panelFormulario.add(getLblDni(), "cell 0 3 2 1,alignx left");
			panelFormulario.add(getTextDni(), "cell 0 4 2 1,growx");
			panelFormulario.add(getLblFechaNacimiento(), "cell 0 5,alignx left,aligny center");
			panelFormulario.add(getTextFechaNacimiento(), "cell 0 6 2 1,grow");
			panelFormulario.add(getLblSexo(), "cell 0 7 2 1,alignx center,aligny top");
			panelFormulario.add(getRdbtnMujer(), "flowx,cell 0 8 2 1");
			panelFormulario.add(getRdbtnHombre(), "cell 0 8 2 1,alignx right,aligny bottom");
		}
		return panelFormulario;
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
			lblDni.setLabelFor(getTextDni());
			lblDni.setToolTipText("Inserte su dni (99999999X)");
			lblDni.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblDni.setDisplayedMnemonic('D');
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
	
	private JPanel getPanelValidarBtn() {
		if (panelValidarBtn == null) {
			panelValidarBtn = new JPanel();
			panelValidarBtn.add(getBtnValidarDatos());
			panelValidarBtn.add(getBtnAtras());
		}
		return panelValidarBtn;
	}
	
	private AtrasOrganizadorButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new AtrasOrganizadorButton(OrganizadorMain.ORGANIZADOR_MENU);
		}
		return btnAtras;
	}
	
	private JButton getBtnValidarDatos() {
		if (btnValidarDatos == null) {
			btnValidarDatos = new JButton("Validar mis Datos");
		}
		return btnValidarDatos;
	}
	
}
