package p02.pres;

import javax.swing.*;
import java.awt.*;
import p02.game.GameEvent;
import p02.game.GameEventListener;
import p02.game.StartEvent;
import p02.game.PlusOneEvent;
import p02.game.ResetEvent;

public class SevenSegmentDigit extends JPanel implements GameEventListener {
    private int value; // wartość wyswietlanej cyfry
    private SevenSegmentDigit nextDigit; // następny cyfr w liczniku

    public SevenSegmentDigit() {
        this.value = 0; // początkowa wartość
    }

    public void setNextDigit(SevenSegmentDigit nextDigit) {
        this.nextDigit = nextDigit;
    }

    // metoda ustawiająca wartość wyświetlanej cyfry
    public void setValue(int value) {
        this.value = value;
        repaint();
    }

    // metoda rysująca cyfrę
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        int width = getWidth();
        int height = getHeight();
        int segmentWidth = width / 10;
        int segmentHeight = height / 20;

        // wybór cyfry do narysowania
        switch (value) {
            case 0:
                drawZero(g, segmentWidth, segmentHeight, height);
                break;
            case 1:
                drawOne(g, segmentWidth, height);
                break;
            case 2:
                drawTwo(g, segmentWidth, segmentHeight, height);
                break;
            case 3:
                drawThree(g, segmentWidth, segmentHeight, height);
                break;
            case 4:
                drawFour(g, segmentWidth, segmentHeight, height);
                break;
            case 5:
                drawFive(g, segmentWidth, segmentHeight, height);
                break;
            case 6:
                drawSix(g, segmentWidth, segmentHeight, height);
                break;
            case 7:
                drawSeven(g, segmentWidth, segmentHeight, height);
                break;
            case 8:
                drawEight(g, segmentWidth, segmentHeight, height);
                break;
            case 9:
                drawNine(g, segmentWidth, segmentHeight, height);
                break;
        }
    }

    // metody rysujące poszczególne cyfry
    private void drawZero(Graphics g, int segmentWidth, int segmentHeight, int height) {
        drawHorizontalSegment(g, segmentHeight, 0);
        drawHorizontalSegment(g, segmentHeight, height - segmentHeight);
        drawVerticalSegment(g, segmentWidth, 0);
        drawVerticalSegment(g, segmentWidth, height / 2);
    }

    private void drawOne(Graphics g, int segmentWidth, int height) {
        drawVerticalSegment(g, segmentWidth, height / 2);
    }

    private void drawTwo(Graphics g, int segmentWidth, int segmentHeight, int height) {
        drawHorizontalSegment(g, segmentHeight, 0);
        drawHorizontalSegment(g, segmentHeight, height / 2);
        drawHorizontalSegment(g, segmentHeight, height - segmentHeight);
        drawVerticalSegment(g, segmentWidth, 0);
        drawVerticalSegment(g, segmentWidth, height / 2);
    }

    private void drawThree(Graphics g, int segmentWidth, int segmentHeight, int height) {
        drawHorizontalSegment(g, segmentHeight, 0);
        drawHorizontalSegment(g, segmentHeight, height / 2);
        drawHorizontalSegment(g, segmentHeight, height - segmentHeight);
        drawVerticalSegment(g, segmentWidth, height / 2);
    }

    private void drawFour(Graphics g, int segmentWidth, int segmentHeight, int height) {
        drawHorizontalSegment(g, segmentHeight, height / 2);
        drawVerticalSegment(g, segmentWidth, 0);
        drawVerticalSegment(g, segmentWidth, height / 2);
    }

    private void drawFive(Graphics g, int segmentWidth, int segmentHeight, int height) {
        drawHorizontalSegment(g, segmentHeight, 0);
        drawHorizontalSegment(g, segmentHeight, height / 2);
        drawHorizontalSegment(g, segmentHeight, height - segmentHeight);
        drawVerticalSegment(g, segmentWidth, 0);
        drawVerticalSegment(g, segmentWidth, height / 2);
    }

    private void drawSix(Graphics g, int segmentWidth, int segmentHeight, int height) {
        drawHorizontalSegment(g, segmentHeight, 0);
        drawHorizontalSegment(g, segmentHeight, height / 2);
        drawHorizontalSegment(g, segmentHeight, height - segmentHeight);
        drawVerticalSegment(g, segmentWidth, 0);
    }

    private void drawSeven(Graphics g, int segmentWidth, int segmentHeight, int height) {
        drawHorizontalSegment(g, segmentHeight, 0);
        drawVerticalSegment(g, segmentWidth, height - segmentHeight);
    }

    private void drawEight(Graphics g, int segmentWidth, int segmentHeight, int height) {
        drawHorizontalSegment(g, segmentHeight, 0);
        drawHorizontalSegment(g, segmentHeight, height / 2);
        drawHorizontalSegment(g, segmentHeight, height - segmentHeight);
        drawVerticalSegment(g, segmentWidth, 0);
        drawVerticalSegment(g, segmentWidth, height / 2);
    }

    private void drawNine(Graphics g, int segmentWidth, int segmentHeight, int height) {
        drawHorizontalSegment(g, segmentHeight, 0);
        drawHorizontalSegment(g, segmentHeight, height / 2);
        drawHorizontalSegment(g, segmentHeight, height - segmentHeight);
        drawVerticalSegment(g, segmentWidth, height / 2);
    }

    // metody rysujące poszczególne segmenty
    private void drawHorizontalSegment(Graphics g, int y, int length) {
        g.fillRect(0, y, length, y + length / 2);
    }

    private void drawVerticalSegment(Graphics g, int x, int length) {
        g.fillRect(x, 0, x + length / 2, length);
    }

    // metoda obsługująca zdarzenia
    @Override
    public void handleEvent(GameEvent e) {
        if (e instanceof StartEvent) {
            this.value = ((StartEvent) e).getDigit();
        } else if (e instanceof PlusOneEvent) {
            this.value = ((PlusOneEvent) e).getDigit();
            if (this.value == 0 && nextDigit != null) {
                nextDigit.handleEvent(e);
            }
        } else if (e instanceof ResetEvent) {
            this.value = ((ResetEvent) e).getDigit();
        }
        repaint();
    }
}