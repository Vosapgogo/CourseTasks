package com.shpp.p2p.cs.oTyshchenko.assignment4;

import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.util.RandomGenerator;

import java.awt.*;

public class Ball {
    // Horizontal and vertical velocity of the ball
    private double vx, vy;

    // Graphical representation of the ball
    private GOval ballGraphics;

    // Initial vertical velocity
    private static final double Y_VELOCITY = 3.0;

    // Gravity acceleration applied to vertical velocity each step
    private static final double ACCELERATION = 0.04;

    // Maximum vertical velocity (terminal velocity)
    private static final double MAX_VY = 7.0;

    // Maximum distance the ball can move in a single step
    private static final double MAX_STEP = 4.0;

    // Percentage of velocity retained after a bounce (energy loss factor)
    private static final double PERCENTAGE = 0.5;

    // Minimum possible horizontal velocity
    private static final double VX_MIN = 1.0;

    // Maximum possible horizontal velocity
    private static final double VX_MAX = 3.0;

    // Constructor: initializes the ball's velocity
    public Ball() {
        // Set the initial vertical velocity
        vy = Y_VELOCITY;

        RandomGenerator rgen = RandomGenerator.getInstance();
        // Set a random horizontal velocity within the defined range
        vx = rgen.nextDouble(VX_MIN, VX_MAX);
        // Randomly flip the direction (left/right) with the given probability
        if (rgen.nextBoolean(PERCENTAGE)) {
            vx = -vx;
        }
    }

    // Creates and returns a black, filled circle at the given position
    public GOval drawCircle(double x, double y, int circle_diameter) {
        ballGraphics = new GOval(x, y, circle_diameter, circle_diameter);
        ballGraphics.setColor(Color.BLACK);
        ballGraphics.setFilled(true);
        ballGraphics.setFillColor(Color.BLACK);
        return ballGraphics;
    }

    // Returns the current X coordinate of the ball
    public double getX() {
        return ballGraphics.getX();
    }

    // Returns the current Y coordinate of the ball
    public double getY() {
        return ballGraphics.getY();
    }

    // Checks whether the ball has hit the left or right wall of the frame
    private boolean hitsSideWall(double screenWidth, int frameMargin) {
        return ballGraphics.getX() <= frameMargin
                || ballGraphics.getX() + ballGraphics.getWidth() >= screenWidth - frameMargin;
    }

    // Checks whether the ball has hit the top wall of the frame
    private boolean hitsTopWall(int frameMargin) {
        return ballGraphics.getY() <= frameMargin;
    }

    /**
     * Calculates how many sub-steps are needed this frame to keep
     * the ball's movement smooth (avoids skipping over collisions
     * when velocity is high)
     */
    public int getStepsForThisFrame() {
        // Apply gravity acceleration while the ball is moving downward
        if (vy > 0) {
            vy = Math.min(vy + ACCELERATION, MAX_VY);
        }

        // Determine the largest distance the ball will travel this frame
        double totalDistance = Math.max(Math.abs(vx), Math.abs(vy));
        // Split the movement into smaller steps, each no larger than MAX_STEP
        return Math.max((int) Math.ceil(totalDistance / MAX_STEP), 1);
    }

    /**
     * Moves the ball by one sub-step and checks
     * for wall collisions along the way
     */
    public void moveOneStep(double screenWidth, int frameMargin, int steps) {
        // Split the velocity evenly across all sub-steps
        double stepVx = vx / steps;
        double stepVy = vy / steps;

        ballGraphics.move(stepVx, stepVy);

        // Reverse horizontal direction if the ball hit a side wall
        if (hitsSideWall(screenWidth, frameMargin)) {
            bounceHorizontally();
        }
        // Reverse vertical direction if the ball hit the top wall
        if (hitsTopWall(frameMargin)) {
            bounceVertically();
        }
    }

    /**
     * Makes the ball bounce upward off the racket and repositions it
     * just above the racket to prevent overlap
     */
    public void bounceOffRacket(double racketY) {
        vy = -Math.abs(vy);

        double ballNewY = racketY - ballGraphics.getHeight();
        ballGraphics.setLocation(ballGraphics.getX(), ballNewY);
    }

    /**
     * Determines the bounce direction after colliding with
     * a brick, based on which axis has the smaller overlap
     */
    public void bounceOffBrick(GObject brick) {
        double ballCenterX = ballGraphics.getX() + ballGraphics.getWidth() / 2;
        double ballCenterY = ballGraphics.getY() + brick.getHeight();

        double brickCenterX = brick.getX() + brick.getWidth() / 2;
        double brickCenterY = brick.getY() + brick.getHeight() / 2;

        // Amount of overlap between the ball and brick along the X axis
        double overlapX = (ballGraphics.getWidth() / 2 + brick.getWidth() / 2) - Math.abs(ballCenterX - brickCenterX);
        // Amount of overlap between the ball and brick along the Y axis
        double overlapY = (ballGraphics.getWidth() / 2 + brick.getHeight() / 2) - Math.abs(ballCenterY - brickCenterY);

        // Smaller overlap indicates the axis along which the collision occurred
        if (overlapX < overlapY) {
            bounceHorizontally();
        } else {
            bounceVertically();
        }
    }

    // Reverses the ball's vertical direction
    public void bounceVertically() {
        vy = -vy;
    }

    // Reverses the ball's horizontal direction
    public void bounceHorizontally() {
        vx = -vx;
    }

    // Checks whether the ball has fallen below the bottom edge of the screen
    public boolean isBelowBottom(double screenHeight, int frameMargin) {
        return ballGraphics.getY() >= screenHeight - frameMargin;
    }

    // Returns the graphical object representing the ball
    public GOval getGraphics() {
        return ballGraphics;
    }
}