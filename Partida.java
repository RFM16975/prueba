package package1;

import java.util.ArrayList;
import java.util.Random;

public class Partida {

    private Random r = new Random();
    private ArrayList<Equipo> participantes = new ArrayList<>();
    private int numRonda = 1;
    private String climaRonda;
    private String[] climas = { "NIEBLA", "LLUVIA", "CALOR", "NIEVE", "TERREMOTO" };

    // Método principal para jugar
    public void jugar(int numJugadores, Menu menu) {
        iniciarPartida(numJugadores, menu);
        while (jugadoresVivos() >= 1) {
            climaRonda = establecerClimaRonda();
            ronda(menu);
            numRonda++;
        }
        if (jugadoresVivos() == 1) {
            System.out.println("EL GANADOR ES: " + participantes.get(0).getNombre());
        } else {
            System.out.println("EMPATE: Todos los jugadores han muerto");
        }
    }

    // Método para iniciar la partida
    private void iniciarPartida(int numJugadores, Menu menu) {
        for (int i = 0; i < numJugadores; i++) {
            Equipo equipo = new Equipo(menu.escogerNombreEquipo(i));
            Pais pais = new Pais(menu.escogerPais(equipo));
            equipo.setPais(pais);
            participantes.add(equipo);
            repartirVidasIniciales(equipo);
        }
    }

    // Método para manejar cada ronda
    private void ronda(Menu menu) {
        System.out.println("\nRONDA " + numRonda);
        Utilities.imprimirValoresInicioRonda(participantes);
        imprimirClima();
        climaTerremoto();
        resetAtacadosRonda();
        actualizarVidasInicioRonda();

        for (int i = 0; i < jugadoresVivos(); i++) {
            repartirMisilesMAXAtaque(participantes.get(i));

            int opcion;
            boolean seleccionadaAyuda = false;
            do {
                opcion = menu.menuAtacarDefender(participantes.get(i), numRonda, climaRonda, seleccionadaAyuda);
                if (opcion == 1) {
                    atacar(participantes.get(i), menu);
                } else if (opcion == 2) {
                    misilesDefensa(participantes.get(i));
                } else if (opcion == 3) {
                    seleccionadaAyuda = true;
                    ayudaAliada(participantes.get(i));
                }
            } while (opcion == 3); // Si el jugador selecciona ayuda aliada
        }
        matarMuertos();
    }

    // Métodos auxiliares para el flujo de la partida
    private void repartirVidasIniciales(Equipo equipo) {
        switch (equipo.getPais().getNombrePais()) {
            case "FRANCIA":
                equipo.getPais().setVidasIniciales(260);
                break;
            case "DINAMARCA":
                equipo.getPais().setVidasIniciales(400);
                break;
            case "SUIZA":
                equipo.getPais().setVidasIniciales(100);
                break;
            default:
                equipo.getPais().setVidasIniciales(200);
        }
    }

    private void repartirMisilesMAXAtaque(Equipo equipo) {
        if ("ALEMANIA".equals(equipo.getPais().getNombrePais())) {
            if ("NIEBLA".equals(climaRonda)) {
                equipo.getPais().setMisilesMaxAtaque(70);
            } else {
                equipo.getPais().setMisilesMaxAtaque(60);
            }
        } else if ("DINAMARCA".equals(equipo.getPais().getNombrePais()) && numRonda <= 5) {
            equipo.getPais().setMisilesMaxAtaque(10 * numRonda);
        } else {
            equipo.getPais().setMisilesMaxAtaque(50);
        }
    }

    private void atacar(Equipo equipo, Menu menu) {
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
        int aux = Menu.numeroMisilesAtaque(equipo);
        equipo.getPais().setMisilesAtaque(aux);
        if ("LLUVIA".equals(climaRonda)) {
            equipo.getPais().setMisilesDefensa((equipo.getPais().getMisilesMaxAtaque() - aux) / 2 - 10);
        } else {
            equipo.getPais().setMisilesDefensa((equipo.getPais().getMisilesMaxAtaque() - aux) / 2);
        }
    }

    private void misilesDefensa(Equipo equipo) {
        if ("AUSTRIA".equals(equipo.getPais().getNombrePais())) {
            if ("LLUVIA".equals(climaRonda)) {
                equipo.getPais().setMisilesDefensa(equipo.getPais().getMisilesMaxAtaque() / 2 + 20);
            } else {
                equipo.getPais().setMisilesDefensa(equipo.getPais().getMisilesMaxAtaque() / 2 + 10);
            }
        } else {
            equipo.getPais().setMisilesDefensa(equipo.getPais().getMisilesMaxAtaque() / 2);
        }
        System.out.println(equipo.getNombre() + ", dispones de " + equipo.getPais().getMisilesDefensa() + " misiles de defensa.");
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

    private int randomAyudaAliada() {
        int aux = r.nextInt(1, 10);
        if (aux < 4) {
            return 0;
        } else if (aux >= 4 && aux < 8) {
            return 1;
        } else {
            return 2;
        }
    }

    private void misilesAtaqueAA(Equipo equipo) {
        equipo.getPais().setMisilesMaxAtaque(equipo.getPais().getMisilesMaxAtaque() + 25);
    }

    private void misilesDefensaAA(Equipo equipo) {
        equipo.getPais().setMisilesDefensa(equipo.getPais().getMisilesDefensa() + 30);
    }

    private void traicionAliadaAA(Equipo equipo) {
        equipo.getPais().setVidasActuales(equipo.getPais().getVidasActuales() - 10);
    }

    private void ataque(Equipo objetivo, int misiles, Equipo atacante) {
        switch (climaRonda) {
            case "NIEBLA":
                int aux = r.nextInt(100);
                if (aux <= 80) {
                    evaluarAtaque(objetivo, misiles, atacante);
                } else {
                    System.out.println("\nATAQUE FALLIDO POR NIEBLA\n");
                    atacante.getPais().setMisilesAtaque(atacante.getPais().getMisilesAtaque() - misiles);
                }
                break;
            case "CALOR":
                misiles += 10;
                evaluarAtaque(objetivo, misiles, atacante);
                break;
            default:
                evaluarAtaque(objetivo, misiles, atacante);
        }
    }

    private void evaluarAtaque(Equipo objetivo, int misiles, Equipo atacante) {
        // Lógica para evaluar el ataque y aplicar daño
        objetivo.getPais().setVidasActuales(objetivo.getPais().getVidasActuales() - misiles);
        atacante.getPais().setMisilesAtaque(atacante.getPais().getMisilesAtaque() - misiles);
    }

    private void evaluarDefensa(Equipo equipo) {
        Pais pais = equipo.getPais();
        if ((pais.getVidasActuales() + pais.getMisilesDefensa()) > equipo.getVidasInicioRonda()) {
            pais.setVidasActuales(equipo.getVidasInicioRonda());
            pais.setMisilesDefensa(pais.getVidasActuales() + pais.getMisilesDefensa() - equipo.getVidasInicioRonda());
        } else {
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
        if (!climaRonda.isEmpty()) {
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

    private void climaTerremoto() {
        if ("TERREMOTO".equals(climaRonda)) {
            for (Equipo participante : participantes) {
                participante.getPais().setVidasActuales(participante.getPais().getVidasActuales() - 5);
            }
            System.out.println("TERREMOTO! Todos los jugadores reciben 5 de daño.");
            Utilities.imprimirValoresInicioRonda(participantes);
        }
    }

    private void matarMuertos() {
        for (int i = participantes.size() - 1; i >= 0; i--) {
            if (participantes.get(i).getPais().getVidasActuales() <= 0) {
                participantes.get(i).setMuerte(true);
                System.out.println(participantes.get(i).getNombre() + " ha muerto");
            }
        }
    }
}