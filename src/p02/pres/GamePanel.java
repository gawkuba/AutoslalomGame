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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

// TODO: change slightly hashmap positions for obstacles

public class GamePanel extends JPanel {
    private static final int BOARD_SIZE = 7;
    private final Timer timer;
    private final Counter counter;
    private final Image[] backgroundImages;
    private AtomicInteger currentBackgroundIndex;
    private boolean gameStarted;
    private final Board board;
    private final Image carImage;
    private final Image[] obstacleImages;
    private final Map<CompositeKey, int[]> obstaclePositions;
    private final Lock lock;
    private final Map<Integer, int[]> carPositions;
    private Timer repaintTimer;


    public GamePanel(Board board, Lock lock) {
        this.board = board;
        this.counter = new Counter();
        this.gameStarted = false;
        this.lock = lock;
        currentBackgroundIndex = new AtomicInteger(0);

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

        // Initialize the obstacle positions
        this.obstaclePositions = new HashMap<>();

        // Add positions for value 4 (left lane)
        this.obstaclePositions.put(new CompositeKey(4, 6), new int[]{521, 15});
        this.obstaclePositions.put(new CompositeKey(4, 5), new int[]{477, 45});
        this.obstaclePositions.put(new CompositeKey(4, 4), new int[]{380, 103});
        this.obstaclePositions.put(new CompositeKey(4, 3), new int[]{325, 143});
        this.obstaclePositions.put(new CompositeKey(4, 2), new int[]{225, 213});
        this.obstaclePositions.put(new CompositeKey(4, 1), new int[]{170, 252});

        // Add positions for value 2 (middle lane)
        this.obstaclePositions.put(new CompositeKey(2, 6), new int[]{543, 15});
        this.obstaclePositions.put(new CompositeKey(2, 5), new int[]{520, 45});
        this.obstaclePositions.put(new CompositeKey(2, 4), new int[]{450, 103});
        this.obstaclePositions.put(new CompositeKey(2, 3), new int[]{405, 143});
        this.obstaclePositions.put(new CompositeKey(2, 2), new int[]{340, 213});
        this.obstaclePositions.put(new CompositeKey(2, 1), new int[]{300, 252});

        // Add positions for value 1 (right lane)
        this.obstaclePositions.put(new CompositeKey(1, 6), new int[]{585, 15});
        this.obstaclePositions.put(new CompositeKey(1, 5), new int[]{563, 45});
        this.obstaclePositions.put(new CompositeKey(1, 4), new int[]{505, 103});
        this.obstaclePositions.put(new CompositeKey(1, 3), new int[]{485, 143});
        this.obstaclePositions.put(new CompositeKey(1, 2), new int[]{440, 213});
        this.obstaclePositions.put(new CompositeKey(1, 1), new int[]{425, 252});

        // Initialize the car positions
        this.carPositions = new HashMap<>();
        carPositions.put(1, new int[]{325, 285});
        carPositions.put(2, new int[]{200, 285});
        carPositions.put(4, new int[]{70, 285});

        timer = new Timer(1000, _ -> updateGame());
        timer.start();
        repaintTimer = new Timer(1000, _ -> repaint());
        repaintTimer.start();
    }

    private Image loadImage(String imagePath) {
        URL imageUrl = getClass().getResource(imagePath);
        try {
            assert imageUrl != null;
            Image image = ImageIO.read(imageUrl);
            System.out.println("Loaded image: " + imagePath);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }
    }

    public void startBackground() {
        System.out.println("Starting background...");
        timer.start();
    }

    private void updateGame() {
        if (gameStarted) {
            updateBackground();
            updateCarPosition();
            updateCounter();
        }
        repaint();
    }

    public void updateBackground() {
        lock.lock();
        try {
            System.out.println("Updating background at " + System.currentTimeMillis());
            currentBackgroundIndex.set((currentBackgroundIndex.get() + 1) % backgroundImages.length);
            System.out.println("background index in updateBackground " + currentBackgroundIndex.get());
        } finally {
            lock.unlock();
        }
        SwingUtilities.invokeLater(this::repaint);
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
            System.out.println("Drawing background image " + currentBackgroundIndex.get() + "...");
            g.drawImage(backgroundImages[currentBackgroundIndex.get()], 0, 0, getWidth(), getHeight(), this);
            System.out.println("background index in paint component: " + currentBackgroundIndex);
            int carPosition = board.getCarPosition();
            // Draw the car image at the corresponding position
            if (carPositions != null && carPositions.containsKey(carPosition)) {
                g.drawImage(carImage, carPositions.get(carPosition)[0], carPositions.get(carPosition)[1], null);
            }

            // Draw the obstacles
            int[] boardArray = board.getBoard();
            for (int i = 1; i < BOARD_SIZE; i++) {
                CompositeKey key = new CompositeKey(boardArray[i], i);
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