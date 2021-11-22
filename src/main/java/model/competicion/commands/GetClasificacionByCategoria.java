package model.competicion.commands;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.competicion.CompeticionDto;
import model.competicion.ClasificacionDto;
import util.database.Database;


public class GetClasificacionByCategoria{

	private static final String OBTENER_RESULTADOS = "select c.dorsal, c.tiempoSalida, c.tiempoLlegada, a.sexo, a.nombre, i.categoria from Clasificacion c, Atleta a, Inscripcion i where c.idCompeticion = ? and a.email=c.emailAtleta and i.idCompeticion = c.idCompeticion and i.emailAtleta = c.emailAtleta and c.tiempoLlegada is not null and i.categoria=? order by c.tiempoLlegada-c.tiempoSalida"; 
	private static final String OBTENER_RESULTADOS_DNS = "select a.sexo, a.nombre, i.categoria from Clasificacion c, Atleta a, Inscripcion i where c.idCompeticion = ? and a.email=c.emailAtleta and i.idCompeticion = c.idCompeticion and i.emailAtleta = c.emailAtleta and c.dorsal is null and i.categoria=?";
	private static final String OBTENER_RESULTADOS_DNF = "select c.dorsal, c.tiempoSalida, a.sexo, a.nombre, i.categoria from Clasificacion c, Atleta a, Inscripcion i where c.idCompeticion = ? and a.email=c.emailAtleta and i.idCompeticion = c.idCompeticion and i.emailAtleta = c.emailAtleta and c.dorsal is not null and c.tiempoLlegada is null and i.categoria=? order by c.dorsal";
	private static final String OBTENER_NO_PRESENTADOS = "select a.sexo, a.nombre, i.categoria from Clasificacion c, Atleta a, Inscripcion i where i.idCompeticion = ? and a.email=i.emailAtleta and i.idCompeticion = c.idCompeticion and i.categoria=? and i.emailAtleta not in (select cl.emailAtleta from Clasificacion cl, Inscripcion inc where cl.idCompeticion = ? and inc.idCompeticion = cl.idCompeticion and inc.categoria=?) group by i.emailAtleta";
	
	
	private Database db = Database.getInstance();
	private String id_competicion;
	private String categoria;
		
	public GetClasificacionByCategoria(CompeticionDto comp, String categoria) {
		this.id_competicion = comp.id;
		this.categoria = categoria;
	}
	
	
	public List<ClasificacionDto> execute() {
		List<ClasificacionDto> noPresentados = db.executeQueryPojo(ClasificacionDto.class, OBTENER_NO_PRESENTADOS,
				id_competicion, categoria, id_competicion, categoria);
		for(ClasificacionDto c : noPresentados) {
			c.presentado = false;
		}
		List<ClasificacionDto> clasificados = db.executeQueryPojo(ClasificacionDto.class, OBTENER_RESULTADOS, 
				id_competicion, categoria); 
		int posicion = 0; 
		for (ClasificacionDto c : clasificados) { 
			c.posicion = ++posicion; 
		} 
		return Stream 
				.concat(Stream.concat( 
						Stream.concat(clasificados.stream(), 
								db.executeQueryPojo(ClasificacionDto.class, OBTENER_RESULTADOS_DNF, id_competicion, 
										categoria).stream()), 
						db.executeQueryPojo(ClasificacionDto.class, OBTENER_RESULTADOS_DNS, id_competicion, categoria) 
								.stream()), 
						noPresentados.stream()) 
				.collect(Collectors.toList()); 
	}
	
}
