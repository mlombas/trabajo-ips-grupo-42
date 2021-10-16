package model.competicion;

import java.sql.Date;

import giis.demo.util.Util;

public class CompeticionDto {
	public String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombreCarrera() {
		return nombreCarrera;
	}
	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = nombreCarrera;
	}
	public String getTipoCarrera() {
		return tipoCarrera;
	}
	public void setTipoCarrera(String tipoCarrera) {
		this.tipoCarrera = tipoCarrera;
	}
	public String getDistancia() {
		return distancia;
	}
	public void setDistancia(String distancia) {
		this.distancia = distancia;
	}
	public String getCuota() {
		return cuota + "";
	}
	public void setCuota(String cuota) {
		this.cuota = Double.parseDouble(cuota);
	}
	public String getFecha() {
		return Util.dateToIsoString(new java.util.Date(fecha.getTime()));
	}
	public void setFecha(String fecha) {
		this.fecha = new java.sql.Date(Util.isoStringToDate(fecha).getTime());
	}
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
	public String getPlazas() {
		return plazas + "";
	}
	public void setPlazas(String plazas) {
		this.plazas = Integer.parseInt(plazas);
	}
	public String getEstadoCarrera() {
		return estadoCarrera;
	}
	public void setEstadoCarrera(String estadoCarrera) {
		this.estadoCarrera = estadoCarrera;
	}
	public String nombreCarrera;
	public String tipoCarrera;
	public String distancia;
	public double cuota;
	public Date fecha;
	public Date fechaInicio;
	public Date fechaFin;
	public int plazas;
	public String estadoCarrera;
}
