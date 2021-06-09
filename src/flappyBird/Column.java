package flappyBird;

import javax.swing.*;
import java.awt.*;
/** The column class
        * Implements column object.
        *
 */
public class Column {
    /**
     specified position value for top column object(x, y) and bottom column object(bottomX, bottomY)
     */
    public int x, y, bottomX, bottomY;
    /**
     specified height value for bird object, value is charged from image,
     specified width value for bird object, value is charged from image
     */
    public int width, height;
    /**
     variable defines image of top pipe object
     */
    public Image pipeTop = new ImageIcon(new Settings().PIPE_TOP_IMAGE).getImage();
    /**
     variable defines image of bottom pipe object
     */
    public Image pipeBottom = new ImageIcon(new Settings().PIPE_BOTTOM_IMAGE).getImage();
    /**
     variable defines boolean value, if it's true it means, that point can be added to player score, otherwise point can't
     be added
     */
    public boolean canGivePoint = true;

    /**
     * Constructs a <code>Column</code> object for column-obstacle.
     * @param topX: specified value of begin column-obstacle
     * @param topY: specified value of end column-obstacle
     */

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
