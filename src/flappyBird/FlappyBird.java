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

  private final int WIDTH = 480, HEIGHT = 640;

  private Renderer renderer;

  private int ticks, yMotion, score;

  private ArrayList<Column> columns;

  private Random rand;

  private boolean isGameOver, isGameStarted;

  private final Image background, ground;

  private final Bird bird;

  private Column columnTemp;

    public FlappyBird() {
        JFrame jFrame = new JFrame();
        Timer timer = new Timer(20, this);

        renderer = new Renderer();
        rand = new Random();

        ImageIcon imageIcon = new ImageIcon("src/images/background.png");
        background = imageIcon.getImage();
        imageIcon = new ImageIcon("src/images/ground.png");
        ground = imageIcon.getImage();

        bird = new Bird();
        columnTemp = new Column(1,1);

        jFrame.add(renderer);
        jFrame.setSize(WIDTH, HEIGHT);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        jFrame.setTitle("Flappy Bird");
        jFrame.addMouseListener(this);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        columns = new ArrayList<Column>();

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
            columns.add(new Column(WIDTH + columnTemp.getWidth() + columns.size() * 300, height));
        } else {
            columns.add(new Column(columns.get(columns.size() - 1).getX() + 300, height));
        }

    }

    public void paintColumn(Graphics g, Column column) {
        g.drawImage(column.getImage(), column.getX(), column.getY(), null);
        g.drawImage(column.getRotatedImage(), column.getBottomX(), column.getBottomY(), null);
    }

    public void jump() {
        if (isGameOver) {
            //restart();
        }

        if (!isGameStarted) {
            isGameStarted = true;
        } else if (!isGameOver) {
            if(yMotion > 0) {
                yMotion = 0;
            }

            yMotion -= 10;
        }

    }

    public void restart() {
        bird.setX( WIDTH / 2 - 25);
        bird.setY( HEIGHT / 2 - 25);
        columns.clear();
        yMotion = 0;
        score = 0;

        addColumn(true);
        addColumn(true);

        isGameOver = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = 7;
        ticks++;

        if(isGameOver) {
            speed = 0;
        }

        if (isGameStarted) {

            for (int i = 0; i < columns.size(); i++) {
                Column column = columns.get(i);
                column.setX(column.getX() - speed);
                column.setBottomX( column.getBottomX() - speed );
            }

            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion += 2;
            }


            for (int i = 0; i < columns.size(); i++) {
                Column column = columns.get(i);

                if (column.getX() + column.getWidth() < 0) {
                    columns.remove(column);

                    /*if (column.y == 0) {
                        addColumn(false);
                    }*/

                    if (columns.size() < 2) {
                        addColumn(false);
                    }
                }
            }

            bird.setY(bird.getY() + yMotion);

            for (Column column : columns) {

                int colCenter = column.getX() + column.getWidth() / 2;
                int birdCenter = bird.getX() + bird.getWidth() / 2;

                if (birdCenter >= colCenter && column.isCanGivePoint()) {
                    column.setCanGivePoint(false);
                    score++;
                }

                Rectangle birdRec = new Rectangle(bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight());
                Rectangle columnRec = new Rectangle(column.getX(), column.getY(), column.getWidth(), column.getHeight());
                Rectangle columnBottomRec = new Rectangle(column.getBottomX(), column.getBottomY(), column.getWidth(), column.getHeight());
                if (columnRec.intersects(birdRec) || columnBottomRec.intersects(birdRec)) {
                    isGameOver = true;

                    /*if (bird.x <= column.x) {
                        bird.x = column.x - bird.width;
                    }
                    else {
                        if (column.y != 0) {
                            bird.y = column.y - bird.height;
                        } else if (bird.y < column.height) {
                            bird.y = column.height;
                        }
                    }*/

                }
            }

            if (bird.getY() > HEIGHT - 115 - bird.getHeight() || bird.getY() < 0) {
                isGameOver = true;
            }

            if(bird.getY() + yMotion >= HEIGHT - 115 - bird.getHeight()) {
                bird.setY(HEIGHT - 115 - bird.getHeight());
            }
        }

        renderer.repaint();
    }

    public void repaint(Graphics g) throws IOException {
        g.drawImage(background, 0,0, null);

        for (Column column: columns) {
            paintColumn(g, column);
        }

        g.drawImage(bird.getImage(), bird.getX(), bird.getY(), null);

        g.drawImage(ground, 0, HEIGHT - 115, null);

        Font scoreFont = null;
        Font bestFont = null;
        try {
            InputStream is = new BufferedInputStream(new FileInputStream("src/font/flappy-font.ttf"));
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            scoreFont = font.deriveFont(Font.PLAIN, 50);
            bestFont = font.deriveFont(Font.PLAIN, 35);
        } catch (FontFormatException e) {
            e.printStackTrace();
        }

        g.setColor(Color.white);
        g.setFont(scoreFont);

        if (!isGameStarted) {
            g.drawString("Click to start!", 50, HEIGHT / 2 - 50);
        }

        if (isGameOver) {
            g.setFont(bestFont);
            g.drawImage(new ImageIcon("src/images/scoreBoard.png").getImage(), WIDTH/2-108/2, HEIGHT/2-115-40, null);
            g.drawString(String.valueOf(score), 230, 235);
            g.drawString(String.valueOf(checkBestScore()), 230, 287);
            g.drawImage(new ImageIcon("src/images/restart.png").getImage(), WIDTH/2 - 137/2, HEIGHT/2 + 10, null);
        }

        if(!isGameOver && isGameStarted) {
            g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
        }
    }

    public int checkBestScore() {
        String filePath = "src/flappyBird/bestScore.txt";
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
        if (isGameOver) {
            PointerInfo pointerInfo = MouseInfo.getPointerInfo();
            Point p = pointerInfo.getLocation();
            if (p.x > (WIDTH/2 - 137/2 + 7) && p.x < (WIDTH/2 - 137/2 + 142) && p.y > (HEIGHT/2 + 10 + 30) && p.y < (HEIGHT/2 + 10 + 79)) {
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
