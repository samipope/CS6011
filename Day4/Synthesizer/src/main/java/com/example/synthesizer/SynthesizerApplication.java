package com.example.synthesizer;


//have to import the classes from the synthesizer tho make this able to access it
import com.example.synthesizer.synthesizer.*;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class SynthesizerApplication extends Application {
    AnchorPane mainCenter;
    public static Circle speaker;
    public static ArrayList<AudioComponentWidget> widgets = new ArrayList<>();
    public static ArrayList<AudioComponentWidget> connectedWidgets = new ArrayList<>();
    @Override
    public void start(Stage stage) throws IOException {

        BorderPane mainLayout = new BorderPane();

        //center panel
        AnchorPane mainCenter = new AnchorPane();
        mainCenter.setStyle("-fx-background-color: pink");
        speaker = new Circle(400,200,15);
        speaker.setFill(Color.BLACK);
        //right panel
        VBox rightpanel = new VBox();
        rightpanel.setStyle("-fx-background-color: white");
        rightpanel.setPadding(new Insets(4));

        javafx.scene.control.Button sinwaveBtn = new Button("SineWave");
        rightpanel.getChildren().add(sinwaveBtn);
       sinwaveBtn.setOnAction(e-> createSinewaveWidget(e, mainCenter));


       javafx.scene.control.Button mixerBtn = new Button("Mixer");
       rightpanel.getChildren().add(mixerBtn);
       mixerBtn.setOnAction(e-> createMixerWidget(e, mainCenter));

        javafx.scene.control.Button volumeAdjusterBtn = new Button("Volume Adjuster");
        rightpanel.getChildren().add(volumeAdjusterBtn);
        volumeAdjusterBtn.setOnAction(e->createVolumeAdjusterWidget(e,mainCenter));

        mainCenter.getChildren().add(speaker);
        mainLayout.setCenter(mainCenter);
        mainLayout.setRight(rightpanel);

        Scene scene = new Scene(mainLayout, 800, 600);
        stage.setTitle("Synthesizer");

        HBox bottomPanel = new HBox();
        Button playButton = new Button();
        playButton.setPrefSize(50,50);
        Label playButtonLabel = new Label("Play");
        playButton.setOnAction(e-> playAudio(e));
        bottomPanel.getChildren().add(playButtonLabel);
        bottomPanel.getChildren().add(playButton);
        mainLayout.setBottom(bottomPanel);

        stage.setScene(scene);
        stage.show();
    }

    private void createMixerWidget(ActionEvent e, AnchorPane mainCenter) {
        Mixer mixer = new Mixer();
        AudioComponentWidget acw = new AudioComponentWidget(mixer,mainCenter);
        mainCenter.getChildren().add(acw);
        widgets.add(acw);
    }

    private void createVolumeAdjusterWidget(ActionEvent e, AnchorPane mainCenter) {
        VolumeAdjuster volumeadjuster = new VolumeAdjuster(1);
        AudioComponentWidget acw = new AudioComponentWidget(volumeadjuster, mainCenter);
        mainCenter.getChildren().add(acw);
        widgets.add(acw);
    }

    private void createSinewaveWidget(javafx.event.ActionEvent e, AnchorPane mainCenter) {
        SineWave sinewave = new SineWave(200);
        AudioComponentWidget acw = new AudioComponentWidget(sinewave, mainCenter);
        mainCenter.getChildren().add(acw);
        widgets.add(acw);
    }

    private void playAudio(javafx.event.ActionEvent e) {
        try {
            Clip c = AudioSystem.getClip(); // Not our AudioClip class
            AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
            Mixer mixer = new Mixer();
            for(AudioComponentWidget w:connectedWidgets){
                AudioComponent ac = w.ac_;
                mixer.connectInput(ac);
            }

            AudioClip clip = mixer.getClip();
            c.open(format16, clip.getData(), 0, clip.getData().length);
            c.start(); // Actually starts playing the sound.
            AudioListener listener = new AudioListener(c);
            c.addLineListener(listener);
        } catch (LineUnavailableException k) {
            System.out.println(k.getMessage());
        }

    }

    public static void main(String[] args) {
        launch();}
}

