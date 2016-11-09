package m9_fork_join;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

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
