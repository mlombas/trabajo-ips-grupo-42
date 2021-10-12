package model.inscripcion;

public enum EstadoInscripcion {

	PRE_INSCRITO("PRE-INSCRITO");
	
	private String estado = "";
	
	EstadoInscripcion(String estado) {
		this.estado = estado;
	}
	
	public String toString() {
		return this.estado;
	}
	
}
