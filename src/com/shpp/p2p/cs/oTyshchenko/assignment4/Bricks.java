package com.shpp.p2p.cs.oTyshchenko.assignment4;

import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Bricks {
    public void wallBuilding(WindowProgram window, double windowWidth, int width, int height, int separation, int nRows, int nColumns, int yOffset) {
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN};

        double totalBricksWidth = nColumns * width + (nColumns - 1) * separation;
        double startX = (windowWidth - totalBricksWidth) / 2.0;

        for (int i = 0; i < nColumns; i++) {
            for (int j = 0; j < nRows; j++) {
                double brickX = startX + i * (width + separation);
                double brickY = yOffset + j * (height + separation);

                Color brickColor = colors[j / 2];

                GRect brick = drawRectangle(brickX, brickY, width, height, brickColor);

                window.add(brick);
            }
        }
    }

    public GRect drawRectangle(double x, double y, double width, double height, Color color) {
        GRect rectangle = new GRect(x, y, width, height);
        rectangle.setColor(color);
        rectangle.setFilled(true);
        rectangle.setFillColor(color);
        return rectangle;
    }
}
