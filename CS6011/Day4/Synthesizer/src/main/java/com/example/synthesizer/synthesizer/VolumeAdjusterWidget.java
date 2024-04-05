package com.example.synthesizer.synthesizer;
import com.example.synthesizer.synthesizer.AudioComponent;
import com.example.synthesizer.synthesizer.VolumeAdjuster;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class VolumeAdjusterWidget extends AudioComponentWidget {
    // Add a separate label for this widget to display the volume
    //private final Label volumeLabel_;
    Slider volumeSlider;
    Label volumeLabel;
    HBox baseLayout;
    VBox rightSide;


    VolumeAdjusterWidget(AudioComponent ac, AnchorPane parent) {
        // Call the constructor of the base class (AudioComponentWidgetBase)
        super(ac, parent);
        //VolumeSlider
        rightSide = new VBox();
        volumeLabel = new Label("Volume");
        volumeLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));
        rightSide.getChildren().add(volumeLabel);
        volumeSlider = new Slider(0, 6, 3);
        volumeSlider.setOnMouseDragged(this::handleVolumeSlider);
        volumeSlider.setStyle("-fx-color: #000000");
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        rightSide.getChildren().add(volumeSlider);
        super.baseLayout.getChildren().add(rightSide);


        //VBOX for RIGHT
        rightSide.setOnMousePressed(this::getPosinf);
        rightSide.setOnMouseDragged(this::moveWidget);

        // Set the position of this widget
        this.getChildren().add(super.baseLayout);
        this.setLayoutX(50);
        this.setLayoutY(60);
    }

        private void handleVolumeSlider(MouseEvent mouseEvent) {
        int result = (int) volumeSlider.getValue();
        volumeLabel.setText("Volume: " + result);
        ((VolumeAdjuster) ac_).updateVolume(result);
    }

    }
