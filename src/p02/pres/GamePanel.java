package p02.pres;

import p02.game.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;

public class GamePanel extends JPanel {
    private static final int BOARD_SIZE = 7;
    private final Timer timer;
    private final Counter counter;
    private final Image[] backgroundImages;
    private int currentBackgroundIndex;
    private boolean gameStarted;
    private int backgroundCounter = 0;
    private final Board board;
    private final Image carImage;
    private final Image[] obstacleImages;
    private final Map<CompositeKey, int[]> obstaclePositions;
    private final Lock lock;
    private final Map<Integer, int[]> carPositions;

    public GamePanel(Board board, Lock lock) {
        this.board = board;
        this.counter = new Counter();
        this.gameStarted = false;
        this.obstaclePositions = new HashMap<>();
        this.lock = lock;

        // Initialize the background images
        this.backgroundImages = new Image[]{
                loadImage("../pres/assets/tracks/track1.jpg"),
                loadImage("../pres/assets/tracks/track2.jpg"),
                loadImage("../pres/assets/tracks/track3.jpg"),
                loadImage("../pres/assets/tracks/track4.jpg"),
        };

        // Initialize the car image
        this.carImage = loadImage("../pres/assets/car/car.png");

        // Initialize the obstacle images
        this.obstacleImages = new Image[]{
                loadImage("../pres/assets/obstacles/obstacleLarge.png"),
                loadImage("../pres/assets/obstacles/obstacleMedium.png"),
                loadImage("../pres/assets/obstacles/obstacleSmall.png"),
        };

        Map<Integer, Map<Integer, int[]>> obstaclePositions = new HashMap<>();

// Add positions for value 4 (left lane)
        obstaclePositions.putIfAbsent(4, new HashMap<>());
        obstaclePositions.get(4).put(6, new int[]{531, 15});
        obstaclePositions.get(4).put(5, new int[]{497, 45});
        obstaclePositions.get(4).put(4, new int[]{415, 103});
        obstaclePositions.get(4).put(3, new int[]{355, 143});
        obstaclePositions.get(4).put(2, new int[]{265, 213});
        obstaclePositions.get(4).put(1, new int[]{235, 252});

// Add positions for value 2 (middle lane)
        obstaclePositions.putIfAbsent(2, new HashMap<>());
        obstaclePositions.get(2).put(6, new int[]{563, 15});
        obstaclePositions.get(2).put(5, new int[]{530, 45});
        obstaclePositions.get(2).put(4, new int[]{470, 103});
        obstaclePositions.get(2).put(3, new int[]{425, 143});
        obstaclePositions.get(2).put(2, new int[]{360, 213});
        obstaclePositions.get(2).put(1, new int[]{330, 252});

// Add positions for value 1 (right lane)
        obstaclePositions.putIfAbsent(1, new HashMap<>());
        obstaclePositions.get(1).put(6, new int[]{595, 15});
        obstaclePositions.get(1).put(5, new int[]{563, 45});
        obstaclePositions.get(1).put(4, new int[]{525, 103});
        obstaclePositions.get(1).put(3, new int[]{495, 143});
        obstaclePositions.get(1).put(2, new int[]{455, 213});
        obstaclePositions.get(1).put(1, new int[]{425, 252});

        // Initialize the car positions
        this.carPositions = new HashMap<>();
        carPositions.put(1, new int[]{325, 285});
        carPositions.put(2, new int[]{200, 285});
        carPositions.put(4, new int[]{70, 285});

        timer = new Timer(1000, _ -> updateGame());
        timer.start();
    }

    private Image loadImage(String imagePath) {
        URL imageUrl = getClass().getResource(imagePath);
        try {
            assert imageUrl != null;
            return ImageIO.read(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }
    }

    private void updateGame() {
        if (gameStarted) {
            updateBackground();
            updateCarPosition();
            updateCounter();
        }
        repaint();
    }

    public synchronized void updateBackground() {
        currentBackgroundIndex = (currentBackgroundIndex + 1) % backgroundImages.length;
    }

    private void updateCarPosition() {
        int carPosition = board.getCarPosition();
        // Update the car's position
        carPositions.put(carPosition, carPositions.get(carPosition));
    }

    private void updateCounter() {
        counter.increment();
    }

    @Override
    protected void paintComponent(Graphics g) {
        lock.lock();
        try {
            super.paintComponent(g);
            g.drawImage(backgroundImages[currentBackgroundIndex], 0, 0, getWidth(), getHeight(), this);

            int carPosition = board.getCarPosition();
            // Draw the car image at the corresponding position
            if (carPositions != null && carPositions.containsKey(carPosition)) {
                g.drawImage(carImage, carPositions.get(carPosition)[0], carPositions.get(carPosition)[1], null);
            }

            // Draw the obstacles
            int[] boardArray = board.getBoard();
            for (int i = 1; i < BOARD_SIZE; i++) {
                CompositeKey key = new CompositeKey(boardArray[i], i);
                System.out.println("Obstacle positions: " + obstaclePositions);
                System.out.println("Checking for key: " + key);
                if (obstaclePositions!= null && obstaclePositions.containsKey(key)) {
                    int[] obstaclePosition = obstaclePositions.get(key);
                    Image obstacleImage;
                    if (i == 6 || i == 5) {
                        obstacleImage = obstacleImages[2];  // Small obstacle
                    } else if (i == 4 || i == 3) {
                        obstacleImage = obstacleImages[1];  // Medium obstacle
                    } else {
                        obstacleImage = obstacleImages[0];  // Large obstacle
                    }
                    g.drawImage(obstacleImage, obstaclePosition[0], obstaclePosition[1], null);
                }
            }

            counter.setBounds(10, 10, 100, 50);
            counter.draw(g);
        } finally {
            lock.unlock();
        }
    }

    public Counter getCounter() {
        return counter;
    }

    static class CompositeKey {
        private final int value;
        private final int index;

        public CompositeKey(int value, int index) {
            this.value = value;
            this.index = index;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CompositeKey that = (CompositeKey) o;
            return value == that.value && index == that.index;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, index);
        }
    }
}