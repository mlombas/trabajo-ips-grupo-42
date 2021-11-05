package model.competicion;


public class ClasificacionDto {

	public int posicion;
	public String sexo;
	public String nombreAtleta;
	public double tiempo;
	public String estadoInscripcion; //TODO Nico cambiar esto por el enum
	
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getNombreAtleta() {
		return nombreAtleta;
	}
	public void setNombreAtleta(String nombreAtleta) {
		this.nombreAtleta = nombreAtleta;
	}
	public double getTiempo() {
		return tiempo;
	}
	public void setTiempo(double tiempo) {
		this.tiempo = tiempo;
	}
	public String getEstadoInscripcion() {
		return estadoInscripcion;
	}
	public void setEstadoInscripcion(String estadoInscripcion) {
		this.estadoInscripcion = estadoInscripcion;
	}
	
	
}
