package package1;

public class Main {
	
	public static void main(String[] args) {
		Marco marcoGeneral = new Marco();
		Menu menu = new Menu();
		AudioPlayer.ReproducirAudio();
		int x;
		
		do {
		x = menu.menuInicial();
		if (x==0)
			System.exit(0);
		
		else if (x==1){
			Partida partida = new Partida();
			partida.jugar(menu.menuNumJugadores());
			
		}
		
		}while(x!=0);
		
		
}

	
}
