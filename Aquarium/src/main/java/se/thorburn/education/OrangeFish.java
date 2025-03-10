package se.thorburn.education;

import java.awt.*;

public class OrangeFish extends Fish {

    public OrangeFish(int x, int y, Aquarium aquarium, int id) {
        super(x, y, aquarium, id);
    }

    @Override
    protected Color getFishColor() {
        return Color.ORANGE;
    }

}