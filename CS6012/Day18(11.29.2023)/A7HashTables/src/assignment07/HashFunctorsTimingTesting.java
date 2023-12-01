package assignment07;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HashFunctorsTimingTesting {

    public static void main(String[] args) {
        // Initialize HashFunctors


        HashFunctor mediocreHash = new MediocreHashFunctor();
        HashFunctor goodHash = new GoodHashFunctor(); // Replace with your actual implementation
        HashFunctor badHash = new BadHashFunctor(); // Replace with your actual implementation

        // Test for array sizes from 2^10 to 2^20
        for (int i = 1; i <= 10; i++) {
            int size = (int) Math.pow(2, i);
          //  System.out.println("Testing for size: " + size);

            // Prepare test data
            List<String> testData = generateTestData(size);

            // Timing for MediocreHashFunctor
            long startTime = System.nanoTime();
            testHashFunctor(mediocreHash, testData);
            long endTime = System.nanoTime();
          //  System.out.println("MediocreHashFunctor Time for size " + size + ": " + (endTime - startTime) + " nanoseconds");
            long totalTime1 = endTime-startTime;

            long startTime2;
            long endTime2;
            // Timing for GoodHashFunctor
            startTime2 = System.nanoTime();
            testHashFunctor(goodHash, testData);
            endTime2 = System.nanoTime();
          //  System.out.println("GoodHashFunctor Time for size " + size + ": " + (endTime - startTime) + " nanoseconds");
            long totalTime2 = endTime2-startTime2;

            long startTime3;
            long endTime3;
            // Timing for ExcellentHashFunctor
            startTime3 = System.nanoTime();
            testHashFunctor(badHash, testData);
            endTime3 = System.nanoTime();
            long totalTime3 = endTime3-startTime3;

           // System.out.println("BadHashFunctor Time for size " + size + ": " + (endTime - startTime) + " nanoseconds");

//
//            int goodCollisions = returnCollisions(new GoodHashFunctor(), testData);
//            int mediocreCollisions = returnCollisions(new MediocreHashFunctor(), testData);
//            int badCollisions = returnCollisions(new BadHashFunctor(), testData);





      //      System.out.println(size +"\t" + goodCollisions + "\t" + mediocreCollisions + "\t" + badCollisions); // print to console

           System.out.println(size +"\t" + totalTime1 + "\t" + totalTime2 + "\t" + totalTime3); // print to console


        }
    }

    private static void testHashFunctor(HashFunctor functor, List<String> testData) {
        ChainingHashTable testing = new ChainingHashTable(testData.size(),functor);
        testing.addAll(testData);
        for (String data : testData) {
            functor.hash(data);
        }
    }
//    private static int returnCollisions(HashFunctor functor, List<String> testData){
//       ChainingHashTable testing = new ChainingHashTable(10,functor);
//       testing.addAll(testData);
//       return 0;
//    }

    private static List<String> generateTestData(int size) {
        Random randomTest = new Random(1);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add("String" + i + randomTest.nextDouble());
        }
        for (int i = 0; i < size; i++) {
            data.remove("String" + i);
        }
        return data;
    }
}
