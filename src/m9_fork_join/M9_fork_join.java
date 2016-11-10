package m9_fork_join;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Esta es nuestra clase para el ejercicio 1, en la que necesitamos extender de RecursiveTask
 * para que se haga bien el fork Join
 * 
 * Tenemos las variables de array que irá al constructor de la clase
 * Las siguientes que se llaman inici y finale nor serviran para comparar los dos
 * numero finales que quedaran para comparar
 * Tamanio y parabifurcar simplemente eliminan el hardcode
 * @author ALUMNEDAM
 */
public class M9_fork_join extends RecursiveTask<Integer>{


        private final ArrayList<Integer> array;
        private final int inici, finale;
        private static final int tamanio = 20000;
        private final int parabifurcar = 1;

        public M9_fork_join(ArrayList<Integer> arr, int ini, int fin) {
            this.array = arr;
            this.inici = ini;
            this.finale = fin;
        }

  

        @Override
        /**
         * En el compute se hace practicamente todo.
         * El if sirve para operar con las partes mas pequeñas para comparar dos
         * numero, mientras que no se haya seaparado en las partes mas pequeñas
         * se irá dividiendo entre dos con dos forkJoin que pausan los procesos
         * anteriores y se hace el forkJoin otra vez de los numeros divididos
         * y asi hasta que puede entrar en el if y va haciendo los Math.max para
         * devolver el mayor valor de entre los que hay en el array
         * 
         */
        protected Integer compute() {
             if (finale - inici <= parabifurcar) {

                int cositi =Math.max(array.get(inici), array.get(finale));
                
                return cositi;
            }else{
                        //Dividir el problema en parts més petites
                        int mitat = inici + (finale - inici) / 2;
                        M9_fork_join forkJoin1 = new M9_fork_join(array, inici, mitat);
                        M9_fork_join forkJoin2 = new M9_fork_join(array, mitat + 1, finale);
                        invokeAll(forkJoin1, forkJoin2);
                        return Math.max(forkJoin1.join(), forkJoin2.join());
                }
        }
    
        

        /**
         * 
         * Aqui se declara un arrayList de 20000 que crea numeros al azar del 1 
         * al 50000 con un bucle for.
         * 
         * Despues de eso se crean tantos hilos como queramos o podamos utilizar
         * y el forkJoin por el que se le pasan el ArrayList, el numero por el que
         * empieza y el tamaño de este para abarcar todo el array
         * 
         * Se llama al resultado con el pool.invoke y se imprimirá
         * @param args 
         */
        public static void main(String[] args) {

            ArrayList<Integer> sueldos = new ArrayList();
            
            Random randomGenerator = new Random();
            for (int i = 1; i < tamanio; i++) {
                int randomInt = randomGenerator.nextInt(50001);
                sueldos.add(randomInt);
                
            }
            
            ForkJoinPool pool = new ForkJoinPool(3);
            M9_fork_join tasca = new M9_fork_join(sueldos, 0, sueldos.size()-1);
            Integer result = pool.invoke(tasca);
            System.out.println("Resultat és: " + result);

        }

    }
