package lab06;

//import jdk.incubator.vector.VectorOperators;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TimingExperiment {

    private static final int ITER_COUNT = 10;

    public static void main(String[] args) {
        // you spin me round baby, right round
        long startTime = System.nanoTime();
        while (System.nanoTime() - startTime < 1_000_000_000)
            ;

        try (FileWriter fw = new FileWriter(new File("BinarySearchExperiment.tsv"))) { // open up a file writer so we can write
            // to file.
            Random random = new Random();

            for (int exp = 10; exp <= 20; exp++) { // This is used as the exponent to calculate the size of the set.
                int size = (int) Math.pow(2, exp); // or ..

                // Do the experiment multiple times, and average out the results
                long totalTime = 0;
                long totalTime2 = 0;

                for (int iter = 0; iter < ITER_COUNT; iter++) {
                    // SET UP!

                    ArrayList<Integer> toAdd = new ArrayList<>();

                    for (int i = 0; i < size; i++){

                        toAdd.add(i);

                        //int findElement = random.nextInt(size); // This gets me a random int between 0 and size;

                    }

                    Collections.shuffle(toAdd);

                    TreeSetPQ<Integer> testTreeSet = new TreeSetPQ<>();
                    for(int i = 0; i < size; i++){


                        testTreeSet.add(toAdd.get(i));

                    }

//
                    long start = System.nanoTime();
                    while(!testTreeSet.isEmpty()){
                        testTreeSet.removeMin();
                    }
                    long stop = System.nanoTime();


                    // TIME IT!

                    ArrayListPQ<Integer> testArrayListPQ = new ArrayListPQ<>(toAdd);
                    long start2 = System.nanoTime();
                    while(!testArrayListPQ.isEmpty()){
                        testArrayListPQ.removeMin();
                    }
                    long stop2 = System.nanoTime();


                    totalTime += stop - start;
                    totalTime2 += stop2 - start2;
                }
                double averageTime = totalTime / (double) ITER_COUNT;
                double averageTime2 = totalTime2/(double) ITER_COUNT;
                System.out.println(size + "\t" + averageTime + "\t" + averageTime2); // print to console
                fw.write(size + "\t" + averageTime + "\n"); // write to file.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}