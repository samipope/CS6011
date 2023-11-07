package com.example.synthesizer.synthesizer;

public interface AudioComponent {

    AudioClip getClip();
    boolean hasInput();
    void connectInput(AudioComponent input);

}
