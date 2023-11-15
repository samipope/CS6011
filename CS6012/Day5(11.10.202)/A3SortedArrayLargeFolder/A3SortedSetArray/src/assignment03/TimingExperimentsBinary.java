package assignment03;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class TimingExperimentsBinary {
    private static final int ITER_COUNT = 30000;

    public static void main(String[] args) {




        long startTime = System.nanoTime();
        while (System.nanoTime() - startTime < 1_000_000_000)
            ;

        Random random = new Random();
        for (int exp = 10; exp <= 20; exp++) { // This is used as the exponent to calculate the size of the set.
            int size = (int) Math.pow(2, exp); // or ..

            // Do the experiment multiple times, and average out the results
            long totalTime = 0;

            for (int iter = 0; iter < ITER_COUNT; iter++) {
              //Set up

             //   BinarySearchSet<Double> doubleBinarySearchSet = null;
//                for (int i = 0; i < size; i++) {
//                    doubleBinarySearchSet = new BinarySearchSet<>();
//                    doubleBinarySearchSet.add((double) i);
//                }


                   BinarySearchSet<Double> addTest = null;
                for (int i = 1; i < size; i++) {
                    addTest = new BinarySearchSet<>();
                    addTest.add((double) i);
                }

                double randomOne = random.nextDouble();
                addTest.remove(randomOne);
                addTest.remove(0.002);

                // TIME IT!
                long start = System.nanoTime();
               // doubleBinarySearchSet.contains(random.nextDouble());
                addTest.add(randomOne);
                addTest.add(0.002);

                long stop = System.nanoTime();
                totalTime += stop - start;

            }
            double averageTime = totalTime / (double) ITER_COUNT;
            System.out.println(size + "\t" + averageTime); // print to console

        }

    }


}
