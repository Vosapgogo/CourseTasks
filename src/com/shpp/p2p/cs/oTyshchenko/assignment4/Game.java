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
    private static final int PADDLE_WIDTH = 800;
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

    public static final int FRAME_MARGIN = 10;

    private GRect racketGraphics;
    private GRect frameGraphics;

    private Robot robot;

    private boolean isDraggingRacket = false;

    private int livesLeft;
    private int bricksLeft;

    private volatile boolean restartClicked = false;
    private GRect restartButton;
    private GLabel restartLabel;

    public void run() {
        try {
            robot = new Robot();
            addMouseListeners();

            while (true) {
                restartButton = null;
                restartLabel = null;
                removeAll();

                drawFrame();

                Racket racket = new Racket();
                double paddleX = (getWidth() - PADDLE_WIDTH) / 2.0;
                double paddleY = getHeight() - PADDLE_HEIGHT - PADDLE_Y_OFFSET;
                racketGraphics = racket.drawRectangle(paddleX, paddleY, PADDLE_WIDTH, PADDLE_HEIGHT);
                add(racketGraphics);

                Ball ball = new Ball();
                double ballX = (getWidth() - BALL_RADIUS * 2) / 2.0;
                double ballY = getHeight() / 2.0;
                add(ball.drawCircle(ballX, ballY, BALL_RADIUS * 2));

                Bricks bricks = new Bricks();
                int availableWidth = getWidth() - 2 * FRAME_MARGIN - 2;
                int brickWidth = (availableWidth - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;
                bricks.wallBuilding(this, getWidth(), brickWidth, BRICK_HEIGHT, BRICK_SEP, NBRICK_ROWS, NBRICKS_PER_ROW, BRICK_Y_OFFSET);

                playGame(ball);

                drawRestartButton();

                restartClicked = false;
                while (!restartClicked) {
                    pause(20);
                }
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void drawFrame() {
        frameGraphics = new GRect(FRAME_MARGIN, FRAME_MARGIN, getWidth()-2*FRAME_MARGIN, getHeight()-2*FRAME_MARGIN);
        frameGraphics.setColor(Color.BLACK);
        add(frameGraphics);
    }

    private void drawRestartButton() {
        restartButton = new GRect((getWidth() - BUTTON_WIDTH) / 2.0, getHeight() / 2.0 + BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT - FRAME_MARGIN);
        restartButton.setFilled(true);
        restartButton.setColor(Color.LIGHT_GRAY);
        add(restartButton);

        restartLabel = new GLabel(BUTTON_TEXT);
        restartLabel.setFont(BUTTON_FONT);

        double labelX = restartButton.getX() + (restartButton.getWidth() - restartLabel.getWidth()) / 2.0;
        double labelY = restartButton.getY() + restartButton.getHeight() / 2.0 + restartLabel.getAscent() / 2.0 - 2;
        add(restartLabel, labelX, labelY);
    }

    private boolean overlapsRacket(Ball ball) {
        double ballLeft = ball.getX();
        double ballRight = ball.getX() + BALL_RADIUS * 2;
        double ballTop = ball.getY();
        double ballBottom = ball.getY() + BALL_RADIUS * 2;

        double racketLeft = racketGraphics.getX();
        double racketRight = racketGraphics.getX() + PADDLE_WIDTH;
        double racketTop = racketGraphics.getY();
        double racketBottom = racketGraphics.getY() + PADDLE_HEIGHT;

        return ballRight >= racketLeft && ballLeft <= racketRight
                && ballBottom >= racketTop && ballTop <= racketBottom;
    }

    private void playGame(Ball ball) {
        bricksLeft = NBRICK_ROWS * NBRICKS_PER_ROW;
        livesLeft = NTURNS;

        waitForClick();

        while (livesLeft > 0 && bricksLeft > 0) {
            moveBallWithCollisions(ball);

            if (ball.isBelowBottom(getHeight(), FRAME_MARGIN)) {
                livesLeft--;
                if (livesLeft > 0) {
                    double ballX = (getWidth() - BALL_RADIUS * 2) / 2.0;
                    double ballY = getHeight() / 2.0;
                    ball.getGraphics().setLocation(ballX, ballY);
                    waitForClick();
                }
            }

            pause(8);
        }

        displayGameOverMessage(bricksLeft == 0);
    }

    private void moveBallWithCollisions(Ball ball) {
        int steps = ball.getStepsForThisFrame();
        for (int i = 0; i < steps; i++) {
            ball.moveOneStep(getWidth(), FRAME_MARGIN, steps);

            GObject collider = getCollidingObject(ball);

            if (collider == racketGraphics) {
                ball.bounceOffRacket(racketGraphics.getY());
            } else if (collider != null) {
                ball.bounceOffBrick(collider);
                remove(collider);
                bricksLeft--;
                break;
            }
        }
    }

    private void displayGameOverMessage(boolean won) {
        String text = won ? WIN_TEXT : GAME_OVER_TEXT;
        GLabel message = new GLabel(text);
        message.setFont(FINISH_FONT);
        message.setColor(won ? Color.GREEN : Color.RED);

        double x = (getWidth() - message.getWidth()) / 2.0;
        double y = (getHeight() + message.getAscent()) / 2.0;
        add(message, x, y);
    }

    private GObject getCollidingObject(Ball ball) {
        double x = ball.getX();
        double y = ball.getY();
        double d = BALL_RADIUS * 2;

        GObject collider;

        collider = getElementAt(x, y);
        if (collider != null && collider != frameGraphics && collider != ball.getGraphics()) return collider;

        collider = getElementAt(x + d, y);
        if (collider != null && collider != frameGraphics && collider != ball.getGraphics()) return collider;

        collider = getElementAt(x, y + d);
        if (collider != null && collider != frameGraphics && collider != ball.getGraphics()) return collider;

        collider = getElementAt(x + d, y + d);
        if (collider != null && collider != frameGraphics && collider != ball.getGraphics()) return collider;

        return null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        GObject obj = getElementAt(e.getX(), e.getY());

        if (restartButton != null && (obj == restartButton || obj == restartLabel)) {
            restartClicked = true;
            return;
        }

        if (obj == racketGraphics) {
            isDraggingRacket = true;
            double centerX = racketGraphics.getX() + PADDLE_WIDTH / 2.0;
            double centerY = racketGraphics.getY() + PADDLE_HEIGHT / 2.0;

            if (robot != null) {
                Point componentLocation = e.getComponent().getLocationOnScreen();
                int screenX = (int) (componentLocation.x + centerX);
                int screenY = (int) (componentLocation.y + centerY);
                robot.mouseMove(screenX, screenY);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isDraggingRacket) {
            double newX = e.getX() - PADDLE_WIDTH / 2.0;

            double leftBound = FRAME_MARGIN;
            double rightBound = getWidth() - FRAME_MARGIN - PADDLE_WIDTH;

            if (newX < leftBound) {
                newX = leftBound;
            } else if (newX > rightBound) {
                newX = rightBound;
            }

            racketGraphics.setLocation(newX, racketGraphics.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isDraggingRacket = false;
    }

}
