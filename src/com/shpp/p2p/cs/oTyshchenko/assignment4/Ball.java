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

    public void move(double screenWidth, int frameMargin) {
        if (vy > 0) {
            vy += ACCELERATION;
        }

        ballGraphics.move(vx, vy);

        if (ballGraphics.getX() <= frameMargin || ballGraphics.getX() + ballGraphics.getWidth() >= screenWidth - frameMargin) {
            vx = -vx;
        }

        if (ballGraphics.getY() <= frameMargin) {
            vy = -vy;
        }
    }

    public void bounceOffRacket(double racketY) {
        vy = -Y_VELOCITY;

        double ballNewY = racketY - ballGraphics.getHeight();
        ballGraphics.setLocation(ballGraphics.getX(), ballNewY);
    }
}