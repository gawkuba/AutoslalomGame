package p02.game;

import p02.pres.GamePanel;

public class BackgroundThread extends Thread {
    private final GamePanel gamePanel;
    private final Board board;
    private boolean running;

    public BackgroundThread(GamePanel gamePanel, Board board) {
        this.gamePanel = gamePanel;
        this.board = board;
        this.running = true;
    }

    @Override
    public synchronized void run() {
        while (running) {
            try {
                Thread.sleep(1000);  // Aktualizuj co sekundę
                gamePanel.updateBackground();
                if (board.getScore() >= 999 || board.hasCollisionOccurred()) {
                    stopBackground();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopBackground() {
        this.running = false;
    }
}