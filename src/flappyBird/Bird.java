package flappyBird;

import javax.swing.*;
import java.awt.*;
/** The bird class
        * Implements bird object.
        *
 */

public class Bird{
    /**
     specified width value for bird object, value is charged from image
     */
    public final int width;
    /**
     specified height value for bird object, value is charged from image
     */
    public final int height;
    /**
     variable defines image of bird object
     */
    public final Image image = new ImageIcon(new Settings().BIRD_IMAGE).getImage();
    /**
     specified position values of x and y of bird object
     */
    public int x, y;

    /**
     * Constructs a <code>Bird</code> object with width, height, position x and y values.
     */

    public Bird() {
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.x = 480 / 2 - 25;
        this.y = 640 / 2 - 25;
    }
}
