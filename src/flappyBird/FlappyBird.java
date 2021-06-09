package flappyBird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 * <h1>Flappy bird!</h1>
 * The Flappy Bird game implements an application that
 * allows you to play video game.
 * <p>
 * Just operate by left mouse clicking and enjoy this wonderful content
 * FlappyBird class implements ActionListener and MouseListener, it allow
 * user to communicate with game.
 * @author  Robert Małek, Bartosz Dymański, Dominik Pająk
 * @version 1.0
 * @since   09.06.2021
 */

public class FlappyBird implements ActionListener, MouseListener {

    /**
     variable defines FlappyBird object
     */
    public static FlappyBird flappyBird;

    /**
     variable defines Setting object
     */
    public Settings settings;

    /**
     variable defines Renderer object
     */
    public Renderer renderer;

    /**
     variables define int value of ticks, yMotion and score
     */
    public int ticks, yMotion, score;

    /**
     variable defines ArrayList of Column objects
     */
    public ArrayList<Column> columns;

    /**
     variable defines Random object
     */
    public Random rand;

    /**
     variables define boolean value, when gameOver is set to true, game is stopped and scoreboard popup, started set on
     true means, that game is running.
     */
    public boolean gameOver, started;

    /**
     variable defines Image object of background and ground
     */
    private final Image background, ground;

    /**
     variable defines Bird object
     */
    private final Bird bird;

    /**
     variable defines Column object
     */
    private final Column columnTemp;

    /**
     * Constructs a <code>FlappyBird</code> object which is rendering while game is running, constructor sets other
     * objects into game like background, column, bird, also render them and sets their values.
     */

    public FlappyBird() {
        JFrame jFrame = new JFrame();
        Timer timer = new Timer(20, this);

        settings = new Settings();
        renderer = new Renderer();
        rand = new Random();

        ImageIcon imageIcon = new ImageIcon(settings.BACKGROUND_IMAGE);
        background = imageIcon.getImage();
        imageIcon = new ImageIcon(settings.GROUND_IMAGE);
        ground = imageIcon.getImage();

        bird = new Bird();
        columnTemp = new Column(1,1);

        jFrame.add(renderer);
        jFrame.setSize(settings.WIDTH, settings.HEIGHT);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        jFrame.setTitle("Flappy Bird");
        jFrame.addMouseListener(this);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        columns = new ArrayList<>();

        addColumn(true);
        addColumn(true);

        timer.start();
    }

    /**
     * <code>main</code> object create flappyBird object.
     */

    public static void main(String[] args) {
        flappyBird = new FlappyBird();
    }

    /**
     * Function add one column into interface, when param is declared as true
     * @param start: boolean value represent if column should be printed
     */

    public void addColumn(boolean start) {
        int height = rand.nextInt(250) - 250;

        if (start) {
            columns.add(new Column(settings.WIDTH + columnTemp.width + columns.size() * 300, height));
        } else {
            columns.add(new Column(columns.get(columns.size() - 1).x + 300, height));
        }
    }

    /**
     * Function sets two column objects into Graphics representation, includes column type (pipeTop or pipeBottom),
     * column x and y value
     * @param g: specified graphics object
     * @param column: specified column object
     */

    public void paintColumn(Graphics g, Column column) {
        g.drawImage(column.pipeTop, column.x, column.y, null);
        g.drawImage(column.pipeBottom, column.bottomX, column.bottomY, null);
    }

    /**
     * Function jump is called when player make mouse-click interaction with interface, then our game is started
     */

    public void jump() {
        if (gameOver) {
            restart();
        }
        if (!started) {
            started = true;
        } else if (!gameOver) {
            if(yMotion > 0) {
                yMotion = 0;
            }

            yMotion -= 10;
        }
    }

    /**
     * Function restart sets default values of all objects included in project
     */

    public void restart() {
        bird.x = settings.WIDTH / 2 - 25;
        bird.y = settings.HEIGHT / 2 - 25;
        columns.clear();
        yMotion = 0;
        score = 0;

        addColumn(true);
        addColumn(true);

        gameOver = false;
    }

    /**
     * Function actionPerformed control game mechanics, if gameOver value is set to true, game is stopped.
     * Otherwise game is running and method assign speed and ticks to columns, removing them if they are out of frame
     * and defines crash hitboxes
     * @param e: Semantic event which indicates that a component-defined action occurred
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = settings.SPEED;
        ticks++;

        if(gameOver) {
            speed = 0;
        }

        if (started) {

            for (Column column : columns) {
                column.x -= speed;
                column.bottomX -= speed;
            }

            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion += 2;
            }


            for (int i = 0; i < columns.size(); i++) {
                Column column = columns.get(i);

                if (column.x + column.width < 0) {
                    columns.remove(column);

                    if (columns.size() < 2) {
                        addColumn(false);
                    }
                }
            }

            bird.y += yMotion;

            for (Column column : columns) {

                int colCenter = column.x + column.width / 2;
                int birdCenter = bird.x + bird.width / 2;

                if (birdCenter >= colCenter && column.canGivePoint) {
                    column.canGivePoint = false;
                    score++;
                }

                Rectangle birdRec = new Rectangle(bird.x, bird.y, bird.width, bird.height);
                Rectangle columnRec = new Rectangle(column.x, column.y, column.width, column.height);
                Rectangle columnBottomRec = new Rectangle(column.bottomX, column.bottomY, column.width, column.height);
                if (columnRec.intersects(birdRec) || columnBottomRec.intersects(birdRec)) {
                    gameOver = true;
                }
            }

            if (bird.y > settings.HEIGHT - 115 - bird.height || bird.y < 0) {
                gameOver = true;
            }

            if(bird.y + yMotion >= settings.HEIGHT - 115 - bird.height) {
                bird.y = settings.HEIGHT - 115 - bird.height;
            }
        }

        renderer.repaint();
    }

    /**
     * Method repainting objects while loop is running and generate interface menu with score when game loop is break
     * @param g graphics type object
     * @throws IOException
     *      <br>throw when function failed attempt to read file stream<br>
     */

    public void repaint(Graphics g) throws IOException {
        g.drawImage(background, 0,0, null);

        for (Column column: columns) {
            paintColumn(g, column);
        }

        g.drawImage(bird.image, bird.x, bird.y, null);

        g.drawImage(ground, 0, settings.HEIGHT - 115, null);

        Font scoreFont = null;
        Font bestFont = null;
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(settings.FONT));
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            scoreFont = font.deriveFont(Font.PLAIN, 50);
            bestFont = font.deriveFont(Font.PLAIN, 35);
        } catch (FontFormatException e) {
            e.printStackTrace();
        }

        g.setColor(Color.white);
        g.setFont(scoreFont);

        if (!started) {
            g.drawString("Click to start!", 50, settings.HEIGHT / 2 - 50);
        }

        if (gameOver) {
            g.setFont(bestFont);
            g.drawImage(new ImageIcon(settings.SCORE_BOARD_IMAGE).getImage(), settings.WIDTH/2-108/2, settings.HEIGHT/2-115-40, null);
            g.drawString(String.valueOf(score), 230, 235);
            g.drawString(String.valueOf(checkBestScore()), 230, 287);
            g.drawImage(new ImageIcon(settings.RESTART_IMAGE).getImage(), settings.WIDTH/2 - 137/2, settings.HEIGHT/2 + 10, null);
        }

        if(!gameOver && started) {
            g.drawString(String.valueOf(score), settings.WIDTH / 2 - 25, 100);
        }
    }

    /**
     * Method scans score file, if achieved score is better then value placed in file, function set our score as best
     * @return int value - best achieved score
     */

    public int checkBestScore() {
        String filePath = settings.SCORE_FILE;
        File file = new File(filePath);
        int bestScore = 0;
        try {
            Scanner scanner = new Scanner(file);
            bestScore = Integer.parseInt(scanner.nextLine());
            scanner.close();
            if (bestScore < score){
                FileWriter fileWriter = new FileWriter(filePath);
                fileWriter.write(String.valueOf(score));
                fileWriter.close();

                bestScore = score;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bestScore;
    }

    /**
     * Method activates when mouse is clicked, function starts jump method. If gameover boolean value is set to true, restart
     * button is activate and ready to click
     */

    @Override
    public void mouseClicked(MouseEvent e) {
        jump();
        if (gameOver) {
            PointerInfo pointerInfo = MouseInfo.getPointerInfo();
            Point p = pointerInfo.getLocation();
            if (p.x > (settings.WIDTH/2 - 137/2 + 7) && p.x < (settings.WIDTH/2 - 137/2 + 142) && p.y > (settings.HEIGHT/2 + 10 + 30) && p.y < (settings.HEIGHT/2 + 10 + 79)) {
                restart();
            }
        }
    }

    /**
     * Method activates when mouse is pressed
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Method activates when mouse is released
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Method activates when mouse is entered
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Method activates when mouse is exited
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
