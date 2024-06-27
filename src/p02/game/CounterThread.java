package p02.game;

import p02.pres.Counter;

// wątek odpowiedzialny za zliczanie czasu
public class CounterThread extends Thread {
    private final Counter counter;
    private final Board board;
    private boolean running;

    public CounterThread(Counter counter, Board board) {
        this.counter = counter;
        this.board = board;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
                counter.increment();
                if (board.getScore() >= 999 || board.hasCollisionOccurred()) {
                    stopCounter();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopCounter() {
        this.running = false;
    }
}