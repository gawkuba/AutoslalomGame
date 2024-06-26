package p02.pres;

import p02.game.*;

import javax.swing.*;
import java.util.concurrent.CyclicBarrier;

public class GameFrame extends JFrame {



    public GameFrame() {
        CyclicBarrier barrier = new CyclicBarrier(2);
        Board board = new Board(barrier);
        GamePanel gamePanel = new GamePanel(board, board.getLock());

        setTitle("Autoslalom Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(660, 400);
        add(gamePanel);
        gamePanel.addKeyListener(board);
        gamePanel.setFocusable(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}