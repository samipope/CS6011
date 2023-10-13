package com.example.synthesizer;

import java.util.Arrays;

public class AudioClip {
    public static int duration =2;
    public static int sampleRate =44100;
    public final static int TOTAL_SAMPLES = duration*sampleRate;
    public byte[] audioArray = new byte[(int) duration * sampleRate*2];



    //return the sample passed as an int
    int getSample(int index){
        int sample;
        int startIndex = index*2;

        byte byte1 = audioArray[startIndex];
        byte byte2 = audioArray[startIndex+1];

        sample = (byte2 <<8) | (byte1 & 0xff);
        return sample;
    }

    void setSample(int index, int value) {
        byte firstbyte = (byte) (value >> 8);
        byte secondbyte = (byte) (value & 0xFF);
        audioArray[(2*index +1)] = firstbyte;
        audioArray[(2*index)] = secondbyte;

    }

    //bytes should be stored where the lower 8 bits should be stored at array[2*i] and the upper array should be stored at array[2i +`1]


    //returns our array - return a copy, use Arrays/copyOf
     byte[] getData(){
     byte[] NewData = Arrays.copyOf(audioArray, audioArray.length);
     return NewData;
    }



}
