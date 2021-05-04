package flappyBird;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Background extends JPanel {

    Image image;

    public Background() {
        ImageIcon imageIcon = new ImageIcon("src/images/background.png");
        image = imageIcon.getImage();

        int width = image.getWidth(this);
        int height = image.getHeight(this);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, null);

        try {
            FlappyBird.flappyBird.repaint(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
