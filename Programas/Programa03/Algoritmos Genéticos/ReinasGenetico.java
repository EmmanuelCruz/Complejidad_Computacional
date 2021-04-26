import java.util.Random;
import java.util.Scanner;

/**
 * Clase en la que se implementa el uso de los algoritmos genéticos
 * para resolver el problema de las n reinas.
 * @author Emmanuel Cruz Hernández. 314272588.
 * @version 0.1 Enero 2021.
 */
public class ReinasGenetico{

    /**
     * Clase auxiliar que representa un Reina de reinas.
     */
    public class Reina{

	/** Representación de un Reina de nxn. */
	public int[] fenotipo;

	/** Aptitud del fenotipo. */
	public int aptitud;

	private Random rnd=new Random();

	/**
	 * Crea un nuevo Reina.
	 * @param n el tamaño del Reina para que se represente de nxn.
	 */
	public Reina(int n){
	    fenotipo=new int[n];
	    for(int i=0;i<n;i++){
	    	fenotipo[i]=rnd.nextInt(n);
	    }
	    this.calculaAptitud();
	}

	/**
	* Crea un Reina a partir de un arreglo ya dado.
	* @param fen el nuevo fenotipo a asignar al objeto.
	*/
	public Reina(int[] fen){
		fenotipo=fen;
		this.calculaAptitud();
	}

	/**
	 * Calcula la aptitud del Reina.
	 * @return la cantidad de ataques entre reinas en el Reina.
	 * NOTA: Si una reina r1 ya atacó a la reina r2 y se contabilizó entonces el ataque entre r2 y r1 no se cuenta.
	 */
	public int calculaAptitud(){
	    int contador=0;
	    int diagonal1=0;
	    int diagonal2=0;
	    int recta=0;
	    // Se reorre el individuo para detectar la cantidad de reinas que se comen entre sí.
	    for(int i=0;i<fenotipo.length;i++){
			diagonal1=0;
			diagonal2=0;
			// Por cada entrada del individuo cuenta las veces que se comen una reina.
			for(int j=i;j<fenotipo.length;j++){
				// Caso para las reinas en el mismo renglón.
		    	if(fenotipo[i]==fenotipo[j])
		    		contador++;
		    	// Caso para las reinas en la misma diaonal \
		    	if(fenotipo[i]+diagonal2 == fenotipo[j]){
		    		contador++;
		    	}
		    	diagonal2++;
		    	// Caso para las reinas en la misma diaonal /
		    	if(fenotipo[i]+diagonal1 == fenotipo[j]){
		    		contador++;
		    	}
		    	diagonal1--;
			}
			contador=contador-3;
	    }
	    // Actualizamos la aptitud.
	    aptitud=contador;
	    return contador;
	}

	/**
	* Función de recombinación.
	* @param tab el Reina con el cuál aplicar la recombinación.
	* @return un Reina resultante de la operación de recombinación.
	*/
	public Reina recombinacion(Reina tab){
		// Se define un corte random.
		int division=rnd.nextInt(fenotipo.length);
		int[] nuevoFen=new int[fenotipo.length];
		for(int i=0;i<nuevoFen.length;i++){
			if(i<=division)
				nuevoFen[i]=fenotipo[i];
			else
				nuevoFen[i]=tab.fenotipo[i];
		}
		return new Reina(nuevoFen);
	}

	/**
	* Función de mutación.
	*/
	public void mutacion(){
		// Si se cumple la probabilidad de 0.2 se realiza un canbio en el valor de cada entrada.
		for(int i=0; i<fenotipo.length;i++)
			if(rnd.nextDouble()<0.2)
				fenotipo[i]=rnd.nextInt(fenotipo.length);
		this.calculaAptitud();	
	}

	@Override
	public String toString(){
		String representacion="[";
		for(int i=0;i<fenotipo.length;i++)
			representacion+=fenotipo[i]+" ";
		representacion+="]\n\n";


		for(int j=0;j<fenotipo.length;j++){
			for(int k=0;k<fenotipo.length;k++){
				if(fenotipo[j]==k)
					representacion+="R ";
				else
					representacion+="* ";
			}
			representacion+="\n";
		}
		representacion+="Aptitud = "+aptitud+".\n";
		return representacion;
		}

	}

	/** Población de individuos (Reina). */
	public Reina[] poblacion=new Reina[50];

	/**
	* Crea un nuevo objeto ReinasGenetico.
	* @param n el tamaño del Reina en el cuál acomodar las reinas.
	*/
	public ReinasGenetico(int n){
		for(int i=0;i<poblacion.length;i++)
			poblacion[i]=new Reina(n);
	}

	/**
	* Calcula la suma total de aptitudes de cada elemento de la población.
	* @return el total de las sumas de aptitudes.
	*/
	public double totalFitness(){
		double total=0;
		for(int i=0;i<poblacion.length;i++)
			total+=poblacion[i].aptitud;
		return total;
	}

	/**
	* Selecciona un Reina (individuo) de la población a partir de cierta probabilidad.
	* @return un Reina seleccionado de la población.
	*/
	public Reina selecciona(){
		Random rnd=new Random();
		Reina selecionado=null;
		// Obtenemos la suma de todos las aptitudes de la población.
		double total=this.totalFitness();
		int recorredor=0;
		// Mientras no se haya seleccionado algún elemento de la población se busca alguno.
		while(selecionado==null){
			if(poblacion[recorredor%poblacion.length].aptitud/total > rnd.nextDouble())
				selecionado=poblacion[recorredor%poblacion.length];
			recorredor++;
		}
		return selecionado;
	}

	/**
	* Regresa la primera mejor solución encontrada entre los individuos de la población.
	* @return el Reina con la aptitud de mejor solución.
	*/
	public Reina daMejorSolucion(){
		int mejor=0;
		for(int i=0;i<poblacion.length;i++)
			// Si la aptitud del elemento donde está el iterador es menor a la mejor encontrada hasta el momento se cambia.
			if(poblacion[i].aptitud < poblacion[mejor].aptitud){
				mejor=i;
				// Si encontramos alguno que ya tiene aptitud 0 entonces lo regresamos.
				if(poblacion[mejor].aptitud==0)
					return poblacion[mejor];
			}
		return poblacion[mejor];
	}

	/**
	* Algoritmo genetico para encontrar la mejor solución al problema de las n reinas.
	* @param n la cantidad de iteraciones para llevar a cabo el algoritmo.
	* @return el Reina con la mejor solución encontrada.
	*/
	public Reina algoritmoGenetico(int n){
		int iterador=0;
		int lleno=0;
		// Realizamos esta operación n veces.
		while(iterador<n){
			// Se crea una nueva población.
			Reina[] nuevaPoblacion=new Reina[poblacion.length];
			// Agregamos la mejor solución que este en la población actual.
			nuevaPoblacion[0]=this.daMejorSolucion();
			lleno=1;
			while(lleno<nuevaPoblacion.length){
				// Creamos 2 nuevos Reina (individuos)
				Reina t1=this.selecciona();
				Reina t2=this.selecciona();
				// De estos creamos uno nuevo a partir de la operación de recombinación.
				Reina hijo=t1.recombinacion(t2);
				// Este nuevo que creamos lo mutamos para cambiar algunos valores si se da el caso.
				hijo.mutacion();
				// Lo agregamos a la nueva población
				nuevaPoblacion[lleno]=hijo;
				lleno++;
			}
			// Atualizmos la población actual por la nueva.
			poblacion=nuevaPoblacion;
			Reina best=this.daMejorSolucion();
			// Si la mejor solución de esta nueva ya tiene aptitud 0 entonces terminamos el algoritmo.
			if(best.aptitud==0){
				System.out.println("ITERACIÓN "+iterador+"\n");
				return best;
			}
			// Cada 50 iteraciones se imprime el resultado hasta el momento.
			if(iterador%50==0)
				System.out.println("ITERACIÓN "+iterador+"\n"+best);
			iterador++;
		}
		// Regresamos la mejor solución después de n iteraciones.
		return this.daMejorSolucion();
	}

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);

		System.out.println("Ingresa la cantidad de reinas a encontrar solución");
		int cantidadReinas = sc.nextInt();
		
		ReinasGenetico tn=new ReinasGenetico(cantidadReinas);

		System.out.println("Ingresa la cantidad de iteeraciones para crear generaciones");
		int iteraciones = sc.nextInt();

		Reina solucionFinal=tn.algoritmoGenetico(iteraciones);
		System.out.println("MEJOR SOLUCIÓN ENCONTRADA\n"+solucionFinal);

    }

}
