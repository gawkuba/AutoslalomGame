package p02;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class TestImageRenderer {
    public static void main(String[] args) {
        // Create a panel with a background image
        JPanel backgroundPanel = new JPanel() {
            private Image[] images = new Image[4];
            private int imageIndex = 0;

            {
                try {
                    for (int i = 0; i < 4; i++) {
                        URL imageUrl = TestImageRenderer.class.getResource("pres/assets/tracks/track" + (i + 1) + ".jpg");
                        assert imageUrl != null;
                        images[i] = ImageIO.read(imageUrl);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Create a timer that updates the background every second
                Timer timer = new Timer(1000, _ -> {
                    // Update the image index for the next tick
                    imageIndex = (imageIndex + 1) % images.length;
                    repaint();
                });

                // Start the timer
                timer.start();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the image to fill the panel
                g.drawImage(images[imageIndex], 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setBounds(0, 0, 660, 400);

        // Create a panel with the car image
        JPanel carPanel = new JPanel() {
            private Image carImage;

            {
                try {
                    URL imageUrl = TestImageRenderer.class.getResource("pres/assets/Car/car.jpg");
                    assert imageUrl != null;
                    carImage = ImageIO.read(imageUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the car image
                g.drawImage(carImage, 0, 0, this);
            }

            @Override
            public Dimension getPreferredSize() {
                if (carImage != null) {
                    return new Dimension(carImage.getWidth(null), carImage.getHeight(null));
                } else {
                    return super.getPreferredSize();
                }
            }
        };
        carPanel.setOpaque(false);
        int carWidth = carPanel.getPreferredSize().width;
        int carHeight = carPanel.getPreferredSize().height;
        carPanel.setBounds((660 - carWidth) / 2, 400 - carHeight, carWidth, carHeight);

        // Create a layered pane and add the panels to it
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(660, 400));
        layeredPane.add(backgroundPanel, Integer.valueOf(1));
        layeredPane.add(carPanel, Integer.valueOf(2));

        // Create a frame, set the layered pane as its content pane and display it
        JFrame frame = new JFrame("Test ImageRenderer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(layeredPane);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }
}