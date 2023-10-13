package com.example.synthesizer;
import java.lang.Math;
import java.util.ArrayList;

public class SineWave implements AudioComponent{
    double frequency_;
    double sampleRate = 44100;


    //constructor that passes in frequency
    public SineWave(double frequency){
        this.frequency_=frequency;
    }

    //empty constructor
    public SineWave(){
    }

    public void setFrequency(double value){
        this.frequency_=value;

    }


    public AudioClip getClip(){
        AudioClip clip = new AudioClip();

        for(int i=0; i<AudioClip.TOTAL_SAMPLES;i++){
            double result = Short.MAX_VALUE*Math.sin(2*Math.PI*frequency_ * i/sampleRate);
            clip.setSample(i,(int)result);
        }
        return clip;
    }


   public boolean hasInput(){

    return false;
    }
    public void connectInput(AudioComponent input){
    }

}
