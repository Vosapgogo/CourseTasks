package com.shpp.p2p.cs.oTyshchenko.assigment2;

import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Assignment2Part3 extends WindowProgram {
    /* Constants controlling the relative positions of the
     * three toes to the upper-left corner of the pawprint.
     *
     * (Yes, I know that actual pawprints have four toes.
     * Just pretend it's a cartoon animal. ^_^)
     */
    static final double FIRST_TOE_OFFSET_X = 0;
    static final double FIRST_TOE_OFFSET_Y = 20;
    static final double SECOND_TOE_OFFSET_X = 30;
    static final double SECOND_TOE_OFFSET_Y = 0;
    static final double THIRD_TOE_OFFSET_X = 60;
    static final double THIRD_TOE_OFFSET_Y = 20;

    /* The pos of the heel relative to the upper-left
     * corner of the pawprint.
     */
    static final double HEEL_OFFSET_X = 20;
    static final double HEEL_OFFSET_Y = 40;

    /* Each toe is an oval with this width and height. */
    static final double TOE_WIDTH = 20;
    static final double TOE_HEIGHT = 30;

    /* The heel is an oval with this width and height. */
    static final double HEEL_WIDTH = 40;
    static final double HEEL_HEIGHT = 60;

    /* The default W and H of the window. These constants will tell Java to
     * create a window whose size is *approximately* given by these dimensions. You should
     * not directly use these constants in your program; instead, use getWidth() and
     * getHeight(), which return the *exact* width and height of the window.
     */
    public static final int APPLICATION_WIDTH = 270;
    public static final int APPLICATION_HEIGHT = 220;

    public void run() {
        drawPaw(20, 20);
        drawPaw(180, 70);
    }

    /**
     * Draws a pawprint. The parameters should specify the upper-left corner of the
     * bounding box containing that pawprint.
     *
     * @param x The x coord of the up-left corner of the bounding box for the pawprint.
     * @param y The y coord of the up-left corner
     */
    void drawPaw(double x, double y) {
        drawToe(x+FIRST_TOE_OFFSET_X, y+FIRST_TOE_OFFSET_Y);
        drawToe(x+SECOND_TOE_OFFSET_X, y+SECOND_TOE_OFFSET_Y);
        drawToe(x+THIRD_TOE_OFFSET_X, y+THIRD_TOE_OFFSET_Y);

        drawHeel(x+HEEL_OFFSET_X, y+HEEL_OFFSET_Y);
    }

    public void drawToe(double x, double y) {
        drawCircle(x,y,TOE_WIDTH, TOE_HEIGHT);
    }

    public void drawHeel(double x, double y) {
        drawCircle(x,y,HEEL_WIDTH,HEEL_HEIGHT);
    }

    public void drawCircle(double x, double y, double width, double height) {
        GOval circle = new GOval(x, y, width, height);
        circle.setColor(Color.BLACK);
        circle.setFilled(true);
        circle.setFillColor(Color.BLACK);
        add(circle);
    }
}
