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
        dx = 0;
        dy = 0;
        width = 25;
        height = 200;
        isAlive = true;
        rect = new Rectangle(xpos, ypos, width, height);
    }

    public void move() {
        xpos += dx;
        ypos += dy;
    }

    public void wrap(int screenWidth, int screenHeight) {
        xpos += dx;
        ypos += dy;

        if (xpos > screenWidth) xpos = -width;
        if (xpos < -width) xpos = screenWidth;
        if (ypos > screenHeight) ypos = -height;
        if (ypos < -height) ypos = screenHeight;
    }

    public void updateRect() {
        rect.setLocation(xpos, ypos);
    }
}
