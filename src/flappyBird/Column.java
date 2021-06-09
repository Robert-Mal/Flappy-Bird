package flappyBird;

import javax.swing.*;
import java.awt.*;

public class Column {
    public int x, y, bottomX, bottomY;
    public int width, height;
    public Image pipeTop = new ImageIcon(new Settings().PIPE_TOP_IMAGE).getImage();
    public Image pipeBottom = new ImageIcon(new Settings().PIPE_BOTTOM_IMAGE).getImage();
    public boolean canGivePoint = true;

    public Column(int topX, int topY) {
        this.width = pipeTop.getWidth(null);
        this.height = pipeTop.getHeight(null);
        this.x = topX;
        this.y = topY;
        int space = 150;
        this.bottomX = topX;
        this.bottomY = topY + height + space;
    }
}
