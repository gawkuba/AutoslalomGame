package p02.game;

import p02.pres.Counter;
import p02.pres.GamePanel;
import p02.pres.SevenSegmentDigit;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Board implements KeyListener {
    private static final int BOARD_SIZE = 7;
    private static final int CAR_POSITION = 0;

    private int[] board;
    private final Random rand;
    private final EventDispatcher eventDispatcher;
    private GameThread gameThread;
    private BackgroundThread backgroundThread;
    private CounterThread counterThread;
    private boolean collisionOccurred;
    private int score;
    private boolean gameStarted;
    private int tickCountSinceLastObstacle;
    private int currentTick;

    public Board() {
        this.board = new int[BOARD_SIZE];
        this.rand = new Random();
        this.score = 0;
        this.collisionOccurred = false;
        this.gameStarted = false;
        this.eventDispatcher = new EventDispatcher();
        SevenSegmentDigit sevenSegmentDigit = new SevenSegmentDigit();
        this.eventDispatcher.addListener(sevenSegmentDigit);
        this.tickCountSinceLastObstacle = 0;
        this.currentTick = 0;
    }

    private void startGame() {
        if (!gameStarted) {
            gameStarted = true;
            board[CAR_POSITION] = 2;
            gameThread = GameThread.getInstance(this, eventDispatcher, 1000);
            backgroundThread = new BackgroundThread(new GamePanel(this), this);
            counterThread = new CounterThread(new Counter(), this);

            gameThread.start();
            backgroundThread.start();
            counterThread.start();
        }
    }

    private void updateBoard() {
        int[] tempBoard = new int[BOARD_SIZE];
        System.arraycopy(board, 1, tempBoard, 2, BOARD_SIZE - 2);
        board = tempBoard;
        int newObstacle = 0;
        tickCountSinceLastObstacle++;
        int scoreDigit = score % 10;
        if ((scoreDigit == 0 && tickCountSinceLastObstacle % 4 == 0) ||
                (scoreDigit > 0 && scoreDigit < 10 && tickCountSinceLastObstacle % 3 == 0) ||
                (scoreDigit == 10 && tickCountSinceLastObstacle % 2 == 0)) {
            newObstacle = generateObstacle();
            tickCountSinceLastObstacle = 0;
        }
        board[1] = newObstacle;
        if ((board[CAR_POSITION] & board[1]) != 0) {
            collisionOccurred = true;
            eventDispatcher.dispatchEvent(new ResetEvent());
            gameThread.stopGame();
        }
    }

    private int generateObstacle() {
        int obstacle = rand.nextInt(8);
        return obstacle;
    }

    public int getCarPosition() {
        return board[CAR_POSITION];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            if (board[CAR_POSITION] > 1) {
                board[CAR_POSITION] >>= 1;
            }
        } else if (key == KeyEvent.VK_D) {
            if (board[CAR_POSITION] < 4) {
                board[CAR_POSITION] <<= 1;
            }
        } else if (key == KeyEvent.VK_S) {
            startGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public void tick() {
        currentTick++;
        updateBoard();
        if (!collisionOccurred) {
            score++;
            eventDispatcher.dispatchEvent(new PlusOneEvent(score));
        }
    }

    public int[] getBoard() {
        return board;
    }

    public boolean hasCollisionOccurred() {
        return collisionOccurred;
    }

    public int getScore() {
        return score;
    }
}