package view.organizador.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDate;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.ModelFactory;
import model.competicion.CompeticionDto;
import net.miginfocom.swing.MigLayout;
import util.Validate;
import util.exceptions.ModelException;
import view.organizador.OrganizadorMain;
import view.organizador.dialog.CrearPlazosDialog;
import view.organizador.dialog.ConfigurarCategoriasDialog;
import view.organizador.util.AtrasOrganizadorButton;

public class CrearCompeticionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panelFormulario;
	private JButton btnValidarDatos;
	private JTextField textNombre;
	private JLabel lblNombre;
	private JLabel lblDescripcion;
	private JTextArea textAreaDescripcion;
	private JLabel lblTipo;
	private JTextField textTipo;
	private JLabel lblNumeroPlazas;
	private JSpinner spinnerNumeroPlazas;
	private JLabel lblNumeroDorsalesReservados;
	private JSpinner spinnerDorsalesReservados;
	private JLabel lblFecha;
	private JLabel lblLongitud;
	private JTextField textFecha;
	private JSpinner spinnerLongitud;
	private JPanel panelCrearAdicional;
	private JButton btnCategorias;
	private JButton btnPlazos;
	private JPanel panelButtons;
	private AtrasOrganizadorButton btnAtras;
	private JButton btnOk;

	private CompeticionDto competicion = new CompeticionDto();
	private boolean isPlazosCreated = false;
	private boolean isCategoriasCreated = false;
	private boolean isCompeticionSuccessfullyCreated = false;

	/**
	 * Create the panel.
	 */
	public CrearCompeticionPanel() {
		setLayout(new BorderLayout(0, 0));
		add(getPanelFormulario(), BorderLayout.NORTH);
		add(getPanelCrearAdicional(), BorderLayout.CENTER);
		add(getPanelButtons(), BorderLayout.SOUTH);

		// RESET EVERYTHING
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent e) {
				if (competicion.id != null && !isCompeticionSuccessfullyCreated)
					try {
						if (isPlazosCreated)
							ModelFactory.forCarreraCrudService().deletePlazosByIdCompetición(competicion.id);
						if (isCategoriasCreated)
							ModelFactory.forCarreraCrudService().deleteAllCategorias(competicion.id);
						if (ModelFactory.forCarreraCrudService().removeCarrera(competicion.id))
							JOptionPane.showMessageDialog(null, "Hemos eliminado la carrera");
					} catch (Exception me) {
						JOptionPane.showMessageDialog(null, "Lo siento, algo ha salido mal");
					}

				reset();
			}
		});
	}

	private void showError(String arg) {
		JOptionPane.showMessageDialog(this, arg, "ERROR - " + arg, JOptionPane.ERROR_MESSAGE);
	}

	public void reset() {
		competicion = new CompeticionDto();

		getTextNombre().setText("");
		getTextTipo().setText("");
		getTextAreaDescripcion().setText("");
		getSpinnerNumeroPlazas().setValue(0);
		getSpinnerDorsalesReservados().setValue(0);
		getSpinnerNumeroPlazas().setValue(0);
		getTextFecha().setText("");
		getSpinnerLongitud().setValue(0);

		getBtnCategorias().setEnabled(false);
		getBtnPlazos().setEnabled(false);
		getBtnOk().setEnabled(false);
	}

	public void setPlazosCreated(boolean isPlazosCreated) {
		this.isPlazosCreated = isPlazosCreated;
	}

	public void setCategoriasCreated(boolean isCategoriasCreated) {
		this.isCategoriasCreated = isCategoriasCreated;
	}

	private JPanel getPanelFormulario() {
		if (panelFormulario == null) {
			panelFormulario = new JPanel();
			panelFormulario.setBorder(
					new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Formulario de la Competici\u00F3n",
							TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelFormulario.setLayout(new MigLayout("", "[grow,center][grow,center]", "[][][][grow][][][][][][]"));
			panelFormulario.add(getLblNombre(), "flowx,cell 0 0 2 1,alignx left,growy");
			panelFormulario.add(getTextNombre(), "cell 0 1,grow");
			panelFormulario.add(getTextTipo(), "cell 1 1,grow");
			panelFormulario.add(getLblDescripcion(), "cell 0 2 2 1,alignx left");
			panelFormulario.add(getTextAreaDescripcion(), "cell 0 3 2 2,grow");
			panelFormulario.add(getLblNumeroDorsalesReservados(), "cell 0 5,alignx left");
			panelFormulario.add(getLblNumeroPlazas(), "cell 1 5,alignx left");
			panelFormulario.add(getSpinnerDorsalesReservados(), "cell 0 6,growx");
			panelFormulario.add(getLblTipo(), "cell 1 0,alignx left,aligny center");
			panelFormulario.add(getSpinnerNumeroPlazas(), "cell 1 6,growx");
			panelFormulario.add(getLblFecha(), "cell 0 7,growx");
			panelFormulario.add(getLblLongitud(), "cell 1 7,growx");
			panelFormulario.add(getTextFecha(), "cell 0 8,growx");
			panelFormulario.add(getSpinnerLongitud(), "cell 1 8,growx");
			panelFormulario.add(getBtnValidarDatos(), "cell 0 9 2 1,growx");
		}
		return panelFormulario;
	}

	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre:");
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNombre.setDisplayedMnemonic('N');
			lblNombre.setToolTipText("Inserte el nombre de la competición");
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

	private JLabel getLblDescripcion() {
		if (lblDescripcion == null) {
			lblDescripcion = new JLabel("Descripción:");
			lblDescripcion.setLabelFor(getTextAreaDescripcion());
			lblDescripcion.setToolTipText("Escriba un breve texto explicando en qué consiste la competición");
			lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblDescripcion.setDisplayedMnemonic('D');
		}
		return lblDescripcion;
	}

	private JTextArea getTextAreaDescripcion() {
		if (textAreaDescripcion == null) {
			textAreaDescripcion = new JTextArea();
		}
		return textAreaDescripcion;
	}

	private JLabel getLblTipo() {
		if (lblTipo == null) {
			lblTipo = new JLabel("Tipo:");
			lblTipo.setToolTipText("");
			lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblTipo.setDisplayedMnemonic('N');
		}
		return lblTipo;
	}

	private JTextField getTextTipo() {
		if (textTipo == null) {
			textTipo = new JTextField();
			textTipo.setColumns(10);
		}
		return textTipo;
	}

	private JLabel getLblNumeroPlazas() {
		if (lblNumeroPlazas == null) {
			lblNumeroPlazas = new JLabel("Número de Plazas:");
			lblNumeroPlazas.setLabelFor(getSpinnerNumeroPlazas());
			lblNumeroPlazas.setToolTipText("");
			lblNumeroPlazas.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNumeroPlazas.setDisplayedMnemonic('N');
		}
		return lblNumeroPlazas;
	}

	private JSpinner getSpinnerNumeroPlazas() {
		if (spinnerNumeroPlazas == null) {
			spinnerNumeroPlazas = new JSpinner();
			spinnerNumeroPlazas.setModel(new SpinnerNumberModel(0, 0, null, 1));
		}
		return spinnerNumeroPlazas;
	}

	private JLabel getLblNumeroDorsalesReservados() {
		if (lblNumeroDorsalesReservados == null) {
			lblNumeroDorsalesReservados = new JLabel("Dorsales Reservados:");
			lblNumeroDorsalesReservados.setLabelFor(getSpinnerDorsalesReservados());
			lblNumeroDorsalesReservados.setToolTipText("");
			lblNumeroDorsalesReservados.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNumeroDorsalesReservados.setDisplayedMnemonic('N');
		}
		return lblNumeroDorsalesReservados;
	}

	private JSpinner getSpinnerDorsalesReservados() {
		if (spinnerDorsalesReservados == null) {
			spinnerDorsalesReservados = new JSpinner();
			spinnerDorsalesReservados.setModel(new SpinnerNumberModel(0, 0, null, 1));
		}
		return spinnerDorsalesReservados;
	}

	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha:");
			lblFecha.setLabelFor(getTextFecha());
			lblFecha.setHorizontalAlignment(SwingConstants.LEFT);
			lblFecha.setToolTipText("");
			lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblFecha.setDisplayedMnemonic('N');
		}
		return lblFecha;
	}

	private JTextField getTextFecha() {
		if (textFecha == null) {
			textFecha = new JTextField();
			textFecha.setColumns(10);
		}
		return textFecha;
	}

	private JLabel getLblLongitud() {
		if (lblLongitud == null) {
			lblLongitud = new JLabel("Longitud:");
			lblLongitud.setHorizontalAlignment(SwingConstants.LEFT);
			lblLongitud.setToolTipText("Establece la longitud de la carrera en KM");
			lblLongitud.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblLongitud.setDisplayedMnemonic('L');
		}
		return lblLongitud;
	}

	private JSpinner getSpinnerLongitud() {
		if (spinnerLongitud == null) {
			spinnerLongitud = new JSpinner();
			spinnerLongitud.setModel(new SpinnerNumberModel(0, 0, null, 1));
		}
		return spinnerLongitud;
	}

	private JButton getBtnValidarDatos() {
		if (btnValidarDatos == null) {
			btnValidarDatos = new JButton("Validar mis Datos");
			btnValidarDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Establecemos el ID
					competicion.id = UUID.randomUUID().toString();

					// Validamos el nombre
					String nombre = getTextNombre().getText();
					if (!nombre.trim().isEmpty())
						competicion.nombreCarrera = nombre;
					else {
						JOptionPane.showMessageDialog(null, "Nombre no puede estar vacío...");
						return;
					} // Show warning

					// Validamos el tipo
					String tipo = getTextTipo().getText();
					if (!tipo.trim().isEmpty()) {
						if (!tipo.toLowerCase().equals("asfalto") && !tipo.toLowerCase().equals("montaña")) {
							JOptionPane.showMessageDialog(null, "Sólo aceptamos carreras de asfalto y montaña...");
							return;
						}
						competicion.tipoCarrera = tipo;
					} else {
						JOptionPane.showMessageDialog(null, "Tipo no puede estar vacío...");
						return;
					} // Show warning

					// Validamos la descripcion
					String descripcion = getTextAreaDescripcion().getText();
					if (!descripcion.trim().isEmpty())
						competicion.descripcion = descripcion;
					else {
						JOptionPane.showMessageDialog(null, "Descripción no puede estar vacía...");
						return;
					} // Show warning

					// Validamos el numero de dorsales reservados
					int dorsales = (int) getSpinnerDorsalesReservados().getValue();
					competicion.dorsalesReservados = dorsales;

					// Validamos el numero de plazas
					int plazas = (int) getSpinnerNumeroPlazas().getValue();
					if (plazas <= 0) {
						JOptionPane.showMessageDialog(null, "Las plazas tiene que ser mayor que 0...");
						return;
					} // Show warning
					competicion.plazas = plazas;

					// Validamos la fecha
					String fecha = getTextFecha().getText();
					competicion.fecha = Validate.validateFecha(fecha);
					if (competicion.fecha == null || competicion.fecha.isBefore(LocalDate.now())) {
						JOptionPane.showMessageDialog(null, "Fecha no valida...");
						return;
					} // Show warning

					// Validamos la longitud de la carrera
					int longitud = (int) getSpinnerLongitud().getValue();
					if (longitud <= 0) {
						JOptionPane.showMessageDialog(null, "La longitud tiene que ser mayor que 0...");
						return;
					} // Show warning
					competicion.distancia = longitud;

					// Establecemos el estado de la competicion
					competicion.estadoCarrera = "creándose"; // TEMP

					try {
						if (ModelFactory.forCarreraCrudService().addCompeticion(competicion)) {
							JOptionPane.showMessageDialog(null,
									"Hemos creado la carrera, ahora configura el resto de opciones");
							getBtnCategorias().setEnabled(true);
							getBtnPlazos().setEnabled(true);
						} else
							JOptionPane.showMessageDialog(null, "No hemos podido añadir la carrera");
					} catch (ModelException me) {
						showError("Lo siento, algo ha salido mal...");
						OrganizadorMain.getInstance().startPanel();
						reset();
						return;
					}
				}
			});
		}
		return btnValidarDatos;
	}

	private JPanel getPanelCrearAdicional() {
		if (panelCrearAdicional == null) {
			panelCrearAdicional = new JPanel();
			panelCrearAdicional.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2),
					"Datos adicionales de la Competici\u00F3n", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			panelCrearAdicional.setLayout(new MigLayout("", "[grow]", "[grow][fill][fill][grow]"));
			panelCrearAdicional.add(getBtnCategorias(), "cell 0 2,grow");
			panelCrearAdicional.add(getBtnPlazos(), "cell 0 1,grow");
		}
		return panelCrearAdicional;
	}

	private JButton getBtnCategorias() {
		if (btnCategorias == null) {
			btnCategorias = new JButton("Configurar Categorías");
			btnCategorias.setEnabled(false);
			btnCategorias.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showConfigurarCategorias();

					// In case PLAZOS and CATEGORIAS are created
					if (isPlazosCreated && isCategoriasCreated)
						getBtnOk().setEnabled(true);
				}
			});
		}
		return btnCategorias;
	}

	private void showCrearPlazos(CompeticionDto competicion) {
		CrearPlazosDialog plazosDialog = new CrearPlazosDialog(this, competicion);
		plazosDialog.setLocationRelativeTo(null);
		plazosDialog.setModal(true);
		plazosDialog.setVisible(true);
	}

	protected void showConfigurarCategorias() {
		ConfigurarCategoriasDialog configurarCategoriasDialog = new ConfigurarCategoriasDialog(this, competicion.id);
		configurarCategoriasDialog.setLocationRelativeTo(null);
		configurarCategoriasDialog.setModal(true);
		configurarCategoriasDialog.setVisible(true);

	}

	private JButton getBtnPlazos() {
		if (btnPlazos == null) {
			btnPlazos = new JButton("Configurar Plazos");
			btnPlazos.setEnabled(false);
			btnPlazos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showCrearPlazos(competicion);

					// In case PLAZOS and CATEGORIAS are created
					if (isPlazosCreated && isCategoriasCreated)
						getBtnOk().setEnabled(true);
				}
			});
		}
		return btnPlazos;
	}

	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.add(getBtnOk());
			panelButtons.add(getBtnAtras());
		}
		return panelButtons;
	}

	private AtrasOrganizadorButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new AtrasOrganizadorButton(OrganizadorMain.ORGANIZADOR_MENU);

			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					OrganizadorMain.getInstance().startPanel();
				}
			});
		}
		return btnAtras;
	}

	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("OK");
			btnOk.setEnabled(false);

			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					competicion.estadoCarrera = "inscripción"; // TODO hacer dinámico
					try {
						ModelFactory.forCarreraCrudService().updateCompeticion(competicion);
					} catch (ModelException e1) {
						showError("Lo siento, algo ha salido mal...");
						OrganizadorMain.getInstance().startPanel();
						reset();
						return;
					}

					isCompeticionSuccessfullyCreated = true;

					JOptionPane.showMessageDialog(null, "Hemos añadido la carrera");
					OrganizadorMain.getInstance().startPanel();
				}
			});
		}
		return btnOk;
	}
}
