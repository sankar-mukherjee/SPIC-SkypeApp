/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Toolkit;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import server.utility;

/**
 *
 * @author smuherejee
 */
public class test {

    public utility U = new utility();

    public static void main(String args[]) {
        test t = new test();
        t.getMicVol();
    }
    
    

    public void getMicVol() {
        float rms = 0;
        double sf = 0;
        float peak = 0;
        float choise = 0;
        
        byte[] beep;
        // make sound data for a tone:
        beep = new byte[10 * 44]; // 44 is samples per msec
        double omega = 2 * Math.PI / 100; // 100 is samples per tone cycle
        for (int i = 0; i < beep.length; i++) {
            beep[i] = (byte) (80 * Math.sin(omega * i)); // 80 is amplitude, < 128
        }

        try {

            Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();

            for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
                System.out.println(mixerInfo[cnt].getName()+"--"+cnt);
            }

            System.out.println("-------------------");
            Mixer mixer = AudioSystem.getMixer(mixerInfo[13]);
//            Toolkit.getDefaultToolkit().beep();                     // beep sound before text presentation

            AudioFormat audioFormat = U.getAudioFormat();
            DataLine.Info micInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

            SourceDataLine mic = (SourceDataLine) mixer.getLine(micInfo);
            mic.open();
            System.out.println("Server Mic open.");
            byte tmpBuff[] = new byte[mic.getBufferSize() / 5];
//            System.out.println("buffer"+mic.getBufferSize());
            mic.start();
            mic.write(beep, 0, beep.length);
//            mic.drain();
            mic.close();
            System.out.println("Server Mic close.");
        } catch (LineUnavailableException e) {
            System.out.println(e.getMessage());
        }
    }

}
