package p02.game;

import p02.pres.GamePanel;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

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
                Thread.sleep(1000);  // Update every second
                System.out.println("BackgroundThread woke up at " + System.currentTimeMillis());
                gamePanel.updateBackground();
                if (board.getScore() >= 999 || board.hasCollisionOccurred()) {
                    stopBackground();
                }
                barrier.await();  // Wait for the other threads to reach this point
            } catch (InterruptedException | BrokenBarrierException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopBackground() {
        this.running = false;
    }
}