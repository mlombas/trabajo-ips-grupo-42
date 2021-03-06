package model.inscripcion;

import java.time.LocalDate;

public class InscripcionDto {

	public String idCompeticion;
	public String emailAtleta;
	public String nombreAtleta;
	public String dniAtleta;
	public String nombreCompeticion;
	public String categoria;
	public LocalDate fechaInscripcion;
	public EstadoInscripcion estadoInscripcion;
	public double cuotaInscripcion;
	public LocalDate fechaCambioEstado;
	public String clubAtleta;
//	public int poisicion;
//	public double tiempo;
	public Double devolver;

	public Double getDevolver() {
		return devolver;
	}

	public String getDniAtleta() {
		return dniAtleta;
	}

	public void setDniAtleta(String dniAtleta) {
		this.dniAtleta = dniAtleta;
	}

	public String getClubAtleta() {
		return clubAtleta;
	}

	public void setClubAtleta(String clubAtleta) {
		this.clubAtleta = clubAtleta;
	}

	public void setFechaInscripcion(LocalDate fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public void setEstadoInscripcion(EstadoInscripcion estadoInscripcion) {
		this.estadoInscripcion = estadoInscripcion;
	}

	public void setFechaCambioEstado(LocalDate fechaCambioEstado) {
		this.fechaCambioEstado = fechaCambioEstado;
	}

	public void setDevolver(Double devolver) {
		this.devolver = devolver;
	}

	public String getIdCompeticion() {
		return idCompeticion;
	}

	public String getEstadoInscripcion() {
		return "" + estadoInscripcion;
	}

	public String getFechaCambioEstado() {
		return fechaCambioEstado.toString();
	}

	public String getEmailAtleta() {
		return emailAtleta;
	}

	public void setEmailAtleta(String emailAtleta) {
		this.emailAtleta = emailAtleta;
	}

	public String getNombreAtleta() {
		return nombreAtleta;
	}

	public void setNombreAtleta(String nombreAtleta) {
		this.nombreAtleta = nombreAtleta;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getFechaInscripcion() {
		return fechaInscripcion.toString();
	}

	public void setFechaInscripcion(String fechaInscripcion) {
		this.fechaInscripcion = LocalDate.parse(fechaInscripcion);
	}

	public double getCuotaInscripcion() {
		return cuotaInscripcion;
	}

	public void setCuotaInscripcion(double cuotaInscripcion) {
		this.cuotaInscripcion = cuotaInscripcion;
	}

//	public int getPoisicion() {
//		return poisicion;
//	}
//
//	public void setPoisicion(int poisicion) {
//		this.poisicion = poisicion;
//	}
//
//	public double getTiempo() {
//		return tiempo;
//	}
//
//	public void setTiempo(double tiempo) {
//		this.tiempo = tiempo;
//	}

	public void setIdCompeticion(String idCompeticion) {
		this.idCompeticion = idCompeticion;
	}

	public void setEstadoInscripcion(String estadoInscripcion) {
		this.estadoInscripcion = EstadoInscripcion.valueOf(estadoInscripcion);
	}

	public void setFechaCambioEstado(String fechaCambioEstado) {
		this.fechaCambioEstado = LocalDate.parse(fechaCambioEstado);
	}

	public String getNombreCompeticion() {
		return nombreCompeticion;
	}

	public void setNombreCompeticion(String nombreCompeticion) {
		this.nombreCompeticion = nombreCompeticion;
	}

}
