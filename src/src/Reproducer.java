/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src;

/**
 *
 * @author erisaac666
 */
import javax.sound.sampled.*;
import java.io.InputStream;

public class Reproducer extends Thread {

    private Clip clip;
    

  

    public void run(String ruta) {
        try {

            InputStream audioSrc = getClass().getResourceAsStream(ruta);
            InputStream bufferedIn = new java.io.BufferedInputStream(audioSrc);

            AudioInputStream audio = AudioSystem.getAudioInputStream(bufferedIn);

            clip = AudioSystem.getClip();
            clip.open(audio);

            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void detener() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}