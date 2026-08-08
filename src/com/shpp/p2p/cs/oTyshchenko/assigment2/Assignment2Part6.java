package com.shpp.p2p.cs.oTyshchenko.assigment2;

import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Assignment2Part6 extends WindowProgram {
    public static final int APPLICATION_WIDTH = 700;
    public static final int APPLICATION_HEIGHT = 500;

    private static final int PARTS = 6;

    public void run() {
        int diameter = getWidth()/PARTS;

        double widthOfPicture = (PARTS - 1) * ((diameter + 20) / 2) + diameter;
        double heightOfPicture = diameter + diameter/2 - 10;

        double startX = (getWidth() - widthOfPicture) / 2;
        double startY = (getHeight() - heightOfPicture) / 2;

        for (int i = 0; i < PARTS; i++) {
            drawCircle(startX+i*(diameter+20)/2,startY + diameter/2-10, diameter);
            i++;
            drawCircle(startX+i*(diameter+20)/2,startY, diameter);
        }
    }

    public void drawCircle(double x, double y, int circle_diameter) {
        GOval circle = new GOval(x,y, circle_diameter, circle_diameter);
        circle.setColor(Color.RED);
        circle.setFilled(true);
        circle.setFillColor(Color.GREEN);
        add(circle);
    }
}
