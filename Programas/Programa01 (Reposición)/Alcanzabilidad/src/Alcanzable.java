import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.Random;

/**
* Se implementa el problema de decision <i>Alcanzabilidad</i>.
* @author Emmanuel Cruz Hernandez.
* @version 2.0 Enero 2021 (Antigua version 1.0 Octubre 2020).
* @since Complejidad Computacional 2021-1.
*/
public class Alcanzable{
	
	/** Grafica a buscar alcanzabilidad entre <i>s</i> y <i>t</i>. */
	private Grafica g;

	/**
	* Crea un nuevo Alcanzable.
	* @param n la cantidad de vertices.
	*/
	public Alcanzable(){
		
	}

	/**
	* Crea una grafica de forma aleatoria.
	* @param n la cantidad de vertices de la grafica.
	*/
	public void creaGrafica(int n){
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
	* BFS - Breadth-First Search. Busqueda en anchura sobre una
	* grafica implementada con una matriz de adyacencias.
	* @param v el vertice de inicio.
	* @return la lista de todos los vertices alcanzados desde
	* un vertice inicial.
	* @throws IllegalArgumentException si el identificador es invalido.
	*/
	public ArrayList<Integer> applyBFS(int v){
		if(v<0 || v>=g.longitud())
			throw new IllegalArgumentException("El verice no es valido");

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
	* Verifica si un vertice <i>t</i> es alcanzable desde
	* un vertice <i>s</i>.
	* @param s el vertice inicial a buscar alcanzabilidad.
	* @param t el vertice a verificar alcanzabilidad desde <i>i</i>.
	* @return true si los vertices son alcanzables, false en otro caso.
	* @throws IllegalArgumentException si alguno de los identificadores son invalidos.
	*/
	public boolean esAlcanzable(int s, int t) throws IllegalArgumentException{
		if(s<0 || s>=g.longitud())
			throw new IllegalArgumentException("El verice s no es valido");
		if(t<0 || t>=g.longitud())
			throw new IllegalArgumentException("El segundo vertice no es valido");

		ArrayList<Integer> alcanzados = this.applyBFS(s);

		return alcanzados.contains(t);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Alcanzable alcanzable = new Alcanzable();

   		int s = 0;
   		int t = 0;

   		System.out.println("\nIngresa la cantidad de vertices que deseas en la grafica");
   		int vertices = sc.nextInt();

   		alcanzable.creaGrafica(vertices);

   		System.out.println("La grafica generada aleatoriamente por la fase adivinadora es:\n"+alcanzable.g);

   		while(true){

   			System.out.println("\nPara salir ingresa como indentificadores: -1 -1");
   			System.out.println("\nIngresa el identificador del vertice s ([0, "+(alcanzable.g.longitud()-1)+"])");

   			try{
   				s = sc.nextInt();
   			} catch(Exception e){
   				System.out.println("Ingresaste un identificador invalido");
   				sc.nextLine();
   				continue;
   			}

   			System.out.println("\nIngresa el identificador del vertice t ([0, "+(alcanzable.g.longitud()-1)+"])");

   			try{
   				t = sc.nextInt();
   			} catch(Exception e){
   				System.out.println("Ingresaste un identificador invalido");
   				sc.nextLine();
   				continue;
   			}

   			if(s==-1 && t==-1){
   				System.out.println("\nSALIENDO . . .");
   				return;
   			}

   			try{
   				System.out.println("\n¿Es alcanzable el vertice "+t+" desde "+s+"?");

   				boolean esAlcanzable = alcanzable.esAlcanzable(s, t);
   				System.out.println("\n"+(esAlcanzable?"SI ES ALCANZABLE!":
   					"NO ES ALCANZABLE :(")+"\n");

   			} catch(IllegalArgumentException iae){
   				System.out.println("Ingresaste un identificador fuera de rango");
   			}
   		}

	}
}