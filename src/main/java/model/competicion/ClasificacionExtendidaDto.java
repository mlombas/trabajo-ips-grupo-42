package model.competicion;

public class ClasificacionExtendidaDto extends ClasificacionDto {

	public String club;
	public int diferenciaTiempo;
	public int minsByKm;
	public int[] puntosIntermedios;

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public int getDiferenciaTiempo() {
		return diferenciaTiempo;
	}

	public void setDiferenciaTiempo(int diferenciaTiempo) {
		this.diferenciaTiempo = diferenciaTiempo;
	}

	public double getMinsByKm() {
		return minsByKm;
	}

	public void setMinsByKm(int minsByKm) {
		this.minsByKm = minsByKm;
	}

	public int[] getPuntosIntermedios() {
		return puntosIntermedios;
	}

	public void setPuntosIntermedios(int[] puntosIntermedios) {
		this.puntosIntermedios = puntosIntermedios;
	}

}
