import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class PongGame implements Runnable, KeyListener {

    // Window size
    final int WIDTH = 1100;
    final int HEIGHT = 900;

    // Graphics
    JFrame frame;
    Canvas canvas;
    JPanel panel;
    BufferStrategy bufferStrategy;

    // Game objects
    Ball ball;
    Brick1 brick1;
    Brick2 brick2;

    // Images
    Image Brick1Img;;
    Image Brick2Img;
    Image BallImg;
    Image bgImage;

    // Input
    boolean pressingKey = false;


    // Main
    public static void main(String[] args) {
        PongGame game = new PongGame();
        new Thread(game).start();
    }

    // Constructor
    public PongGame() {
        setUpGraphics();

        ball = new Ball("pig.png", 300, 300, 0.75);
        brick1 = new Brick1("Truck.png", 600, 300, 0.25);
        brick2 = new Brick2("Rocket.png", 200, 500, 0.25);

        BallImg = Toolkit.getDefaultToolkit().getImage("ball.jpg");
        Brick1Img = Toolkit.getDefaultToolkit().getImage("Brick1.png");
        Brick2Img = Toolkit.getDefaultToolkit().getImage("Brick2.png");
//        porkImg = Toolkit.getDefaultToolkit().getImage("pork.png");
        bgImage = new ImageIcon("bgimg.jpg").getImage();

//        trucks = new Truck[6];
//        for (int i = 0; i < trucks.length; i++) {
//            trucks[i] = new Truck("Truck " + i, (int) (Math.random()*WIDTH), (int)(Math.random()*HEIGHT), 0.25);
//        }
    }

    // Game loop
    public void run() {
        while (true) {
            moveThings();
            render();
            pause(30);
        }
    }

    // Movement
    public void moveThings() {

        ball.bounce(WIDTH, HEIGHT);

        if (pressingKey) {
            brick1.move();
        }

        brick2.ymove(WIDTH, HEIGHT);

        ball.updateRect();
        brick1.updateRect();
        brick2.updateRect();
    }
//        for (int i = 0; i < trucks.length; i++) {
//           trucks[i].wrap(WIDTH, HEIGHT);
//        }
//    }

    // Interaction 1: Ball & Brick1
    public void checkPigTruckCollision() {
        if (ball.rect.intersects(brick1.rect); {

            ball.dx *= -1;
            ball.dy *= -1;

    }
    }

    // Interaction 2: Pig & Rocket
    public void checkPigRocketCollision() {
        if (ball.rect.intersects(brick2.rect)) {

            ball.dx *= -1;
            ball.dy *= -1;
        }
    }

    // Render
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(bgImage, 0, 0, WIDTH, HEIGHT, null);


        // Health bar

        // Draw characters

            g.drawImage(BallImg, ball.xpos, ball.ypos, ball.width, ball.height, null);

        g.drawImage(Brick1Img, brick1.xpos, brick1.ypos, brick1.width, brick1.height, null);
        g.drawImage(Brick2Img, brick2.xpos, brick2.ypos, brick2.width, brick2.height, null);

        g.dispose();
        bufferStrategy.show();
    }


    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ignored) {}
    }

    // Graphics setup
    private void setUpGraphics() {
        frame = new JFrame("Screensaver");
        panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        canvas.addKeyListener(this);
    }

    // Key input
    public void keyPressed(KeyEvent e) {
        pressingKey = true;

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            ball.dx = 0;
            ball.dy = -10;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            ball.dx = 0;
            ball.dy = 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            ball.dx = -10;
            ball.dy = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            ball.dx = 10;
            ball.dy = 0;
        }
    }

    public void keyReleased(KeyEvent e) {
        pressingKey = false;
    }

    public void keyTyped(KeyEvent e) {}
}
