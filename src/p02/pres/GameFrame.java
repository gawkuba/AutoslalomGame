package p02.pres;

import p02.game.*;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        Board board = new Board();
        GamePanel gamePanel = new GamePanel(board);

        setTitle("Autoslalom Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(660, 400);
        add(gamePanel);
        setResizable(false);
        setVisible(true);

        GameThread gameThread = new GameThread(board, EventDispatcher.getInstance(), 1000);
        BackgroundThread backgroundThread = new BackgroundThread(gamePanel, board);
        CounterThread counterThread = new CounterThread(gamePanel.getCounter(), board);

        gameThread.start();
        backgroundThread.start();
        counterThread.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}