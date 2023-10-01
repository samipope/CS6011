import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class RainData {
    private ArrayList<RainfallEntry> data = new ArrayList<>();

    public RainData(String filename) {
        readDataFromFile(filename);
    }

    private void readDataFromFile(String filename) {
        try {
            Scanner fileReader = new Scanner(new FileInputStream(filename));

            // Read the city name (first line) and ignore it
            String cityName = fileReader.nextLine();

            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String month = parts[0];
                    int year = Integer.parseInt(parts[1]);
                    double rainfall = Double.parseDouble(parts[2]);
                    data.add(new RainfallEntry(month, year, rainfall));
                }
            }

            fileReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found.");
            e.printStackTrace();
        }
    }

    public double calculateOverallAverage() {
        double totalRainfall = 0.0;
        for (RainfallEntry entry : data) {
            totalRainfall += entry.getRainfall();
        }
        return totalRainfall / data.size();
    }

    public void calculateMonthlyAverages() {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileOutputStream("rainfall_results.txt"));

            for (int month = 1; month <= 12; month++) {
                String monthName = getMonthName(month);
                double totalRainfall = 0.0;
                int count = 0;
                for (RainfallEntry entry : data) {
                    //if (entry.getMonth() = month) continue;
                    totalRainfall += entry.getRainfall();
                    count++;
                }
                double monthlyAverage = totalRainfall / count;
                pw.println("The average rainfall amount for " + monthName + " is " + String.format("%.2f", monthlyAverage) + " inches.");
            }

            pw.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found.");
            e.printStackTrace();
        }
    }

    private String getMonthName(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month - 1];
    }
}
