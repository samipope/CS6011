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

   static Circle input_;

   Line line_;

   public AudioComponentWidget(SineWave ac, AnchorPane parent){
      ac_=ac;
      parent_=parent;

      HBox baseLayout=new HBox();
      Label baselayoutlabel = new Label("SineWave");
      baseLayout.setStyle("-fx-border-color: black; -fx-border-image-width: 5 ");
      VBox rightSide = new VBox();


      Button closeBtn=new Button("x");
      closeBtn.setOnAction(e->closeWidget(e));
      Circle output = new Circle(10);
      output.setFill(Color.GRAY);

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
      Slider freqSlider = new Slider(200, 1000, 300);
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
   }


   public AudioComponentWidget(Mixer ac, AnchorPane parent){
      ac_=ac;
      parent_=parent;

      HBox baseLayout=new HBox();
      Label baselayoutlabel = new Label("Mixer");
      baseLayout.getChildren().add(baselayoutlabel);
      baseLayout.setStyle("-fx-border-color: #7cf6ec; -fx-border-image-width: 10 ");
      VBox rightSide = new VBox();


      Button closeBtn=new Button("x");
      closeBtn.setOnAction(e->closeWidget(e));
      Circle output = new Circle(10);
      output.setFill(Color.GRAY);

      Circle input = new Circle(10);
      input.setFill(Color.BLACK);
      input_ =input;


      //handle drawing the line for output
      output.setOnMousePressed(e->startConn(e,output));
      output.setOnMouseDragged(e->moveConn(e, output));
      output.setOnMouseReleased(e->endConn(e,output));

      input.setOnMouseReleased(e->endConn(e,input));

      rightSide.getChildren().add(closeBtn);
      rightSide.getChildren().add(output);
      rightSide.getChildren().add(input);

      rightSide.setAlignment(Pos.CENTER);
      rightSide.setPadding(new Insets(5));
      rightSide.setSpacing(5);


      VBox leftside = new VBox();
      Label dragginglabel = new Label("O");
      leftside.getChildren().add(dragginglabel);

      leftside.setOnMousePressed(e->getPosInf(e));
      leftside.setOnMouseDragged(e->moveWidget(e));

      baseLayout.getChildren().add(rightSide);
      baseLayout.getChildren().add(leftside);
      this.getChildren().add(baseLayout);

      this.setLayoutX(50);
      this.setLayoutY(50);
   }

   public AudioComponentWidget(VolumeAdjuster ac, AnchorPane parent) {
      ac_ = ac;
      parent_ = parent;

      HBox baseLayout = new HBox();
      Label baselayoutlabel = new Label("Volume");
      baseLayout.getChildren().add(baselayoutlabel);
      baseLayout.setStyle("-fx-border-color: #7cf6ec; -fx-border-image-width: 10 ");
      VBox rightSide = new VBox();

      Button closeBtn = new Button("x");
      closeBtn.setOnAction(e -> closeWidget(e));
      Circle output = new Circle(10);
      output.setFill(Color.GRAY);
      Circle input = new Circle(10);
      input.setFill(Color.BLACK);
      input_ =input;

      //handle drawing the line for output/input
      output.setOnMousePressed(e -> startConn(e, output));
      output.setOnMouseDragged(e -> moveConn(e, output));
      output.setOnMouseReleased(e -> endConn(e, output));
      input.setOnMouseReleased(e -> endConn(e, input));

      rightSide.getChildren().add(closeBtn);
      rightSide.getChildren().add(output);
      rightSide.getChildren().add(input);

      rightSide.setAlignment(Pos.CENTER);
      rightSide.setPadding(new Insets(5));
      rightSide.setSpacing(5);

      //left side to make dragging the widget easier
      VBox leftside = new VBox();
      Label dragginglabel = new Label("O");
      leftside.getChildren().add(dragginglabel);

      Slider volumeSlider = new Slider(0, 5, 30);
      leftside.getChildren().add(volumeSlider);
      leftside.setOnMousePressed(e->getPosInf(e));
      leftside.setOnMouseDragged(e->moveWidget(e));


      volumeSlider.setOnMouseDragged((e->setVolume(e,volumeSlider)));
      //freqSlider.setOnMouseDragged(e->setFrequency(e, freqSlider, freqLabel));
      baseLayout.getChildren().add(volumeSlider);//to check it later on
      baseLayout.getChildren().add(leftside);
      baseLayout.getChildren().add(rightSide);
      this.getChildren().add(baseLayout);

      this.setLayoutX(50);
      this.setLayoutY(50);

   }



   private void moveConn(MouseEvent e, Circle output) {
      Bounds parentBounds = parent_.getBoundsInParent();
   line_.setEndX(e.getSceneX()-parentBounds.getMinX());
   line_.setEndY(e.getSceneY() - parentBounds.getMinY());

   }

   private void startConn(MouseEvent e, Circle output) {
      if (line_ != null) {
         parent_.getChildren().remove(line_);
      }

      line_ = new Line();
      Bounds parentBounds = parent_.getBoundsInParent();
      Bounds bounds = output.localToScene(output.getBoundsInLocal());

      line_.setStrokeWidth(3);
      line_.setStartX(bounds.getCenterX() - parentBounds.getMinX());
      line_.setStartY(bounds.getCenterY() - parentBounds.getMinY());

      line_.setEndX(e.getSceneX());
      line_.setEndY(e.getSceneY());

      parent_.getChildren().add(line_);

   }

   private void endConn(MouseEvent e, Circle output){
      Circle speaker = SynthesizerApplication.speaker;
      Bounds speakerBounds = speaker.localToScene(speaker.getBoundsInLocal());
      double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX()-e.getSceneX(),2.0)+Math.pow(speakerBounds.getCenterY() - e.getSceneY(),2.0));
      if (distance <10){
         SynthesizerApplication.connectedWidgets.add(this);
      } else{
         Circle input = AudioComponentWidget.input_;
         Bounds inputBounds = input.localToScene((input.getBoundsInLocal()));
         double inputDistance = Math.sqrt(Math.pow(inputBounds.getCenterX()-e.getSceneX(),2.0)+Math.pow(inputBounds.getCenterY() - e.getSceneY(),2.0));
         if(inputDistance<10){
            SynthesizerApplication.connectedWidgets.add(this);
         } else {
            parent_.getChildren().remove(line_);
            line_=null;
         }
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




   public float setVolume(MouseEvent e, Slider volumeSlider) {
//      VolumeAdjuster adjuster = new VolumeAdjuster((float) volumeSlider.getValue());
//      adjuster.connectInput((SineWave)this.ac_);

       float volume = (float) volumeSlider.getValue();
       return volume;
   }



   private void closeWidget(javafx.event.ActionEvent e) {
      parent_.getChildren().remove(this);
      SynthesizerApplication.widgets.remove(this);
      SynthesizerApplication.connectedWidgets.remove(this);
      parent_.getChildren().remove(line_);

   }



   private void handleSlider(javafx.scene.input.MouseEvent e, Slider freqSlider, Label freqLabel) {
      //play the sound if they press the button
      ((SineWave)ac_).setFrequency(freqSlider.getValue());
      freqLabel.setText("Frequency of Sinewave:  "  + "Hz");
   }



}


