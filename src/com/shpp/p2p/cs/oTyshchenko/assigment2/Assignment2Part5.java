package com.shpp.p2p.cs.oTyshchenko.assigment2;

import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Assignment2Part5 extends WindowProgram {
    public static final int APPLICATION_WIDTH = 500;
    public static final int APPLICATION_HEIGHT = 500;

    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 6;

    private static final double BOX_SIZE = 40;

    private static final double BOX_SPACING = 10;

    public void run() {
        double widthOfPicture = NUM_COLS * BOX_SIZE + BOX_SPACING * (NUM_COLS-1);
        double heightOfPicture = NUM_ROWS * BOX_SIZE + BOX_SPACING * (NUM_ROWS-1);

        double startX = (getWidth() - widthOfPicture) / 2;
        double startY = (getHeight() - heightOfPicture) / 2;

        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                drawRectangle(startX+((BOX_SIZE+BOX_SPACING)*j), startY+((BOX_SIZE+BOX_SPACING)*i));
            }
        }
    }

    public void drawRectangle(double x, double y) {
        GRect rectangle = new GRect(x, y, BOX_SIZE, BOX_SIZE);
        rectangle.setColor(Color.BLACK);
        rectangle.setFilled(true);
        rectangle.setFillColor(Color.BLACK);
        add(rectangle);
    }
}
