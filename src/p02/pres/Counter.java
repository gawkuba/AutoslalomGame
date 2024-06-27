package p02.pres;

import p02.game.GameEvent;
import p02.game.GameEventListener;
import p02.game.PlusOneEvent;
import p02.game.ResetEvent;

import javax.swing.*;
import java.awt.*;

public class Counter extends JPanel implements GameEventListener {
    private static Counter instance;
    private final SevenSegmentDigit hundreds;
    private final SevenSegmentDigit tens;
    private final SevenSegmentDigit ones;

    Counter() {
        this.hundreds = new SevenSegmentDigit();
        this.tens = new SevenSegmentDigit();
        this.ones = new SevenSegmentDigit();
        this.hundreds.setValue(0);
        this.tens.setValue(0);
        this.ones.setValue(0);

        add(hundreds);
        add(tens);
        add(ones);

        // Set the bounds of the SevenSegmentDigit instances
        hundreds.setBounds(10, 10, 30, 50); // Adjust these values as needed
        tens.setBounds(40, 10, 30, 50); // Adjust these values as needed
        ones.setBounds(70, 10, 30, 50); // Adjust these values as needed
    }

    public static Counter getInstance() {
        if (instance == null) {
            instance = new Counter();
        }
        return instance;
    }

    public void increment() {
        ones.handleEvent(new PlusOneEvent(ones.getValue()));
        if (ones.getValue() == 0) {
            tens.handleEvent(new PlusOneEvent(tens.getValue()));
            if (tens.getValue() == 0) {
                hundreds.handleEvent(new PlusOneEvent(hundreds.getValue()));
            }
        }
    }

    public void reset() {
        hundreds.setValue(0);
        tens.setValue(0);
        ones.setValue(0);
    }

    public void draw(Graphics g) {
        int digitWidth = 30;  // Adjust this value as needed
        int digitHeight = 50; // Adjust this value as needed
        int padding = 10; // Adjust this value as needed

        hundreds.setBounds(padding, padding, digitWidth, digitHeight);
        hundreds.draw(g);
        tens.setBounds(padding + digitWidth, padding, digitWidth, digitHeight);
        tens.draw(g);
        ones.setBounds(padding + 2 * digitWidth, padding, digitWidth, digitHeight);
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

    public String getValue() {
        return hundreds.getValue() + "" + tens.getValue() + ones.getValue();
    }
}