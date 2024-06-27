package p02.game;

import p02.pres.GamePanel;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// klasa BackgroundThread odpowiada za wątek tła
public class BackgroundThread extends Thread {
    private final GamePanel gamePanel;
    private final Board board;
    private boolean running;
    private final CyclicBarrier barrier;

    public BackgroundThread(GamePanel gamePanel, Board board, CyclicBarrier barrier) {
        this.gamePanel = gamePanel;
        this.board = board;
        this.barrier = barrier;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
                System.out.println("BackgroundThread woke up at " + System.currentTimeMillis());
                gamePanel.updateBackground();
                if (board.getScore() >= 999 || board.hasCollisionOccurred()) {
                    stopBackground();
                }
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopBackground() {
        this.running = false;
    }
}