module com.example.hw7 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hw7 to javafx.fxml;
    exports com.example.hw7;
}