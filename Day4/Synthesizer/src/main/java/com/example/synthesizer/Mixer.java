package com.example.synthesizer;
import java.util.ArrayList;

public class Mixer implements AudioComponent {
        private ArrayList<AudioClip> mixerArrayList;

        public Mixer() {

            mixerArrayList = new ArrayList<>();
        }

        public AudioClip getClip() {


            AudioClip result = new AudioClip();
            //iterate over inputs arraylist and for each item in it, do the below
          for(AudioClip audioComponents : mixerArrayList) {
              for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
                  int sample = (int)(result.getSample(i) + audioComponents.getSample(i));

                  //this part used for clamping
                  int max = Short.MAX_VALUE;
                  int min = Short.MIN_VALUE;
                  if(sample<min){
                      sample=min;
                  }
                  else if(sample>max){
                      sample=max;
                  }
                  result.setSample(i,sample);
              }
          }
            return result;
        }



        //anyone can connect to the mixer anytime they want so we submit all of it to the input
        public boolean hasInput() {
            return false;
        }


        public void connectInput(AudioComponent input) {
            AudioClip clip = input.getClip();
            mixerArrayList.add(clip);
        }
    }





