package se.thorburn.education;

import java.awt.*;

public class Food {

    private int x, y;

    public Food(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x - 5, y - 5, 10, 10);
    }
}