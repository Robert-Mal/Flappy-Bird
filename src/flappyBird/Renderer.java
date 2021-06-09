package flappyBird;

import java.awt.*;
import java.io.IOException;
/** The Renderer class
 * Implements Renderer object with JPanel extend.
 *
 */
public class Renderer extends javax.swing.JPanel {
    /**
        Value of the numeric user ID
    */
    private static final long serialVersionUID = 1L;

    /**
     * Render function <code>paintComponent</code> generates flappybird object while game is running.
     */

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
