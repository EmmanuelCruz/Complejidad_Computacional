import java.util.ArrayList;

/**
* Representacion de una grafica basada en listas.
* @author Emmanuel Cruz Hernandez.
* @version 1.0 diciembre 2020.
* @since Complejidad Computacional 2021-1.
*/
public class Grafica{
	
	/** Vertices de la grafica. */
	public ArrayList<Integer> vertices;

	/** Aristas de la grafica. */
	public ArrayList<Arista> aristas;

	/**
	* Crea una nueva grafica sin vertices ni aristas.
	*/
	public Grafica(){
		vertices = new ArrayList<>();
		aristas = new ArrayList<>();
	}

	/**
	* Crea una grafica a partir de un conjunto de aristas.
	* @param aristasNuevas las aristas a agregar a la grafica.
	*/
	public Grafica(ArrayList<Arista> aristasNuevas){
		vertices = new ArrayList<>();
		aristas = new ArrayList<>();
		for(Arista a : aristasNuevas){
			aristas.add(a);
			if(!vertices.contains(a.v1))
				vertices.add(a.v1);
			if(!vertices.contains(a.v2))
				vertices.add(a.v2);
		}

		vertices.sort(null);
	}

	/**
	* Algoritmo de Prim para encontrar un arbol de peso minimo.
	* @return un arbol generador de peso minimo a partir del actual.
	*/
	public Grafica prim(){
		ArrayList<Arista> ordenada = new ArrayList<>(this.aristas);
		ordenada.sort(null);
		ArrayList<Integer> verticesRecorridos = new ArrayList<>();
		ArrayList<Arista> aristasArbol = new ArrayList<>();

		Arista minima = ordenada.remove(0);
		aristasArbol.add(minima);

		verticesRecorridos.add(minima.v1);
		verticesRecorridos.add(minima.v2);

		ordenada.clear();
		ordenada.addAll(vecinos(minima.v1));
		ordenada.addAll(vecinos(minima.v2));
		ordenada.sort(null);

		while(verticesRecorridos.size()<vertices.size()){
			Arista probable = ordenada.remove(0);
			if(!verticesRecorridos.contains(probable.v1) || !verticesRecorridos.contains(probable.v2)){
				aristasArbol.add(probable);
				ArrayList<Arista> vecinosActual = vecinos(probable.v1);
				vecinosActual.addAll(vecinos(probable.v2));
				for(Arista a : vecinosActual)
					if(!ordenada.contains(a))
						ordenada.add(a);
				ordenada.sort(null);
				if(!verticesRecorridos.contains(probable.v1))
					verticesRecorridos.add(probable.v1);
				if(!verticesRecorridos.contains(probable.v2))
					verticesRecorridos.add(probable.v2);
			}
		}

		return new Grafica(aristasArbol);

	}

	/**
	* Encuentra un apareamiento minimo.
	* @param grafica la grafica de la cual se genera un arbol.
	* @param arbol la grafica con el arbol generador.
	* @return la lista con aristas de apareamiento minimo.
	*/
	public ArrayList<Arista> apareamiento(Grafica grafica, Grafica arbol){
		ArrayList<Integer> impares = arbol.impares();
		ArrayList<Arista> vecinos = grafica.vecinos(impares);
		ArrayList<Arista> apareamiento = new ArrayList<>();
		ArrayList<Integer> recorridos = new ArrayList<>();

		for(Arista a : vecinos){
			if(!recorridos.contains(a.v1) && !recorridos.contains(a.v2) && 
				arbol.grado(a.v1)%2!=0 && arbol.grado(a.v2)%2!=0){
				apareamiento.add(a);
				recorridos.add(a.v1);
				recorridos.add(a.v2);
			}
		}
		return apareamiento;
	}

	/**
	* Regresa todos los vecinos de un conjunto de vertices.
	* @param vertice el vertice a encontrar vecinos.
	* @return una lista con todos los vecinos de un vertice.
	*/
	public ArrayList<Arista> vecinos(int vertice){
		ArrayList<Arista> vecinos = new ArrayList<>();
		for(Arista a : aristas)
			if(a.contiene(vertice))
				vecinos.add(a);
		return vecinos;
	}

	/**
	* Encuentra todo el conjunto de vecinos de una lista de vertices.
	* @param vertices el conjunto de vertices a buscar.
	* @return una lista de aristas vecinas del conjunto de vertices.
	*/
	public ArrayList<Arista> vecinos(ArrayList<Integer> vertices){
		ArrayList<Arista> vecinos = new ArrayList<>();
		for(int vertice : vertices){
			ArrayList<Arista> vecinosActuales = vecinos(vertice);
			for(Arista a : vecinosActuales)
				if(!vecinos.contains(a))
					vecinos.add(a);
		}
		vecinos.sort(null);
		return vecinos;
	}

	/**
	* Da el grado de un vertice de la grafica.
	* @param v el vertice a encontrar el grado.
	* @return el grado del vertice v.
	*/
	public int grado(int v){
		int grado = 0;
		for(Arista a : aristas)
			if(a.contiene(v))
				grado++;
		return grado;
	}

	/**
	* Obtiene una lista de los vertices de grado impar.
	* @return una lista con los vertices de grado impar.
	*/
	public ArrayList<Integer> impares(){
		ArrayList<Integer> impares = new ArrayList<>();

		for(int vertice : vertices)
			if(grado(vertice)%2 != 0)
				impares.add(vertice);

		return impares;
	}

	/**
	* Regresa una grafica creada a partir de dos listas de aristas.
	* @param aristas1 la primera lista de aristas.
	* @param aristas2 la segunda lista de aristas.
	* @return una grafica con las aristas que pasan como parametro.
	*/
	public Grafica creaGrafica(ArrayList<Arista> aristas1, ArrayList<Arista> aristas2){
		ArrayList<Arista> resultante = new ArrayList<>(aristas1);
		for(Arista a : aristas2)
			if(!resultante.contains(a))
				resultante.add(a);
		return new Grafica(resultante);
	}

	/**
	* Realiza el corte de vertices para encontrar un camino sin ciclos.
	* @return un camino sin ciclos que representa una solucion para TSP.
	*/
	public ArrayList<Integer> shortcut(){
		ArrayList<Arista> aristasActuales = new ArrayList<>(aristas);
		ArrayList<Integer> verticesCamino = new ArrayList<>();

		//this.muestraAristas();

		Arista actual = aristasActuales.remove(0);
		verticesCamino.add(actual.v1);

		int idActual = actual.v2;

		Arista recorrido = null;
		while(!aristasActuales.isEmpty()){
			for(int j = 0; j<aristasActuales.size(); j++){
				recorrido = aristasActuales.get(j);
				if(recorrido.contiene(idActual)){
					verticesCamino.add(idActual);
					idActual = recorrido.daAdyacente(idActual);
					aristasActuales.remove(j);
				}
			}
		}

		ArrayList<Integer> caminoFinal = new ArrayList<>();

		for(int elemento : verticesCamino)
			if(!caminoFinal.contains(elemento))
				caminoFinal.add(elemento);

		return caminoFinal;
	}

	/**
	* Muestra las aristas de la grafica.
	*/
	public void muestraAristas(){
		for(Arista a : aristas){
			System.out.println(a.v1+" - "+a.v2);
		}
	}

}