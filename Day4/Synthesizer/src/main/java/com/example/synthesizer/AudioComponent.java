package com.example.synthesizer;
import java.util.Arrays;
public interface AudioComponent {

    AudioClip getClip();
    boolean hasInput();
    void connectInput(AudioComponent input);

}
