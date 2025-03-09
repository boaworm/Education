package se.thorburn.education;

import java.awt.*;
import java.util.List;
import java.util.Random;

public abstract class Fish {

    protected int x, y;
    protected int dx, dy;
    protected Aquarium aquarium;
    protected Random random = new Random();
    protected static final int FISH_WIDTH = 30;
    protected static final int FISH_HEIGHT = 20;
    protected boolean facingRight;
    protected double angle;
    protected int id; // Fish ID

    public Fish(int x, int y, Aquarium aquarium, int id) {
        this.x = x;
        this.y = y;
        this.aquarium = aquarium;
        this.id = id;
        facingRight = random.nextBoolean();
        changeDirection();
    }

    public void move(List<Fish> otherFishes) {
        x += dx;
        y += dy;

        // Check boundaries
        if (x < aquarium.getBorderWidth()) {
            x = aquarium.getBorderWidth();
            flipFish();
        }
        if (x > aquarium.getWidth() - aquarium.getBorderWidth() - FISH_WIDTH) {
            x = aquarium.getWidth() - aquarium.getBorderWidth() - FISH_WIDTH;
            flipFish();
        }
        if (y < aquarium.getWaterLine()) {
            y = aquarium.getWaterLine();
            flipFish();
        }
        if (y > aquarium.getHeight() - aquarium.getBorderWidth() - FISH_HEIGHT) {
            y = aquarium.getHeight() - aquarium.getBorderWidth() - FISH_HEIGHT;
            flipFish();
        }

        // Check collision with other fish
        for (Fish otherFish : otherFishes) {
            if (otherFish != this && collidesWith(otherFish)) {
                if (this instanceof NorthernPike) {
                    aquarium.removeFish(otherFish.id);
                } else {
                    flipFish();
                }
                break;
            }
        }
    }

    protected void flipFish() {
        facingRight = !facingRight;
        changeDirection();
    }

    protected void changeDirection() {
        angle = Math.toRadians(random.nextInt(121) + 30);
        if (random.nextBoolean()) {
            angle = -angle;
        }
        dx = (int) (Math.cos(angle) * 5);
        dy = (int) (Math.sin(angle) * 5);

        if (!facingRight) {
            dx = -dx;
        }
    }

    protected boolean collidesWith(Fish otherFish) {
        int thisLeft = x;
        int thisRight = x + getWidth();
        int thisTop = y;
        int thisBottom = y + getHeight();

        int otherLeft = otherFish.x;
        int otherRight = otherFish.x + otherFish.getWidth();
        int otherTop = otherFish.y;
        int otherBottom = otherFish.y + otherFish.getHeight();

        return !(thisLeft > otherRight || thisRight < otherLeft || thisTop > otherBottom || thisBottom < otherTop);
    }

    public void draw(Graphics g) {
        // Fish body (oval)
        g.setColor(getFishColor());
        g.fillOval(x, y, getWidth(), getHeight());

        // Fish tail (triangle, inverted, same color, 8 pixels to the left)
        g.setColor(getFishColor());
        int[] tailX = facingRight ? new int[]{x - 8, x - 8 + getTailWidth(), x - 8} : new int[]{x + getWidth() + 8, x + getWidth() + 8 - getTailWidth(), x + getWidth() + 8};
        int[] tailY = {y, y + getHeight() / 2, y + getHeight()};
        g.fillPolygon(tailX, tailY, 3);

        // Fish eye (black dot, position based on direction)
        g.setColor(Color.BLACK);
        int eyeX = facingRight ? x + (int) (getWidth() * 0.8) - 2 : x + (int) (getWidth() * 0.2) - 2;
        g.fillOval(eyeX, y + getHeight() / 2 - 2, 4, 4);
    }

    protected abstract Color getFishColor();

    protected int getTailWidth() {
        return 15;
    }

    public int getWidth() {
        return FISH_WIDTH;
    }

    public int getHeight() {
        return FISH_HEIGHT;
    }

    public int getId() {
        return id;
    }
}