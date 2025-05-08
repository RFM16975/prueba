package package1;
import java.util.Scanner;
import java.util.ArrayList;

public class Menu {
	
	Scanner input = new Scanner(System.in);
	Pais pais = new Pais();
	
	int opcion;
	private ArrayList<String> paisesEscogidos = new ArrayList<>();
	
	//MÉTODOS
	public int menuInicial() {
		System.out.println("------ COLD WAR ------");
		System.out.println("(1) Jugar\n(2) Reglas\n(3) Información\n(4) Cargar partida\n(0) Salir");
		opcion=input.nextInt();
		while (opcion<0||opcion>4) {
			System.out.print("Opción incorrecta. Inténtalo de nuevo: ");
			opcion=input.nextInt();
		}
		if (opcion==2||opcion==3 || opcion==4) {
			Utilities.mostrarReglasInformacion(opcion);
		}
		return opcion;
	}
	
	public int menuNumJugadores() {
		System.out.print("\nIntroduzca el número de jugadores entre 3 y 10: ");
		opcion = input.nextInt();
		while (opcion<3||opcion>10) {
			System.out.println("Número de jugadores incorrecto.\nIntroduzca un número entre 3 y 10.");
			System.out.print("> ");
			opcion = input.nextInt();
		}
		return opcion;
	}
	
	public String escogerNombreEquipo(int i) {
		System.out.println("Jugador "+(i+1)+", escoge un nombre: ");
		String nombre = input.next();
		return nombre;
	}
	
	
	public String escogerPais(Equipo equipo) {
		String paisEscogido;
		imprimirPaisesEscoger();
		System.out.print("\n"+equipo.getNombre()+", introduzca el nombre del pais: ");
		paisEscogido = input.next().toUpperCase();
		System.out.println();
		while (!Utilities.comprobarPaisesRegex(paisEscogido) || Utilities.contains(paisesEscogidos, paisEscogido)) {
			System.out.println("ERROR: Pais introducido incorrecto.\nInténtalo de nuevo\n");
			paisEscogido = input.next().toUpperCase();
		}
		paisesEscogidos.add(paisEscogido);
		return paisEscogido;
	}
	private void imprimirPaisesEscoger() {
		for (int i = 0 ; i < 10; i++) {
			if(!paisesEscogidos.contains(pais.getPaises()[i])) {
				System.out.println(pais.getPaises()[i]);
			}
		}  
	}
	
	
	
	public int menuAtacarDefender(Equipo equipo, int numRonda, String climaRonda, boolean seleccionadaAyuda) {
		if (numRonda%3!=0 || seleccionadaAyuda==true) {
			System.out.println("(1) Atacar\n(2) Defender");
			System.out.print(equipo.getNombre()+", escoge una opción: ");
			opcion=input.nextInt();
			while(opcion<1||opcion>2) {
				System.out.print("Error: Inténtalo de nuevo: ");
				opcion=input.nextInt();
			}
			return opcion;
		}
		else {
			if (!climaRonda.equals("NIEVE")) {
				System.out.println("\n(1) Atacar\n(2) Defender\n(3) Ayuda aliada");
				System.out.print(equipo.getNombre()+", escoge una opción: ");
				opcion=input.nextInt();
				while(opcion<1||opcion>3) {
					System.out.print("Error: Inténtalo de nuevo: ");
					opcion=input.nextInt();
				}
				return opcion;
			}
			else {
				System.out.println("AYUDA ALIADA no disponible en NIEVE");
				climaRonda = climaRonda + " ";
				return menuAtacarDefender(equipo,numRonda,climaRonda, seleccionadaAyuda);
			}
		}		
	}
	
	public int menuAtacarDefender(Equipo equipo) {
			System.out.println("(1) Atacar\n(2) Defender");
			System.out.print(equipo.getNombre()+", escoge una opción: ");
			opcion=input.nextInt();
			while(opcion<1||opcion>2) {
				System.out.print("Error: Inténtalo de nuevo: ");
				opcion=input.nextInt();
			}
			return opcion;
			
	}
	
	public int numeroMisilesAtaque(Equipo equipo) {
		System.out.println("Con cuantos misiles quieres atacar en total (dispones de "+equipo.getPais().getMisilesMaxAtaque()+" misiles de ataque).");
		int misilesOpcion=input.nextInt();
		while (misilesOpcion>equipo.getPais().getMisilesMaxAtaque()) {
			System.out.println("ERROR: Número introducido incorrecto.");
			System.out.println("Inténtelo de nuevo: ");
			misilesOpcion=input.nextInt();
		}
		return misilesOpcion;
	}
	

	public Equipo escogerEquipoAtacar(ArrayList<Equipo> participantes, Equipo equipo) {
		System.out.println(equipo.getNombre()+", escoge un equipo a atacar: ");
		for (int i = 0 ; i < participantes.size() ; i++) {
			if (!participantes.get(i).equals(equipo)) {
				System.out.println("("+(i+1)+") "+participantes.get(i).getNombre()+"("+participantes.get(i).getPais().getNombrePais()+")");
			}
		}
		int opcion = input.nextInt();
		while (opcion<1||opcion>participantes.size()) {
			System.out.print("ERROR: Opción incorrecta.\nInténtalo de nuevo: ");
			opcion = input.nextInt();
		}
		return participantes.get(opcion-1);
	}
	
	
	public int cuantosMisilesAtacar(Equipo equipo) {
		System.out.print("Introduce con cuantos misiles: ");
		int x = input.nextInt();
		while (x>equipo.getPais().getMisilesAtaque()) {
			System.out.print("ERROR: Número de misiles incorrectos.\nInténtalo de nuevo: ");
			x=input.nextInt();
		}
		return x;
	}
	
	
	
	
	
	
	
}
