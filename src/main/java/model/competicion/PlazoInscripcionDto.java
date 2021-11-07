package model.competicion;

import java.util.Date;

import util.Util;

public class PlazoInscripcionDto {

	public String id;
	public double cuota;
	public Date fechaInicio;
	public Date fechaFin;
	public String idCompeticion;

	public String getFechaInicio() {
		return Util.dateToIsoString(new java.util.Date(fechaInicio.getTime()));
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = new java.sql.Date(Util.isoStringToDate(fechaInicio).getTime());
	}

	public String getFechaFin() {
		return Util.dateToIsoString(new java.util.Date(fechaFin.getTime()));
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = new java.sql.Date(Util.isoStringToDate(fechaFin).getTime());
	}

	public String getCuota() {
		return cuota + "";
	}

	public void setCuota(String cuota) {
		this.cuota = Double.parseDouble(cuota);
	}

	public String getIdCompeticion() {
		return idCompeticion;
	}

	public void setIdCompeticion(String idCompeticion) {
		this.idCompeticion = idCompeticion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
	

}
