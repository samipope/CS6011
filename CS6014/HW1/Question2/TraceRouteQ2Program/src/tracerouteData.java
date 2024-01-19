import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class tracerouteData {
    public static void main(String[] args) throws IOException {
        //input with my data from the first three runs
    //    String inputFile = "tracerouteDataHW1.txt";

        //input with my data from the three runs taken a few hours later
        String inputFile = "traceRouteDataHW1Part2.txt";

        String outputFile = "averageDelays.txt";


        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             PrintWriter pw = new PrintWriter(new FileWriter(outputFile))) {

            String line; //empty string to hold line
            Pattern pattern = Pattern.compile("\\((.*?)\\).*?([\\d.]+) ms.*?([\\d.]+) ms.*?([\\d.]+) ms");

            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String ip = matcher.group(1);
                    double delay1 = Double.parseDouble(matcher.group(2));
                    double delay2 = Double.parseDouble(matcher.group(3));
                    double delay3 = Double.parseDouble(matcher.group(4));

                    double avgDelay = (delay1 + delay2 + delay3) / 3;
                    pw.println(ip + "," + avgDelay);
                }
            }
        }
    }
}
