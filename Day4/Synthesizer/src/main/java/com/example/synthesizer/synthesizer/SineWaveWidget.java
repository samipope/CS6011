package com.example.synthesizer.synthesizer;

import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SineWaveWidget extends com.example.synthesizerproject.AudioComponentWidget {

    static Slider freqSlider;

    HBox baseLayout;
    VBox rightSide;
    VBox leftSide;

    Label frequencyLabel;


    //constructor
    SineWaveWidget(AudioComponent ac, AnchorPane parent) {
        super (ac, parent);

        //VBOX for LEFT
        leftSide = new VBox();
        frequencyLabel = new Label("Frequency");
        frequencyLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));
        leftSide.getChildren().add(frequencyLabel);
        freqSlider = new Slider(50, 2000, 300);
        freqSlider.setOnMouseDragged(this::handleSlider);
        freqSlider.setStyle("-fx-color: #F18DBC");
        freqSlider.setShowTickLabels(true);
        freqSlider.setShowTickMarks(true);
        leftSide.getChildren().add(freqSlider); //to check it later on
        super.baseLayout.getChildren().add(leftSide);

        leftSide.setOnMousePressed(this::getPosinf);
        leftSide.setOnMouseDragged(this::moveWidget);
        //VolumeSlider
        //VBox leftSideVolume = new VBox();
        this.getChildren().add(super.baseLayout);
        this.setLayoutX(70);
        this.setLayoutY(70);

    }

        //FOR DRAWING THE LINE

    private void handleSlider(MouseEvent mouseEvent) {
        // AudioComponent ac = getAudioComponent();
        int result = (int) freqSlider.getValue();
        frequencyLabel.setText("Frequency: " + result + " Hz");
        ((SineWave) ac_).setFrequency(result);
    }

}
