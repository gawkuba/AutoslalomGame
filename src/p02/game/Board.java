package p02.game;

import p02.pres.Counter;
import p02.pres.GamePanel;
import p02.pres.SevenSegmentDigit;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// TODO 3: The car is mobing correctly but the posiibility of moving should be disabled when the game is over
// TODO 4: The counter is not working properly
// TODO 5: The game is not synchronized with graphics and counter

public class Board implements KeyListener {
    public static final int BOARD_SIZE = 7;
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
    private final Lock lock = new ReentrantLock();
    private final CyclicBarrier barrier;
    private GamePanel gamePanel;
    SevenSegmentDigit hundreds = new SevenSegmentDigit();
    SevenSegmentDigit tens = new SevenSegmentDigit();
    SevenSegmentDigit ones = new SevenSegmentDigit();

    public Board(CyclicBarrier barrier) {
        this.barrier = barrier;
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
            gameThread = GameThread.getInstance(this, eventDispatcher, 1000, barrier);
            gamePanel = new GamePanel(this, lock); // Modify this line
            backgroundThread = new BackgroundThread(gamePanel, this, barrier);

            counterThread = new CounterThread(Counter.getInstance(), this);

            gameThread.start();
            gamePanel.startBackground(); // Add this line
            backgroundThread.start();
            counterThread.start();
        }
    }

    private void updateBoard() {
        lock.lock();
        try {
            // Store the car position
            int carPosition = board[CAR_POSITION];

            // Shift the obstacles towards the car
            for (int i = 1; i < BOARD_SIZE; i++) {
                board[i - 1] = board[i];
            }

            // Restore the car position
            board[CAR_POSITION] = carPosition;

            // Determine the frequency of obstacle generation based on the score
            int score = getScore();
            int frequency;
            if (score < 10) {
                frequency = 4;
            }
            else if (score < 100) {
                frequency = 3;
            }
            else {
                frequency = 2;
            }

            // Generate a new obstacle at index 6
            tickCountSinceLastObstacle++;
            if (tickCountSinceLastObstacle % frequency == 0) {
                board[6] = generateObstacle();
                tickCountSinceLastObstacle = 0;
            }
            else {
                board[6] = 0;
            }

            // Check for a collision
            if ((board[CAR_POSITION] & board[1]) != 0) {
                collisionOccurred = true;
                eventDispatcher.dispatchEvent(new ResetEvent());
                gameThread.stopGame();
            }
        }
        finally {
            lock.unlock();
        }
    }

    private int generateObstacle() {
        int[] lanes = {1, 2, 4};
        int newObstacle;
        do {
            newObstacle = lanes[rand.nextInt(lanes.length)];
        } while (newObstacle == board[6]);
        return newObstacle;
    }

    public int getCarPosition() {
        return board[CAR_POSITION];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_D) {
            // Move the car to the left
            if (board[CAR_POSITION] > 1) {
                board[CAR_POSITION] /= 2;
            }
        } else if (key == KeyEvent.VK_A) {
            // Move the car to the right
            if (board[CAR_POSITION] < 4) {
                board[CAR_POSITION] *= 2;
            }
        } else if (key == KeyEvent.VK_S) {
            // Start the game
            if (!gameStarted) {
                startGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public void tick() {
        lock.lock();
        try {
            currentTick++;
            updateBoard();
            System.out.println(Arrays.toString(board));
            if (!collisionOccurred) {
                score++;
                eventDispatcher.dispatchEvent(new PlusOneEvent(score));
            }
        }
        finally {
            lock.unlock();
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

    public Lock getLock() {
        return lock;
    }
}