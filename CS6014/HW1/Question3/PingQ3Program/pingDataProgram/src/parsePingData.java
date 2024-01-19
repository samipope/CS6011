import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class parsePingData {

    public static double findAverage(ArrayList<Double> array) {
        double sum = 0.0;
        for (double value : array) {
            sum += value;
        }
        return sum / array.size();
    }

    public static double findMinimum(ArrayList<Double> array){
        double min = array.get(0);
        for( double value : array){
            if(value<min){
                min = value;
            }
        }
        return  min;
    }



    public static void main(String[] args) throws IOException{
            String inputFile = "pingData.txt";
            String outputFile = "averagePingTime.txt";


            try(BufferedReader br = new BufferedReader(new FileReader(inputFile));
                PrintWriter pw = new PrintWriter(new FileWriter(outputFile))){

                String line; //empty string to hold line
                //pattern to parse the document
                Pattern pattern = Pattern.compile("(\\d+) bytes from ([\\d.]+): icmp_seq=(\\d+) ttl=(\\d+) time=([\\d.]+) ms");
                ArrayList<Double> TotalTimes = new ArrayList<>();

                while((line = br.readLine()) !=null){ //while there is a next line

                    Matcher matcher = pattern.matcher(line);
                    if(matcher.find()){
                        int bytes = Integer.parseInt(matcher.group(1));
                        String ipAddress = matcher.group(2);
                        int icmpSeq = Integer.parseInt(matcher.group(3));
                        int ttl = Integer.parseInt(matcher.group(4));
                        double time = Double.parseDouble(matcher.group(5));
                        //add times to the array so i can use it later
                        TotalTimes.add(time);
                        pw.println(time);
                    }
                }

                //find average and minimum for calculations later
                double average =  findAverage(TotalTimes);
                double minimum = findMinimum(TotalTimes);
                double averageQueingDelay = average - minimum;

                System.out.println("Average: " +average + "\n" + "Minimum: " + minimum + "\n" + "Average Queuing Delay: " +averageQueingDelay);
                //see image in Question 3 folder to see my work to find the average queuing delay 
        }
    }
}
