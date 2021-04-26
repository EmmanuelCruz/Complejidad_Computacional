package fciencias.complejidad.programa1;

import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.Random;

/**
* Se implementa el problema de decisión <i>Alcanzabilidad</i>.
* @author Emmanuel Cruz Hernández.
* @version 1.0 Octubre 2020.
* @since Complejidad Computacional 2021-1.
*/
public class Alcanzable{
	
	/** Grafica a buscar alcanzabilidad entre <i>s</i> y <i>t</i>. */
	private Grafica g;

	/**
	* Crea un nuevo Alcanzable.
	* @param n la cantidad de vértices.
	*/
	public Alcanzable(int n){
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
	* Crea un nuevo alcanzable con una gráfica específica.
	* @param g la gráfica donde buscar alcanzabilidad.
	*/
	public Alcanzable(Grafica g){
		this.g = g;
	}

	/**
	* BFS - Breadth-First Search. Búsqueda en anchura sobre una
	* gráfica implementada con una matriz de adyacencias.
	* @param v el vértice de inicio.
	* @return la lista de todos los vértices alcanzados desde
	* un vértice inicial.
	* @throws IllegalArgumentException si el identificador es inválido.
	*/
	public ArrayList<Integer> applyBFS(int v){
		if(v<0 || v>=g.longitud())
			throw new IllegalArgumentException("El vérice no es válido");

		ArrayDeque<Integer> pendientes = new ArrayDeque<>();
		ArrayList<Integer> visitados = new ArrayList<>();

		visitados.add(v);
		pendientes.add(v);

		while(!pendientes.isEmpty()){
			int u = pendientes.remove();
			ArrayList<Integer> vecinosU = g.daVecinos(u);

			for(int w : vecinosU)
				if(!visitados.contains(w)){
					visitados.add(w);
					pendientes.add(w);
				}
		}

		return visitados;
	}

	/**
	* Verifica si un vértice <i>t</i> es alcanzable desde
	* un vértice <i>s</i>.
	* @param s el vértice inicial a buscar alcanzabilidad.
	* @param t el vértice a verificar alcanzabilidad desde <i>i</i>.
	* @return true si los vértices son alcanzables, false en otro caso.
	* @throws IllegalArgumentException si alguno de los identificadores son inválidos.
	*/
	public boolean esAlcanzable(int s, int t) throws IllegalArgumentException{
		if(s<0 || s>=g.longitud())
			throw new IllegalArgumentException("El vérice s no es válido");
		if(t<0 || t>=g.longitud())
			throw new IllegalArgumentException("El segundo vértice no es válido");

		ArrayList<Integer> alcanzados = this.applyBFS(s);

		return alcanzados.contains(t);
	}

	/**
	* Encuentra un camino entre dos vértices.
	* @param v1 el vértice de inicio.
	* @param v2 el vértice fin.
	* @return un camino entre ambos vértices.
	*/
	public ArrayList<Integer> encuentraCamino(int v1, int v2){
		ArrayList<Integer> camino = new ArrayList<>();
		camino.add(v1);

		ArrayList<Integer> visitados = new ArrayList<>();
		visitados.add(v1);
		return encuentraCamino(v2, camino, visitados);
	}

	/**
	* Encuentra un camino al vértice v
	* @param vértice a buscar.
	*/
	private ArrayList<Integer> encuentraCamino(int v, ArrayList<Integer> camino,
		ArrayList<Integer> visitados){
		int ultimo = camino.get(camino.size()-1);
		if(ultimo == v)
			return camino;

		ArrayList<Integer> vecinos = g.daVecinos(ultimo);
		vecinos.remove(visitados);

		if(vecinos.isEmpty()){
			camino.remove(ultimo);
			return camino;
		}

		for(int w : vecinos){
			ArrayList<Integer> caminoAux = new ArrayList<>();
			caminoAux.addAll(0, camino);
			caminoAux.add(w);
			ArrayList<Integer> visitadosAux = new ArrayList<>();
			visitadosAux.addAll(0, visitados);
			if(!visitadosAux.contains(w)){
				visitadosAux.add(w);
				ArrayList<Integer> posible = encuentraCamino(v, caminoAux, visitadosAux);
				if(posible.get(posible.size()-1) == v)
					return posible;
			}
			
		}

		return new ArrayList<Integer>();
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Alcanzable alcanzable = null;

		/* Colores */
   		String red = "\033[31m"; 
   		String green = "\033[32m"; 
   		String yellow = "\033[33m"; 
   		String blue = "\033[34m"; 
   		String cyan = "\033[36m"; 
   		String reset = "\u001B[0m";

   		int s = 0;
   		int t = 0;
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

   			alcanzable = new Alcanzable(nueva);
   		} else if(opcion == 0){
   			System.out.println(cyan+"\nHas elegido usar la gráfica generada aleatoriamente");
   			System.out.println(reset+"\nIngresa la cantidad de vértices que deseas en la gráfica");
   			vertices = sc.nextInt();
   			alcanzable = new Alcanzable(vertices);
   		} else
   			return;

   		System.out.println(green+"La gráfica de entrada está representada por la matriz de adyacencia:\n"+reset+alcanzable.g);

   		while(true){

   			System.out.println(reset+"\nPara salir ingresa como indentificadores: -1 -1");
   			System.out.println(reset+"\nIngresa el identificador del vértice "+yellow+
   				"s"+reset+" ([0, "+(alcanzable.g.longitud()-1)+"])");

   			try{
   				s = sc.nextInt();
   			} catch(Exception e){
   				System.out.println(red + "Ingresaste un identificador inválido");
   				sc.nextLine();
   				continue;
   			}

   			System.out.println(reset+
   				"\nIngresa el identificador del vértice "+yellow+
   				"t"+reset+" ([0, "+(alcanzable.g.longitud()-1)+"])");
   			try{
   				t = sc.nextInt();
   			} catch(Exception e){
   				System.out.println(red + "Ingresaste un identificador inválido");
   				sc.nextLine();
   				continue;
   			}

   			if(s==-1 && t==-1){
   				System.out.println(yellow + "\nSALIENDO . . .");
   				return;
   			}

   			try{
   				System.out.println(reset+"\n¿Es alcanzable el vértice "+t+" desde "+s+"?");

   				boolean esAlcanzable = alcanzable.esAlcanzable(s, t);
   				System.out.println("\n"+(esAlcanzable?green+"SI ES ALCANZABLE!":
   					red+"NO ES ALCANZABLE :(")+"\n");

   				if (esAlcanzable) {
   					ArrayList<Integer> camino = alcanzable.encuentraCamino(s, t);
   					String caminoCreado = "";
   					for(int ver : camino)
   						caminoCreado = "-> "+ver;
   					System.out.println("Uno de lso caminos posibles es: "+camino);
   				}
   			} catch(IllegalArgumentException iae){
   				System.out.println(red + "Ingresaste un identificador fuera de rango");
   			}
   		}

	}
}