package p02.game;

// nasłuchuje zdarzeń w grze
public interface GameEventListener {
    void handleEvent(GameEvent event);
}
