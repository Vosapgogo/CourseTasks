package com.shpp.p2p.cs.oTyshchenko.assigment2;
import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Assignment2Part2 extends WindowProgram {
    public static final int APPLICATION_WIDTH = 300;
    public static final int APPLICATION_HEIGHT = 300;

    private static final double CIRCLE_DIAMETER = 100;

    public void run() {
        drawCircle(0,0);
        drawCircle(getWidth()-CIRCLE_DIAMETER, 0);
        drawCircle(0, getHeight()-CIRCLE_DIAMETER);
        drawCircle(getWidth()-CIRCLE_DIAMETER, getHeight()-CIRCLE_DIAMETER);

        drawRectangle();
    }

    public void drawCircle(double x, double y) {
        GOval circle = new GOval(x,y, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
        circle.setColor(Color.BLACK);
        circle.setFilled(true);
        circle.setFillColor(Color.BLACK);
        add(circle);
    }

    public void drawRectangle() {
        GRect rectangle = new GRect(CIRCLE_DIAMETER/2, CIRCLE_DIAMETER/2, getWidth()-CIRCLE_DIAMETER, getHeight()-CIRCLE_DIAMETER);
        rectangle.setColor(Color.WHITE);
        rectangle.setFilled(true);
        rectangle.setFillColor(Color.WHITE);
        add(rectangle);
    }
}
