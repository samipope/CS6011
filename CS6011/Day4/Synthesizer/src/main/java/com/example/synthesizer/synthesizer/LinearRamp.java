package com.example.synthesizer.synthesizer;

import com.example.synthesizer.synthesizer.AudioClip;
import com.example.synthesizer.synthesizer.AudioComponent;

public class LinearRamp implements AudioComponent {
    float start;
    float stop;

    LinearRamp(float start, float stop){
       this.stop=stop;
       this.start=start;
    }



    @Override
    public AudioClip getClip() {
        AudioClip sample = new AudioClip();
        for(int i=0; i<AudioClip.TOTAL_SAMPLES;i++) {
            double newvalue  =  ((start * (AudioClip.TOTAL_SAMPLES - i) + stop * i) / AudioClip.TOTAL_SAMPLES);
            sample.setSample(i, (int) newvalue);
        }
        return sample;
    }


    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {

    }




}
