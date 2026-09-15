package com.shpp.p2p.cs.oTyshchenko.assignment4;

import acm.graphics.GRect;

import java.awt.*;

public class Racket {
    // Creates and returns a black, filled rectangle at the given position
    public GRect drawRectangle(double x, double y, double width, double height) {
        GRect rectangle = new GRect(x, y, width, height);
        rectangle.setColor(Color.BLACK);
        rectangle.setFilled(true);
        rectangle.setFillColor(Color.BLACK);
        return rectangle;
    }
}
