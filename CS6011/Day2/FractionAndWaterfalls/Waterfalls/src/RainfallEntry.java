public class RainfallEntry {
    private String month;
    private int year;
    private double rainfall;

    public RainfallEntry(String month, int year, double rainfall) {
        this.month = month;
        this.year = year;
        this.rainfall = rainfall;
    }

    public String getMonth() {
        return month;
        // Implement this to return the month as an integer (e.g., 1 for January)
    }

    public int getYear() {
        return year;
    }

    public double getRainfall() {
        return rainfall;
    }
}
