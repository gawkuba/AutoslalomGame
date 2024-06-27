package p02.pres;

import p02.game.GameEvent;
import p02.game.GameEventListener;
import p02.game.PlusOneEvent;
import p02.game.ResetEvent;

import javax.swing.*;
import java.awt.*;

public class Counter extends JPanel implements GameEventListener {
    private SevenSegmentDigit hundreds;
    private SevenSegmentDigit tens;
    private SevenSegmentDigit ones;

    public Counter() {
        this.hundreds = new SevenSegmentDigit();
        this.tens = new SevenSegmentDigit();
        this.ones = new SevenSegmentDigit();

        // Link the digits
        ones.setNextDigit(tens);
        tens.setNextDigit(hundreds);
    }

    public void increment() {
        ones.handleEvent(new PlusOneEvent(ones.getValue()));
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

    @Override
    public void handleEvent(GameEvent e) {
        if (e instanceof PlusOneEvent) {
            increment();
        } else if (e instanceof ResetEvent) {
            reset();
        }
    }
}