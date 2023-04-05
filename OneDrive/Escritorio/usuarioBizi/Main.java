package ayuntamiento;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

/**
 * Programa que mediante varias funciones y algoritmos obtiene a partir de un
 * fichero '.csv' que contiene un registro de los usos de BiziZaragoza, una
 * serie de datos .El programa se divide en dos partes. Antes de todo crea unos
 * ficheros nuevos mas pequeños para poder trabajar con ellos, luego según
 * seleccion del usuario realiza la trea 2 o la 3. La tarea dos indica cuantos
 * usos circulares, traslados y usos totales hay en el fichero.La tarea 3
 * devuelve una lista con los usuarios que mas usos totales tienen y el numero
 * de usuarios diferentes que hay dentro de este.
 */
public class Main {
	/**
	 * Pre:--- 
	 * Post: Funcion crearFicheroCorto, crea un fichero nuevo y almacena la
	 * cantidad deseada de lineas del fichero original a este.
	 */
	private static File crearFicheroCorto(File ficehro, String ficheroCorto, int num) {
		File corto = new File(ficheroCorto);
		try {
			Formatter salida = new Formatter(ficheroCorto);
			Scanner f = new Scanner(ficehro);
			for (int i = 0; i < num; i++) {
				salida.format(f.nextLine() + "\n");
			}
			f.close();
			salida.close();
		} catch (FileNotFoundException e) {
			System.out.println("El fichero " + ficehro + " no ha podido ser abierto.");
		}
		return corto;
	}

	/**
	 * Pre:--- 
	 * Post: Funcion contarUsos, va linea por linea del fichero viendo si
	 * hay alguna coincidencia entre los elementos 2 y 4 que representan los puntos
	 * de anclaje de una estacion de bicis. Si coinciden entonces la variable
	 * contadorCirc aumentará y si no coinciden la variable contadorTras aumentará,
	 * por lo que sabremos si el usuario ha realizado una ruta circular o
	 * simplemente un traslado. Por último lo mostramos por pantalla.
	 */
	private static void contarUsos(File fichero) {
		try {
			Scanner f = new Scanner(fichero);
			int contador = 0;
			int contadorCirc = 0;
			int contadorTras = 0;
			while (f.hasNextLine()) {
				String line = f.nextLine();
				String[] elemento = line.split(";");
				if (contador > 0) {
					int retEstacion = Integer.parseInt(elemento[2]);
					int ancEstacion = Integer.parseInt(elemento[4]);
					if (retEstacion == ancEstacion) {
						contadorCirc++;
					} else {
						contadorTras++;
					}
				}
				contador++;
			}
			System.out.println("Número de usuos circulares: " + contadorCirc + "\n" + "Número de usos de traslados: "
					+ contadorTras + "\n" + "Número de usos totales: " + (contadorCirc + contadorTras) + "\n");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Pre:--- 
	 * Post: Funcion infoLista, Muestra la los 15 usuarios que mas han
	 * viajes han realizado y la cantidad de usuarios difernetes que existen en el
	 * archivo.
	 */
	private static void infoLista(File fichero) {
		try {
			Scanner f = new Scanner(fichero);
			List<UsuarioBizi> totalJugadores = new ArrayList<>();
			int contador = 0;
			int contadorCirc = 0;
			int contadorTras = 0;
			int idUsuario = 0;
			while (f.hasNextLine()) {
				String line = f.nextLine();
				if (contador == 0) { // se salta la primera linea del archivo
					contador++;
					continue;
				}
				String[] elemento = line.split(";");
				if (contador > 0) {
					int retEstacion = Integer.parseInt(elemento[2]);
					int ancEstacion = Integer.parseInt(elemento[4]);
					idUsuario = Integer.parseInt(elemento[0]);
					if (retEstacion == ancEstacion) {
						contadorCirc++;
					} else {
						contadorTras++;
					}
				}
				contador++;
				// creo objetos de la calse UsuarioBizi
				UsuarioBizi usuario = new UsuarioBizi(idUsuario, contadorTras, contadorCirc,
						(contadorCirc + contadorTras));
				totalJugadores.add(usuario);
				contadorCirc = 0;
				contadorTras = 0;
			}
			// ordeno usuarios para despues poder contar mejor.
			ordenaPorUsuario(totalJugadores);
			// comparo usuarios y ordeno por "totales" y muestro por pantalla la lista
			compararUsuarios(totalJugadores);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Pre:--- 
	 * Post: Funcion compararUsuarios lo que hace es una vez ya hemos creado
	 * los objetos de tipo UsuarioBizi como ya los tenemos ordenados vamos viendo
	 * cuales se repiten y cuales no recorriendo el archivo una vez, mientras vemos
	 * que un elemento se repite almacenamos los datos en unas variables para luego
	 * terminar de sumarlas cuando encontramos un el usuario que es diferente,
	 * entonces añadimos en el Array llamado jugadoresUnicos los usuarios con los
	 * valores totales y seran diferentes. Finalmente ordenamos por "totales" y
	 * sacamos por pantalla los 15 usuarios que mas han viajes han hecho.
	 */
	private static void compararUsuarios(List<UsuarioBizi> totalJugadores) {
		int tras = 0;
		int circ = 0;
		List<UsuarioBizi> jugadoresUnicos = new ArrayList<>();
		jugadoresUnicos.add(totalJugadores.get(0));
		for (int i = 1; i < totalJugadores.size(); i++) {
			if (totalJugadores.get(i).getIdUsuario() != (totalJugadores.get(i - 1).getIdUsuario())) {
				tras = tras + totalJugadores.get(i).getTraslados();
				circ = circ + totalJugadores.get(i).getCircular();
				totalJugadores.get(i).setTraslados(tras);
				totalJugadores.get(i).setCircular(circ);
				totalJugadores.get(i).setTotal(circ + tras);
				jugadoresUnicos.add(totalJugadores.get(i));
				tras = 0;
				circ = 0;
			} else {
				tras = tras + totalJugadores.get(i).getTraslados();
				circ = circ + totalJugadores.get(i).getCircular();
			}
		}
		ordenaPorTotales(jugadoresUnicos);
		for (int i = 0; i < 15; i++) {
			String formattedString = jugadoresUnicos.get(i).toString().replace(",", "").replace("[", "")
					.replace("]", "\n").trim();
			System.out.println(formattedString);
		}
		// devuelve el numero de usuarios distintos.
		System.out.println("El numero de usuarios distintos es: " + jugadoresUnicos.size());
	}

	/**
	 * Pre:--- 
	 * Post: Funcion ordenaPorTablas, funcion que se encarga de decirle al
	 * Collections.Sort como ordenar el ArrayList de UsuariosBizi según"totales"
	 */
	private static void ordenaPorTotales(List<UsuarioBizi> totalJugadores) {
		Comparator<UsuarioBizi> comparator = new Comparator<UsuarioBizi>() {
			@Override
			// hecho al reves para que el comparator me devuelva el orden decreciente
			public int compare(UsuarioBizi o1, UsuarioBizi o2) {
				if (o1.getTotal() > o2.getTotal()) {
					return -1;
				}
				if (o1.getTotal() < o2.getTotal()) {
					return 1;
				} else {
					return 0;
				}
			}
		};
		// Ordena utilizando el comparador definido arriba
		Collections.sort(totalJugadores, comparator);
	}

	/**
	 * Pre:--- 
	 * Post: Funcion ordenaPorTablas, funcion que se encarga de decirle al
	 * Collections.Sort como ordenar el ArrayList de UsuariosBizi según"Usuarios"
	 */
	private static void ordenaPorUsuario(List<UsuarioBizi> totalJugadores) {
		Comparator<UsuarioBizi> comparator = new Comparator<UsuarioBizi>() {
			@Override
			// hecho al reves para que el comparator me devuelva el orden decreciente
			public int compare(UsuarioBizi o1, UsuarioBizi o2) {
				if (o1.getIdUsuario() > o2.getIdUsuario()) {
					return -1;
				}
				if (o1.getIdUsuario() < o2.getIdUsuario()) {
					return 1;
				} else {
					return 0;
				}
			}
		};
		// Ordena utilizando el comparador definido arriba
		Collections.sort(totalJugadores, comparator);
	}

	/**
	 * Pre:--- 
	 * Post: Funcion main, almacena todas las rutas de los ficheros en sus
	 * respectivas varialbes, despues organiza en un menú todos estos y las
	 * funciones para poder ir probando,
	 */
	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		String ficheroCorto16 = "C:\\Users\\trifd\\OneDrive\\Documentos\\Dani\\SALESIANOS_2021_2022\\PROGRAMACION\\leerficheros\\corto16.csv";
		String ficheroCorto17 = "C:\\Users\\trifd\\OneDrive\\Documentos\\Dani\\SALESIANOS_2021_2022\\PROGRAMACION\\leerficheros\\corto17.csv";
		File ficehro16 = new File(
				"C:\\Users\\trifd\\OneDrive\\Documentos\\Dani\\SALESIANOS_2021_2022\\PROGRAMACION\\leerficheros\\usos-16.csv");
		File ficehro17 = new File(
				"C:\\Users\\trifd\\OneDrive\\Documentos\\Dani\\SALESIANOS_2021_2022\\PROGRAMACION\\leerficheros\\usos-17.csv");
		File def16 = crearFicheroCorto(ficehro16, ficheroCorto16, 10);
		File def17 = crearFicheroCorto(ficehro17, ficheroCorto17, 2000);
		while (true) {
			System.out.println("¿Qué fichero desea probar?");
			System.out.println("1. tarea 2");
			System.out.println("2. tarea 3");
			System.out.println("0. Salir");
			String selector = entrada.next();
			if (selector.equals("1")) {
				System.out.println("Seleccione el archivo:");
				System.out.println("1. Fichero 16-pequeno");
				System.out.println("2. Fichero 17-pequeno");
				System.out.println("3. Fichero 16-grande");
				System.out.println("4. Fichero 17-grande");
				selector = entrada.next();
				if (selector.equals("1")) {
					contarUsos(def16);
				} else if (selector.equals("2")) {
					contarUsos(def17);
				} else if (selector.equals("3")) {
					contarUsos(ficehro16);
				} else if (selector.equals("4")) {
					contarUsos(ficehro17);
				}
			} else if (selector.equals("2")) {
				System.out.println("Seleccione el archivo:");
				System.out.println("1. Fichero 16-pequeno");
				System.out.println("2. Fichero 17-pequeno");
				System.out.println("3. Fichero 16-grande");
				System.out.println("4. Fichero 17-grande");
				selector = entrada.next();
				if (selector.equals("1")) {
					infoLista(def16);
				} else if (selector.equals("2")) {
					infoLista(def17);
				} else if (selector.equals("3")) {
					infoLista(ficehro16);
				} else if (selector.equals("4")) {
					infoLista(ficehro17);
				}
			} else if (selector.equals("0")) {
				break;
			}
		}
	}
}