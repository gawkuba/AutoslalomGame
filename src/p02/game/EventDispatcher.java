package p02.game;

import java.util.ArrayList;
import java.util.List;

public class EventDispatcher {
    private static EventDispatcher instance;
    private final List<GameEventListener> listeners;

    EventDispatcher() {
        this.listeners = new ArrayList<>();
    }

    public static EventDispatcher getInstance() {
        if (instance == null) {
            instance = new EventDispatcher();
        }
        return instance;
    }

    public void addListener(GameEventListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(GameEventListener listener) {
        this.listeners.remove(listener);
    }

    public void dispatchEvent(GameEvent event) {
        for (GameEventListener listener : listeners) {
            listener.handleEvent(event);
        }
    }
}