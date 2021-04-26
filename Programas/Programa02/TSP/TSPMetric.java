import java.util.ArrayList;

/**
* Clase que implementa el algoritmo de Christofides
* para la metrica del problema TSP.
* @author Emmanuel Cruz Hernandez.
* @version 1.0 Noviembre 2020.
* @since Complejidad Computacional 2021-1.
*/
public class TSPMetric{
	
	/**
	* Algoritmo de Christofides.
	* @param g una grafica metrica.
	* @return un tour de TSP problem en G.
	*/
	public static ArrayList<Integer> christofides(Grafica g){
		Grafica arbol = g.prim();
		System.out.println("\nArbol con aristas:");
		arbol.muestraAristas();
		ArrayList<Arista> apareamiento = g.apareamiento(g, arbol);
		System.out.println("\nApareamiento minimo perfecto:");
		for(Arista a : apareamiento)
			System.out.println(a.v1+" - "+a.v2);
		Grafica compuesta = g.creaGrafica(arbol.aristas, apareamiento);
		System.out.println("\nGrafica compuesta del arbol y el apareamiento minimo perfecto:");
		compuesta.muestraAristas();
		ArrayList<Integer> camino = compuesta.shortcut();
		return camino;
	}

	/**
	* Muestra el camino que se sigue en una grafica.
	* @param camino el camino a mostrar.
	*/
	public static void muestraCamino(ArrayList<Integer> camino){
		for(int i : camino)
			System.out.print(i+" ");
		System.out.println(camino.get(0));
	}

	/**
	* Crea una nueva grafica.
	* @return una grafica nueva con pesos dados.
	*/
	public static Grafica construyeEjemplar(){
		Arista a1 = new Arista(1,2,11);
		Arista a2 = new Arista(1,3,15);
		Arista a3 = new Arista(1,4,9);
		Arista a4 = new Arista(1,5,16);
		Arista a5 = new Arista(1,6,9);
		Arista a6 = new Arista(2,3,10);
		Arista a7 = new Arista(2,4,10);
		Arista a8 = new Arista(2,5,15);
		Arista a9 = new Arista(2,6,14);
		Arista a10 = new Arista(3,4,9);
		Arista a11 = new Arista(3,5,8);
		Arista a12 = new Arista(3,6,13);
		Arista a13 = new Arista(4,5,10);
		Arista a14 = new Arista(4,6,6);
		Arista a15 = new Arista(5,6,11);

		ArrayList<Arista> lista = new ArrayList<>();
		lista.add(a1);
		lista.add(a2);
		lista.add(a3);
		lista.add(a4);
		lista.add(a5);
		lista.add(a6);
		lista.add(a7);
		lista.add(a8);
		lista.add(a9);
		lista.add(a10);
		lista.add(a11);
		lista.add(a12);
		lista.add(a13);
		lista.add(a14);
		lista.add(a15);

		return new Grafica(lista);
	}

	public static void main(String[] args) {
		Grafica g = construyeEjemplar();
		ArrayList<Integer> sol = christofides(g);
		System.out.println("\nCamino con la solucion de aproximacion:");
		muestraCamino(sol);
	}
}