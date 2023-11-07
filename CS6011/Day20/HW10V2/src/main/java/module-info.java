module com.example.hw10v2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hw10v2 to javafx.fxml;
    exports com.example.hw10v2;
}