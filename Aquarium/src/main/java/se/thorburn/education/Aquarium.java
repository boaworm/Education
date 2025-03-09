package se.thorburn.education;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aquarium extends JPanel implements ActionListener {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;
    private static final int WATER_LINE = 100;
    private static final int BORDER_WIDTH = 5;

    private Timer timer;
    private Map<Integer, Fish> fishMap = new HashMap<>();
    private int nextFishId = 0;

    public Aquarium() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.decode("#ADD8E6"));
        timer = new Timer(100, this);
        timer.start();

        // Add red fish
        RedFish redFish = new RedFish(WIDTH / 2, HEIGHT / 2, this, nextFishId++);
        fishMap.put(redFish.getId(), redFish);

        // Add five orange fish
        for (int i = 0; i < 5; i++) {
            int x = (int) (Math.random() * (WIDTH - 100) + 50);
            int y = (int) (Math.random() * (HEIGHT - 150) + 100);
            OrangeFish orangeFish = new OrangeFish(x, y, this, nextFishId++);
            fishMap.put(orangeFish.getId(), orangeFish);
        }

        // Add Northern Pike
        NorthernPike pike = new NorthernPike(WIDTH / 4, HEIGHT / 4, this, nextFishId++);
        fishMap.put(pike.getId(), pike);
    }

    public void removeFish(int fishId) {
        fishMap.remove(fishId);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw borders
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, BORDER_WIDTH, HEIGHT); // Left border
        g.fillRect(WIDTH - BORDER_WIDTH, 0, BORDER_WIDTH, HEIGHT); // Right border
        g.fillRect(0, HEIGHT - BORDER_WIDTH, WIDTH, BORDER_WIDTH); // Bottom border

        // Draw water line and fill below
        g.setColor(Color.BLUE);
        g.fillRect(0, WATER_LINE, WIDTH, HEIGHT - WATER_LINE); // Fill below with blue

        // Draw fishes
        for (Fish fish : fishMap.values()) {
            fish.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Fish> fishList = new ArrayList<>(fishMap.values());
        for (Fish fish : fishList) {
            fish.move(fishList);
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Aquarium");
        Aquarium aquarium = new Aquarium();
        frame.add(aquarium);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWaterLine() {
        return WATER_LINE;
    }

    public int getBorderWidth() {
        return BORDER_WIDTH;
    }
}