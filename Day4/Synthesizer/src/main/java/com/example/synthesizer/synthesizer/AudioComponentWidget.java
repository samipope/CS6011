package com.example.synthesizer.synthesizer;

import com.example.synthesizer.SynthesizerApplication;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.geometry.Bounds;

public class AudioComponentWidget extends Pane {

   public AudioComponent ac_;
//   AnchorPane parent_;
   AnchorPane parent_;
   double mouseXpos, mouseYpos, widgetXpos, widgetYpos;

   Line line_;

   public AudioComponentWidget(AudioComponent ac, AnchorPane parent){
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

      //handle drawing the line
      output.setOnMousePressed(e->startConn(e,output));
      output.setOnMouseDragged(e->moveConn(e, output));
      output.setOnMouseReleased(e->endConn(e,output));



      rightSide.getChildren().add(closeBtn);
      rightSide.getChildren().add(output);

      rightSide.setAlignment(Pos.CENTER);
      rightSide.setPadding(new Insets(5));
      rightSide.setSpacing(5);
      //left side
      VBox leftside = new VBox();
      Label freqLabel = new Label("SineWave");
      Slider freqSlider = new Slider(200, 400, 300);
      leftside.getChildren().add(freqLabel);
      leftside.getChildren().add(freqSlider);
      leftside.setOnMousePressed(e->getPosInf(e));
      leftside.setOnMouseDragged(e->moveWidget(e));


      freqSlider.setOnMouseDragged(e->setFrequency(e, freqSlider, freqLabel));
      baseLayout.getChildren().add(freqSlider);//to check it later on
      baseLayout.getChildren().add(leftside);
      baseLayout.getChildren().add(rightSide);
      this.getChildren().add(baseLayout);

      this.setLayoutX(50);
      this.setLayoutY(50);
//      parent_.getChildren().add(this);
   }

   private void moveConn(MouseEvent e, Circle output) {
      Bounds parentBounds = parent_.getBoundsInParent();
   line_.setEndX(e.getSceneX()-parentBounds.getMinX());
   line_.setEndY(e.getSceneY() - parentBounds.getMinY());

   }

   private void startConn(MouseEvent e, Circle output) {
      if(line_!=null){
         parent_.getChildren().remove(line_);
      }
      line_ = new Line();
      Bounds parentBounds = parent_.getBoundsInParent();
      Bounds bounds = output.localToScene(output.getBoundsInLocal());

      line_.setStrokeWidth(3);
      line_.setStartX(bounds.getCenterX() - parentBounds.getMinX());
      line_.setStartY(bounds.getCenterY() - parentBounds.getMinY());

      line_.setEndX((e.getSceneX()));
      line_.setEndY(e.getSceneY());

      parent_.getChildren().add(line_);


   }

   private void endConn(MouseEvent e, Circle output){
      Circle speaker = SynthesizerApplication.speaker;
      Bounds speakerBounds = speaker.localToScene(speaker.getBoundsInLocal());
      double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX()-e.getSceneX(),2.0)+Math.pow(speakerBounds.getCenterY() - e.getSceneY(),2.0));
      if (distance <10){
         SynthesizerApplication.connectedWidgets.add(this);
      }
      else{
         parent_.getChildren().remove(line_);
         line_=null;
      }

   }

   private void moveWidget(MouseEvent e) {
      double delX = e.getSceneX() - mouseXpos;
      double delY = e.getSceneY() - mouseYpos;
      this.relocate((delX + widgetXpos), (delY + widgetYpos));
   }

   private void getPosInf(MouseEvent e) {
      mouseXpos = e.getSceneX();
      mouseYpos = e.getSceneY();
      widgetXpos = this.getLayoutX();
      widgetYpos = this.getLayoutY();
   }

   private void setFrequency(javafx.scene.input.MouseEvent e, Slider freqSlider, Label frequencyLabel) {
      ((SineWave)ac_).setFrequency(freqSlider.getValue());
      int value = (int) freqSlider.getValue();
      frequencyLabel.setText("SineWave" + value + "Hz");
   }

   private void closeWidget(javafx.event.ActionEvent e) {
      parent_.getChildren().remove(this);
      SynthesizerApplication.widgets.remove(this);
      SynthesizerApplication.connectedWidgets.remove(this);
      parent_.getChildren().remove(line_);

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
      ((SineWave)ac_).setFrequency(freqSlider.getValue());
      freqLabel.setText("Frequency of Sinewave:  "  + "Hz");
   }





}


