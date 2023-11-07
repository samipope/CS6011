package com.example.synthesizer.synthesizer;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MixerWidget extends AudioComponentWidget{


    public MixerWidget(AudioComponent ac, AnchorPane parent) {
        super(ac, parent);


        //VBOX for LEFT
        leftSide = new VBox();
        Label mixerLabel = new Label("Mixer");
        mixerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));
        leftSide.getChildren().add(mixerLabel);
        super.baseLayout.getChildren().add(leftSide);

        leftSide.setOnMousePressed(this::getPosinf);
        leftSide.setOnMouseDragged(this::moveWidget);
        //VolumeSlider
        //VBox leftSideVolume = new VBox();
        this.getChildren().add(super.baseLayout);
        this.setLayoutX(70);
        this.setLayoutY(70);

    }
}
