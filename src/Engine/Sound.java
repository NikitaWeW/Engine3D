package Engine;

import java.util.ArrayList;

public class Sound {
    private String path; //only .wav
    //some sound parameters
    private ArrayList<Code> listeners = new ArrayList<>();

    public Sound(String path) {
        this.path = path;
    }
    public Sound() {}


    //getters
    public String getPath() {
        return path;
    }
    public ArrayList<Code> getListeners() {
        return listeners;
    }


    //setters
    public void setPath(String path) {
        this.path = path;
    }
    public void addListener(Code listener) {
        listeners.add(listener);
    }
    public void removeListener(Code listener) {
        listeners.remove(listener);
    }

    public void play() {}
    public void play(long millis) {}
    public void play(long millis, long nanos) {}

    public void pause() {}

    public void stop() {}

    public void record() {}
    public void record(long timer) {}

    public void pauseRecording() {}

    public void stopRecording() {}
}
