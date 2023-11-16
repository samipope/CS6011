package assignment04;

import java.util.*;

public class TimingExperiments {
    private static final int ITER_COUNT = 10000;

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        while(System.nanoTime() - startTime < 1000000000L) {
            // Warm-up phase to ensure JVM optimization doesn't affect timing
        }

//        for(int exp = 1; exp <= 10; ++exp) {
//            int size = (int) Math.pow(1.0, (double) exp);
//            long totalTime = 0L;

        for(int size = 0; size <= 100; ++size) {
            long totalTime = 0L;


            for (int iter = 0; iter < ITER_COUNT; ++iter) {
                // Generate worst case data for each iteration
                ArrayList<Integer> testing = SortUtil.generateWorstCase(size);

                long start = System.nanoTime();
                SortUtil.quicksort(testing,Comparator.naturalOrder());
              //  SortUtil.mergesort(testing, Comparator.naturalOrder());
                long stop = System.nanoTime();

                totalTime += stop - start;
            }

            double averageTime = (double) totalTime / ITER_COUNT;
            System.out.println(size + "\t" + averageTime);
        }
    }
}
