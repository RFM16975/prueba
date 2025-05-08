package package1;

public class Equipo {
	
	private String nombre;
	private boolean muerte;
	private Pais pais;
	private boolean atacadoEnRonda;
	private int vidasRonda;
	
	
	public Equipo(String nombre) {
		this.nombre=nombre;
	}
	
	//METÓDOS	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean isMuerte() {
		return muerte;
	}
	public void setMuerte(boolean muerte) {
		this.muerte = muerte;
	}
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public boolean isAtacadoEnRonda() {
		return atacadoEnRonda;
	}

	public void setAtacadoEnRonda(boolean atacadoEnRonda) {
		this.atacadoEnRonda = atacadoEnRonda;
	}

	public int getVidasInicioRonda() {
		return vidasRonda;
	}

	public void setVidasInicioRonda(int vidasRonda) {
		this.vidasRonda = vidasRonda;
	}
	
	
	
}


