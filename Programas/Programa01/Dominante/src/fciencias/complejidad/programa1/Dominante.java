package fciencias.complejidad.programa1;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
* Implementación para calcular un conjunto dominante de una gráfica.
* @author Emmanuel Cruz Hernández,
* @version 1.0 Octubre 2020.
* @since Complejidad Computacional 2021-1.
*/
public class Dominante{

	/**
	* Clase que representa un par.
	*/
	private class Pair<T, U>{

		/** Llave del par. */
		private T llave;

		/** Valor asociado. */
		private U valor;

		public Pair(T t, U u){
			this.llave = t;
			this.valor = u;
		}

		public T getKey(){
			return llave;
		}

		public U getValue(){
			return valor;
		}
	}

	/** Grafica a buscar alcanzabilidad entre <i>s</i> y <i>t</i>. */
	private Grafica g;
	
	/**
	* Crea un nuevo dominante.
	* @param n la cantidad de vértices.
	*/
	public Dominante(int n){
		Random rn = new Random();
		g = new Grafica(n);

		int aristas = n*(n-1)/2;

		for(int i = 0; i<aristas; i++){
			int a = rn.nextInt(n);
			int b = rn.nextInt(n);
			g.insertaArista(a, b);
		}
	}

	/**
	* Crea un nuevo dominante con una gráfica específica.
	* @param g la gráfica donde buscar calcular conjunto dominante.
	*/
	public Dominante(Grafica g){
		this.g = g;
	}

	/**
	* Implementación de la búsqueda del conjunto dominante.
	* @return una lista con los identificadores de los vértices del conjunto dominante
	*/
	public ArrayList<Integer> conjuntoDominante(){
		ArrayList<Pair<Integer, Integer>> ordenada = mergeSortModificado(this.generaGrados());
		ArrayList<Integer> dominante = new ArrayList<>();
		ArrayList<Integer> eliminados = new ArrayList<>();

		for(int i = 0; i<g.longitud(); i++)
			dominante.add(i);

		for(int i = 0; i<ordenada.size(); i++){
			int id = ordenada.get(i).getKey();
			int pos = dominante.indexOf(id);
			int eliminado = dominante.remove(pos);
			
			boolean adyacente = false;

			for(int j = 0; j<dominante.size(); j++)
				if(g.esAdyacente(eliminado, dominante.get(j))){
					adyacente = true;
					break;
				}

			if(!adyacente)
				dominante.add(eliminado);

			for(int w : eliminados){
				adyacente = false;
				for(int v : dominante)
					if(g.esAdyacente(w, v)){
						adyacente = true;
						break;
					}

				if(!adyacente){
					dominante.add(eliminado);
					break;
				}

			}

			if(adyacente)
				eliminados.add(eliminado);
		}

		return dominante;

	}

	/**
	* Regresa una lista de vértices con sus grados.
	* @return la lista de vértices con su grados.
	*/
	public ArrayList<Pair<Integer, Integer>> generaGrados(){
		ArrayList<Pair<Integer, Integer>> vertices = new ArrayList<>();
		for(int i = 0; i<g.longitud(); i++){
			Pair<Integer, Integer> nuevo = new Pair<>(i, g.daGrado(i));
			vertices.add(nuevo);
		}

		return vertices;
	}

	/**
	* Merge sort modificado para ordenar el valor de pares con su llave.
	* @param lista la lista de pares a ordenar.
	* @return la lista de pares ordenada.
	*/
	private ArrayList<Pair<Integer, Integer>> mergeSortModificado(ArrayList<Pair<Integer, Integer>> lista){
		if(lista.size()==1)
			return lista;
		int mitad = lista.size()/2;
		ArrayList<Pair<Integer, Integer>> l1 = sublista(0, mitad, lista);
		ArrayList<Pair<Integer, Integer>> l2 = sublista(mitad, lista.size(), lista);

		l1 = mergeSortModificado(l1);
		l2 = mergeSortModificado(l2);
		return mergeAux(l1, l2);
	}

	/**
	* Corta una lista en un rango de la lista.
	* @param i el inicio de corte.
	* @param f el fin de corte.
	* @param lista la lista a subdividir.
	* @return una sublista desde el índice i al índice f.
	*/
	private ArrayList<Pair<Integer, Integer>> sublista(int i, int f,
		ArrayList<Pair<Integer, Integer>> lista){
		ArrayList<Pair<Integer, Integer>> sublista = new ArrayList<>();
		for(int j = i; j<f ; j++)
			sublista.add(lista.get(j));
		return sublista;
	}

	/**
	* Auxiliar de merge sort modificado para ordenar los elementos.
	* @param l1 la primera lista a ordenar elementos.
	* @param l2 la segunda lista a ordenar elementos.
	* @param una lista ordenada a partir de las listas l1 y l2.
	*/
	private ArrayList<Pair<Integer, Integer>> mergeAux(ArrayList<Pair<Integer, Integer>> l1,
		ArrayList<Pair<Integer, Integer>> l2){

		ArrayList<Pair<Integer, Integer>> ordenada = new ArrayList<>();

		while(!l1.isEmpty() && !l2.isEmpty())
			if(l1.get(0).getValue() < l2.get(0).getValue())
				ordenada.add(l1.remove(0));
			else
				ordenada.add(l2.remove(0));

		ordenada.addAll(l1);
		ordenada.addAll(l2);

		return ordenada;
	}

	/**
	* Verifica si un conjunto es conjunto dominante.
	* @param conjunto una lista de vértices candidatos a ser conjunto
	* dominante.
	* @return true si el conjunto es conjunto dominante, false en otro caso.
	*/
	public boolean esDominante(ArrayList<Integer> conjunto){
		ArrayList<Integer> complemento = new ArrayList<>();

		if(conjunto.isEmpty())
			return false;

		for(int i = 0; i<g.longitud(); i++)
			complemento.add(i);

		complemento.removeAll(conjunto);

		

		for(int i : complemento)
			for(int w : conjunto)
				if(!g.esAdyacente(i, w))
					return false;

		return true;
	}

	/**
	* Imprime los elementos de una lista en forma de conjunto.
	* @param lista la lista a representar elementos.
	* @return una representación en conjunto de los elementos de una lista.
	*/
	public static String muestra(ArrayList<Integer> lista){
		String conjunto = "| ";
   		for(int w : lista)
   			conjunto += w+" | ";
   		return conjunto;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Dominante dominante = null;

		/* Colores */
   		String red = "\033[31m"; 
   		String green = "\033[32m"; 
   		String yellow = "\033[33m"; 
   		String blue = "\033[34m"; 
   		String cyan = "\033[36m"; 
   		String reset = "\u001B[0m";

   		int opcion = Integer.parseInt(args[0]);

   		int vertices = 0;

   		if(opcion == 1){
   			System.out.println(cyan+"\nHas elegido crear una nueva gráfica");

   			System.out.println(reset+"\nIngresa la cantidad de vértices que deseas en la gráfica");
   			vertices = sc.nextInt();

   			Grafica nueva = new Grafica(vertices);

   			System.out.println(reset+"\nIngresa las aristas de la gráfica con el formato: a b"+blue+
   				"\nPor ejemplo, si quiero hacer una arista entre el vértice "+
   				"0 y el vértice 5,\nen la línea pondría: 0 5"+reset+
   				"\nPara terminar la ejecución ingresa: -1 -1\n");

   			int a = 0;
   			int b = 0;
   			while(true){
   				try{
   					a = sc.nextInt();
   					b = sc.nextInt();

   					if(a==-1 && b==-1)
   						break;
   					nueva.insertaArista(a, b);
   				} catch(IllegalArgumentException iae){
   					System.out.println(red + "\nIngresaste un identificador fuera de rango" + reset);
   				} catch(Exception e){
   					System.out.println(red + "\nIngresaste un identificador inválido" + reset);
   					sc.nextLine();
   				}
   			}

   			dominante = new Dominante(nueva);
   		} else if(opcion == 0){
   			System.out.println(cyan+"\nHas elegido usar la gráfica generada aleatoriamente");
   			System.out.println(reset+"\nIngresa la cantidad de vértices que deseas en la gráfica");
   			vertices = sc.nextInt();
   			dominante = new Dominante(vertices);
   		} else
   			return;

   		System.out.println(green+"La gráfica de entrada está representada por la matriz de adyacencia:\n"+reset+dominante.g);

   		System.out.println(reset+"\nIngresa los vértices del conjunto a verificar si es conjunto dominante"+blue+
   				"\nCada uno de los vértices debe ser un identifiador de tipo entero entre 0 y "+(dominante.g.longitud()-1)+
   				"\nPara terminar la ejecución ingresa: -1\n"+reset);

   		ArrayList<Integer> entrada = new ArrayList<>();

   		int vert = -2;

   		while(vert != -1){
   			try{
   				vert = sc.nextInt();
   				if(vert == -1)
   					break;
   				if(vert < 0 || vert>=dominante.g.longitud())
   					continue;
   				entrada.add(vert);
   			} catch(Exception e){
   				System.out.println(red + "\nIngresaste un identificador inválido" + reset);
   				sc.nextLine();
   			}
   		}

   		System.out.println(reset+"¿El conjunto "+green+(muestra(entrada))+reset+" es dominante? "+
   			(dominante.esDominante(entrada)?(green+"Si es dominante!"):(red+"No es dominante :("))+reset);

   		ArrayList<Integer> resultado = dominante.conjuntoDominante();

   		String conjunto = muestra(resultado);

   		System.out.println(green+"\nUn conjunto dominante es: "+conjunto);

	}

}