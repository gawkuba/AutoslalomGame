package p02.pres;

import p02.game.Board;

import javax.swing.*;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;

    public GameFrame() {
        Board board = new Board();
        gamePanel = new GamePanel(board);

        setTitle("Autoslalom Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(660, 400);  // Zmieniamy rozmiar okna na 660x400
        add(gamePanel);  // Dodajemy GamePanel do JFrame

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}