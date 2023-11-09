package Engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.*;

public class Sound {
    private String path; //only .wav
    private ArrayList<Code> listeners = new ArrayList<>();
    private Clip clip;

    public Sound(String path) {
        this.path = path;
        try {
        clip = AudioSystem.getClip();
        } catch(LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public String getPath() {
        return path;
    }
    public ArrayList<Code> getListeners() {
        return listeners;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public void addListener(Code listener) {
        listeners.add(listener);
    }
    public void removeListener(Code listener) {
        listeners.remove(listener);
    }

    public void play() {
        new Thread(() -> {
            try {
            File soundFile = new File(path);

            clip.open(AudioSystem.getAudioInputStream(soundFile));

            clip.start();

            Thread.sleep(clip.getMicrosecondLength() / 1000);

            clip.close();

            } catch(LineUnavailableException e) {
                playConverted();
            } catch (Exception e) {
            e.printStackTrace();
            } finally {
                for(Code listener : listeners)
                    listener.doSomething();
            }
        }).start();
    }
    public void playConverted() {
        try {
            AudioInputStream originalStream = AudioSystem.getAudioInputStream(new File(path));
    
            AudioFormat desiredFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    44100,
                    16,
                    1,     
                    2,    
                    44100,
                    false 
            );
    
            if (AudioSystem.isConversionSupported(desiredFormat, originalStream.getFormat())) {

                AudioInputStream convertedStream = AudioSystem.getAudioInputStream(desiredFormat, originalStream);
    
                Clip clip = AudioSystem.getClip();
    
                clip.open(convertedStream);
    
                clip.start();
                
                Thread.sleep(clip.getMicrosecondLength() / 1000);
    
                clip.close();
                
                convertedStream.close();
            }
    
            originalStream.close();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        clip.stop();
    }
}
