package com.example.synthesizer.synthesizer;

import com.example.synthesizer.synthesizer.AudioClip;
import com.example.synthesizer.synthesizer.AudioComponent;

public class VolumeAdjuster implements AudioComponent {
   private AudioComponent input;
    public float scale;

    public VolumeAdjuster(float scale) {
        this.scale = scale;
    }


    public AudioClip getClip() {
        //put in a random frequency to see if it works
        AudioClip result = new AudioClip();

        AudioClip original = input.getClip();

        for(int i=0; i<AudioClip.TOTAL_SAMPLES;i++) {
            int value = (int) scale*(original.getSample(i));
            result.setSample(i, value);
        }
        return result;
    }


    public boolean hasInput() {
        return true;
    }
    public void connectInput(AudioComponent input) {
        this.input = input;
    }

    public void updateVolume(int volume){
        scale = volume;
    }


}
