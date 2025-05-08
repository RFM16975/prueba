package package1;

import java.util.ArrayList;
import java.util.Random;

public class Partida {

	Random r = new Random();
	Menu menu = new Menu();

	private int numRonda = 1;
	private String climaRonda;
	private String[] climas = { "NIEBLA", "LLUVIA", "CALOR", "NIEVE", "TERREMOTO" };
	private ArrayList<Equipo> participantes = new ArrayList<>();

	// MÉTODO PRINCIPAL DE LA CLASE
	public void jugar(int numJugadores) {
		iniciarPartida(numJugadores);
		while (jugadoresVivos() >= 1) {
			climaRonda = establecerClimaRonda();
			ronda();
			numRonda++;
		}
		if (jugadoresVivos() == 1) {
			System.out.println("EL GANADOR ES: " + participantes.get(0).getNombre());
		} else {
			System.out.println("EMPATE: Todos los jugadores han muerto");
		}

	}

	// MÉTODO RONDA
	private void ronda() {
		System.out.println("\nRONDA " + numRonda);
		Utilities.imprimirValoresInicioRonda(participantes);
		imprimirClima();
		climaTerremoto();
		resetAtacadosRonda();
		actualizarVidasInicioRonda();
		for (int i = 0; i < jugadoresVivos(); i++) {
			escudoItalia(participantes.get(i).getPais());
			repartirMisilesMAXAtaque(participantes.get(i));
			int opc;
			boolean seleccionadaAyuda = false;
			do {
				opc = menu.menuAtacarDefender(participantes.get(i), numRonda, climaRonda, seleccionadaAyuda);
				if (opc == 1) {
					atacar(participantes.get(i));
				} else if (opc == 2) {
					misilesDefensa(participantes.get(i));
				} else {
					seleccionadaAyuda=true;
					ayudaAliada(participantes.get(i));
				}
			}while(opc==3);
		}
		matarMuertos();
	}

	// MÉTODOS AUXILIARES
	private void iniciarPartida(int numJugadores) {
		for (int i = 0; i < numJugadores; i++) {
			Equipo equipo = new Equipo(menu.escogerNombreEquipo(i));
			Pais pais = new Pais(menu.escogerPais(equipo));
			equipo.setPais(pais);
			participantes.add(equipo);
			repartirVidasIniciales(equipo);
		}
	}

	private void repartirVidasIniciales(Equipo equipo) {
		if (equipo.getPais().getNombrePais().equals("FRANCIA")) {
			equipo.getPais().setVidasIniciales(260);
		} else if (equipo.getPais().getNombrePais().equals("DINAMARCA")) {
			equipo.getPais().setVidasIniciales(400);
		} else if (equipo.getPais().getNombrePais().equals("SUIZA")) {
			equipo.getPais().setVidasIniciales(100);
		} else {
			equipo.getPais().setVidasIniciales(200);
		}
	}

	private void repartirMisilesMAXAtaque(Equipo equipo) {
		if (equipo.getPais().getNombrePais().equals("ALEMANIA")) {
			if (climaRonda.equals("NIEBLA"))
				equipo.getPais().setMisilesMaxAtaque(70);
			else
				equipo.getPais().setMisilesMaxAtaque(60);
		} else if (equipo.getPais().getNombrePais().equals("DINAMARCA") && numRonda <= 5) {
			equipo.getPais().setMisilesMaxAtaque(10 * numRonda);
		} else {
			equipo.getPais().setMisilesMaxAtaque(50);
		}
	}

	private void atacar(Equipo equipo) {
		repartirMisilesAtaque(equipo);
		while (equipo.getPais().getMisilesAtaque() > 0) {
			Utilities.imprimirValoresEntreAtaques(equipo);
			Equipo objetivo = menu.escogerEquipoAtacar(participantes, equipo);
			objetivo.setAtacadoEnRonda(true);
			ataque(objetivo, menu.cuantosMisilesAtacar(equipo), equipo);
			evaluarDefensa(objetivo);
		}
	}

	private void repartirMisilesAtaque(Equipo equipo) {
		int aux = menu.numeroMisilesAtaque(equipo);
		equipo.getPais().setMisilesAtaque(aux);
		if (climaRonda.equals("LLUVIA")) {
			equipo.getPais().setMisilesDefensa((equipo.getPais().getMisilesMaxAtaque() - aux) / 2 - 10);
		} else {
			equipo.getPais().setMisilesDefensa((equipo.getPais().getMisilesMaxAtaque() - aux) / 2);
		}

	}

	private void misilesDefensa(Equipo equipo) {
		if (equipo.getPais().getNombrePais().equals("AUSTRIA")) {
			if (climaRonda.equals("LLUVIA")) {
				equipo.getPais().setMisilesDefensa(equipo.getPais().getMisilesMaxAtaque() / 2 + 20);
			} else {
				equipo.getPais().setMisilesDefensa(equipo.getPais().getMisilesMaxAtaque() / 2 + 10);
			}
		} else {
			equipo.getPais().setMisilesDefensa(equipo.getPais().getMisilesMaxAtaque() / 2);
		}
		System.out.println(
				equipo.getNombre() + ", dispones de " + equipo.getPais().getMisilesDefensa() + " misiles de defensa.");
	}

	private void ataque(Equipo objetivo, int misiles, Equipo atacante) {
		switch (climaRonda) {
		case ("NIEBLA"):
			int aux = r.nextInt(100);
			if (aux <= 80) {
				evaluarAtaque(objetivo, misiles, atacante);
			} else {
				System.out.println("\nATAQUE FALLIDO POR NIEBLA\n");
				atacante.getPais().setMisilesAtaque(atacante.getPais().getMisilesAtaque() - misiles);
			}
			break;

		case ("CALOR"):
			misiles = misiles + 10;
			evaluarAtaque(objetivo, misiles, atacante);

		default:
			evaluarAtaque(objetivo, misiles, atacante);
		}

	}

	private void evaluarAtaque(Equipo objetivo, int misiles, Equipo atacante) {
		switch (objetivo.getPais().getNombrePais()) {
		case ("SUIZA"):
			int random = r.nextInt(100);
			if (random >= 40) {
				evaluarAtaqueAUX(objetivo, misiles, atacante);
			} else {
				atacante.getPais().setMisilesAtaque(atacante.getPais().getMisilesAtaque() - misiles);
				System.out.println("SUIZA ha evitado el ataque!");
			}
			break;
		
		case ("YUGOSLAVIA"):
			if (!climaRonda.equals("")) {
				misiles=(int)(misiles*1.1);
				evaluarAtaqueAUX(objetivo, misiles, atacante);
			}
			else {
				evaluarAtaqueAUX(objetivo, misiles, atacante);
			}
		}
		//------------------------------------------------------	
		switch(atacante.getPais().getNombrePais()) {
		case ("FRANCIA"):
			int rando = r.nextInt(100);
			if (rando >= 95) {
				evaluarAtaqueAUX(objetivo, misiles, atacante);
			} else {
				atacante.getPais().setMisilesAtaque(atacante.getPais().getMisilesAtaque() - misiles);
				System.out.println("Ataque fallido!");
			}
			break;
		case ("YUGOSLAVIA"):
			misiles=(int)(misiles*1.2);
			break;
			
		default:
			evaluarAtaqueAUX(objetivo, misiles, atacante);
		}		
	}

	private void evaluarAtaqueAUX(Equipo objetivo, int misiles, Equipo atacante) {
		objetivo.getPais().setVidasActuales(objetivo.getPais().getVidasActuales() - misiles);
		atacante.getPais().setMisilesAtaque(atacante.getPais().getMisilesAtaque() - misiles);
	}

	private void evaluarDefensa(Equipo equipo) {
		Pais pais = equipo.getPais();
		
		if ((pais.getVidasActuales() + pais.getMisilesDefensa()) > equipo.getVidasInicioRonda()) {
			pais.setVidasActuales(equipo.getVidasInicioRonda());
			pais.setMisilesDefensa(pais.getVidasActuales()+ pais.getMisilesDefensa() - equipo.getVidasInicioRonda());
		}
		else {
			pais.setVidasActuales(pais.getVidasActuales() + pais.getMisilesDefensa());
			pais.setMisilesDefensa(0);
		}
	}

	private String establecerClimaRonda() {
		int aux = r.nextInt(8);
		if (aux < climas.length) {
			return climas[aux];
		} else {
			return "";
		}
	}

	private void imprimirClima() {
		if (climaRonda != "") {
			System.out.println("El clima especial en la ronda " + numRonda + " es: " + climaRonda);
		} else {
			System.out.println("No hay clima especial en la ronda " + numRonda);
		}
	}

	private int jugadoresVivos() {
		int aux = 0;
		for (int i = participantes.size() - 1; i >= 0; i--) {
			if (!participantes.get(i).isMuerte()) {
				aux++;
			} else {
				participantes.remove(i);
			}
		}
		return aux;
	}

	private void resetAtacadosRonda() {
		for (int i = 0; i < jugadoresVivos(); i++) {
			participantes.get(i).setAtacadoEnRonda(false);
		}
	}

	private void actualizarVidasInicioRonda() {
		for (int i = 0; i < jugadoresVivos(); i++) {
			participantes.get(i).setVidasInicioRonda(participantes.get(i).getPais().getVidasActuales());
		}
	}

	private void escudoItalia(Pais pais) {
		if (pais.getNombrePais().equals("ITALIA")) {
			for (int i = 1 ; i <= numRonda ; i++) {
				if (i%2==0) {
					pais.setEscudo(pais.getEscudo()+5);
				}
			}
		}
	}

	private void climaTerremoto() {
		if (climaRonda.equals("TERREMOTO")) {
			for (int i = 0; i < participantes.size(); i++) {
				participantes.get(i).getPais().setVidasActuales(participantes.get(i).getPais().getVidasActuales() - 5);
			}
			System.out.println("TERREMOTO! Todos los jugadores reciben 5 de daño.");
			Utilities.imprimirValoresInicioRonda(participantes);
		}

	}

	private void matarMuertos() {
		for (int i = 0; i < participantes.size(); i++) {
			if (participantes.get(i).getPais().getVidasActuales() <= 0) {
				participantes.get(i).setMuerte(true);
				System.out.println(participantes.get(i).getNombre() + " ha muerto");

			}
		}
	}
	
	//METODOS AYUDA ALIADA
	private int randomAyudaAliada() {
		int aux = r.nextInt(1,10);
		if (aux < 4) {
			return 0;
		}else if (aux>=4 && aux <8) {
			return 1;
		}else {
			return 2;
		}
	}
	
	private void ayudaAliada(Equipo equipo) {
		int random = randomAyudaAliada();
		switch (random) {
		case 0:
			misilesAtaqueAA(equipo);
			break;
		case 1:
			misilesDefensaAA(equipo);
			break;
		default:
			traicionAliadaAA(equipo);
		}
	}
	
	private void misilesAtaqueAA(Equipo equipo) {
		equipo.getPais().setMisilesMaxAtaque(equipo.getPais().getMisilesMaxAtaque()+25);
	}
	
	private void misilesDefensaAA(Equipo equipo) {
		equipo.getPais().setMisilesDefensa(equipo.getPais().getMisilesDefensa()+30);
	}
	
	private void traicionAliadaAA(Equipo equipo) {
		equipo.getPais().setVidasActuales(equipo.getPais().getVidasActuales()-10);
	}
}
  