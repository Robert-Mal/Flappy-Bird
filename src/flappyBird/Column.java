package flappyBird;

import javax.swing.*;
import java.awt.*;

public class Column {
    private int x, y, bottomX, bottomY;
  private int width, height;
  private Image image;
  private Image rotatedImage;
  private boolean canGivePoint = true;

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

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getBottomX() {
    return bottomX;
  }

  public int getBottomY() {
    return bottomY;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Image getImage() {
    return image;
  }

  public Image getRotatedImage() {
    return rotatedImage;
  }

  public boolean isCanGivePoint() {
    return canGivePoint;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setCanGivePoint(boolean canGivePoint) {
    this.canGivePoint = canGivePoint;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setBottomX(int bottomX) {
    this.bottomX = bottomX;
  }

  public void setBottomY(int bottomY) {
    this.bottomY = bottomY;
  }
}
