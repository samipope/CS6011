package assignment07;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChainingHashTableTimingTesting {

    public static void main(String[] args) {
        // Different capacities to test
        int[] capacities = {10,100,500,1000,1500,2000,2500,3000};

        // Testing with each capacity
        for (int capacity : capacities) {
            // Initialize the hash table with a specific capacity
            ChainingHashTable table = new ChainingHashTable(capacity, new MediocreHashFunctor()); // Replace GoodHashFunctor with your actual implementation

            // Generate test data
            List<String> testData = generateTestData(); // Generate 1,000 test strings

            // Measure time taken to add strings
            long startTime = System.nanoTime();
            for (String data : testData) {
                table.add(data);
            }
            long endTime = System.nanoTime();
            long addTime = endTime - startTime;

//            // Measure time taken to remove strings
//            startTime = System.nanoTime();
//            for (String data : testData) {
//                table.remove(data);
//            }
//            endTime = System.nanoTime();
//            long removeTime = endTime - startTime;

            // Output the results
          //  System.out.println("Capacity: " + capacity + ", Add Time: " + addTime + " ns, Remove Time: " + removeTime + " ns");
//            System.out.println(capacity +"\t" + table.countCollisions()); // print to console
          System.out.println( table.countCollisions()); // print to console

        }
    }

    private static List<String> generateTestData() {
        List<String> data = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 6000; i++) {
            data.add("String" + (char) random.nextInt());
        }
        return data;
    }
}
