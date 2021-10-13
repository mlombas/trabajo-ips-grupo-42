<<<<<<< HEAD
package model.atleta;

import java.sql.Date;

public class AtletaDto {

	public String email;
	public String dni;
	public String nombre;
	public Date fechaNacimiento;
	public String sexo;
  
}
=======
package model.atleta;

import java.sql.Date;

import giis.demo.util.Util;

public class AtletaDto {

	public String email;
	public String dni;
	public String nombre;
	public Date fechaNacimiento;
	public String sexo;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFechaNacimiento() {
		return Util.dateToIsoString(new java.util.Date(fechaNacimiento.getTime()));
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = new java.sql.Date(Util.isoStringToDate(fechaNacimiento).getTime());
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

}
>>>>>>> main
