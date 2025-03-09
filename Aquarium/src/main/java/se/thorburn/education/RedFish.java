package se.thorburn.education;

import java.awt.*;

public class RedFish extends Fish {

    public RedFish(int x, int y, Aquarium aquarium, int id) {
        super(x, y, aquarium, id);
    }

    @Override
    protected Color getFishColor() {
        return Color.RED;
    }
}