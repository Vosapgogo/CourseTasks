package com.shpp.p2p.cs.oTyshchenko.assignment4;

import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.util.RandomGenerator;

import java.awt.*;

public class Ball {
    private double vx, vy;
    private GOval ballGraphics;

    private static final double Y_VELOCITY = 3.0;
    private static final double ACCELERATION = 0.04;
    private static final double MAX_VY = 7.0;

    public Ball() {
        vy = Y_VELOCITY;

        RandomGenerator rgen = RandomGenerator.getInstance();
        vx = rgen.nextDouble(1.0, 3.0);
        if (rgen.nextBoolean(0.5)) {
            vx = -vx;
        }
    }

    public GOval drawCircle(double x, double y, int circle_diameter) {
        ballGraphics = new GOval(x, y, circle_diameter, circle_diameter);
        ballGraphics.setColor(Color.BLACK);
        ballGraphics.setFilled(true);
        ballGraphics.setFillColor(Color.BLACK);
        return ballGraphics;
    }

    public double getX() {
        return ballGraphics.getX();
    }

    public double getY() {
        return ballGraphics.getY();
    }

    private boolean hitsSideWall(double screenWidth, int frameMargin) {
        return ballGraphics.getX() <= frameMargin
                || ballGraphics.getX() + ballGraphics.getWidth() >= screenWidth - frameMargin;
    }

    private boolean hitsTopWall(int frameMargin) {
        return ballGraphics.getY() <= frameMargin;
    }

    public void move(double screenWidth, int frameMargin) {
        if (vy > 0) {
            vy = Math.min(vy + ACCELERATION, MAX_VY);
        }

        ballGraphics.move(vx, vy);

        if (hitsSideWall(screenWidth, frameMargin)) {
            bounceHorizontally();
        }
        if (hitsTopWall(frameMargin)) {
            bounceVertically();
        }
    }

    public void bounceOffRacket(double racketY) {
        vy = -Math.abs(vy);

        double ballNewY = racketY - ballGraphics.getHeight();
        ballGraphics.setLocation(ballGraphics.getX(), ballNewY);
    }

    public void bounceVertically() {
        vy = -vy;
    }

    public void bounceHorizontally() {
        vx = -vx;
    }

    public boolean isBelowBottom(double screenHeight, int frameMargin) {
        return ballGraphics.getY() >= screenHeight - frameMargin;
    }

    public GOval getGraphics() {
        return ballGraphics;
    }
}