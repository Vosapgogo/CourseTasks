package com.shpp.p2p.cs.oTyshchenko.assignment4;

import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Bricks {
    /**
     * Builds the wall of bricks: calculates layout so the grid is centered
     * horizontally, then creates and adds each brick to the window,
     * coloring rows in groups of two using the colors array
     */
    public void wallBuilding(WindowProgram window, double windowWidth, int width, int height, int separation, int nRows, int nColumns, int yOffset) {
        // Row colors, from top to bottom (each color spans 2 rows)
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN};

        // Total width occupied by all bricks and the gaps between them
        double totalBricksWidth = nColumns * width + (nColumns - 1) * separation;
        // X coordinate to start from so the wall is centered on the screen
        double startX = (windowWidth - totalBricksWidth) / 2.0;

        for (int i = 0; i < nColumns; i++) {
            for (int j = 0; j < nRows; j++) {
                // Position of the current brick
                double brickX = startX + i * (width + separation);
                double brickY = yOffset + j * (height + separation);

                // Every two rows share the same color
                Color brickColor = colors[j / 2];

                GRect brick = drawRectangle(brickX, brickY, width, height, brickColor);

                window.add(brick);
            }
        }
    }

    // Creates and returns rectangle at the given position and color
    public GRect drawRectangle(double x, double y, double width, double height, Color color) {
        GRect rectangle = new GRect(x, y, width, height);
        rectangle.setColor(color);
        rectangle.setFilled(true);
        rectangle.setFillColor(color);
        return rectangle;
    }
}
