package prueba;

public class Prueba1 {
	public static void main(String[] arg) {
		int hola = 3;
		int[] lista1 = {1,2,3,4};
		System.out.println(mostrar(lista1,hola));
	}

	public static boolean mostrar(int[] lista1, int num) {
		return mostrar(lista1,num, 0);
		
	}
	//tiene que ser private si o si.
	private static boolean mostrar(int[] lista1,int num, int i) {
		if(i==lista1.length)return false;
		if(lista1[i]==num)return true;
		else return mostrar (lista1,num, i+1);
	}
}
