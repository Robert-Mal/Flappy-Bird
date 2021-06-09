package flappyBird;

import javax.swing.*;
import java.awt.*;

public class Column {
<<<<<<< HEAD
    public int x, y, bottomX, bottomY;
    public int width, height;
    public Image pipeTop = new ImageIcon(new Settings().PIPE_TOP_IMAGE).getImage();
    public Image pipeBottom = new ImageIcon(new Settings().PIPE_BOTTOM_IMAGE).getImage();
    public boolean canGivePoint = true;
=======
    private int x, y, bottomX, bottomY;
  private int width, height;
  private Image image;
  private Image rotatedImage;
  private boolean canGivePoint = true;
>>>>>>> 1c30d682cfac8fa5e67e81def55acffbdd3d3268

    public Column(int topX, int topY) {
        this.width = pipeTop.getWidth(null);
        this.height = pipeTop.getHeight(null);
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
