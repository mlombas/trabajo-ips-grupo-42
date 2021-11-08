package view.organizador.dialog;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;

import model.ModelFactory;
import model.competicion.CategoriaDto;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import net.miginfocom.swing.MigLayout;
import util.exceptions.ModelException;

import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class ConfigurarCategoriasDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTextField txtNombre;
	private JButton btnValidar;
	private JPanel panelValidar;
	private JLabel lblConfCategorias;
	private JPanel pnAtrasConfirmar;
	private JButton btnConfirmar;
	private JButton btnAtras;
	private JPanel pnCentro;
	private JPanel pnConf;
	private JLabel lblNombreCat;
	private JLabel lblEdadMin;
	private JSpinner spinnerEdadMin;
	private JLabel lblTipo;
	private JLabel lblEdadMax;
	private JPanel pnBotonesTipo;
	private JRadioButton rdbtnHombre;
	private JRadioButton rdbtnMujer;
	private JRadioButton rdbtnDiscapacitados;
	private JSpinner spinnerEdadMax;

	private String idCompeticion;

	public ConfigurarCategoriasDialog(String idCompeticion) {
		this.idCompeticion = idCompeticion;

		getContentPane().setLayout(new BorderLayout(0, 0));
		setSize(new Dimension(465, 285));
		setLocationRelativeTo(null);
		setResizable(true);
		getContentPane().add(getLblConfCategorias(), BorderLayout.NORTH);
		getContentPane().add(getPnAtrasConfirmar(), BorderLayout.SOUTH);
		getContentPane().add(getPnCentro(), BorderLayout.CENTER);

		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					ModelFactory.forCarreraCrudService().deleteAllCategorias(idCompeticion);
				} catch (ModelException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Todas las categorías han sido eliminadas con éxito");
				backToCrearCompeticion();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	private JSpinner getSpinnerEdadMax() {
		if (spinnerEdadMax == null) {
			spinnerEdadMax = new JSpinner();
			spinnerEdadMax.setPreferredSize(new Dimension(60, 20));
			spinnerEdadMax.setModel(new SpinnerNumberModel(18, 18, null, 1));
		}
		return spinnerEdadMax;
	}

	private JRadioButton getRdbtnDiscapacitados() {
		if (rdbtnDiscapacitados == null)
			rdbtnDiscapacitados = new JRadioButton("Discapacitados");
		return rdbtnDiscapacitados;
	}

	private JRadioButton getRdbtnMujer() {
		if (rdbtnMujer == null)
			rdbtnMujer = new JRadioButton("Mujer");
		return rdbtnMujer;
	}

	private JRadioButton getRdbtnHombre() {
		if (rdbtnHombre == null)
			rdbtnHombre = new JRadioButton("Hombre");
		rdbtnHombre.setSelected(true);
		return rdbtnHombre;
	}

	private JPanel getPnBotonesTipo() {
		if (pnBotonesTipo == null) {
			pnBotonesTipo = new JPanel();
			pnBotonesTipo.setLayout(new BoxLayout(pnBotonesTipo, BoxLayout.Y_AXIS));
			pnBotonesTipo.add(getRdbtnHombre());
			pnBotonesTipo.add(getRdbtnMujer());
			pnBotonesTipo.add(getRdbtnDiscapacitados());
			ButtonGroup group = new ButtonGroup();
			group.add(getRdbtnHombre());
			group.add(getRdbtnMujer());
			group.add(getRdbtnDiscapacitados());
		}
		return pnBotonesTipo;
	}

	private JLabel getLblEdadMax() {
		if (lblEdadMax == null) {
			lblEdadMax = new JLabel("EdadMáxima");
		}
		return lblEdadMax;
	}

	private JLabel getlblTipo() {
		if (lblTipo == null) {
			lblTipo = new JLabel("Tipo de categoría");
		}
		return lblTipo;
	}

	private JSpinner getSpinnerEdadMin() {
		if (spinnerEdadMin == null) {
			spinnerEdadMin = new JSpinner();
			spinnerEdadMin.setPreferredSize(new Dimension(60, 20));
			spinnerEdadMin.setModel(new SpinnerNumberModel(18, 18, null, 1));
		}
		return spinnerEdadMin;
	}

	private JLabel getlblEdadMin() {
		if (lblEdadMin == null) {
			lblEdadMin = new JLabel("Edad mínima");
		}
		return lblEdadMin;
	}

	private JLabel getLblNombreCat() {
		if (lblNombreCat == null) {
			lblNombreCat = new JLabel("Nombre");
			lblNombreCat.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNombreCat;
	}

	private JPanel getPnConf() {
		if (pnConf == null) {
			pnConf = new JPanel();
			pnConf.setLayout(new MigLayout("", "[225px,grow][grow]", "[bottom][grow][][grow]"));
			pnConf.add(getLblNombreCat(), "cell 0 0");
			pnConf.add(getlblEdadMin(), "cell 1 0");
			pnConf.add(getTxtNombre(), "cell 0 1,growx");
			pnConf.add(getSpinnerEdadMin(), "cell 1 1");
			pnConf.add(getlblTipo(), "cell 0 2");
			pnConf.add(getLblEdadMax(), "cell 1 2");
			pnConf.add(getPnBotonesTipo(), "cell 0 3,grow");
			pnConf.add(getSpinnerEdadMax(), "flowx,cell 1 3");
		}
		return pnConf;
	}

	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setLayout(new BorderLayout(0, 0));
			pnCentro.add(getPnConf(), BorderLayout.CENTER);
			pnCentro.add(getPanelValidar(), BorderLayout.SOUTH);
		}
		return pnCentro;
	}

	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atrás");
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						ModelFactory.forCarreraCrudService().deleteAllCategorias(idCompeticion);
					} catch (ModelException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Todas las categorías han sido eliminadas con éxito");
					backToCrearCompeticion();
				}
			});
		}
		return btnAtras;
	}

	private JButton getBtnConfirmar() {
		if (btnConfirmar == null) {
			btnConfirmar = new JButton("Confirmar");
			btnConfirmar.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					backToCrearCompeticion();
				}
			});
		}
		return btnConfirmar;
	}

	protected void backToCrearCompeticion() {
		dispose();
	}

	private JPanel getPnAtrasConfirmar() {
		if (pnAtrasConfirmar == null) {
			pnAtrasConfirmar = new JPanel();
			pnAtrasConfirmar.add(getBtnConfirmar());
			pnAtrasConfirmar.add(getBtnAtras());
		}
		return pnAtrasConfirmar;
	}

	private JLabel getLblConfCategorias() {
		if (lblConfCategorias == null) {
			lblConfCategorias = new JLabel("Configuración de Categorías para Carrera ");
			lblConfCategorias.setHorizontalAlignment(SwingConstants.CENTER);
			lblConfCategorias.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lblConfCategorias;
	}

	private JPanel getPanelValidar() {
		if (panelValidar == null) {
			panelValidar = new JPanel();
			panelValidar.add(getBtnValidar());
		}
		return panelValidar;
	}

	private JButton getBtnValidar() {
		if (btnValidar == null) {
			btnValidar = new JButton("Validar");
			btnValidar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					CategoriaDto cat = new CategoriaDto();
					cat.idCompeticion = idCompeticion;
					cat.nombreCategoria = txtNombre.getText();
					cat.edadMinima = (int) spinnerEdadMin.getValue();
					cat.edadMaxima = (int) spinnerEdadMax.getValue();
					if (rdbtnHombre.isSelected()) {
						cat.sexo = "H";
					}
					if (rdbtnMujer.isSelected()) {
						cat.sexo = "M";
					}
					if (rdbtnDiscapacitados.isSelected()) {
						cat.sexo = "D";
					}
					try {
						ModelFactory.forCarreraCrudService().addCategoria(cat);
						JOptionPane.showMessageDialog(null, "Categoría creada con éxito");
					} catch (ModelException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
			});
		}
		return btnValidar;
	}

	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setColumns(10);
		}
		return txtNombre;
	}

}
