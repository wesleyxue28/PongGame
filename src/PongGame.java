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

    // Collision flags
    boolean pigTruckCrash = true;
    boolean pigRocketCrash = true;

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

        BallImg = Toolkit.getDefaultToolkit().getImage("pig.png");
        Brick1Img = Toolkit.getDefaultToolkit().getImage("Truck.png");
        Brick2Img = Toolkit.getDefaultToolkit().getImage("Rocket.png");
//        porkImg = Toolkit.getDefaultToolkit().getImage("pork.png");
        bgImage = new ImageIcon("farm.jpg").getImage();

//        trucks = new Truck[6];
//        for (int i = 0; i < trucks.length; i++) {
//            trucks[i] = new Truck("Truck " + i, (int) (Math.random()*WIDTH), (int)(Math.random()*HEIGHT), 0.25);
//        }
//    }

    // Game loop
    public void run() {
        while (true) {
            moveThings();
            checkPigTruckCollision();
            checkPigRocketCollision();
            render();
            pause(30);
        }
    }

    // Movement
    public void moveThings() {
        ball.bounce();

        if (pressingKey) {
            brick1.move();
        }

        truck.wrap(WIDTH, HEIGHT);
        Brick1.bounce(WIDTH, HEIGHT);

        piggy.updateRect();
        Brick1.updateRect();
        Brick2.updateRect();

        for (int i = 0; i < trucks.length; i++) {
           trucks[i].wrap(WIDTH, HEIGHT);
        }
    }

    // Interaction 1: Pig & Truck
    public void checkPigTruckCollision() {
        if (piggy.rect.intersects(truck.rect) && pigTruckCrash) {

            pigTruckCrash = false;
            piggy.health -= 5;


            if (Math.random() < 0.5) {
                piggy.dx *= -1;
            } else {
                piggy.dy *= -1;
            }

            truck.width += 10;
            truck.height += 10;
        }

        if (!piggy.rect.intersects(truck.rect)) {
            pigTruckCrash = true;
        }
    }

    // Interaction 2: Pig & Rocket
    public void checkPigRocketCollision() {
        if (piggy.rect.intersects(rocket.rect) && pigRocketCrash) {

            pigRocketCrash = false;
            piggy.health -= 5;

            piggy.dx *= -1;
            piggy.dy *= -1;
            rocket.dx *= -1;
            rocket.dy *= -1;
        }

        if (!piggy.rect.intersects(rocket.rect)) {
            pigRocketCrash = true;
        }
    }

    // Render
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(bgImage, 0, 0, WIDTH, HEIGHT, null);

        for (int i = 0; i < trucks.length; i++) {
            g.drawImage(truckImg, trucks[i].xpos, trucks[i].ypos, WIDTH, HEIGHT, null);
        }

        // Health bar
        piggy.health = Math.max(0, piggy.health);
        g.setColor(Color.PINK);
        g.fillRect(800, 20, 15, piggy.health);

        // Draw characters
        if (piggy.isAlive) {
            g.drawImage(pigImg, piggy.xpos, piggy.ypos, piggy.width, piggy.height, null);
        } else {
            g.drawImage(porkImg, piggy.xpos, piggy.ypos, piggy.width, piggy.height, null);
        }

        g.drawImage(truckImg, truck.xpos, truck.ypos, truck.width, truck.height, null);
        g.drawImage(rocketImg, rocket.xpos, rocket.ypos, rocket.width, rocket.height, null);

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
            piggy.dx = 0;
            piggy.dy = -10;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            piggy.dx = 0;
            piggy.dy = 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            piggy.dx = -10;
            piggy.dy = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            piggy.dx = 10;
            piggy.dy = 0;
        }
    }

    public void keyReleased(KeyEvent e) {
        pressingKey = false;
    }

    public void keyTyped(KeyEvent e) {}
}
