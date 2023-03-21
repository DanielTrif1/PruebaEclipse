package prueba1;

//Implementa un programa Java que dada una tabla 
//multidimensional de números enteros, muestre todos los sus elementos por pantalla de forma recursiva.
public class Ej1 {
	public static void main(String[] arg) {
		int[][] lista1 = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 } };
		mostrar(lista1);
	}

	public static void mostrar(int[][] lista1) {
		mostrar(lista1, 0, 0);
	}

	// tiene que ser private si o si.
	private static void mostrar(int[][] lista1, int i, int j) {
		if (i < lista1.length && j == lista1[i].length) {
			System.out.println();
			mostrar(lista1, i + 1, 0);
		} else if (i < lista1.length) {
			System.out.println(lista1[i][j] + " ");
			mostrar(lista1, i, j + 1);
		} else {

		}
	}
}
