package assignment09;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BSPTreeTimingExperiment {

    private static final int ITER_COUNT = 10;

    public static void main(String[] args) {
        // Warmup
        long startTime = System.nanoTime();
        while (System.nanoTime() - startTime < 1_000_000_000);

        // Random for segment generation
        Random random = new Random(432);

        try (FileWriter writer = new FileWriter("bsptree_timing_results.txt")) {
            writer.write("Size\tBulkTime(ns)\tNonBulkTime(ns)\n");

            for (int exp = 1; exp <= 14; exp++) {
                int size = (int) Math.pow(2, exp);
                long totalTimeBulk = 0;
                long totalTimeNonBulk = 0;

                for (int iter = 0; iter < ITER_COUNT; iter++) {
                    // Generate segments
                    List<Segment> segments = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        segments.add(new Segment(0, i, 0, -i));
                    }

                    // Bulk construction
                    long startBulk = System.nanoTime();
                    BSPTree treeBulk = new BSPTree((ArrayList<Segment>) segments);
                    long endBulk = System.nanoTime();
                    totalTimeBulk += (endBulk - startBulk);

                    // Non-bulk construction
                    BSPTree treeNonBulk = new BSPTree();
                    long startNonBulk = System.nanoTime();
                    for (Segment seg : segments) {
                        treeNonBulk.insert(seg);
                    }
                    long endNonBulk = System.nanoTime();
                    totalTimeNonBulk += (endNonBulk - startNonBulk);
                }

                double averageTimeBulk = totalTimeBulk / (double) ITER_COUNT;
                double averageTimeNonBulk = totalTimeNonBulk / (double) ITER_COUNT;

                String result = size + "\t" + averageTimeBulk + "\t" + averageTimeNonBulk;
                writer.write(result);
                System.out.println(result); // print to console
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
