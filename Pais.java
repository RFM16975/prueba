package package1;

public class Pais {
	
	private String nombrePais;
	private int vidasIniciales;
	private int vidasActuales;
    private int misilesAtaque;
    private int misilesDefensa;
    private int misilesMaxAtaque;
    private int escudo; //pasiva Italia
	
    private String[] paises = {"ALEMANIA", "AUSTRIA", "FRANCIA", "YUGOSLAVIA", "ITALIA", "HUNGRÍA", "POLONIA", "BÉLGICA", "DINAMARCA", "SUIZA"}; 
    
    //CONSTRUCTOR
    public Pais (String nombre) {
    	this.nombrePais = nombre;
    	
    }
    
    public Pais () {
    	
    }
    
    //MÉTODOS
    
    
    
    
    //GETTERS Y SETTERS
	public String getNombrePais() {
		return nombrePais;
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}
	
	public int getVidasIniciales() {
		return vidasIniciales;
	}

	public void setVidasIniciales(int vidas) {
		this.vidasIniciales = vidas;
		this.vidasActuales = vidas;
	}

	public int getVidasActuales() {
		return vidasActuales;
	}

	public void setVidasActuales(int vidas) {
		this.vidasActuales = vidas;
	}

	public int getMisilesAtaque() {
		return misilesAtaque;
	}

	public void setMisilesAtaque(int misilesAtaque) {
		this.misilesAtaque = misilesAtaque;
	}

	public int getMisilesDefensa() {
		return misilesDefensa;
	}

	public void setMisilesDefensa(int misilesDefensa) {
		this.misilesDefensa = misilesDefensa;
	}

	public int getEscudo() {
		return escudo;
	}

	public void setEscudo(int escudo) {
		this.escudo = escudo;
	}

	public String[] getPaises() {
		return paises;
	}

	public void setPaises(String[] paises) {
		this.paises = paises;
	}

	public int getMisilesMaxAtaque() {
		return misilesMaxAtaque;
	}

	public void setMisilesMaxAtaque(int misilesAux) {
		this.misilesMaxAtaque = misilesAux;
	}
    
	
}
