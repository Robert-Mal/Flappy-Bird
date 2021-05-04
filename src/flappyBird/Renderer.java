package flappyBird;

import java.awt.*;
import java.io.IOException;

public class Renderer extends javax.swing.JPanel {
    private static final long serialVersionUID = 1L;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            FlappyBird.flappyBird.repaint(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
