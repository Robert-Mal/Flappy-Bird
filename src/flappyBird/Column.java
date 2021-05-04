package flappyBird;

import javax.swing.*;
import java.awt.*;

public class Column {
    public int x, y, bottomX, bottomY;
    public int width, height;
    public Image image;
    public Image rotatedImage;
    public boolean canGivePoint = true;

    public Column(int topX, int topY) {
        ImageIcon imageIcon = new ImageIcon("src/images/pipe.png");
        image = imageIcon.getImage();
        imageIcon = new ImageIcon("src/images/bottomPipe.png");
        rotatedImage = imageIcon.getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.x = topX;
        this.y = topY;
        int space = 150;
        this.bottomX = topX;
        this.bottomY = topY + height + space;
    }
}
