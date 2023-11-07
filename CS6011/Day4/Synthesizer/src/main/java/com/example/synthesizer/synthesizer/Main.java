package com.example.synthesizer.synthesizer;

import javax.sound.sampled.*;

public class Main {
    public static void main(String[] args) throws LineUnavailableException {


            // Get properties from the system about samples rates, etc.
// AudioSystem is a class from the Java standard library.
            Clip c = AudioSystem.getClip(); // Note, this is different from our AudioClip class.

        AudioListener listener = new AudioListener(c);

            // This is the format that we're following, 44.1 KHz mono audio, 16 bits per sample.
            AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);

            AudioComponent gen = new SineWave(262); // Your code
            AudioComponent gen1 = new SineWave(393); // Your code
            AudioComponent gen2 = new SineWave(524); // Your code

            AudioComponent mixer = new Mixer();
            mixer.connectInput(gen);
            mixer.connectInput(gen1);
            mixer.connectInput(gen2);


            //testing the volume adjuster and mixer
//            VolumeAdjuster adjuster = new VolumeAdjuster(1f);
//            adjuster.connectInput(mixer);
//           AudioClip clip = adjuster.getClip();


       // comment out to test linear ramp
            VFWave wave = new VFWave();
            LinearRamp ramp = new LinearRamp(50,2000);
            wave.connectInput(ramp);
            AudioClip clip = wave.getClip();






        c.open(format16, clip.getData(), 0, clip.getData().length); // Reads data from our byte array to play it.

            System.out.println("About to play...");
            c.start(); // Plays it.
        c.addLineListener(listener);

         //   c.loop(0); // Plays it 2 more times if desired, so 6 seconds total

            //Change this to class example today (AudioListener
//            while (c.getFramePosition() < AudioClip.TOTAL_SAMPLES || c.isActive() || c.isRunning()) {
//                // Do nothing while we wait for the note to play.
//            }



            System.out.println("Done.");
        //    c.close();

            }

    }

