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

public class FlappyBird implements ActionListener, MouseListener {
    public static FlappyBird flappyBird;

    public Settings settings;

    public Renderer renderer;

    public int ticks, yMotion, score;

    public ArrayList<Column> columns;

    public Random rand;

    public boolean gameOver, started;

    private final Image background, ground;

    private final Bird bird;

    private final Column columnTemp;

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

    public static void main(String[] args) {
        flappyBird = new FlappyBird();
    }

    public void addColumn(boolean start) {
        int height = rand.nextInt(250) - 250;

        if (start) {
            columns.add(new Column(settings.WIDTH + columnTemp.width + columns.size() * 300, height));
        } else {
            columns.add(new Column(columns.get(columns.size() - 1).x + 300, height));
        }
    }

    public void paintColumn(Graphics g, Column column) {
        g.drawImage(column.pipeTop, column.x, column.y, null);
        g.drawImage(column.pipeBottom, column.bottomX, column.bottomY, null);
    }

    public void jump() {
        if (!started) {
            started = true;
        } else if (!gameOver) {
            if(yMotion > 0) {
                yMotion = 0;
            }

            yMotion -= 10;
        }
    }

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

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
