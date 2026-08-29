package com.shpp.p2p.cs.oTyshchenko.assignment3;

import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Assignment3Part4 extends WindowProgram {
    // Constants defining the dimensions of a single brick and the base size of the pyramid
    private static final double BRICK_HEIGHT = 30;
    private static final double BRICK_WIDTH = 50;
    private static final int BRICKS_IN_BASE = 10;

    public void run() {
        // Outer loop: iterates through each row from the bottom up (starting from the base row down to 1 brick)
        for (int i = BRICKS_IN_BASE; i > 0; i--) {
            // Calculate the vertical position (y) for the current row, keeping the pyramid anchored to the bottom of the window
            double y = getHeight() - (BRICKS_IN_BASE - i + 1) * BRICK_HEIGHT;

            // Calculate the horizontal starting position (startX) to center the current row dynamically
            double startX = (getWidth() - (i * BRICK_WIDTH)) / 2.0;

            // Inner loop: draws each individual brick in the current row
            for (int j = 0; j < i; j++) {
                double x = startX + j * BRICK_WIDTH;
                drawABrick(x, y);
            }
        }
    }

    /**
     * Creates and adds a single brick with a border and filled color to the window.
     * @param x the x-coordinate of the brick
     * @param y the y-coordinate of the brick
     */
    public void drawABrick(double x, double y) {
        GRect rectangle = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        rectangle.setColor(Color.BLACK);
        rectangle.setFilled(true);
        rectangle.setFillColor(Color.GRAY);
        add(rectangle);
    }
}