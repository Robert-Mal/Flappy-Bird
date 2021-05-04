package flappyBird;

import javax.swing.*;
import java.awt.*;

public class Bird{
    public final int width;
    public final int height;
    public final Image image = new ImageIcon("src/images/bird.png").getImage();
    public int x, y;

    public Bird() {
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.x = 480 / 2 - 25;
        this.y = 640 / 2 - 25;
    }
}
