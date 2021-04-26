package fciencias.complejidad.programa1;

import java.lang.IllegalArgumentException;
import java.util.ArrayList;

/**
* Implementación de una gráfica basada en una matriz de adyacencia.
* @author Emmanuel Cruz Hernández,
* @version 1.0 Octubre 2020.
* @since Complejidad Computacional 2021-1.
*/
public class Grafica{
	
	/** Matriz de adyacencias. */
	private boolean[][] adyacencias;

	/**
	* Crea una nueva gráfica con <i>n</i> vértices.
	* @param n la cantidad de vértices a crear.
	*/
	public Grafica(int n){
		adyacencias = new boolean[n][n];
	}

	/**
	* Regresa la cantidad de vértices en la gráfica.
	* @return la cantidad de vértices.
	*/
	public int longitud(){
		return adyacencias.length;
	}

	/**
	* Pemite agregar una nueva arista a la gráfica.
	* @param v1 el identificador del primer vértice a crear adyacencia.
	* @param v2 el identificador del segundo vértice a crear adyacencia.
	* @throws IllegalArgumentException si alguno de los identificadores son inválidos.
	*/
	public void insertaArista(int v1, int v2) throws IllegalArgumentException{
		if(v1<0 || v1>=adyacencias.length)
			throw new IllegalArgumentException("El primer vérice no es válido");
		if(v2<0 || v2>=adyacencias.length)
			throw new IllegalArgumentException("El segundo vértice no es válido");

		if(v1 == v2)
			return;

		adyacencias[v1][v2] = true;
		adyacencias[v2][v1] = true;
	}

	/**
	* Verifica si existe adyacencia entre dos vértices.
	* @param v1 el primer vértice a verificar adyacencia.
	* @param v2 el segundo vértice a verificar adyacencia.
	* @return true si existe adyacencia, false en otro caso.
	* @throws IllegalArgumentException si alguno de los identificadores son inválidos.
	*/
	public boolean esAdyacente(int v1, int v2) throws IllegalArgumentException{
		if(v1<0 || v1>=adyacencias.length)
			throw new IllegalArgumentException("El primer vérice no es válido");
		if(v2<0 || v2>=adyacencias.length)
			throw new IllegalArgumentException("El segundo vértice no es válido");

		return adyacencias[v1][v2];
	}

	/**
	* Obtiene el grado de un vértice.
	* @param v el vértice a obtener el grado.
	* @return el grado del vértice <i>v</i>.
	* @throws IllegalArgumentException si el identificador es inválido.
	*/
	public int daGrado(int v){
		if(v<0 || v>=adyacencias.length)
			throw new IllegalArgumentException("El vérice no es válido");
		int grado = 0;
		for(boolean vecino : adyacencias[v])
			if(vecino)
				grado++;
		return grado;
	}

	/**
	* Regresa los vecinos de un vértice.
	* @param v el vértice a regresar vecinos.
	* @return una lista con los vecinos del vétice v.
	* @throws IllegalArgumentException si el identificador es inválido.
	*/
	public ArrayList<Integer> daVecinos(int v){
		if(v<0 || v>=adyacencias.length)
			throw new IllegalArgumentException("El vérice no es válido");

		ArrayList<Integer> vecinos = new ArrayList<>();
		for(int i = 0; i<adyacencias.length ; i++)
			if(adyacencias[v][i])
				vecinos.add(i);

		return vecinos;
	}

	@Override
	public String toString(){ 
   		String yellow = "\033[33m"; 
   		String blue = "\033[34m";
   		String reset = "\u001B[0m";

		String resultado = "|   |";
		for(int i = 0; i<adyacencias.length; i++)
			resultado += " "+blue+i+reset+" |";

		for (int i = 0; i<adyacencias.length ; i++ ){
			resultado += "\n| "+blue+i+reset+" |";
			for (int j = 0; j<adyacencias.length ; j++ )
				resultado += " "+(adyacencias[i][j]?yellow+"1"+reset:"0")+" |";
		}

		return resultado;
	}
}