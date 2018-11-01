/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.W32APIOptions;
import static com.sun.jna.win32.W32APIOptions.DEFAULT_OPTIONS;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.sound.sampled.AudioFormat;

/**
 *
 * @author smuherejee
 */
public class utility {

    public static final int WINDOW_SIZE_SAMPLES = 4096;

    public float NewRMS(byte[] buf, float[] samples, int b) {
        // convert bytes to samples here
        for (int i = 0, s = 0; i < b;) {
            int sample = 0;
            sample |= buf[i++] & 0xFF; // (reverse these two lines
            sample |= buf[i++] << 8;   //  if the format is big endian)
            // normalize to range of +/-1.0f
            samples[s++] = sample / 32768f;
        }
        float rms = 0f;
        for (float sample : samples) {
            rms += sample * sample;
        }
        rms = (float) Math.sqrt(rms / samples.length);
        return rms;
    }

    public double calculateAverageDouble(ArrayList<Double> marks) {
        Double sum = 0.0;
        if (!marks.isEmpty()) {
            for (Double mark : marks) {
                sum += mark;
            }
            return sum.doubleValue() / marks.size();
        }
        return sum;
    }

    public float calculateAverageFloat(ArrayList<Float> marks) {
        Float sum = 0f;
        if (!marks.isEmpty()) {
            for (Float mark : marks) {
                sum += mark;
            }
            return sum.floatValue() / marks.size();
        }
        return sum;
    }

    protected static float calculateRMSLevel(byte[] audioData) { // audioData might be buffered data read from a data line
        long lSum = 0;
        for (int i = 0; i < audioData.length; i++) {
            lSum = lSum + audioData[i];
        }

        double dAvg = lSum / audioData.length;

        double sumMeanSquare = 0d;
        for (int j = 0; j < audioData.length; j++) {
            sumMeanSquare = sumMeanSquare + Math.pow(audioData[j] - dAvg, 2d);
        }

        double averageMeanSquare = sumMeanSquare / audioData.length;
        return (float) (Math.pow(averageMeanSquare, 0.5d) + 0.5);
    }

    protected static float calculatePeak(byte[] audioData) {
        float[] samples = new float[2048 / 2];
        // convert bytes to samples here
        for (int i = 0, s = 0; i < audioData.length;) {
            int sample = 0;

            sample |= audioData[i++] & 0xFF; // (reverse these two lines
            sample |= audioData[i++] << 8;   //  if the format is big endian)

            // normalize to range of +/-1.0f
            samples[s++] = sample / 32768f;
        }

        float peak = 0f;
        for (float sample : samples) {

            float abs = Math.abs(sample);
            if (abs > peak) {
                peak = abs;
            }
        }
        return peak;
    }

    public static double spectralFlatness(short[] window) {
        double[] ps = null;

        try {
            ps = (new JAudioFFT(doublesFromShorts(window), null, false, true)).getPowerSpectrum();
        } catch (Exception e) {
            e.printStackTrace();
        }

        double geometricMean = 1.0, arithmeticMean = 0.0;
        for (int i = 0; i < ps.length; i++) {
            geometricMean *= ps[i];
            arithmeticMean += ps[i];
        }
        geometricMean = Math.pow(geometricMean, 1 / WINDOW_SIZE_SAMPLES);
        arithmeticMean /= WINDOW_SIZE_SAMPLES;

        return geometricMean / arithmeticMean;
    }

    public static double[] doublesFromShorts(short[] in) {
        double[] ret = new double[in.length];

        for (int i = 0; i < in.length; i++) {
            ret[i] = (double) in[i];
        }

        return ret;
    }

    public static short[] shortFromByte(byte[] byteArray) {
        int size = byteArray.length;
        short[] shortArray = new short[size];

        for (int index = 0; index < size; index++) {
            shortArray[index] = (short) byteArray[index];
        }
        return shortArray;
    }

    public AudioFormat getAudioFormat() {
        float sampleRate = 8000.0F;
        //8000,11025,16000,22050,44100
        int sampleSizeInBits = 16;
        //8,16
        int channels = 1;
        //1,2
        boolean signed = true;
        //true,false
        boolean bigEndian = false;
        //true,false
        return new AudioFormat(
                sampleRate,
                sampleSizeInBits,
                channels,
                signed,
                bigEndian);
    }//end getAudioFormat

    public ArrayList<String> getAllDominos(String path) {
        ArrayList<String> dominnos = new ArrayList<String>();
        File folder = new File(path);
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                System.out.println("Dir exists");
            } else {
                dominnos.add(fileEntry.getPath());
            }
        }
        return dominnos;
    }

    public ArrayList<String> build_dominno(String path) {
        ArrayList<String> data2 = new ArrayList<String>();
        try {
            //BufferedReader br = new BufferedReader(new FileReader(path));
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String line = "";
            ArrayList<String> data1 = new ArrayList<String>();
            br.readLine();
            while ((line = br.readLine()) != null) {
                data1.add(line);
            }
            br.close();

            for (String t : data1) {
                String[] tmp = t.split(",");
                data2.add(tmp[0] + "," + tmp[3]);
            }
            data2.removeAll(Arrays.asList(null, ""));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return data2;
    }

    public void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = new FileInputStream(source);
        OutputStream os = new FileOutputStream(dest);
        try {
//            is = new FileInputStream(source);
//            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public interface User32 extends W32APIOptions {
        User32 instance = (User32) Native.loadLibrary("user32", User32.class, DEFAULT_OPTIONS);
        boolean ShowWindow(WinDef.HWND hWnd, int nCmdShow);
        boolean PostMessage(WinDef.HWND hWnd, int Msg, int wParam, int lParam);
        WinDef.HWND FindWindow(String winClass, String title);       
    }
    
    public void TriggerParallel(int i) {
        JParallelPort parport = new JParallelPort(); // instantiate ParallelPort object
        try {
            parport.open(); // open port
            // write 0 value to data pins (usually 0x378) put all pins to low
            parport.writeData((byte) i);
            // read from status and print it as an integer
            System.out.println("Status: " + (int) parport.readStatus());
            parport.close(); // close port
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
