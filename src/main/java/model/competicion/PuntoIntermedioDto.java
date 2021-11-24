package model.competicion;

public class PuntoIntermedioDto implements Comparable<PuntoIntermedioDto> {
	
	public String id;
	public String idCompeticion;
	public int distanciaSalida;
	public int tiempoMaximo;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIdCompeticion() {
		return idCompeticion;
	}
	
	public void setIdCompeticion(String idCompeticion) {
		this.idCompeticion = idCompeticion;
	}
	
	public int getDistanciaSalida() {
		return distanciaSalida;
	}
	
	public void setDistanciaSalida(int distanciaSalida) {
		this.distanciaSalida = distanciaSalida;
	}
	
	public int getTiempoMaximo() {
		return tiempoMaximo;
	}
	
	public void setTiempoMaximo(int tiempoMaximo) {
		this.tiempoMaximo = tiempoMaximo;
	}

	@Override
	public int compareTo(PuntoIntermedioDto o) {
		return this.distanciaSalida - o.distanciaSalida;
	}

}
