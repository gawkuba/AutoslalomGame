package p02;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SimpleGame extends JFrame implements KeyListener {
    private static final int BOARD_SIZE = 7;
    private int carPosition = 1;  // Car starts in the middle
    private String[] board;
    private JTable table;
    private Timer timer;

    public SimpleGame() {
        this.board = new String[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = "012";  // Initialize the board with no obstacles
        }

        DefaultTableModel model = new DefaultTableModel(BOARD_SIZE, 1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // This causes all cells to be not editable
            }
        };
        table = new JTable(model);
        table.setRowHeight(50);  // Increase row height
        table.setShowGrid(true);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        add(new JScrollPane(table));

        addKeyListener(this);
        setFocusable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(660, 400);  // Set window size to 660x400
        setVisible(true);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();
            }
        });
        timer.start();
    }

    private String generateObstacle() {
        int freeSpace = (int) (Math.random() * 3);
        return freeSpace == 0 ? "0==" : freeSpace == 1 ? "=1=" : "==2";
    }

    private void updateGame() {
        // Move obstacles down the board
        System.arraycopy(board, 0, board, 1, BOARD_SIZE - 1);
        // Generate new obstacle at the top of the board
        board[1] = generateObstacle();
        // Check for collisions
        checkCollision();
        // Update the game board
        updateBoard();
    }

    private void checkCollision() {
        if (board[BOARD_SIZE - 1].charAt(carPosition) == '=') {
            JOptionPane.showMessageDialog(this, "Collision occurred!");
            timer.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            // Move car left
            if (carPosition > 0) {
                carPosition--;
            }
        } else if (key == KeyEvent.VK_D) {
            // Move car right
            if (carPosition < 2) {
                carPosition++;
            }
        }
        updateBoard();  // Update the board immediately after key press
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    private void updateBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (i == BOARD_SIZE - 1) {
                table.setValueAt(String.valueOf(carPosition), i, 0);
            } else {
                table.setValueAt(board[i], i, 0);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleGame::new);
    }
}