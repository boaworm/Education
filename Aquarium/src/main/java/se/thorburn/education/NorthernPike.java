package se.thorburn.education;

import java.awt.*;
import java.util.List;

public class NorthernPike extends Fish {

    private static final int PIKE_WIDTH = (int) (30 * 2.0);
    private static final int PIKE_HEIGHT = (int) (20 * 1.5);
    private static final int PIKE_SPEED = 10;

    public NorthernPike(int x, int y, Aquarium aquarium, int id) {
        super(x, y, aquarium, id);
    }

    @Override
    protected Color getFishColor() {
        return new Color(0, 100, 0); // Dark green
    }

    @Override
    public int getWidth() {
        return PIKE_WIDTH;
    }

    @Override
    public int getHeight() {
        return PIKE_HEIGHT;
    }

    @Override
    protected void changeDirection() {
        angle = Math.toRadians(random.nextInt(121) + 30);
        if (random.nextBoolean()) {
            angle = -angle;
        }
        dx = (int) (Math.cos(angle) * PIKE_SPEED);
        dy = (int) (Math.sin(angle) * PIKE_SPEED);

        if (!facingRight) {
            dx = -dx;
        }
    }
}