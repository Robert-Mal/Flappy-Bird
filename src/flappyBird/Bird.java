package flappyBird;

import javax.swing.*;
import java.awt.*;

public class Bird {
  private final int width;
  private final int height;
  private final Image image = new ImageIcon("src/images/bird.png").getImage();
  private int x, y;

  public Bird() {
    this.width = image.getWidth(null);
    this.height = image.getHeight(null);
    this.x = 480 / 2 - 25;
    this.y = 640 / 2 - 25;
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

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }
}
