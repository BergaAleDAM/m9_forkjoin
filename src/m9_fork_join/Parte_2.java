package m9_fork_join;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import static java.util.concurrent.ForkJoinTask.invokeAll;
import java.util.concurrent.RecursiveTask;

public class Parte_2 extends RecursiveTask<Double>{

        private final double[] array;
        private final int inici, finale;

        public Parte_2(double[] arr, int ini, int fin) {
            this.array = arr;
            this.inici = ini;
            this.finale = fin;
        }

        @Override
        protected Double compute() {
            if (finale - inici <= 4) {
                double cositi = 0;
                
                for (int i = inici; i <= finale; i++) {
                    cositi += array[i];
                }
                cositi /= 4;
                
                return cositi;
            } else {
                //Dividir el problema en parts més petites
                int meitat = inici + (finale - inici) / 2;
                
                Parte_2 forkJoin1 = new Parte_2(array, inici, meitat);
                Parte_2 forkJoin2 = new Parte_2(array, meitat+1,  finale);
                
                invokeAll(forkJoin1, forkJoin2);
                
                return Math.min(forkJoin1.join(), forkJoin2.join());
            }
        }

        public static void main(String[] args) {

            double[] temperatures = {
                13.0, 13.2, 13.3, 13.4, //00:00 h.
                13.5, 13.7, 13.8, 13.9, //01:00 h.
                14.1, 14.2, 14.3, 14.4, //02:00 h.
                14.6, 14.7, 14.8, 14.9, //03:00 h.
                15.0, 15.2, 15.3, 15.4, //04:00 h.
                15.5, 15.7, 15.8, 15.9, //05:00 h.
                16.1, 16.2, 16.3, 16.4, //06:00 h.
                16.6, 16.7, 16.8, 16.9, //07:00 h.
                17.0, 17.2, 17.3, 17.4, //08:00 h.
                17.5, 17.7, 17.8, 17.9, //09:00 h.
                18.1, 18.2, 18.3, 18.4, //10:00 h.
                18.6, 18.7, 18.8, 18.9, //11:00 h.
                18.0, 18.2, 18.3, 18.4, //12:00 h.
                18.5, 18.7, 18.8, 18.9, //13:00 h.
                17.1, 17.2, 17.3, 17.4, //14:00 h.
                17.6, 17.7, 17.8, 17.9, //15:00 h.
            };
            
            
            
            ForkJoinPool pool = new ForkJoinPool(4);
            Parte_2 tasca = new Parte_2(temperatures, 0, temperatures.length-1);
            Double result = pool.invoke(tasca);
            System.out.println("Resultat és: " + result);
        }
    }

