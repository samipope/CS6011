public class MultithreadingLab {
    private static int answer = 0;


    //the threads do not all run in order. They run in random order it looks like to me
    public static void main(String[] args) {
        sayHello(10);
        badSum(1, 40000);
        System.out.println("Computed Answer: " + answer);
        System.out.println("Correct Answer: " + (40000L * (40000L - 1) / 2));
    }

    public static void sayHello(int numThreads) {
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i <= numThreads; i++) {
            final int threadNumber = i;
            threads[i] = new Thread(() -> {
                for (int count = 0; count < 100; count++) {
                    System.out.print("Hello number " + (count + 1) + " from thread number " + threadNumber);
                    if ((count + 1) % 10 == 0) {
                        System.out.println();
                    } else {
                        System.out.print(", ");
                    }
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void badSum(int numThreads, int maxValue) {
        answer = 0;
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            final int threadNumber = i;
            threads[i] = new Thread(() -> {
                int start = threadNumber * maxValue / numThreads;
                int end = Math.min((threadNumber + 1) * maxValue / numThreads, maxValue);
                int localSum = 0;
                for (int j = start; j < end; j++) {
                    localSum += j;
                }
                synchronized (MultithreadingLab.class) {
                    answer += localSum;
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
