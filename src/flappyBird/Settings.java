package flappyBird;
/** The settings class
        * Implements settings object.
        *
 */
public class Settings {
    /**
       variable defines height of game frame
     */
    public final int HEIGHT = 640;
    /**
        variable defines width of game frame
     */
    public final int WIDTH = 480;
    /**
     variable defines game speed
     */
    public final int SPEED = 7;

    /**
     variable defines image of background frame
     */
    public final String BACKGROUND_IMAGE = "src/images/background.png";
    /**
     variable defines image of bottom frame part
     */
    public final String GROUND_IMAGE = "src/images/ground.png";
    /**
     variable defines image of top pipe
     */
    public final String PIPE_TOP_IMAGE = "src/images/pipe.png";
    /**
     variable defines image of bottom pipe
     */
    public final String PIPE_BOTTOM_IMAGE = "src/images/bottomPipe.png";
    /**
     variable defines image of bird
     */
    public final String BIRD_IMAGE = "src/images/bird.png";
    /**
     variable defines image of scoreboard
     */
    public final String SCORE_BOARD_IMAGE = "src/images/scoreBoard.png";
    /**
     variable defines image of restart button
     */
    public final String RESTART_IMAGE = "src/images/restart.png";
    /**
     variable defines flappy-font font
     */
    public final String FONT = "src/font/flappy-font.ttf";

    /**
     variable defines score file
     */
    public final String SCORE_FILE = "src/flappyBird/bestScore.txt";
}
