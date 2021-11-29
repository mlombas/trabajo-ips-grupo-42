package view.organizador.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import model.ModelFactory;
import model.competicion.CompeticionDto;
import model.competicion.PuntoIntermedioDto;
import net.miginfocom.swing.MigLayout;
import util.exceptions.ModelException;
import view.util.table.PuntoIntermedioToTable;

public class CrearPuntosIntermediosPanel extends CrearCompeticionSubPanel {

	private static final long serialVersionUID = 1L;
	
	private PuntoIntermedioToTable puntoIntermedioTable;
	private JLabel lblDistanciaPuntoIntermedio;
	private JSpinner spinnerPuntoIntermedio;

	private CrearCompeticionPanel crearCompeticionPanel;
	
	private CompeticionDto competicion;
	List<PuntoIntermedioDto> puntosIntermedios = new ArrayList<>();

	public CrearPuntosIntermediosPanel(CrearCompeticionPanel crearCompeticionPanel, 
			CompeticionDto competicion) {
		this.crearCompeticionPanel = crearCompeticionPanel;
		this.competicion = competicion;
		
		cargarDatos();
		addToFormulario();
		
		// Añadimos el event listener
		getBtnAñadir().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearCompeticionPanel.setPuntosIntermediosCreated(true);
				añadirPuntoIntermedio();
			}
		});
	}
	
	public void reset() {
		puntosIntermedios = new ArrayList<>();
		getSpinnerPuntoIntermedio().setValue(0);
	}
	
	private void showError(String arg) {
		JOptionPane.showMessageDialog(this, arg, "ERROR - " + arg, JOptionPane.ERROR_MESSAGE);
	}
	
	private void añadirPuntoIntermedio() {
		int puntoKilometrico = (int) getSpinnerPuntoIntermedio().getValue();
		
		// Comprobamos que no nos estemos pasando de largo al escribir el punto kilométrico
		if (puntoKilometrico > competicion.distancia || puntoKilometrico == 0) {
			showError("Estás introduciendo un punto no válido");
			return;
		}
		
		// Comprobamos que no haya sido introducido con anterioridad
		puntosIntermedios.stream().forEach(x -> {
			if (x.distanciaSalida == puntoKilometrico) {
				showError("Estás intentando añadir dos veces el mismo punto");
				return;
			}
		});
		
		//Creamos el DTO
		PuntoIntermedioDto puntoIntermedio = new PuntoIntermedioDto();
		puntoIntermedio.id = UUID.randomUUID().toString();
		puntoIntermedio.idCompeticion = competicion.id;
		puntoIntermedio.distanciaSalida = puntoKilometrico;
		
		// Añadimos el punto intermedio a la tabla y a la base de datos
		try {
			ModelFactory.forCarreraCrudService().addPuntoIntermedio(puntoIntermedio);
			puntosIntermedios.add(puntoIntermedio); // Añadimos el punto intermedio a la lista
			cargarDatos();
			crearCompeticionPanel.setPuntosIntermediosCreated(true);
		} catch (ModelException e) {
			showError(e.getMessage());
		}
	}
	
	private void cargarDatos() {
		Collections.sort(puntosIntermedios);
		puntoIntermedioTable = new PuntoIntermedioToTable(puntosIntermedios);
		getScrollPaneVisualizacion().setViewportView(puntoIntermedioTable);
		this.revalidate();
	}
	
	private void addToFormulario() {
		getPanelFormulario().setLayout(new MigLayout("", "[grow,fill]", "[][]"));
		getPanelFormulario().add(getLblPuntoIntermedio(), "cell 0 0,alignx left,aligny center");
		getPanelFormulario().add(getSpinnerPuntoIntermedio(), "flowx,cell 0 1");
	}

	private JLabel getLblPuntoIntermedio() {
		if (lblDistanciaPuntoIntermedio == null) {
			lblDistanciaPuntoIntermedio = new JLabel("Distancia punto intermedio: ");
			lblDistanciaPuntoIntermedio.setToolTipText("Establece la distancia a la que se medirán los tiempos con respecto a la llegada");
			lblDistanciaPuntoIntermedio.setLabelFor(getSpinnerPuntoIntermedio());
		}
		
		return lblDistanciaPuntoIntermedio;
	}
	
	private JSpinner getSpinnerPuntoIntermedio() {
		if (spinnerPuntoIntermedio == null) {
			spinnerPuntoIntermedio = new JSpinner();
			spinnerPuntoIntermedio.setModel(new SpinnerNumberModel(0, 0, null, 1));
		}
		
		return spinnerPuntoIntermedio;
	}
	
}
