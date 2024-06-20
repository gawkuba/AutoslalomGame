// Klasa Board
package p02.game;

import p02.pres.SevenSegmentDigit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Random;

public class Board extends JPanel implements KeyListener {
    private static final int BOARD_SIZE = 7;
    private static final int CAR_POSITION = 0;

    private int[] board;
    private final Random rand;
    private final Image carImage;
    private final Image[] obstacleImages;
    private final EventDispatcher eventDispatcher;
    private GameThread gameThread;
    private final Image[] backgroundImages;
    private int currentTick;
    private Image backgroundImage;
    private boolean collisionOccurred;
    private int score;
    private boolean gameStarted;
    private int tickCountSinceLastObstacle;

    public Board() {
        this.board = new int[BOARD_SIZE];
        this.rand = new Random();
        this.carImage = loadImage("../pres/assets/car/car.jng");  // Load the car image
        this.score = 0;
        this.collisionOccurred = false;
        this.gameStarted = false;
        this.obstacleImages = new Image[] {
                loadImage("../pres/assets/obstacles/obstacleLarge.jpg"),
                loadImage("../pres/assets/obstacles/obstacleMedium.jpg"),
                loadImage("../pres/assets/obstacles/obstacleSmall.jpg"),
        };  // Load obstacle images
        this.eventDispatcher = new EventDispatcher();
        SevenSegmentDigit sevenSegmentDigit = new SevenSegmentDigit();
        this.eventDispatcher.addListener(sevenSegmentDigit);
        addKeyListener(this);
        setFocusable(true);
        startGame();
        this.backgroundImages = new Image[] {
                loadImage("../pres/assets/tracks/track1.jpg"),
                loadImage("../pres/assets/tracks/track2.jpg"),
                loadImage("../pres/assets/tracks/track3.jpg"),
                loadImage("../pres/assets/tracks/track4.jpg"),
        };
        this.tickCountSinceLastObstacle = 0;
    }

    private Image loadImage(String path) {
        URL resource = getClass().getResource(path);
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        } else {
            return null;
        }
    }

    private void startGame() {
        if (gameThread == null) {
            gameThread = GameThread.getInstance(this, eventDispatcher, 1000);
            gameThread.start();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background
        g.drawImage(backgroundImage, 0, 0, null);
        // Draw the car
        g.drawImage(carImage, getWidth() / 2, getHeight() - 100, null);
        // Draw obstacles
        for (int i = 1; i < BOARD_SIZE; i++) {
            if (board[i] != 0) {
                g.drawImage(obstacleImages[board[i] - 1], i * 50, i * 50, null);
            }
        }
    }

    public Image getObstacleImage(int index) {
        return obstacleImages[index];
    }

    public Image getCarImage() {
        return carImage;
    }

    public int getCarPosition() {
        return board[CAR_POSITION];
    }

    public boolean hasCollisionOccurred() {
        return collisionOccurred;
    }

    public int getScore() {
        return score;
    }

    private void updateBoard() {
        // Update background image
        int backgroundIndex = currentTick % backgroundImages.length;
        backgroundImage = backgroundImages[backgroundIndex];
        // Move obstacles down the board
        int[] tempBoard = new int[BOARD_SIZE];
        System.arraycopy(board, 1, tempBoard, 2, BOARD_SIZE - 2);
        board = tempBoard;
        // Generate new obstacle
        int newObstacle = 0;
        tickCountSinceLastObstacle++;
        int scoreDigit = score % 10;
        if ((scoreDigit == 0 && tickCountSinceLastObstacle % 4 == 0) ||
                (scoreDigit > 0 && scoreDigit < 10 && tickCountSinceLastObstacle % 3 == 0) ||
                (scoreDigit == 10 && tickCountSinceLastObstacle % 2 == 0)) {
            newObstacle = generateObstacle();
            tickCountSinceLastObstacle = 0;
        }
        // Check if the new obstacle violates the rules
        if ((board[2] != 0 && board[3] != 0) || (board[1] == newObstacle)) {
            newObstacle = 0;  // If it does, make the new obstacle a free space
        }
        board[1] = newObstacle;
        // Check for collisions
        if ((board[CAR_POSITION] & board[1]) != 0) {
            collisionOccurred = true;
            eventDispatcher.dispatchEvent(new ResetEvent());
        }
    }

    private int generateObstacle() {
        int obstacle = rand.nextInt(3); // Generate a random obstacle

        // Check if the obstacle violates the first rule
        if (board[2] == obstacle && board[3] == obstacle) {
            obstacle = (obstacle + 1) % 3;  // If it does, make the new obstacle at a different position
        }

        // Check if the obstacle violates the second rule
        if (board[1] == obstacle) {
            obstacle = (obstacle + 1) % 3;  // If it does, make the new obstacle at a different position
        }

        return obstacle;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            // Move car left
            if (board[CAR_POSITION] > 0) {
                board[CAR_POSITION]--;
            }
        } else if (key == KeyEvent.VK_D) {
            // Move car right
            if (board[CAR_POSITION] < BOARD_SIZE - 1) {
                board[CAR_POSITION]++;
            }
        } else if (key == KeyEvent.VK_S) {
            // Start the game
            if (!gameStarted) {
                startGame();
                gameStarted = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public void tick() {
        currentTick++;
        updateBoard();
    }

    public int[] getBoard() {
        return board;
    }
}