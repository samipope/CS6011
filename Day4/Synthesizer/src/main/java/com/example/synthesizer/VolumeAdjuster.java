package com.example.synthesizer;

public class VolumeAdjuster implements AudioComponent {
   private AudioComponent input;
    public float scale;

    VolumeAdjuster(float scale) {
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



}
