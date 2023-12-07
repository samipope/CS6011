package assignment09;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollisionTimingExperiment {
    private static final int START_SIZE = 10;
    private static final int MAX_SIZE = 100;
    private static final int STEP_SIZE = 10;
    private static final int TEST_QUERIES_PER_SIZE = 10;

    public static void main(String[] args) {
        Random random = new Random();

        for (int size = START_SIZE; size <= MAX_SIZE; size += STEP_SIZE) {
            BSPTree tree = createTreeWithSegments(size, random);

            for (int i = 0; i < TEST_QUERIES_PER_SIZE; i++) {
                Segment query = generateRandomSegment(random);

                // Optimized collision detection
                long startOptimized = System.nanoTime();
                Segment collisionOptimized = tree.collision(query);
                long timeOptimized = System.nanoTime() - startOptimized;

                // Brute-force collision detection
                long startBruteForce = System.nanoTime();
                boolean collisionBruteForce = checkCollisionBruteForce(tree, query);
                long timeBruteForce = System.nanoTime() - startBruteForce;

                // Record the results
             //   recordResult(size, timeOptimized, timeBruteForce);
                System.out.println(size +"\t" + timeOptimized +"\t" + timeBruteForce);
            }
        }
    }

    private static BSPTree createTreeWithSegments(int size, Random random) {
        List<Segment> segments = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            segments.add(generateRandomSegment(random));
        }
        return new BSPTree((ArrayList<Segment>) segments);
    }

    private static Segment generateRandomSegment(Random random) {
        // Generate a random segment
        // You can adjust this method to create the segments according to your needs
        return new Segment(random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt());
    }

    private static boolean checkCollisionBruteForce(BSPTree tree, Segment query) {
        final boolean[] collisionFound = {false};
        tree.traverseFarToNear(0, 0, (segment) -> {
            if (segment.intersects(query)) {
                collisionFound[0] = true;
            }
        });
        return collisionFound[0];
    }









}
