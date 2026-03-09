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
        width = 25;
        height = 200;
        isAlive = true;
        rect = new Rectangle(xpos, ypos, width, height);
    }

    public void ybounce (int screenwidth, int screenheight) {

        ypos += dy;

        for (int x = 0; x < 200; x++){
            if (ypos <= 0 || ypos + height >= 1000) {
                dy *= -1;
            }
        }
    }

    public void updateRect() {
        rect.setLocation(xpos, ypos);
    }
}
