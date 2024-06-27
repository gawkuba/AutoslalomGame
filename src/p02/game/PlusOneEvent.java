package p02.game;

// klasa odpowiadająca za event dodawania liczby
public class PlusOneEvent extends GameEvent {
    private final int digit;

    public PlusOneEvent(int currentDigit) {
        this.digit = (currentDigit + 1) % 10;
    }

    public int getDigit() {
        return digit;
    }
}