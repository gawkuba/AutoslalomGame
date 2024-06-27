package p02.pres;

import p02.game.*;

import javax.swing.*;
import java.awt.*;

// klasa reprezentująca licznik w grze (wyświetlany w postaci trzech cyfr siedmiosegmentowych)
public class Counter extends JPanel implements GameEventListener {
    private static Counter instance;
    private final SevenSegmentDigit hundreds;
    private final SevenSegmentDigit tens;
    private final SevenSegmentDigit ones;
    private int counterValue = 0;

    private Counter(SevenSegmentDigit hundreds, SevenSegmentDigit tens, SevenSegmentDigit ones) {
        this.hundreds = hundreds;
        this.tens = tens;
        this.ones = ones;

        add(hundreds);
        add(tens);
        add(ones);

        hundreds.setBounds(10, 10, 30, 50);
        tens.setBounds(40, 10, 30, 50);
        ones.setBounds(70, 10, 30, 50);
    }

    public static Counter getInstance(SevenSegmentDigit hundreds, SevenSegmentDigit tens, SevenSegmentDigit ones) {
        if (instance == null) {
            instance = new Counter(hundreds, tens, ones);
        }
        return instance;
    }

    public void increment() {
        counterValue++;
        ones.setValue(counterValue % 10);
        tens.setValue((counterValue / 10) % 10);
        hundreds.setValue((counterValue / 100) % 10);

    }

    public void reset() {
        hundreds.setValue(0);
        tens.setValue(0);
        ones.setValue(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int digitWidth = 30;
        int digitHeight = 50;
        int padding = 10;

        hundreds.setBounds(padding, padding, digitWidth, digitHeight);
        hundreds.paintComponent(g.create(padding, padding, digitWidth, digitHeight));
        tens.setBounds(padding + digitWidth + padding, padding, digitWidth, digitHeight);
        tens.paintComponent(g.create(padding + digitWidth + padding, padding, digitWidth, digitHeight));
        ones.setBounds(padding + 2 * (digitWidth + padding), padding, digitWidth, digitHeight);
        ones.paintComponent(g.create(padding + 2 * (digitWidth + padding), padding, digitWidth, digitHeight));
    }

    @Override
    public void handleEvent(GameEvent e) {
        if (e instanceof PlusOneEvent) {
            increment();
        } else if (e instanceof ResetEvent) {
            reset();
        } else if (e instanceof TickEvent) {

        }
    }

}