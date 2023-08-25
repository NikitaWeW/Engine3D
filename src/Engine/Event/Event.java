package Engine.Event;

import java.util.ArrayList;

public class Event {
    private ArrayList<Listener> listeners = new ArrayList<>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }
    public ArrayList<Listener> getListeners() {
        return listeners;
    }
    public void update() {
        for (Listener listener : listeners) {
            listener.handleEvent(this);
        }
    }
}
