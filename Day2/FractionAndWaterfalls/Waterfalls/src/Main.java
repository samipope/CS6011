// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        RainData rainData = new RainData("Macon.txt");

        // Calculate and print the overall average rainfall
        double overallAverage = rainData.calculateOverallAverage();
        System.out.println("The overall average rainfall amount is " + String.format("%.2f", overallAverage) + " inches.");

        // Calculate and print the monthly averages
        rainData.calculateMonthlyAverages();
    }

}