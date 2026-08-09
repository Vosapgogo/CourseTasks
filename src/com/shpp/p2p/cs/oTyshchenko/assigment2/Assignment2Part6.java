package com.shpp.p2p.cs.oTyshchenko.assigment2;

import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Assignment2Part6 extends WindowProgram {
    // The width and height of the application window
    public static final int APPLICATION_WIDTH = 700;
    public static final int APPLICATION_HEIGHT = 500;

    // The number of segments (circles) the caterpillar consists of
    private static final int PARTS = 8;

    public void run() {
        // Calculate the diameter of each circle based on the window width
        int diameter = getWidth()/PARTS;

        // Calculate the horizontal and vertical spacing between overlapping segments
        double diameterX=(diameter+20)/2.0;
        double diameterY= diameter/2.0 - 10;

        // Calculate the total width and height of entire caterpillar
        double widthOfPicture = (PARTS - 1) * diameterX + diameter;
        double heightOfPicture = diameter + diameterY;

        // Calculate starting X and Y coordinates
        double startX = (getWidth() - widthOfPicture) / 2;
        double startY = (getHeight() - heightOfPicture) / 2;

        for (int i = 0; i < PARTS; i++) {
            // Calculate the X coordinate for the current segment
            double currentX = startX + i * diameterX;
            double currentY;

            // Even segments go down, odd segments go up
            if (i % 2 == 0) {
                currentY = startY + diameterY;
            } else {
                currentY = startY;
            }

            drawCircle(currentX, currentY, diameter);
        }
    }

    /**
     * Creates and adds a single circular segment of the caterpillar to the screen.
     *
     * @param x The X coordinate of the top-left corner of the circle.
     * @param y The Y coordinate of the top-left corner of the circle.
     * @param circle_diameter The width and height of the circle.
     */
    public void drawCircle(double x, double y, int circle_diameter) {
        GOval circle = new GOval(x,y, circle_diameter, circle_diameter);
        circle.setColor(Color.RED);
        circle.setFilled(true);
        circle.setFillColor(Color.GREEN);
        add(circle);
    }
}
