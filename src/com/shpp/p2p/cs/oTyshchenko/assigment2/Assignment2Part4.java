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

    public void run() {
        GRect rectangle1 = new GRect((getWidth()-FLAG_WIDTH)/2, (getHeight()-FLAG_HEIGHT)/2, FLAG_WIDTH/3 ,FLAG_HEIGHT);
        rectangle1.setColor(Color.BLUE);
        rectangle1.setFilled(true);
        rectangle1.setFillColor(Color.BLUE);
        add(rectangle1);

        GRect rectangle2 = new GRect((getWidth()-FLAG_WIDTH)/2+FLAG_WIDTH/3, (getHeight()-FLAG_HEIGHT)/2, FLAG_WIDTH/3 ,FLAG_HEIGHT);
        rectangle2.setColor(new Color(251, 251,248));
        rectangle2.setFilled(true);
        rectangle2.setFillColor(new Color(251, 251,248));
        add(rectangle2);

        GRect rectangle3 = new GRect((getWidth()-FLAG_WIDTH)/2+2*FLAG_WIDTH/3, (getHeight()-FLAG_HEIGHT)/2, FLAG_WIDTH/3 ,FLAG_HEIGHT);
        rectangle3.setColor(Color.RED);
        rectangle3.setFilled(true);
        rectangle3.setFillColor(Color.RED);
        add(rectangle3);

        GLabel l = new GLabel("Flag of France");
        l.setFont("Times New Roman-18");
        l.setColor(Color.BLACK);
        l.setLocation(getWidth() - l.getWidth(), getHeight() - l.getDescent());
        add(l);
    }
}
