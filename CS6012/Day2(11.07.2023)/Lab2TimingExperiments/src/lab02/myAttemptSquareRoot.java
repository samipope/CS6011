package lab02;

public class myAttemptSquareRoot {
    public static void main(String[] args) {
        long startTime, midpointTime, stopTime;
        startTime = System.nanoTime();
        long times = 10000 * 100;

        for (long i = 0; i < times; i++)
            for (double d = 1; d <= 10; d++)
                Math.sqrt(d);

        stopTime = System.nanoTime();

        double averageTime = ((stopTime - startTime)) / times;
        System.out.println(
                "It takes  " + averageTime + " nanoseconds to compute the square roots of the " + " numbers 1..10.");
    }



}
