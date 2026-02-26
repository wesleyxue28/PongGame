import java.awt.*;

public class Brick1 {

    public String name;
    public int xpos, ypos;
    public int dx, dy;
    public int width, height;
    public boolean isAlive;
    public Rectangle rect;

    public Brick1(String pName, int pXpos, int pYpos, double psuccess) {
        name = pName;
        xpos = pXpos;
        ypos = pYpos;
        dx = 6;
        dy = 6;
        width = 100;
        height = 100;
        isAlive = true;
        rect = new Rectangle(xpos, ypos, width, height);
    }

    public void move() {
        xpos += dx;
        ypos += dy;
    }

    public void updateRect() {
        rect.setLocation(xpos, ypos);
    }
}
