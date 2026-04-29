import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

public class PongGame implements Runnable, KeyListener, MouseListener {

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

    int playerPoints;
    int computerPoints;
    String stringPlayerPoints = Integer.toString(playerPoints);
    String stringComputerPoints = Integer.toString(computerPoints);
    String stringReset = "Reset";

   int resetXPos;
   int resetYPos;
    int resetButtonX = 450;
    int resetButtonY = 20;
    int resetButtonWidth = 150;
    int resetButtonHeight = 40;
    double speedMultiplier = 1.0;

    public SoundFile hitSound;
    public SoundFile pointSound;

    // Images
    Image Brick1Img;
    ;
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

        ball = new Ball("ball.png", 300, 300, 0.75);
        brick1 = new Brick1("Brick1.png", WIDTH - 50, 0, 0.25);
        brick2 = new Brick2("Brick2.png", 50, 400, 0.25);

        BallImg = Toolkit.getDefaultToolkit().getImage("ball.png");
        Brick1Img = Toolkit.getDefaultToolkit().getImage("Brick1.png");
        Brick2Img = Toolkit.getDefaultToolkit().getImage("Brick2.png");
        bgImage = Toolkit.getDefaultToolkit().getImage("bgimg.jpg");

        hitSound = new SoundFile("hit.wav");
        pointSound = new SoundFile("point.wav");
//        porkImg = Toolkit.getDefaultToolkit().getImage("pork.png");

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
            checkBallBrick1Collision();
            checkBallBrick2Collision();
        }
    }

    // Movement
    public void moveThings() {

        ball.bounce(WIDTH, HEIGHT);

        brick1.wrap(WIDTH, HEIGHT);

        brick2.ybounce(200, 200);
        brick2.wrap(WIDTH, HEIGHT);


        ball.updateRect();
        brick1.updateRect();
        brick2.updateRect();
        checkPoints();
    }
//        for (int i = 0; i < trucks.length; i++) {
//           trucks[i].wrap(WIDTH, HEIGHT);
//        }
//    }

    // Interaction 1: Ball & Brick1
    public void checkBallBrick1Collision() {
        if (ball.rect.intersects(brick1.rect)) {
            hitSound.play();

            ball.dx *= -1.125 * speedMultiplier;

            ball.dy *= 1;

        }

    }

    public void checkPoints(){
        if (ball.xpos >= 1000) {
        computerPoints++;
        stringComputerPoints = Integer.toString(computerPoints);
        pointSound.play();
        resetXPos = (int) ((Math.random()* 200+ 300));
        resetYPos = (int) ((Math.random()* 200+ 500));
        ball.xpos = resetXPos;
        ball.ypos = resetYPos;
        ball.dx = -10;

        }
        if (ball.xpos <= 0) {
            playerPoints++;
            stringPlayerPoints = Integer.toString(playerPoints);
            pointSound.play();
            resetXPos = (int) ((Math.random()* 200+ 300));
            resetYPos = (int) ((Math.random()* 200+ 500));
            ball.xpos = resetXPos;
            ball.ypos = resetYPos;
            ball.dx = 10;

        }
    }

    // Interaction 2: Pig & Rocket
    public void checkBallBrick2Collision() {
        if (ball.rect.intersects(brick2.rect)) {

            ball.dx *= -1.125 * speedMultiplier;
            hitSound.play();
            ball.dy *= 1;

        }
    }

    // Render
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(bgImage, 0, 0, WIDTH, HEIGHT, null);
        g.setFont(new Font("Times New Roman", Font.BOLD, 37));
        g.setColor(new Color(255, 255, 255));
        g.drawString("Player", 800, 50);
        g.drawString("Computer", 100, 50);
        g.drawString("Reset", 475, 0);
        g.drawString(stringPlayerPoints, 925, 50);
        g.drawString(stringComputerPoints, 25, 50);
        g.drawString(stringReset, 475, 50);
        if (computerPoints == 7 || playerPoints == 7) {
            g.drawString("Game Over", 500, 400);
            ball.dx *= 0;
            ball.dy *= 0;
        }


        g.drawImage(BallImg, ball.xpos, ball.ypos, ball.width, ball.height, null);

        g.drawImage(Brick1Img, brick1.xpos, brick1.ypos, brick1.width, brick1.height, null);
        g.drawImage(Brick2Img, brick2.xpos, brick2.ypos, brick2.width, brick2.height, null);

        g.dispose();
        bufferStrategy.show();
        g.setColor(new Color(100, 100, 255));
        g.fillRect(resetButtonX, resetButtonY, resetButtonWidth, resetButtonHeight);
        g.setColor(new Color(255, 255, 255));
        g.drawRect(resetButtonX, resetButtonY, resetButtonWidth, resetButtonHeight);
        g.drawString(stringReset, 475, 50);


    }


    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ignored) {
        }
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

        canvas.addMouseListener(this);
    }


    // Key input
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());

        if (e.getKeyCode() == 38) {// up key
            brick1.dy = -20;
        }
        if (e.getKeyCode() == 40) { // down key
            brick1.dy = 20;
        }
//        if (e.getKeyCode() == 39) {
//            brick1.dx = 20;
//        }
//        if (e.getKeyCode() == 37) {
//            brick1.dx = -20;
//        }
        if (e.getKeyCode() == 87) { // w key
            brick2.dy = -20;
        }
        if (e.getKeyCode() == 83) { // s key
            brick2.dy =20;
        }

        if (e.getKeyCode() == 49) {
            speedMultiplier = 0.75;
            System.out.println("Speed multiplier set to: " + speedMultiplier);
        }
        if (e.getKeyCode() == 50) {
            speedMultiplier = 1;
            System.out.println("Speed multiplier set to: " + speedMultiplier);
        }
        if (e.getKeyCode() == 51) {
            speedMultiplier = 1.25;
            System.out.println("Speed multiplier set to: " + speedMultiplier);
        }
    }


    public void resetGame() {
        playerPoints = 0;
        computerPoints = 0;
        stringPlayerPoints = Integer.toString(playerPoints);
        stringComputerPoints = Integer.toString(computerPoints);

        // Reset ball position
        ball.xpos = 300;
        ball.ypos = 300;
        ball.dx = 10;
        ball.dy = 10;

        // Reset bricks to original positions
        brick1.xpos = WIDTH - 50;
        brick1.ypos = 0;
        brick1.dy = 0;

        brick2.xpos = 50;
        brick2.ypos = 400;
        brick2.dy = 0;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 38) {
            brick1.dy = 0;
        }
        if (e.getKeyCode() == 40) {
            brick1.dy = 0;
        }
//        if (e.getKeyCode() == 39) {
//            brick1.dx = 0;
//        }
//        if (e.getKeyCode() == 37) {
//            brick1.dx = 0;
//        }
        if (e.getKeyCode() == 87) {
            brick2.dy = 0;
        }
        if (e.getKeyCode() == 83) {
            brick2.dy = 0;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (mouseX >= resetButtonX && mouseX <= resetButtonX + resetButtonWidth &&
                mouseY >= resetButtonY && mouseY <= resetButtonY + resetButtonHeight) {
            resetGame();
            System.out.println("Game Reset!");
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