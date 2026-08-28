package com.shpp.p2p.cs.oTyshchenko.assignment3;

import acm.graphics.GLabel;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.Color;
import java.awt.event.MouseEvent;

/**
 * "Five Seconds of Fame": an animation of a glass gradually filling with water
 * after a button is pressed. Runs for exactly 5000 ms (±150 ms), 60 frames (12 FPS).
 */
public class Assignment3Part6 extends WindowProgram {

    private static final int APPLICATION_WIDTH = 400;
    private static final int APPLICATION_HEIGHT = 500;

    // Animation parameters
    private static final int TOTAL_FRAMES = 60;
    private static final long DURATION_MS = 5000;
    private static final long FRAME_DELAY_MS = DURATION_MS / TOTAL_FRAMES;
    private static final long POLL_DELAY_MS = 20;

    // Glass geometry
    private static final int GLASS_X = 100;
    private static final int GLASS_Y = 60;
    private static final int GLASS_WIDTH = 200;
    private static final int GLASS_HEIGHT = 320;
    private static final int GLASS_BORDER_INSET = 3;

    // Button geometry
    private static final int BUTTON_WIDTH = 140;
    private static final int BUTTON_HEIGHT = 45;
    private static final int BUTTON_Y = 420;
    private static final String BUTTON_TEXT = "Fill with water";

    // Percent label geometry
    private static final int PERCENT_LABEL_X_OFFSET = 12;
    private static final int PERCENT_LABEL_Y_OFFSET = 15;

    // Color constants
    private static final Color BUTTON_COLOR = new Color(52, 120, 199);
    private static final Color GLASS_COLOR = new Color(90, 90, 90);
    private static final Color WATER_COLOR = new Color(64, 164, 223);
    private static final Color TEXT_COLOR = new Color(60, 60, 60);

    // Graphical objects
    private GRect button;
    private GLabel buttonLabel;
    private GRect water;
    private GLabel percentLabel;

    private volatile boolean started = false;

    @Override
    public void run() {
        setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        addMouseListeners();
        drawButton();

        // Wait until the button is clicked
        while (!started) {
            pause(POLL_DELAY_MS);
        }

        // Remove the button and prepare the scene
        remove(button);
        remove(buttonLabel);

        drawGlass();
        initWater();
        initPercentLabel();

        // Start the animation itself
        fillGlassAnimation();
    }

    /**
     * Draws the "Fill with water" button in the center of the screen.
     */
    private void drawButton() {
        double buttonX = (APPLICATION_WIDTH - BUTTON_WIDTH) / 2.0;
        button = new GRect(buttonX, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setFilled(true);
        button.setColor(BUTTON_COLOR);
        add(button);

        buttonLabel = new GLabel(BUTTON_TEXT);
        buttonLabel.setColor(Color.WHITE);

        double labelX = APPLICATION_WIDTH / 2.0 - buttonLabel.getWidth() / 2.0;
        double labelY = BUTTON_Y + BUTTON_HEIGHT / 2.0 + buttonLabel.getAscent() / 2.0;
        add(buttonLabel, labelX, labelY);
    }

    /**
     * Draws the static outline of the empty glass.
     */
    private void drawGlass() {
        GRect glass = new GRect(GLASS_X, GLASS_Y, GLASS_WIDTH, GLASS_HEIGHT);
        glass.setColor(GLASS_COLOR);
        add(glass);
    }

    /**
     * Creates the water object (initial height is 0).
     */
    private void initWater() {
        water = new GRect(
                GLASS_X + GLASS_BORDER_INSET,
                GLASS_Y + GLASS_HEIGHT - GLASS_BORDER_INSET,
                GLASS_WIDTH - 2 * GLASS_BORDER_INSET,
                0
        );
        water.setFilled(true);
        water.setColor(WATER_COLOR);
        add(water);
    }

    /**
     * Creates the fill-percentage text label above the glass.
     */
    private void initPercentLabel() {
        percentLabel = new GLabel("0%");
        percentLabel.setColor(TEXT_COLOR);
        add(percentLabel,
                GLASS_X + GLASS_WIDTH / 2.0 - PERCENT_LABEL_X_OFFSET,
                GLASS_Y - PERCENT_LABEL_Y_OFFSET
        );
    }

    /**
     * Handles only the frame math and the water's size/position changes over time.
     */
    private void fillGlassAnimation() {
        long animationStart = System.currentTimeMillis();
        double maxWaterHeight = GLASS_HEIGHT - 2 * GLASS_BORDER_INSET;
        double waterWidth = GLASS_WIDTH - 2 * GLASS_BORDER_INSET;

        for (int frame = 0; frame < TOTAL_FRAMES; frame++) {
            long frameStart = System.currentTimeMillis();

            double progress = (double) frame / (TOTAL_FRAMES - 1);

            // Calculate the new water height and position
            double waterHeight = Math.max(0, Math.min(maxWaterHeight, maxWaterHeight * progress));
            double waterY = GLASS_Y + GLASS_HEIGHT - GLASS_BORDER_INSET - waterHeight;

            water.setSize(waterWidth, waterHeight);
            water.setLocation(GLASS_X + GLASS_BORDER_INSET, waterY);

            // Update the percentage label
            percentLabel.setLabel((int) Math.round(progress * 100) + "%");

            // Timing control to stay within the 5-second budget
            long spentOnFrame = System.currentTimeMillis() - frameStart;
            long toSleep = FRAME_DELAY_MS - spentOnFrame;
            if (toSleep > 0) {
                pause(toSleep);
            }
        }

        long totalElapsed = System.currentTimeMillis() - animationStart;
        System.out.println("Frames: " + TOTAL_FRAMES + ", duration: " + totalElapsed + " ms");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!started && button != null && button.contains(e.getX(), e.getY())) {
            started = true;
        }
    }
}