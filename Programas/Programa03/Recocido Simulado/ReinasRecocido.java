import java.util.Random;
import java.util.Scanner;

/**
 * Clase en la que se implementa el uso de recodido simulado
 * para resolver el problema de las n reinas.
 * @author Emmanuel Cruz Hernández. 314272588.
 * @version 0.1 Enero 2021.
 */
public class ReinasRecocido{

    /**
     * Clase auxiliar que representa un Reina de reinas.
     */
    public class Reina{

	/** Representación de un Reina de nxn. */
	public int[] tablero;

	/** Costo del tablero. */
	public double costo;

	private Random rnd=new Random();

	/** Tabla de colisiones. */
	public boolean[] colisiones;

	/**
	 * Crea un nuevo Reina.
	 * @param n el tamaño del Reina para que se represente de nxn.
	 */
	public Reina(int n){
	    tablero=new int[n];
	    for(int i=0;i<n;i++){
	    	tablero[i]=rnd.nextInt(n);
	    }
	    colisiones = new boolean[n];
	    this.calculacosto();
	}

	/**
	* Crea un Reina a partir de un arreglo ya dado.
	* @param tab el nuevo tablero a asignar al objeto.
	*/
	public Reina(int[] tab){
		tablero=tab;
		colisiones = new boolean[tab.length];
		this.calculacosto();
	}

	/**
	 * Calcula la costo del Reina.
	 * @return la cantidad de ataques entre reinas en el Reina.
	 * NOTA: Si una reina r1 ya atacó a la reina r2 y se contabilizó entonces el ataque entre r2 y r1 no se cuenta.
	 */
	public double calculacosto(){
	    int contador=0;
	    int diagonal1=0;
	    int diagonal2=0;
	    int recta=0;
	    // Se reorre el individuo para detectar la cantidad de reinas que se comen entre sí.
	    for(int i=0;i<tablero.length;i++){
			diagonal1=0;
			diagonal2=0;
			// Por cada entrada del individuo cuenta las veces que se comen una reina.
			for(int j=i;j<tablero.length;j++){
				// Caso para las reinas en el mismo renglón.
		    	if(tablero[i]==tablero[j]){
		    		colisiones[i] = true;
		    		colisiones[j] = true;
		    		contador++;
		    	}
		    	// Caso para las reinas en la misma diaonal \
		    	if(tablero[i]+diagonal2 == tablero[j]){
		    		colisiones[i] = true;
		    		colisiones[j] = true;
		    		contador++;
		    	}
		    	diagonal2++;
		    	// Caso para las reinas en la misma diaonal /
		    	if(tablero[i]+diagonal1 == tablero[j]){
		    		colisiones[i] = true;
		    		colisiones[j] = true;
		    		contador++;
		    	}
		    	diagonal1--;
			}
			contador=contador-3;
	    }
	    // Actualizamos la costo.
	    costo=contador;
	    return contador;
	}

	@Override
	public String toString(){
		String representacion="[";
		for(int i=0;i<tablero.length;i++)
			representacion+=tablero[i]+" ";
		representacion+="]\n\n";


		for(int j=0;j<tablero.length;j++){
			for(int k=0;k<tablero.length;k++){
				if(tablero[j]==k)
					representacion+="R ";
				else
					representacion+="* ";
			}
			representacion+="\n";
		}
		representacion+="costo = "+costo+".\n";
		return representacion;
		}

	}

	/**
	* Implementación de recocido simulado para encontrar la mejor solución
	* de las n reinas.
	* @param tam la longitud del tablero de reinas.
	*/
	public Reina recocidoSimulado(int tam){
		double t = 10;
		double n = 1;
		Random rn = new Random();

		Reina ri = new Reina(tam);

		while(ri.costo > 0 && t > 0){
			int[] rjTab = new int[tam];
			for(int i = 0; i < rjTab.length; i++){
				if(ri.colisiones[i]){ // Se le da mas probabilidad a las colisiones
					if(rn.nextDouble() < 0.8 )
						rjTab[i] = rn.nextInt(tam);
				} else if( rn.nextDouble() < 0.4){
					rjTab[i] = rn.nextInt(tam);
				} else{
					rjTab[i] = ri.tablero[i];
				}
			}

			Reina rj = new Reina(rjTab);

			if(rj.costo <= ri.costo)
				ri = rj;
			else
				if(Math.exp((ri.costo - rj.costo) / t) > rn.nextDouble())
					ri = rj;

			t *= 0.80		;
			n *= 2;
		}

		return ri;	
	}

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);

		System.out.println("Ingresa la cantidad de reinas a encontrar solución");
		int cantidadReinas = sc.nextInt();
		
		ReinasRecocido tn = new ReinasRecocido();
/*
		System.out.println("Ingresa la cantidad de iteeraciones para crear generaciones");
		int iteraciones = sc.nextInt();
*/
		Reina solucionFinal=tn.recocidoSimulado(cantidadReinas);
		System.out.println("MEJOR SOLUCIÓN ENCONTRADA\n"+solucionFinal);

    }

}
