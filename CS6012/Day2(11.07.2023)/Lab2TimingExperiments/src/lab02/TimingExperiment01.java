package lab02;

public class TimingExperiment01 {

    public static void main(String[] args) {
        long lastTime = System.currentTimeMillis();
        int advanceCount = 0;
        //goes through loop 100 times
        while (advanceCount < 100) {
            //takes the current time
            long currentTime = System.currentTimeMillis();
            if (currentTime == lastTime)
                continue;
            //prints out how many miliseconds between the continue and the print statement
            System.out.println("Time advanced " + (currentTime - lastTime) + " milliseconds.");
            lastTime = currentTime;
            advanceCount++;
        }
    }
}

//there was a printed message that was not one millisecond, but 5 milliseconds
//the rest of the steps advanced one millisecond at a time

