package p02.pres;

import javax.swing.*;
import java.awt.*;
import p02.game.GameEvent;
import p02.game.GameEventListener;
import p02.game.StartEvent;
import p02.game.PlusOneEvent;
import p02.game.ResetEvent;

public class SevenSegmentDigit extends JPanel implements GameEventListener {
    private static final int DIGIT_WIDTH = 20;
    private static final int DIGIT_HEIGHT = 40;
    private static final int DIGIT_PADDING = 5;


    private int value;
    private SevenSegmentDigit nextDigit;

    public SevenSegmentDigit() {
        this.value = 0;
    }

    public void setNextDigit(SevenSegmentDigit nextDigit) {
        this.nextDigit = nextDigit;
    }

    public void setValue(int value) {
        this.value = value;
        repaint();
    }

    public int getValue() {
        return this.value;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        System.out.println("Current value: " + value);

        switch (value) {
            case 0:
                drawZero(g);
                break;
            case 1:
                drawOne(g);
                break;
            case 2:
                drawTwo(g);
                break;
            case 3:
                drawThree(g);
                break;
            case 4:
                drawFour(g);
                break;
            case 5:
                drawFive(g);
                break;
            case 6:
                drawSix(g);
                break;
            case 7:
                drawSeven(g);
                break;
            case 8:
                drawEight(g);
                break;
            case 9:
                drawNine(g);
                break;
        }
    }

    private void drawZero(Graphics g) {
        g.drawRect(DIGIT_PADDING, DIGIT_PADDING, DIGIT_WIDTH, DIGIT_HEIGHT);
    }

    private void drawOne(Graphics g) {
        g.drawLine(DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT);
    }

    private void drawTwo(Graphics g) {
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING);
        g.drawLine(DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT / 2);
        g.drawLine(DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT / 2, DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT / 2);
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT / 2, DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT);
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT);
    }

    private void drawThree(Graphics g) {
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING);
        g.drawLine(DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT);
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT / 2, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT / 2);
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT);
    }

    private void drawFour(Graphics g) {
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING, DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT / 2);
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT / 2, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT / 2);
        g.drawLine(DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT);
    }

    private void drawFive(Graphics g) {
        g.drawLine(DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING, DIGIT_PADDING, DIGIT_PADDING);
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING, DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT / 2);
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT / 2, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT / 2);
        g.drawLine(DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT / 2, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT);
        g.drawLine(DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT, DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT);
    }

    private void drawSix(Graphics g) {
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING, DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT);
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING);
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT / 2, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT / 2);
        g.drawLine(DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT / 2, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT);
        g.drawLine(DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT, DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT);
    }

    private void drawSeven(Graphics g) {
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING);
        g.drawLine(DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT);
    }

    private void drawEight(Graphics g) {
        g.drawRect(DIGIT_PADDING, DIGIT_PADDING, DIGIT_WIDTH, DIGIT_HEIGHT);
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT / 2, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT / 2);
    }

    private void drawNine(Graphics g) {
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING);
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING, DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT / 2);
        g.drawLine(DIGIT_PADDING, DIGIT_PADDING + DIGIT_HEIGHT / 2, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT / 2);
        g.drawLine(DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING, DIGIT_PADDING + DIGIT_WIDTH, DIGIT_PADDING + DIGIT_HEIGHT);
    }

    @Override
    public void handleEvent(GameEvent e) {
        System.out.println("handleEvent method called in SevenSegmentDigit with event: " + e);
        if (e instanceof PlusOneEvent) {
            this.value = ((PlusOneEvent) e).getDigit();
            System.out.println("Updated value: " + this.value);
            if (this.value == 10) {
                this.value = 0;
                if (nextDigit != null) {
                    nextDigit.handleEvent(new PlusOneEvent(0));
                }
            }
        } else if (e instanceof ResetEvent) {
            this.value = 0;
        }
        repaint();
    }

    public void draw(Graphics g) {
        paintComponent(g);
    }
}