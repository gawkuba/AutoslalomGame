package p02.pres;

import p02.game.Board;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GamePanel extends JPanel {
    private final Timer timer;
    private final Image[] backgroundImages;
    private int currentBackgroundIndex;
    private boolean gameStarted;
    private int backgroundCounter = 0;
    private final Board board;
    private final Image carImage;
    private final Image[] obstacleImages;
    private final int[][] carPositions = {{100, 285}, {212, 285}, {385, 285}};  // Pozycje dla samochodu
    private final int[][][] obstaclePositions = {  // Pozycje dla przeszkód
            {  // Duże przeszkody
                    {235, 252}, {265, 213},  // Pozycja lewa
                    {330, 252}, {360, 213},  // Pozycja środkowa
                    {425, 252}, {455, 213}   // Pozycja prawa
            },
            {  // Średnie przeszkody
                    {355, 143}, {415, 103},  // Pozycja lewa
                    {425, 143}, {470, 103},  // Pozycja środkowa
                    {495, 143}, {525, 103}   // Pozycja prawa
            },
            {  // Małe przeszkody
                    {497, 45}, {531, 15},  // Pozycja lewa
                    {530, 45}, {563, 15},  // Pozycja środkowa
                    {563, 45}, {595, 15}   // Pozycja prawa
            }
    };

    public GamePanel(Board board) {
        this.board = board;
        this.timer = new Timer(1000, _ -> updateGame());

        backgroundImages = new Image[] {
                loadImage("../pres/assets/tracks/track1.jpg"),
                loadImage("../pres/assets/tracks/track2.jpg"),
                loadImage("../pres/assets/tracks/track3.jpg"),
                loadImage("../pres/assets/tracks/track4.jpg"),
        };
        carImage = loadImage("../pres/assets/car.png");
        obstacleImages = new Image[] {
                loadImage("../pres/assets/obstacle1.png"),
                loadImage("../pres/assets/obstacle2.png"),
                loadImage("../pres/assets/obstacle3.png"),
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
                int obstacleSize = gameBoard[i] - 1;
                int obstacleY = obstaclePositions[obstacleSize][i][1];
                int obstacleX = obstaclePositions[obstacleSize][i][0];
                g.drawImage(obstacleImages[obstacleSize], obstacleX, obstacleY, null);
            }
        }
        int carPosition = board.getCarPosition();
        g.drawImage(carImage, carPositions[carPosition][0], carPositions[carPosition][1], null);

        // Rysowanie licznika SevenSegmentDigit w lewym górnym rogu
        int score = board.getScore();
        String scoreString = String.format("%03d", score);  // Formatujemy wynik na 3 cyfry
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(scoreString, 10, 30);  // Rysujemy wynik w lewym górnym rogu
    }
    public void updateBackground() {
        // Logic to update the background image
        currentBackgroundIndex = backgroundCounter % backgroundImages.length;
        backgroundCounter++;
    }
}