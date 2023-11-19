package assignment05;

import java.util.*;

public class TimingExperiments {
    private static final int ITER_COUNT = 10000;

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        while(System.nanoTime() - startTime < 1000000000L) {
            // Warm-up phase to ensure JVM optimization doesn't affect timing
        }
   //    ArrayStack<Integer> stack = new ArrayStack<>();
       LinkedListStack<Integer> stack = new LinkedListStack<>();
//
//        for(int exp = 0; exp <= 13; ++exp) {
//            int size = (int) Math.pow(2.0, (double) exp);
//            long totalTime = 0L;


//        LinkedListStack<Integer> stack = new LinkedListStack<>();
        for(int size = 0; size <= 500; ++size) {
            long totalTime = 0L;


            for (int iter = 0; iter < ITER_COUNT; ++iter) {

                stack.push(size);

                long start = System.nanoTime();
                stack.peek();

                long stop = System.nanoTime();

                totalTime += stop - start;
            }

            double averageTime = (double) totalTime / ITER_COUNT;
            System.out.println(size + "\t" + averageTime);
        }
    }
}
