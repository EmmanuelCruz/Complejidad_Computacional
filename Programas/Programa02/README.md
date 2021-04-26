# Algoritmos de apoximación. TSP-Metric y Subset Sum.
## Emmanuel Cruz Hernández.

### Subset Sum

Para compilar y ejecutar la implementación del algoritmo se utilizan los siguientes comandos dentro del directorio llamado SubsetSum.

1. javac SubsetSum.java

Para compilar el código.

2. java SubsetSum

Para ejecutar el código.

Se solicita que el usuario ingrese los valores con los cuales va a trabajar el algoritmo. Primero se solicita la cantidad de pesos a considerar, después los pesos, seguido de un entero t que define el límite de pesos y finalmente un número de punto flotante entre 0 y 1. Se muestran tres listas en cada iteración, la primera corresponde a merge, la segunda corresponde a trim y la última corresponde a la lista sin los elementos mayores a t.

### TSP-Metric.

Para poder compilar y ejecutar la implementación se utilizan los siguientes comandos en una terminal dentro del directorio llamado TSP:

1. javac Arista.java Grafica.java TSPMetric.java

Para compilar las clases.

2. java TSPMetric

Para correr el programa. 

NOTA: Se anexa un PDF con el proceso que sigue el programa para encontrar la solución con una gráfica ya definida en el programa. Las aristas se encuentran definidas en el método construyeEjemplar() en la clase TSPMetric.