package com.shpp.p2p.cs.oTyshchenko.assigment2;

import acm.graphics.GLabel;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Assignment2Part4 extends WindowProgram {
    public static final int APPLICATION_WIDTH = 300;
    public static final int APPLICATION_HEIGHT = 300;

    public static final double FLAG_WIDTH = 200;
    public static final double FLAG_HEIGHT = 170;

    public static final Color BLUE = Color.BLUE;
    public static final Color WHITE = new Color(251, 251,248);
    public static final Color RED = Color.RED;

    public void run() {
        drawRectangle((getWidth()-FLAG_WIDTH)/2, BLUE);
        drawRectangle((getWidth()-FLAG_WIDTH)/2+FLAG_WIDTH/3, WHITE);
        drawRectangle((getWidth()-FLAG_WIDTH)/2+2*FLAG_WIDTH/3, RED);

        GLabel l = new GLabel("Flag of France");
        l.setFont("Times New Roman-18");
        l.setColor(Color.BLACK);
        l.setLocation(getWidth() - l.getWidth(), getHeight() - l.getDescent());
        add(l);
    }

    public void drawRectangle(double x, Color color) {
        GRect rectangle = new GRect(x,(getHeight()-FLAG_HEIGHT)/2, FLAG_WIDTH/3 ,FLAG_HEIGHT);
        rectangle.setColor(color);
        rectangle.setFilled(true);
        rectangle.setFillColor(color);
        add(rectangle);
    }
}
