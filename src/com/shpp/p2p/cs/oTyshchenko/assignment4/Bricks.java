package com.shpp.p2p.cs.oTyshchenko.assignment4;

import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Bricks {
    /** Number of consecutive rows that share the same color */
    private static final int ROWS_PER_COLOR = 2;

    /** Minimum brick width/height in pixels, so bricks never shrink to invisible (0px) */
    private static final int MIN_BRICK_SIZE = 1;

    /**
     * Builds the wall of bricks: calculates layout so the grid is centered
     * horizontally, then creates and adds each brick to the window,
     * coloring rows in groups of two using the colors array
     */
    public void wallBuilding(WindowProgram window, double windowWidth, int width, int height, int separation, int nRows, int nColumns, int yOffset) {
        // Row colors, from top to bottom (each color spans 2 rows, then repeats)
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN};

        // Guard against a computed width/height of 0 or less (e.g. way too many columns
        // for the window), which would make bricks invisible instead of just small
        width = Math.max(width, MIN_BRICK_SIZE);
        height = Math.max(height, MIN_BRICK_SIZE);

        // Total width occupied by all bricks and the gaps between them
        double totalBricksWidth = nColumns * width + (nColumns - 1) * separation;
        // X coordinate to start from so the wall is centered on the screen
        double startX = (windowWidth - totalBricksWidth) / 2.0;

        for (int i = 0; i < nColumns; i++) {
            for (int j = 0; j < nRows; j++) {
                // Position of the current brick
                double brickX = startX + i * (width + separation);
                double brickY = yOffset + j * (height + separation);

                // Cycle back through the palette every ROWS_PER_COLOR rows,
                // so this works for any number of rows, not just up to 10
                Color brickColor = colors[(j / ROWS_PER_COLOR) % colors.length];

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