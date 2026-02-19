import java.awt.*;

public class Brick2 {

    public String name;
    public int xpos, ypos;
    public int dx, dy;
    public int width, height;
    public boolean isAlive;
    public Rectangle rect;

    public Brick2(String pName, int pXpos, int pYpos, double psuccess) {
        name = pName;
        xpos = pXpos;
        ypos = pYpos;
        dx = (int)(Math.random()*11)-5;
        dy = 0;
        width = 100;
        height = 100;
        isAlive = true;
        rect = new Rectangle(xpos, ypos, width, height);
    }

    public void bounce(int screenWidth, int screenHeight) {
        xpos += dx;
        ypos += dy;

        if (xpos <= 0 || xpos + width >= screenWidth) {
            dx *= -1;
        }

        if (ypos <= 0 || ypos + height >= screenHeight) {
            dy *= -1;
        }
    }

    public void updateRect() {
        rect.setLocation(xpos, ypos);
    }
}
