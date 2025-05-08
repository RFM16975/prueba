package package1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;

public class Utilities {
	

	// MÉTODOS
	public static void mostrarReglasInformacion(int opcion) {
		if (opcion == 2) {
			leerReglas();
		}
		else if (opcion == 3) {
			leerInformacion();
		}
	}

	
	public static void leerReglas() {
		try {
            File archivo = new File("reglas.txt");
            Scanner scanner = new Scanner(archivo);
            while (scanner.hasNextLine()) { 
                System.out.println(scanner.nextLine());
            }

            scanner.close(); 
        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado.");
            e.printStackTrace();
        }
	}
	
	
	public static void leerInformacion() {
		try {
            File archivo = new File("informacion.txt");
            Scanner scanner = new Scanner(archivo);

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                System.out.println(linea);
            }
          scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado.");
            e.printStackTrace();
        }
	}
	
	
	
	public static boolean contains(int[] array, int valor) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == valor) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean contains(String[] array, String valor) {
        for (String elemento : array) {
            if (elemento.equals(valor)) {
                return true;
            }
        }
        return false;
    }

	public static boolean contains(ArrayList<String> array, String valor) {
        for (String elemento : array) {
            if (elemento.equals(valor)) {
                return true;
            }
        }
        return false;
    }
	
	public static int indexOf(String[] array, String valor) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(valor)) {
				return i;
			}
		}
		return -1; // si no encontrara el valor, retornaria menis 1
	}
	
	public static boolean comprobarPaisesRegex(String texto) {
        Pattern pattern = Pattern.compile("^(ALEMANIA|FRANCIA|AUSTRIA|YUGOSLAVIA|ITALIA|HUNGRIA|POLONIA|BELGICA|DINAMARCA|SUIZA)$"); // Compilar la expresión regular
        Matcher matcher = pattern.matcher(texto); // Crear el matcher para la cadena
        return matcher.matches(); // Verificar si el texto coincide completamente con la regex
    }
	
	public static void imprimirValoresInicioRonda(ArrayList<Equipo> participantes) {
		for (int i = 0 ; i < participantes.size() ; i++) {
			System.out.println(participantes.get(i).getNombre()+", dispones de "+participantes.get(i).getPais().getVidasActuales()+" vidas.");
		}
	}
	
	public static void imprimirValoresEntreAtaques(Equipo atacante) {
		System.out.println(atacante.getNombre()+", te quedan "+atacante.getPais().getMisilesAtaque()
				+" misiles de ataque y "+atacante.getPais().getMisilesDefensa()+" misiles de defensa.");
	}
}