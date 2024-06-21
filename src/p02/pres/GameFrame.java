package p02.pres;

import p02.game.*;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        Board board = new Board();
        GamePanel gamePanel = new GamePanel(board);

        setTitle("Autoslalom Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(660, 400);  // Zmieniamy rozmiar okna na 660x400
        add(gamePanel);  // Dodajemy GamePanel do JFrame

        setVisible(true);

        // Inicjalizujemy i uruchamiamy wątki
        GameThread gameThread = new GameThread(board, EventDispatcher.getInstance(), 1000);
        BackgroundThread backgroundThread = new BackgroundThread(gamePanel, board);
        CounterThread counterThread = new CounterThread(new Counter(), board);

        gameThread.start();
        backgroundThread.start();
        counterThread.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}