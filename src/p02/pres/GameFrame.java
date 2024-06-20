package p02.pres;

import p02.game.Board;

import javax.swing.*;

public class GameFrame extends JFrame {
    private Board board;

    public GameFrame() {
        board = new Board();

        setTitle("Autoslalom Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(660, 400);  // Zmieniamy rozmiar okna na 660x400
        add(board);  // Dodajemy Board do JFrame

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}