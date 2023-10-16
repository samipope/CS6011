module com.example.cs6011notes {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cs6011notes to javafx.fxml;
    exports com.example.cs6011notes;
}