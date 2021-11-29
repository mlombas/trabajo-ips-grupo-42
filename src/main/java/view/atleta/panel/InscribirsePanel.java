package view.atleta.panel;

import javax.swing.JPanel;

import model.ModelFactory;
import model.atleta.AtletaDto;
import model.competicion.CompeticionDto;
import util.Validate;
import util.csv.CSVCreator;
import util.csv.CSVTable;
import util.exceptions.AtletaNoValidoException;
import util.exceptions.ModelException;
import view.atleta.AtletaMain;
import view.atleta.util.AtrasAtletaButton;
import view.util.panel.VerCompeticionesPanel;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.awt.GridLayout;

public class InscribirsePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private VerCompeticionesPanel competicionesPane;
	private JButton btnInscribirseAtleta;
	private AtrasAtletaButton btnAtras;

	private JPanel panelButtons;
	private JPanel panelInscripcion;
	private JButton btnInscribirseClubManual;
	private JButton btnInscribirseClubCSV;

	/**
	 * Create the panel.
	 * @throws ModelException 
	 */
	public InscribirsePanel() {
		setLayout(new BorderLayout(0, 0));
		add(getCompeticionesPane(), BorderLayout.CENTER);
		add(getPanelButtons(), BorderLayout.SOUTH);

	}
	
	public void refreshCompetitions() {
		try {
			competicionesPane.updateCompeticiones( ModelFactory
									.forCarreraCrudService()
									.GetAllCompeticiones()
									.stream()
									.filter(competicion -> LocalDate.parse(competicion.getFecha())
															.compareTo(LocalDate.now()) > 0)
									.collect(Collectors.toList()));
		} catch (ModelException e) {
			competicionesPane.updateCompeticiones(new ArrayList<CompeticionDto>());
		}
	}

	VerCompeticionesPanel getCompeticionesPane() {
		if (competicionesPane == null) {
			competicionesPane = new VerCompeticionesPanel();
			refreshCompetitions();
		}
		return competicionesPane;
	}
	
	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new GridLayout(1, 0, 0, 0));
			panelButtons.add(getPanelInscripcion());
			panelButtons.add(getBtnAtras());
		}
		return panelButtons;
	}
	
	private JPanel getPanelInscripcion() {
		if (panelInscripcion == null) {
			panelInscripcion = new JPanel();
			panelInscripcion.setLayout(new GridLayout(3, 1, 0, 0));
			panelInscripcion.add(getBtnInscribirseAtleta());
			panelInscripcion.add(getBtnInscribirseClubCSV());
			panelInscripcion.add(getBtnInscribirseClubManual());
		}
		return panelInscripcion;
	}
	
	private JButton getBtnInscribirseAtleta() {
		if (btnInscribirseAtleta == null) {
			btnInscribirseAtleta = new JButton("Inscribirse como atleta");
			
			btnInscribirseAtleta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CompeticionDto competicion = getCompeticion();
					
					if (competicion != null) {
						// Move to the other card
						AtletaMain.getInstance().getFormularioInscripcion().setCompeticionDto(competicion);
						AtletaMain.getInstance().flipCard(AtletaMain.FORMULARIO_INSCRIPCION_ATLETA);
					}
				}
			});
		}
		return btnInscribirseAtleta;
	}
	
	private JButton getBtnInscribirseClubCSV() {
		if (btnInscribirseClubCSV == null) {
			btnInscribirseClubCSV = new JButton("Inscribirse como club (csv)");
			
			btnInscribirseClubCSV.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CompeticionDto competicion = getCompeticion();
					
					if (competicion != null) {
						// Move to the other card
						String nombreClub = JOptionPane.showInputDialog(
								   null,
								   "Bienvenido! ¿Cuál es el nombre de su club?");
						
						if (nombreClub != null) {
							try {
								readAndProcessCSV(nombreClub);
							} catch (FileNotFoundException e1) {
								JOptionPane.showMessageDialog(
										InscribirsePanel.this, 
										"El archivo club.csv no esta presente",
										"CarrerasPopulares APP - Error",
										JOptionPane.ERROR_MESSAGE);
							} catch (ModelException e1) {
								JOptionPane.showMessageDialog(
										InscribirsePanel.this, 
										e1.getMessage(),
										"CarrerasPopulares APP - Error",
										JOptionPane.ERROR_MESSAGE);
							} catch (AtletaNoValidoException e1) {
								JOptionPane.showMessageDialog(
										InscribirsePanel.this, 
										"Un atleta no era válido: " + e1.getMessage(),
										"CarrerasPopulares APP - Error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}

			});
		}
		return btnInscribirseClubCSV;
	}
	
	private void readAndProcessCSV(String nombreClub) throws FileNotFoundException, ModelException, AtletaNoValidoException {
		CSVTable<String, String> table = CSVCreator.read("club.csv", ";");
		
		if(ModelFactory.forCarreraCrudService().getPlazasLibres(getCompeticion()) < table.nRows())
			throw new ModelException("No hay suficientes plazas");
			
		List<AtletaDto> atletas = new LinkedList<>();
		for(List<String> line : table) {
			AtletaDto atleta = new AtletaDto();
			atleta.club = nombreClub;
			
			atleta.nombre = line.get(table.getColumnIndex("nombre"));
			
			String email = line.get(table.getColumnIndex("email"));
			if(!Validate.validateEmail(email))
				throw new ModelException("Email no válido: " + email);
			atleta.email = email;
			
			atleta.fechaNacimiento = Validate.validateFecha(
					line.get(table.getColumnIndex("fecha_nacimiento"))
					);
			
			atleta.sexo = line.get(table.getColumnIndex("sexo"));
			
			//If not exists, exist
			if(ModelFactory.forAtletaCrudService().findByAtletaEmail(email).isEmpty())
				ModelFactory.forAtletaCrudService().addAtleta(atleta);
			
			//If not inscribed, inscribe
			if(
				ModelFactory.forAtletaCrudService().getCompetionesInscritas(atleta).stream()
				.filter(i -> i.idCompeticion.equals(getCompeticion().id))
				.findAny()
				.isEmpty()
			) atletas.add(atleta);
		}
		
		ModelFactory.forAtletaCrudService().registerAtletasToCompetition(atletas, getCompeticion());
	}
	
	private JButton getBtnInscribirseClubManual() {
		if (btnInscribirseClubManual == null) {
			btnInscribirseClubManual = new JButton("Inscribirse como club (manual)");
			
			btnInscribirseClubManual.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CompeticionDto competicion = getCompeticion();
					
					if (competicion != null) {
						// Move to the other card
						String nombreClub = JOptionPane.showInputDialog(
								   null,
								   "Bienvenido! ¿Cuál es el nombre de su club?");
						
						if (nombreClub != null) {
							AtletaMain.getInstance().getFormularioInscripcionClub().setCompeticionDto(competicion);
							AtletaMain.getInstance().getFormularioInscripcionClub().setNombreClub(nombreClub);
							AtletaMain.getInstance().getFormularioInscripcionClub().reset();
							AtletaMain.getInstance().flipCard(AtletaMain.FORMULARIO_INSCRIPCION_CLUB);
						}
					}
				}
			});
		}
		return btnInscribirseClubManual;
	}
	
	private CompeticionDto getCompeticion() { 
		CompeticionDto competicion = new CompeticionDto();
		
		try {
			competicion.id = competicionesPane.getCompeticionId();
			competicion.cuota = competicionesPane.getCuota();
			competicion.nombreCarrera = competicionesPane.getNombreCompeticion();
			
			if (!competicionesPane.getEstadoCompeticion().equals("inscripción")) {
				JOptionPane.showMessageDialog(null, "Seleccione una carrera cuyo estado sea INSCRIPCIÓN...");
				return null;
			}
		} catch (ArrayIndexOutOfBoundsException aiobe) {
			JOptionPane.showMessageDialog(null, "Seleccione una carrera...");
			return null;
		}
		
		return competicion;
	}
	
	private AtrasAtletaButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new AtrasAtletaButton(AtletaMain.ATLETAS_MENU);
		}
		return btnAtras;
	}
	
}
