import java.lang.IllegalArgumentException;
import java.util.ArrayList;

/**
* Implementacion de una grafica basada en una matriz de adyacencia.
* @author Emmanuel Cruz Hernandez,
* @version 1.0 Octubre 2020.
* @since Complejidad Computacional 2021-1.
*/
public class Grafica{
	
	/** Matriz de adyacencias. */
	private boolean[][] adyacencias;

	/**
	* Crea una nueva grafica con <i>n</i> vertices.
	* @param n la cantidad de vertices a crear.
	*/
	public Grafica(int n){
		adyacencias = new boolean[n][n];
	}

	/**
	* Regresa la cantidad de vertices en la grafica.
	* @return la cantidad de vertices.
	*/
	public int longitud(){
		return adyacencias.length;
	}

	/**
	* Pemite agregar una nueva arista a la grafica.
	* @param v1 el identificador del primer vertice a crear adyacencia.
	* @param v2 el identificador del segundo vertice a crear adyacencia.
	* @throws IllegalArgumentException si alguno de los identificadores son invalidos.
	*/
	public void insertaArista(int v1, int v2) throws IllegalArgumentException{
		if(v1<0 || v1>=adyacencias.length)
			throw new IllegalArgumentException("El primer verice no es valido");
		if(v2<0 || v2>=adyacencias.length)
			throw new IllegalArgumentException("El segundo vertice no es valido");

		if(v1 == v2)
			return;

		adyacencias[v1][v2] = true;
		adyacencias[v2][v1] = true;
	}

	/**
	* Verifica si existe adyacencia entre dos vertices.
	* @param v1 el primer vertice a verificar adyacencia.
	* @param v2 el segundo vertice a verificar adyacencia.
	* @return true si existe adyacencia, false en otro caso.
	* @throws IllegalArgumentException si alguno de los identificadores son invalidos.
	*/
	public boolean esAdyacente(int v1, int v2) throws IllegalArgumentException{
		if(v1<0 || v1>=adyacencias.length)
			throw new IllegalArgumentException("El primer verice no es valido");
		if(v2<0 || v2>=adyacencias.length)
			throw new IllegalArgumentException("El segundo vertice no es valido");

		return adyacencias[v1][v2];
	}

	/**
	* Obtiene el grado de un vertice.
	* @param v el vertice a obtener el grado.
	* @return el grado del vertice <i>v</i>.
	* @throws IllegalArgumentException si el identificador es invalido.
	*/
	public int daGrado(int v){
		if(v<0 || v>=adyacencias.length)
			throw new IllegalArgumentException("El verice no es valido");
		int grado = 0;
		for(boolean vecino : adyacencias[v])
			if(vecino)
				grado++;
		return grado;
	}

	/**
	* Regresa los vecinos de un vertice.
	* @param v el vertice a regresar vecinos.
	* @return una lista con los vecinos del vetice v.
	* @throws IllegalArgumentException si el identificador es invalido.
	*/
	public ArrayList<Integer> daVecinos(int v){
		if(v<0 || v>=adyacencias.length)
			throw new IllegalArgumentException("El vertice no es valido");

		ArrayList<Integer> vecinos = new ArrayList<>();
		for(int i = 0; i<adyacencias.length ; i++)
			if(adyacencias[v][i])
				vecinos.add(i);

		return vecinos;
	}

	@Override
	public String toString(){ 

		String resultado = "|   |";
		for(int i = 0; i<adyacencias.length; i++)
			resultado += " "+i+" |";

		for (int i = 0; i<adyacencias.length ; i++ ){
			resultado += "\n| "+i+" |";
			for (int j = 0; j<adyacencias.length ; j++ )
				resultado += " "+(adyacencias[i][j]?"1":"0")+" |";
		}

		return resultado;
	}
}