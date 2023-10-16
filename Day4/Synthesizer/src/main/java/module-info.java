module com.example.synthesizer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.synthesizer to javafx.fxml;
    exports com.example.synthesizer;
    exports com.example.synthesizer.synthesizer;
    opens com.example.synthesizer.synthesizer to javafx.fxml;
}