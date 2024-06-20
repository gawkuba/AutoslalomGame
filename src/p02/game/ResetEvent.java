package p02.game;

public class ResetEvent extends GameEvent {
    private final int digit;

    public ResetEvent() {
        this.digit = 0;
    }

    public int getDigit() {
        return digit;
    }
}