package lab03;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class myTimingExperimentonReverse {

    private static final int ITER_COUNT = 100;

    public static void main(String[] args) {
        // you spin me round baby, right round
        long startTime = System.nanoTime();
        while (System.nanoTime() - startTime < 1_000_000_000)
            ;

            Random random = new Random();
            for (int exp = 10; exp <= 20; exp++) { // This is used as the exponent to calculate the size of the set.
                int size = (int) Math.pow(2, exp); // or ..

                // Do the experiment multiple times, and average out the results
                long totalTime = 0;

                for (int iter = 0; iter < ITER_COUNT; iter++) {
                    // SET UP!
                    SortedSet<Integer> set = new TreeSet<>();
                    for (int i = 0; i < size; i++) {
                        set.add(i);
                    }


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
