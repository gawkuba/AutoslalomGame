package p02.game;

public class StartEvent extends GameEvent {
    private final int digit;

    public StartEvent() {
        this.digit = 0;
    }

    public int getDigit() {
        return digit;
    }
}