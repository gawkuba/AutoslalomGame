package p02.pres;

import java.awt.*;

public class Counter extends SevenSegmentDigit {
    private SevenSegmentDigit hundreds;
    private SevenSegmentDigit tens;
    private SevenSegmentDigit ones;

    public Counter() {
        this.hundreds = new SevenSegmentDigit();
        this.tens = new SevenSegmentDigit();
        this.ones = new SevenSegmentDigit();
    }

    public void increment() {
        ones.setValue((ones.getValue() + 1) % 10);
        if (ones.getValue() == 0) {
            tens.setValue((tens.getValue() + 1) % 10);
            if (tens.getValue() == 0) {
                hundreds.setValue((hundreds.getValue() + 1) % 10);
            }
        }
    }

    public void reset() {
        hundreds.setValue(0);
        tens.setValue(0);
        ones.setValue(0);
    }

    public void draw(Graphics g) {
        hundreds.draw(g);
        tens.draw(g);
        ones.draw(g);
    }
}
