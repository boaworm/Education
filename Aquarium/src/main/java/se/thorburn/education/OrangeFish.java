package se.thorburn.education;

import java.awt.*;

public class OrangeFish extends Fish {

    private static final int FISH_WIDTH = (int) (30 * 0.8);
    private static final int FISH_HEIGHT = (int) (20 * 0.8);

    public OrangeFish(int x, int y, Aquarium aquarium, int id) {
        super(x, y, aquarium, id);
    }

    @Override
    protected Color getFishColor() {
        return Color.ORANGE;
    }

    @Override
    public int getWidth() {
        return FISH_WIDTH;
    }

    @Override
    public int getHeight() {
        return FISH_HEIGHT;
    }

    @Override
    protected int getTailWidth() {
        return (int) (15 * 0.8);
    }
}