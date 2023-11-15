package assignment04;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class TimingExperiments {
    public static void main(String[] args) {

        long startTime = System.nanoTime();
        while (System.nanoTime() - startTime < 1_000_000_000)
            ;

        Random random = new Random();
        for (int exp = 10; exp <= 20; exp++) { // This is used as the exponent to calculate the size of the set.
            int size = (int) Math.pow(2, exp); // or ..

            // Do the experiment multiple times, and average out the results
            long totalTime = 0;



                // TIME IT!
                long start = System.nanoTime();
                set.reversed();

                long stop = System.nanoTime();
                totalTime += stop - start;
            }
            double averageTime = totalTime / (double) ITER_COUNT;
            System.out.println(size + "\t" + averageTime); // print to console

        }



    }
}
