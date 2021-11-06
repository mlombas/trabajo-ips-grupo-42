package model.competicion;

public class ClasificacionDto {

	public int posicion;
	public int dorsal;
	public int tiempoSalida;
	public int tiempoLlegada;
	public String sexo;
	public String nombre;
	public String categoria;
	public boolean presentado = true;

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public int getDorsal() {
		return dorsal;
	}

	public void setDorsal(int dorsal) {
		this.dorsal = dorsal;
	}

	public int getTiempoSalida() {
		return tiempoSalida;
	}

	public void setTiempoSalida(int tiempoSalida) {
		this.tiempoSalida = tiempoSalida;
	}

	public int getTiempoLlegada() {
		return tiempoLlegada;
	}

	public void setTiempoLlegada(int tiempoLllegada) {
		this.tiempoLlegada = tiempoLllegada;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombreAtleta) {
		this.nombre = nombreAtleta;
	}

	public String formatTime(int tiempo) {
		String aux = "";
		int horas = tiempo / 60;
		aux += horas + ":";
		int minutos = tiempo % 60;
		if (minutos < 10) {
			aux += "0" + minutos;
		} else {
			aux += minutos;
		}
		return aux;
	}

}
