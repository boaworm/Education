package se.thorburn.education;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.util.List;
import java.util.Random;

public abstract class Fish {

    protected int x, y;
    protected int dx, dy;
    protected Aquarium aquarium;
    protected Random random = new Random();
    protected int FISH_WIDTH = 30;
    protected int FISH_HEIGHT = 20;
    protected boolean facingRight;
    protected double angle;
    protected int id;
    protected Path2D.Double fishShape;
    protected Area fishArea;

    public Fish(int x, int y, Aquarium aquarium, int id) {
        this.x = x;
        this.y = y;
        this.aquarium = aquarium;
        this.id = id;
        facingRight = random.nextBoolean();
        defineFishShape();
        changeDirection();
    }

    protected void defineFishShape() {
        fishShape = new Path2D.Double();

        // Start at the left of the oval
        fishShape.moveTo(0, FISH_HEIGHT / 2.0);

        // Draw the oval curve
        fishShape.curveTo(FISH_WIDTH * 0.2, 0, FISH_WIDTH * 0.8, 0, FISH_WIDTH, FISH_HEIGHT / 2.0);
        fishShape.curveTo(FISH_WIDTH * 0.8, FISH_HEIGHT, FISH_WIDTH * 0.2, FISH_HEIGHT, 0, FISH_HEIGHT / 2.0);

        // Draw the tail
        fishShape.lineTo(-8, 0);
        fishShape.lineTo(-8 + getTailWidth(), FISH_HEIGHT / 2.0);
        fishShape.lineTo(-8, FISH_HEIGHT);

        // Close the polygon
        fishShape.closePath();

        fishArea = new Area(fishShape);
    }

    public void move(List<Fish> otherFishes) {
        int oldX = x;
        int oldY = y;
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
                    increaseSize();
                } else {
                    flipFish();
                }
                break;
            }
        }
        if(x > oldX){
            facingRight = true;
        }else{
            facingRight = false;
        }
    }

    protected void flipFish() {
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
        Area thisArea = new Area(fishArea);
        thisArea.transform(AffineTransform.getTranslateInstance(x, y));

        Area otherArea = new Area(otherFish.fishArea);
        otherArea.transform(AffineTransform.getTranslateInstance(otherFish.x, otherFish.y));

        thisArea.intersect(otherArea);
        return !thisArea.isEmpty();
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getFishColor());

        Area transformedFishArea = new Area(fishArea);
        AffineTransform transform = AffineTransform.getTranslateInstance(x, y);

        if (!facingRight) {
            AffineTransform flipTransform = AffineTransform.getScaleInstance(-1, 1);
            flipTransform.translate(-FISH_WIDTH, 0);
            transform.concatenate(flipTransform);
        }

        transformedFishArea.transform(transform);
        g2d.fill(transformedFishArea);

        g2d.setColor(Color.BLACK);
        int eyeX = facingRight ? x + (int) (FISH_WIDTH * 0.8) - 2 : x + (int) (FISH_WIDTH * 0.2) - 2;
        g2d.fillOval(eyeX, y + FISH_HEIGHT / 2 - 2, 4, 4);
    }

    protected abstract Color getFishColor();

    protected int getTailWidth() {
        return 15;
    }

    public int getId() {
        return id;
    }

    private void increaseSize() {
        FISH_WIDTH = (int) (FISH_WIDTH * 1.5);
        FISH_HEIGHT = (int) (FISH_HEIGHT * 1.5);
        defineFishShape();
    }
}