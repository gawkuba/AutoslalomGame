package p02.pres;

import p02.game.Board;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GamePanel extends JPanel {
    private final Timer timer;
    private final Counter counter;
    private final Image[] backgroundImages;
    private int currentBackgroundIndex;
    private boolean gameStarted;
    private int backgroundCounter = 0;
    private final Board board;
    private final Image carImage;
    private final Image[] obstacleImages;

    private final int[][] carPositions = {{100, 285}, {212, 285}, {385, 285}};
    private final int[][][] obstaclePositions = {
            {{205, 252}, {245, 213}},
            {{300, 252}, {340, 213}},
            {{405, 252}, {435, 213}}
    };

    public GamePanel(Board board) {
        this.board = board;
        this.counter = new Counter();
        this.timer = new Timer(1000, _ -> updateGame());
        this.counter.setBounds(10, 10, 100, 50);  // Set the position and size of the counter
        this.add(counter);  // Add the counter to the GamePanel

        backgroundImages = new Image[] {
                loadImage("../pres/assets/tracks/track1.jpg"),
                loadImage("../pres/assets/tracks/track2.jpg"),
                loadImage("../pres/assets/tracks/track3.jpg"),
                loadImage("../pres/assets/tracks/track4.jpg"),
        };
        carImage = loadImage("../pres/assets/car/car.png");
        obstacleImages = new Image[] {
                loadImage("../pres/assets/obstacles/obstacleLarge.png"),
                loadImage("../pres/assets/obstacles/obstacleMedium.png"),
                loadImage("../pres/assets/obstacles/obstacleSmall.png"),
        };
        currentBackgroundIndex = 0;
        gameStarted = false;

        timer.start();
    }

    private void updateGame() {
        if (gameStarted) {
            updateBackground();
        }
        repaint();
    }

    private Image loadImage(String path) {
        URL resource = getClass().getResource(path);
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        } else {
            System.err.println("Failed to load image: " + path);
            return null;
        }
    }

    public void startGame() {
        gameStarted = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImages[currentBackgroundIndex], 0, 0, getWidth(), getHeight(), this);

        int[] gameBoard = board.getBoard();
        for (int i = 0; i < gameBoard.length; i++) {
            if (gameBoard[i] != 0) {
                int obstacleSize = (gameBoard[i] - 1) % obstaclePositions.length;
                int obstacleY = obstaclePositions[obstacleSize][i % obstaclePositions[0].length][1];
                int obstacleX = obstaclePositions[obstacleSize][i % obstaclePositions[0].length][0];
                g.drawImage(obstacleImages[obstacleSize % obstacleImages.length], obstacleX, obstacleY, null);
            }
        }
        int carPosition = board.getCarPosition();
        g.drawImage(carImage, carPositions[carPosition][0], carPositions[carPosition][1], null);

        counter.setBounds(10, 10, 100, 50);
        counter.draw(g);
    }

    public Counter getCounter() {
        return counter;
    }

    public void updateBackground() {
        currentBackgroundIndex = backgroundCounter % backgroundImages.length;
        backgroundCounter++;
    }
}