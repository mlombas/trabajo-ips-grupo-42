package model.competicion;

import java.time.LocalDate;
import java.util.Objects;

public class CompeticionDto {
	
	public String id;
	public String nombreCarrera;
	public String tipoCarrera;
	public int distancia;
	public Double cuota;
	public LocalDate fecha;
	public String fechaInicio;
	public String fechaFin;
	public int plazas;
	public String estadoCarrera;
	public int dorsalesReservados;
	public String descripcion;
	
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
	
	public int getDistancia() {
		return distancia;
	}
	
	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
	
	public String getCuota() {
		return cuota + "";
	}
	
	public void setCuota(String cuota) {
		if(cuota == null)
		{
			this.cuota = 0.0;
		}else {
			this.cuota = Double.parseDouble(cuota);
		}		
	}
	
	public String getFecha() {
		return fecha.toString();
	}
	
	public void setFecha(String fecha) {
		this.fecha = LocalDate.parse(fecha);
	}
	
	public String getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public String getFechaFin() {
		return fechaFin;
	}
	
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
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

	public int getDorsalesReservados() {
		return dorsalesReservados;
	}

	public void setDorsalesReservados(int dorsalesReservados) {
		this.dorsalesReservados = dorsalesReservados;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompeticionDto other = (CompeticionDto) obj;
		return Objects.equals(id, other.id);
	}
	
}
