/**
* Representacion de una arista.
* @author Emmanuel Cruz Hernandez.
* @version 1.0 Diciembre 2020.
* @since Complejidad Computacional.
*/
public class Arista implements Comparable<Arista>{
	
	/** Identificador del primer vertice adyacente. */
	public int v1;

	/** Identificador del segundo vertice adyacente. */
	public int v2;

	/** Peso de la arista. */
	public int peso;

	/**
	* Crea una nueva arista.
	* @param v1 el identificador del primer vertice.
	* @param v2 el identificador del segundo vertice.
	* @param peso el peso de la arista.
	*/
	public Arista(int v1, int v2, int peso){
		this.v1 = v1;
		this.v2 = v2;
		this.peso = peso;
	}

	/**
	* Verifica si un vertice esta en la arista.
	* @param v el vertice a verificar aparicion.
	* @return true si el vertice forma parte de la arista,
	* false en otro caso.
	*/
	public boolean contiene(int v){
		return v==v1 || v==v2;
	}

	/**
	* Dado un vertice, regresa el otro vertice.
	* @param v el vertice a regresar contrario.
	* @return el identificador del vertice contrario a v.
	*/
	public int daAdyacente(int v){
		return v1==v ? v2 : v1;
	}

	@Override
	public int compareTo(Arista o){
		return this.peso - o.peso;
	}
}