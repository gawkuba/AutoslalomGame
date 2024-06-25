// Klasa GameThread
package p02.game;

public class GameThread extends Thread {
    private final Board board;
    private final EventDispatcher eventDispatcher;
    private boolean running;
    private static GameThread instance;
    private final int someIntValue;

    public GameThread(Board board, EventDispatcher eventDispatcher, int someIntValue) {
        this.board = board;
        this.eventDispatcher = eventDispatcher;
        this.running = true;
        this.someIntValue = someIntValue;
    }

    public synchronized static GameThread getInstance(Board board, EventDispatcher eventDispatcher, int someIntValue) {
        if (instance == null) {
            instance = new GameThread(board, eventDispatcher, someIntValue);
        }
        return instance;
    }

    @Override
    public synchronized void run() {
        while (running) {
            try {
                Thread.sleep(1000);  // Aktualizuj co sekundę
                board.tick();
                if (board.getScore() >= 999 || board.hasCollisionOccurred()) {
                    stopGame();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopGame() {
        this.running = false;
    }
}