import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
* Implementacion para calcular un conjunto dominante de una grafica.
* @author Emmanuel Cruz Hernandez,
* @version 1.0 Octubre 2020.
* @since Complejidad Computacional 2021-1.
*/
public class Dominante{

	/** Grafica a buscar alcanzabilidad entre <i>s</i> y <i>t</i>. */
	private Grafica g;
	
	/**
	* Crea un nuevo dominante.
	* @param n la cantidad de vertices.
	*/
	public Dominante(int n){
		Random rn = new Random();
		g = new Grafica(n);

		for(int i = 0; i<n; i++)
			for(int j = i+1 ; j<n ; j++){
				boolean moneda = rn.nextBoolean();
				if(moneda)
					g.insertaArista(i, j);
			}
	}

	/**
	* Crea un subconjunto V' de V.
	* @return un subconjunto de vertices de V.
	*/
	public ArrayList<Integer> generaSubconjuntoV(){
		ArrayList<Integer> subconjunto = new ArrayList<>();

		Random rn = new Random();

		for (int i = 0; i<g.longitud() ; i++ ) {
			boolean moneda = rn.nextBoolean();
			if(moneda)
				subconjunto.add(i);
		}

		return subconjunto;
	}

	/**
	* Verifica si un conjunto es conjunto dominante.
	* @param conjunto una lista de vertices candidatos a ser conjunto
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

		for(int i = 0; i<complemento.size() ; i++){
			boolean adyacente = false;
			for(int j = 0; j<conjunto.size() ; j++)
				if(g.esAdyacente(complemento.get(i), conjunto.get(j))){
					adyacente = true;
					break;
				}

			if(!adyacente)
				return false;
		}

		return true;
	}

	/**
	* Imprime los elementos de una lista en forma de conjunto.
	* @param lista la lista a representar elementos.
	* @return una representacion en conjunto de los elementos de una lista.
	*/
	public static String muestra(ArrayList<Integer> lista){
		String conjunto = "| ";
   		for(int w : lista)
   			conjunto += w+" | ";
   		return conjunto;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

   		System.out.println("\nIngresa la cantidad de vertices que deseas en la grafica");
   		int vertices = sc.nextInt();
   		Dominante dominante = new Dominante(vertices);

   		System.out.println("La grafica de entrada esta representada por la matriz de adyacencia:\n"+dominante.g);

   		ArrayList<Integer> entrada = dominante.generaSubconjuntoV();

   		System.out.println("\nEl subconjunto propuesto por la fase adivinadora es: "+muestra(entrada));

   		System.out.println("¿El conjunto "+(muestra(entrada))+" es dominante? "+
   			(dominante.esDominante(entrada)?("Si es dominante!"):("No es dominante :(")));

	}

}