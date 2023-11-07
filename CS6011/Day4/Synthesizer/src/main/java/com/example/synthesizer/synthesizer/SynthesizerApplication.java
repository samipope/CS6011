package com.example.synthesizer.synthesizer;
//From the Main

import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Bounds;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class SynthesizerApplication extends Application {

    AnchorPane mainCenter;
    public static Circle speaker;

    private static Mixer mixer;
    //Slider frequencySlider = new Slider(200, 400, 310);
    public static ArrayList<AudioComponentWidget> widgets = new ArrayList<>();
    public static ArrayList<AudioComponentWidget> connectedWidgets = new ArrayList<>();
    public static ArrayList<SineWave> sineWaves = new ArrayList();
    //public static ArrayList<VolumeAdjusterWidget> volWidgets = new ArrayList<>();

    public VolumeAdjuster changeVolume;

    @Override
    public void start(Stage stage) throws IOException {
       com.example.synthesizer.synthesizer.Mixer mixer = new Mixer();
        BorderPane mainLayout = new BorderPane();

        //Right Panel
        VBox rightpanel = new VBox();
        rightpanel.setStyle("-fx-background-color: #159cda");
        rightpanel.setPadding(new Insets(4));
        rightpanel.setSpacing(10);


        //SineWave Button
        Button sinewaveBtn = new Button("SineWave");
        sinewaveBtn.setStyle("-fx-background-color: #FFECF6; -fx-text-fill: #85c9e3; -fx-border-color: #159cda; -fx-border-width: 2px; -fx-font-family: 'Comic Sans MS'; -fx-font-weight: bold; -fx-font-size: 14;");
        sinewaveBtn.setOnAction(this::createSinewave);
        rightpanel.getChildren().add(sinewaveBtn);

        //Volume Button
        Button volumeBtn = new Button("Volume");
        volumeBtn.setStyle("-fx-background-color: #FFECF6; -fx-text-fill: #85c9e3; -fx-border-color: #159cda; -fx-border-width: 2px; -fx-font-family: 'Comic Sans MS'; -fx-font-weight: bold; -fx-font-size: 14;");
        volumeBtn.setOnAction(this::createVolume);
        rightpanel.getChildren().add(volumeBtn);

        //Linear Ramp Button
        Button mixerBtn = new Button("Mixer");
        mixerBtn.setStyle("-fx-background-color: #FFECF6; -fx-text-fill: #85c9e3; -fx-border-color: #159cda; -fx-border-width: 2px; -fx-font-family: 'Comic Sans MS'; -fx-font-weight: bold; -fx-font-size: 14;");
        mixerBtn.setOnAction(this::createMixer);
        rightpanel.getChildren().add(mixerBtn);

        //Center Panel
        mainCenter = new AnchorPane();
        mainCenter.setStyle("-fx-background-color: #000000");

        //Define speaker so other classes can access it
        speaker = new Circle(400, 200, 15);
        speaker.setStyle("-fx-fill: #cbd3cb");
        mainCenter.getChildren().add(speaker);

        //Adding the AnchorPane's to the layout
        mainLayout.setCenter(mainCenter);
        mainLayout.setRight(rightpanel);

        //Bottom Panel
        HBox bottomPanel = new HBox();
        bottomPanel.setStyle("-fx-background-color: #FFECF6");
        bottomPanel.setAlignment(Pos.CENTER);
        Button playBtn=new Button("⫸");
        playBtn.setStyle("-fx-background-color: #159cda; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2px;");
        playBtn.setOnAction(this::playAudio);
        bottomPanel.getChildren().add(playBtn);
        mainLayout.setBottom(bottomPanel);

        //The entire scene
        Scene scene = new Scene(mainLayout, 600, 400);
        stage.setTitle("✿ Synthesizer ✿");
        stage.setScene(scene);
        stage.show();
    }

    private void playAudio(javafx.event.ActionEvent e) {
        try {
            Clip c = AudioSystem.getClip(); // Not our AudioClip class
            AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
            // Remove this line, so you don't shadow the class-level mixer
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

    private void createSinewave(ActionEvent e) {
        int frequency = 200;
        AudioComponent sineWave = new SineWave(frequency);
        sineWaves.add((SineWave) sineWave);
        SineWaveWidget sw = new SineWaveWidget(sineWave, mainCenter);
        mainCenter.getChildren().add(sw);
        widgets.add(sw);
    }

    // Creates a volume adjustment widget
    private void createVolume(ActionEvent e) {
        AudioComponent volAdj = new VolumeAdjuster(100);
        VolumeAdjusterWidget vw = new VolumeAdjusterWidget(volAdj, mainCenter);
        mainCenter.getChildren().add(vw);
        widgets.add(vw);
    }

    private void createMixer(ActionEvent e) {
        AudioComponent mixer = new Mixer();
        MixerWidget vw = new MixerWidget(mixer, mainCenter);
        mainCenter.getChildren().add(vw);
        widgets.add(vw);
    }



    public static AudioComponentWidget findClosestConnectable(MouseEvent e){
        for(AudioComponentWidget acw:widgets){
            Bounds speakerBounds=acw.getCircleBounds();
            double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX() - e.getSceneX(), 2.0)+Math.pow(speakerBounds.getCenterY()-e.getSceneY(), 2.0));
            if (distance <15){
                return acw;
            }
        }
        return null;
    }

    public static void main (String[]args){
        launch();
    }
}