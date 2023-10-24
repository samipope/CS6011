package com.example.synthesizerproject;
import com.example.synthesizer.synthesizer.AudioComponent;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class AudioComponentWidget extends Pane {
    public AudioComponent ac_;
    AnchorPane parent_;

    Slider volumeSlider;

    Circle output;


    Line line_;
    double mouseXpos, mouseYpos, widgetXpos, widgetYpos;

    public HBox baseLayout;
    VBox rightSide;
    VBox leftSide;


    Label volumeLabel;

    //constructor
    public AudioComponentWidget(AudioComponent ac, AnchorPane parent) {
        ac_ = ac;
        parent_ = parent;
        baseLayout = new HBox();
        baseLayout.setStyle("-fx-background-color: #FFECF6; -fx-border-color: white; -fx-border-width: 2px; -fx-font-family: 'Comic Sans MS'; -fx-font-weight: bold; -fx-text-fill: #E0218A; -fx-font-size: 14;");

        //VBOX for LEFT
        leftSide = new VBox();


        //VolumeSlider
        VBox leftSideVolume = new VBox();
        volumeLabel = new Label("Volume");
        volumeLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));
        leftSide.getChildren().add(volumeLabel);
        volumeSlider = new Slider(0, 10, 5);

        volumeSlider.setStyle("-fx-color: #F18DBC");
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        leftSide.getChildren().add(volumeSlider);

        leftSide.setOnMousePressed(this::getPosinf);
        leftSide.setOnMouseDragged(this::moveWidget);

        //VBOX for RIGHT
        rightSide = new VBox();
        Button closeBtn = new Button("x");
        closeBtn.setStyle("-fx-background-color: #F18DBC; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2px; -fx-padding: 8px; -fx-font-size: 12px;");
        closeBtn.setOnAction(this::closeWidget);
        output = new Circle(10);
        output.setStyle("-fx-fill: #E0218A");
        //rightSide.getChildren().add(closeBtn);

        //Handle drawing the line - handle 3 events
        output.setOnMousePressed(e -> startConnection(e, output));
        output.setOnMouseDragged(e -> moveConnection(e, output));
        output.setOnMouseReleased(e -> endConnection(e, output));


        rightSide.getChildren().add(closeBtn);
        rightSide.getChildren().add(output);
        //Alignment and padding
        rightSide.setAlignment(Pos.CENTER);
        rightSide.setPadding(new Insets(5));
        rightSide.setSpacing(5);

      //  baseLayout.getChildren().add(leftSide);
        baseLayout.getChildren().add(rightSide);
    }

    //FOR DRAWING THE LINE
    private void startConnection(MouseEvent e, Circle output) {

        if (line_ != null) {
            parent_.getChildren().remove(line_);
        }

        Bounds parentBounds = parent_.getBoundsInParent();
        Bounds bounds = output.localToScene(output.getBoundsInLocal());

        line_ = new Line();
        line_.setStrokeWidth(4);
        line_.setStroke(Color.WHITE);
        line_.setStartX(bounds.getCenterX() - parentBounds.getMinX());
        line_.setStartY(bounds.getCenterY() - parentBounds.getMinY());
        line_.setEndX(e.getSceneX());
        line_.setEndY(e.getSceneY());
        parent_.getChildren().add(line_);
    }

    private void moveConnection(MouseEvent e, Circle output) {
        Bounds parentBounds = parent_.getBoundsInParent();
        line_.setEndX(e.getSceneX() - parentBounds.getMinX());
        line_.setEndY(e.getSceneY() - parentBounds.getMinY());
    }

    private void endConnection(MouseEvent e, Circle output) {
        Circle speaker = SynthesizeApplication.speaker; //get the speaker from the main application
        Bounds speakerBounds = speaker.localToScene(speaker.getBoundsInLocal());
        double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX() - e.getSceneX(), 2.0) + Math.pow(speakerBounds.getCenterY() - e.getSceneY(), 2.0));


        AudioComponentWidget widget = SynthesizeApplication.findClosestConnectable(e);

        if (widget != null){
//            SynthesizeApplication.connectedWidgets_.add(this);
            widget.ac_.connectInput(this.ac_);

        }
        else if (distance<15){
//            SynthesizeApplication.connectedWidgets_.add(this);
            SynthesizeApplication.connectToSpeaker(this.ac_);
        }
        else{
            parent_.getChildren().remove(line_);
            line_=null;
        }
    }
    private void closeWidget(ActionEvent e) {
        parent_.getChildren().remove(this);
        SynthesizeApplication.widgets.remove(this);
        SynthesizeApplication.connectedWidgets.remove(this);
        parent_.getChildren().remove(line_);
    }

    public void moveWidget(MouseEvent e) {
        double delX = e.getSceneX() - mouseXpos;
        double delY = e.getSceneY() - mouseYpos;
        relocate(delX + widgetXpos, delY + widgetYpos);
    }

    public void getPosinf(MouseEvent e) {
        mouseXpos = e.getSceneX();
        mouseYpos = e.getSceneY();
        widgetXpos = this.getLayoutX();
        widgetYpos = this.getLayoutY();
    }

    public AudioComponent getAudioComponent() {
        return ac_;
    }

    public Bounds getCircleBounds(){
        return output.localToScene(output.getBoundsInLocal());
    }
}
