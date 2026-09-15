package com.shpp.p2p.cs.oTyshchenko.assignment4;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Game extends WindowProgram {
    /** Width and height of application window in pixels */
    public static final int APPLICATION_WIDTH = 600;
    public static final int APPLICATION_HEIGHT = 800;

    /** Dimensions of the paddle */
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 10;

    /** Offset of the paddle up from the bottom */
    private static final int PADDLE_Y_OFFSET = 30;

    /** Dimension, label and styling for the "Play again" button shown after each game ends */
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 40;

    private static final String BUTTON_TEXT = "PLAY AGAIN";
    private static final String BUTTON_FONT = "SansSerif-bold-16";

    /** Label and styling for the win / game-over banner */
    private static final String WIN_TEXT = "YOU WIN!";
    private static final String GAME_OVER_TEXT = "GAME OVER";
    private static final String FINISH_FONT ="SansSerif-bold-36";

    /** Number of bricks per row */
    private static final int NBRICKS_PER_ROW = 10;

    /** Number of rows of bricks */
    private static final int NBRICK_ROWS = 10;

    /** Separation between bricks */
    private static final int BRICK_SEP = 4;

    /** Height of a brick */
    private static final int BRICK_HEIGHT = 8;

    /** Radius of the ball in pixels */
    private static final int BALL_RADIUS = 10;

    /** Offset of the top brick row from the top */
    private static final int BRICK_Y_OFFSET = 70;

    /** Number of turns */
    private static final int NTURNS = 3;

    /** Frame margin */
    public static final int FRAME_MARGIN = 10;

    /** Objects of Racket and Frame */
    private GRect racketGraphics;
    private GRect frameGraphics;

    /** Robot instance used to programmatically move the mouse cursor */
    private Robot robot;

    /** Tracks whether the racket is currently being dragged by the mouse */
    private boolean isDraggingRacket = false;

    /** Number of lives the player has remaining */
    private int livesLeft;

    /** Number of bricks remaining to be broken*/
    private int bricksLeft;

    /** Flag indicating whether the restart button was clicked */
    private volatile boolean restartClicked = false;

    /** The restart button rectangle shown on the game-over screen */
    private GRect restartButton;

    /** The text label displayed on top of the restart button */
    private GLabel restartLabel;

    /**
     * Entry point of the game. Initializes the Robot instance and mouse listeners,
     * then repeatedly sets up and runs a fresh game until the player wins or loses.
     * After each game ends, shows the restart button and waits for the player to
     * click it before starting a new round.
     */
    public void run() {
        try {
            robot = new Robot();
            addMouseListeners();

            while (true) {
                /** Clear any leftover restart-button reference from
                 * the previous round before wiping the screen,
                 * so mousePressed() can't match against stale
                 * (already-removed) graphics objects.
                 */
                restartButton = null;
                restartLabel = null;
                removeAll();

                // Set up a frame
                drawFrame();

                // Set up a fresh racket, centered horizontally just above the bottom edge
                Racket racket = new Racket();
                double paddleX = (getWidth() - PADDLE_WIDTH) / 2.0;
                double paddleY = getHeight() - PADDLE_HEIGHT - PADDLE_Y_OFFSET;
                racketGraphics = racket.drawRectangle(paddleX, paddleY, PADDLE_WIDTH, PADDLE_HEIGHT);
                add(racketGraphics);

                // Set up a fresh ball, centered in the middle of the window
                Ball ball = new Ball();
                double ballX = (getWidth() - BALL_RADIUS * 2) / 2.0;
                double ballY = getHeight() / 2.0;
                add(ball.drawCircle(ballX, ballY, BALL_RADIUS * 2));

                // Build a fresh wall of bricks, centered horizontally
                Bricks bricks = new Bricks();
                int availableWidth = getWidth() - 2 * FRAME_MARGIN - 2;
                int brickWidth = (availableWidth - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;
                bricks.wallBuilding(this, getWidth(), brickWidth, BRICK_HEIGHT, BRICK_SEP, NBRICK_ROWS, NBRICKS_PER_ROW, BRICK_Y_OFFSET);

                // Run the game until the player wins or loses all lives
                playGame(ball);

                // Show the "Play again" button and block here until it's clicked
                drawRestartButton();

                restartClicked = false;
                while (!restartClicked) {
                    pause(20);
                }
                // Loop back to the top of the while(true) and start a brand new game
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the black outline that marks the playable area, inset from the
     * window edges by FRAME_MARGIN on every side.
     */
    public void drawFrame() {
        frameGraphics = new GRect(FRAME_MARGIN, FRAME_MARGIN, getWidth()-2*FRAME_MARGIN, getHeight()-2*FRAME_MARGIN);
        frameGraphics.setColor(Color.BLACK);
        add(frameGraphics);
    }

    /**
     * Draws the "Play again" button and its centered text label,
     * shown after a game ends so the player can start a new round.
     */
    private void drawRestartButton() {
        // Create the restart button rectangle
        restartButton = new GRect((getWidth() - BUTTON_WIDTH) / 2.0, getHeight() / 2.0 + BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT - FRAME_MARGIN);
        restartButton.setFilled(true);
        restartButton.setColor(Color.LIGHT_GRAY);
        add(restartButton);

        // Create the label that will be displayed on the button
        restartLabel = new GLabel(BUTTON_TEXT);
        restartLabel.setFont(BUTTON_FONT);

        // Center the label text both horizontally and vertically inside the button rectangle
        double labelX = restartButton.getX() + (restartButton.getWidth() - restartLabel.getWidth()) / 2.0;
        double labelY = restartButton.getY() + restartButton.getHeight() / 2.0 + restartLabel.getAscent() / 2.0 - 2;
        add(restartLabel, labelX, labelY);
    }

    /**
     * Runs the main game loop: moves the ball, handles collisions,
     * tracks remaining lives and bricks, and resets the ball's position
     * after it falls below the bottom of the screen (if the player still has lives left).
     * The loop continues until the player either loses all lives or clears all bricks.
     *
     * @param ball the Ball object to be moved and tracked during gameplay
     */
    private void playGame(Ball ball) {
        // Initialize the number of bricks and lives at the start of the game
        bricksLeft = NBRICK_ROWS * NBRICKS_PER_ROW;
        livesLeft = NTURNS;

        // Wait for the player to click before launching the ball
        waitForClick();

        // Main game loop: continues while the player has lives left and bricks remain
        while (livesLeft > 0 && bricksLeft > 0) {
            // Move the ball one step and handle any collisions with walls, paddle, or bricks
            moveBallWithCollisions(ball);

            // Check if the ball has fallen below the bottom of the screen
            if (ball.isBelowBottom(getHeight(), FRAME_MARGIN)) {
                livesLeft--;

                // If the player still has lives left, reset the ball
                // to the center and wait for the next click
                if (livesLeft > 0) {
                    double ballX = (getWidth() - BALL_RADIUS * 2) / 2.0;
                    double ballY = getHeight() / 2.0;
                    ball.getGraphics().setLocation(ballX, ballY);
                    waitForClick();
                }
            }

            // Pause briefly to control the animation speed
            pause(8);
        }

        // Display the game over message, indicating
        // whether the player won (all bricks cleared) or lost
        displayGameOverMessage(bricksLeft == 0);
    }

    /**
     * Moves the ball forward by its allotted number of steps for the current frame,
     * checking for collisions after each step. The method stops moving the ball
     * further this frame as soon as a brick collision occurs.
     *
     * @param ball the Ball object to be moved and checked for collisions
     */
    private void moveBallWithCollisions(Ball ball) {
        // Determine how many steps the ball should move this frame (based on its speed)
        int steps = ball.getStepsForThisFrame();

        for (int i = 0; i < steps; i++) {
            // Move the ball one step, bouncing off walls and the ceiling if needed
            ball.moveOneStep(getWidth(), FRAME_MARGIN, steps);

            // Check if the ball is currently colliding with any object
            GObject collider = getCollidingObject(ball);

            if (collider == racketGraphics) {
                // Bounce the ball off the racket based on the racket's vertical position
                ball.bounceOffRacket(racketGraphics.getY());
            } else if (collider != null) {
                // Bounce the ball off the brick, remove the brick, and update the brick count
                ball.bounceOffBrick(collider);
                remove(collider);
                bricksLeft--;
                // Stop further movement this frame since the ball just hit a brick
                break;
            }
        }
    }

    /**
     * Displays a centered end-of-game message on the screen, indicating whether
     * the player won or lost. The message text and color change depending on
     * the outcome (green for a win, red for a loss).
     *
     * @param won true if the player cleared all the bricks and won the game, false otherwise
     */
    private void displayGameOverMessage(boolean won) {
        // Choose the message text and color based on whether the player won or lost
        String text = won ? WIN_TEXT : GAME_OVER_TEXT;
        GLabel message = new GLabel(text);
        message.setFont(FINISH_FONT);
        message.setColor(won ? Color.GREEN : Color.RED);

        // Center the message both horizontally and vertically on the screen
        double x = (getWidth() - message.getWidth()) / 2.0;
        double y = (getHeight() + message.getAscent()) / 2.0;
        add(message, x, y);
    }

    /**
     * Checks the four corners of the ball's bounding box for a collision with
     * another object on the screen. Returns the first colliding object found,
     * ignoring the background frame and the ball itself.
     *
     * @param ball the Ball object whose corners are checked for collisions
     * @return the GObject the ball is colliding with, or null if there is no collision
     */
    private GObject getCollidingObject(Ball ball) {
        // Get the ball's top-left position and diameter
        double x = ball.getX();
        double y = ball.getY();
        double d = BALL_RADIUS * 2;

        GObject collider;

        // Check the top-left corner of the ball's bounding box
        collider = getElementAt(x, y);
        if (collider != null && collider != frameGraphics && collider != ball.getGraphics()) return collider;

        // Check the top-right corner of the ball's bounding box
        collider = getElementAt(x + d, y);
        if (collider != null && collider != frameGraphics && collider != ball.getGraphics()) return collider;

        // Check the bottom-left corner of the ball's bounding box
        collider = getElementAt(x, y + d);
        if (collider != null && collider != frameGraphics && collider != ball.getGraphics()) return collider;

        // Check the bottom-right corner of the ball's bounding box
        collider = getElementAt(x + d, y + d);
        if (collider != null && collider != frameGraphics && collider != ball.getGraphics()) return collider;

        // No collision found at any corner
        return null;
    }

    /**
     * Handles mouse press events. If the restart button (or its label) was clicked,
     * flags that a restart was requested. If the racket was clicked, begins dragging
     * the racket and, if a Robot is available, snaps the mouse cursor to the racket's
     * center so dragging starts smoothly from that position.
     *
     * @param e the MouseEvent containing the location of the mouse press
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Find the object located at the mouse press position
        GObject obj = getElementAt(e.getX(), e.getY());

        // If the restart button or its label was clicked, flag the restart request and stop here
        if (restartButton != null && (obj == restartButton || obj == restartLabel)) {
            restartClicked = true;
            return;
        }

        // If the racket was clicked, start dragging it
        if (obj != restartButton && bricksLeft!=0 && livesLeft!=0) {
            isDraggingRacket = true;

            // Calculate the center point of the racket
            double centerX = racketGraphics.getX() + PADDLE_WIDTH / 2.0;
            double centerY = racketGraphics.getY() + PADDLE_HEIGHT / 2.0;

            // If a Robot instance is available, move the actual mouse cursor
            // to the racket's center so subsequent dragging feels natural
            if (robot != null) {
                Point componentLocation = e.getComponent().getLocationOnScreen();
                int screenX = (int) (componentLocation.x + centerX);
                int screenY = (int) (componentLocation.y + centerY);
                robot.mouseMove(screenX, screenY);
            }
        }
    }

    /**
     * Handles mouse drag events. If the racket is currently being dragged,
     * updates its horizontal position to follow the mouse cursor, while
     * keeping the racket within the bounds of the playing field.
     *
     * @param e the MouseEvent containing the current mouse position
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (isDraggingRacket) {
            // Calculate the new racket X position so the cursor stays centered on it
            double newX = e.getX() - PADDLE_WIDTH / 2.0;

            // Determine the allowed horizontal range for the racket within the frame
            double leftBound = FRAME_MARGIN;
            double rightBound = getWidth() - FRAME_MARGIN - PADDLE_WIDTH;

            // Clamp the racket's position so it doesn't move outside the playing field
            if (newX < leftBound) {
                newX = leftBound;
            } else if (newX > rightBound) {
                newX = rightBound;
            }

            // Move the racket to its new position, keeping the same Y coordinate
            racketGraphics.setLocation(newX, racketGraphics.getY());
        }
    }

    /**
     * Handles mouse release events. Stops the racket dragging process
     * when the mouse button is released.
     *
     * @param e the MouseEvent triggered when the mouse button is released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Stop dragging the racket since the mouse button is no longer held down
        isDraggingRacket = false;
    }

}
