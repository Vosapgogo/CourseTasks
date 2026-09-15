package com.shpp.p2p.cs.oTyshchenko.assignment4;

import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.util.RandomGenerator;

import java.awt.*;

public class Ball {
    private double vx, vy;
    private GOval ballGraphics;

    private static final double Y_VELOCITY = 3.0;
    private static final double ACCELERATION = 0.04;
    private static final double MAX_VY = 7.0;

    private static final double MAX_STEP = 4.0;

    private static final double PERCENTAGE = 0.5;

    private static final double VX_MIN = 1.0;
    private static final double VX_MAX = 3.0;

    public Ball() {
        vy = Y_VELOCITY;

        RandomGenerator rgen = RandomGenerator.getInstance();
        vx = rgen.nextDouble(VX_MIN, VX_MAX);
        if (rgen.nextBoolean(PERCENTAGE)) {
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

    public int getStepsForThisFrame() {
        if (vy > 0) {
            vy = Math.min(vy + ACCELERATION, MAX_VY);
        }

        double totalDistance = Math.max(Math.abs(vx), Math.abs(vy));
        return Math.max((int) Math.ceil(totalDistance / MAX_STEP), 1);
    }

    public void moveOneStep(double screenWidth, int frameMargin, int steps) {
        double stepVx = vx / steps;
        double stepVy = vy / steps;

        ballGraphics.move(stepVx, stepVy);

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

    public void bounceOffBrick(GObject brick) {
        double ballCenterX = ballGraphics.getX() + ballGraphics.getWidth() / 2;
        double ballCenterY = ballGraphics.getY() + brick.getHeight();

        double brickCenterX = brick.getX() + brick.getWidth() / 2;
        double brickCenterY = brick.getY() + brick.getHeight() / 2;

        double overlapX = (ballGraphics.getWidth() / 2 + brick.getWidth() / 2) - Math.abs(ballCenterX - brickCenterX);
        double overlapY = (ballGraphics.getWidth() / 2 + brick.getHeight() / 2) - Math.abs(ballCenterY - brickCenterY);

        if (overlapX < overlapY) {
            bounceHorizontally();
        } else {
            bounceVertically();
        }
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