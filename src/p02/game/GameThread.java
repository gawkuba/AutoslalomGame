package p02.game;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// klasa GameThread odpowiada za wątek gry
public class GameThread extends Thread {
    private final Board board;
    private final EventDispatcher eventDispatcher;
    private boolean running;
    private static GameThread instance;
    private final int someIntValue;
    private final CyclicBarrier barrier;
    private int currentTick = 0;

    public GameThread(Board board, EventDispatcher eventDispatcher, int someIntValue, CyclicBarrier barrier) {
        this.board = board;
        this.eventDispatcher = eventDispatcher;
        this.running = true;
        this.someIntValue = someIntValue;
        this.barrier = barrier;
        this.currentTick = 0;
    }

    public static GameThread getInstance(Board board, EventDispatcher eventDispatcher, int someIntValue, CyclicBarrier barrier) {
        if (instance == null) {
            instance = new GameThread(board, eventDispatcher, someIntValue, barrier);
        }
        return instance;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
                board.tick();
                eventDispatcher.dispatchEvent(new TickEvent(currentTick));
                if (board.getScore() >= 999 || board.hasCollisionOccurred()) {
                    stopGame();
                }
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopGame() {
        this.running = false;
    }
}