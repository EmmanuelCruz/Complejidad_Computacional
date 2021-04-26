import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
* Implementacion de un algoritmo de aproximacion
* para Subset Sum.
* @author Emmanuel Cruz Hernandez.
* @version 1.0  Noviembre 2020.
* @since Complejidad Computacional 2021-1.
*/
public class SubsetSum{

	/**
	* Mezcla dos listas de tal forma que la lista es ordenada.
	* @param l1 la primera lista a mezclar.
	* @param l2 la segunda lista a mezclar.
	* @return una lista mezclada de l1 con l2 y ordenada.
	*/
	public static ArrayList<Integer> mergeList(ArrayList<Integer> l1, ArrayList<Integer> l2){
		ArrayList<Integer> merged = new ArrayList<>(l1);
		merged.addAll(l2);
		Collections.sort(merged);
		return merged;
	}
	
	/**
	* Recorta la lista que pasa como parametro.
	* @param l la lista a ser recortada.
	* @param delta el factor de recorte sobre l lista.
	* @return la lista resultante del corte.
	*/
	public static ArrayList<Integer> trim(ArrayList<Integer> l, double delta){
		int m = l.size();
		ArrayList<Integer> recortada = new ArrayList<>();
		recortada.add(l.get(0));
		double last = l.get(0);
		for(int i = 1; i<m ; i++){
			int yi = l.get(i);
			if(yi > last*(1+delta)){
				recortada.add(yi);
				last = yi;
			}
		}
		return recortada;
	}

	/**
	* Suma un numero a todos los elementos de una lista.
	* @param l la lista a sumar el entero.
	* @param x el entero a sumar los elementos.
	*/
	private static ArrayList<Integer> sumX(ArrayList<Integer> l, int x){
		ArrayList<Integer> sumada = new ArrayList<>();
		for(int xi : l)
			sumada.add(xi+x);
		return sumada;
	}

	/**
	* Elimina todos los elementos de una lista mayores a un entero t.
	* @param l la lista a eliminar elementos.
	* @param t el factor de eliminacion.
	*/
	private static void removeT(ArrayList<Integer> l, double t){
		for(int i = 0; i<l.size(); i++){
			if(l.get(i) > t)
				l.remove(i);
		}
	}

	/**
	* Algoritmo de aproximacion de Subset Sum.
	* @param s los pesos.
	* @param t solucion de peso al menos t.
	* @param e el parametro de aproximacion.
	* @return una solucion apoximada de subset sum.
	*/
	public static int approxSubsetSum(ArrayList<Integer> s, int t, double e){
		int n = s.size();
		ArrayList<Integer> li = new ArrayList<>();
		li.add(0);
		for(int i = 0; i<n ; i++){
			System.out.println("\nL"+(i+1));
			li = mergeList(li, sumX(li, s.get(i)));
			muestra(li);
			li = trim(li, e/(2*n));
			muestra(li);
			removeT(li, t);
			muestra(li);
		}
		return li.get(li.size()-1);
	}

	public static void muestra(ArrayList<Integer> l){
		for(int i : l)
			System.out.print(i+" ");
		System.out.println();
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.print("Ingresa la cantidad de pesos: ");
		int cantidad = sc.nextInt();
		ArrayList<Integer> pesos = new ArrayList<>();
		for(int i = 0; i < cantidad ; i++){
			System.out.print("\nIngresa el peso "+(i+1)+": ");
			int peso = sc.nextInt();
			pesos.add(peso);
		}

		System.out.print("\nIngresa un entero t para una solucion de al menos t: ");
		int t = sc.nextInt();

		System.out.print("\nIngresa un factor de aproximacion entre 0 y 1: ");
		double e = sc.nextDouble();

		System.out.println("\nUna solucion apoximada de subset sum con los pesos dados es: "+
			approxSubsetSum(pesos, t, e));

	}

}