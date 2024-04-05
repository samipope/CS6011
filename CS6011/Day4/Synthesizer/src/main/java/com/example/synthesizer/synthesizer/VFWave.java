package com.example.synthesizer.synthesizer;

import com.example.synthesizer.synthesizer.AudioClip;
import com.example.synthesizer.synthesizer.AudioComponent;

public class VFWave implements AudioComponent {
        AudioComponent input_;

    public VFWave(){
    }

    @Override
    public AudioClip getClip() {

       AudioClip output = this.input_.getClip();
       // AudioClip output = new AudioClip();
        double phase = 0;
        for(int i=0; i<AudioClip.TOTAL_SAMPLES; i++){
            phase += ((2 * Math.PI * output.getSample(i))/AudioClip.sampleRate);
            double result = Short.MAX_VALUE * Math.sin(phase);
            output.setSample(i, (int)result);
        }
        return output;
    }

    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    public void connectInput(AudioComponent input) {
        this.input_=input;


    }
}
