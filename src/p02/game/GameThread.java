// Klasa GameThread
package p02.game;

public class GameThread extends Thread {
    private static GameThread instance;
    private final Board board;
    private int interval;
    private boolean running;

    private GameThread(Board board, EventDispatcher eventDispatcher, int interval) {
        this.board = board;
        this.interval = interval;
        this.running = true;
    }

    public static GameThread getInstance(Board board, EventDispatcher eventDispatcher, int interval) {
        if (instance == null) {
            instance = new GameThread(board, eventDispatcher, interval);
        }
        return instance;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(interval);
                board.tick();
                interval = Math.max(100, interval - 10);  // zwiększa stopniowo prędkość gry

                // sprawdza czy wynik jest 999 lub czy doszło do kolizji
                if (board.getScore() >= 999 || board.hasCollisionOccurred()) {
                    stopGame();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopGame() {
        running = false;
    }
}