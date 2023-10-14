package com.example.synthesizer;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class AudioComponentWidget extends Pane {

   AudioComponent ac_;
//   AnchorPane parent_;
   AnchorPane parent_;

   AudioComponentWidget(AudioComponent ac, AnchorPane parent){
      ac_=ac;
      parent_=parent;

      HBox baseLayout=new HBox();
      Label baselayoutlabel = new Label("SineWave");
      baseLayout.setStyle("-fx-border-color: black; -fx-border-image-width: 5 ");
      VBox rightSide = new VBox();
      Button closeBtn=new Button("x");
      closeBtn.setOnAction(e->closeWidget(e));
      Circle output = new Circle(10);
      output.setFill(Color.AQUA);

      rightSide.getChildren().add(closeBtn);
      rightSide.getChildren().add(output);

      rightSide.setAlignment(Pos.CENTER);
      rightSide.setPadding(new Insets(5));
      rightSide.setSpacing(5);



      //fix the values in this slider
      Slider freqSlider = new Slider(200, 400, 300);
      freqSlider.setOnMouseDragged(e->setFrequency(e, freqSlider));
      baseLayout.getChildren().add(freqSlider); //to check it later on
      baseLayout.getChildren().add(rightSide);
      this.getChildren().add(baseLayout);

      this.setLayoutX(50);
      this.setLayoutY(50);

   }

   private void setFrequency(javafx.scene.input.MouseEvent e, Slider freqSlider) {
      ((SineWave)ac_).setFrequency(freqSlider.getValue());
   }

   private void closeWidget(javafx.event.ActionEvent e) {
      parent_.getChildren().remove(this);
      SynthesizerApplication.widgets.remove(this);
   }







//   private void playAudio(javafx.event.ActionEvent e, AudioComponent ac) {
//      try {
//         AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
//         Clip c = AudioSystem.getClip(); // Not our AudioClip class
//         AudioListener listener = new AudioListener(c);
//         byte[] data = ac.getClip().getData();
//         // Reads data from our byte array to play it.
//         c.open(format16, data, 0, data.length);
//         c.start(); // Actually starts playing the sound.
//         // Don’t end the program until the sound finishes playing...
//         c.addLineListener(listener);
//      } catch (LineUnavailableException k) {
//         //System.out.println(k.getMessage());
//      }
//
//   }

   private void handleSlider(javafx.scene.input.MouseEvent e, Slider freqSlider, Label freqLabel) {
      //play the sound if they press the button
      double result = freqSlider.getValue();
      freqLabel.setText("Frequency of Sinewave:  " + result + "Hz");
   }






//    private void createComponent(ActionEvent e){
//        AudioComponent sinewave = new SineWave(200);
//        AudioComponentWidget acw = new AudioComponentWidget(sinewave, mainCenter);
//        mainCenter.getChildren().add(acw);
//        widgets_.add(acw);
//    }



}


