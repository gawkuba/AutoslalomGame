package p02.game;

// klasa odpowiadająca za resetowanie gry
public class ResetEvent extends GameEvent {
    private final int digit;

    public ResetEvent() {
        this.digit = 0;
    }
}