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

    private double vx, vy;

    private boolean isDraggingRacket = false;

    private int livesLeft;
    private boolean gameOver = false;

    private GLabel loseMessage;
    private GRect restartButton;
    private GLabel restartLabel;

    private Robot robot;

    public void run() {
        try {
            robot = new Robot();
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
            addMouseListeners();

            playGame(ball);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }

    public void drawFrame() {
        frameGraphics = new GRect(FRAME_MARGIN, FRAME_MARGIN, getWidth()-2*FRAME_MARGIN, getHeight()-2*FRAME_MARGIN);
        frameGraphics.setColor(Color.BLACK);
        add(frameGraphics);
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
        int bricksLeft = NBRICK_ROWS * NBRICKS_PER_ROW;
        livesLeft = NTURNS;

        waitForClick();

        while (livesLeft > 0 && bricksLeft > 0) {
            ball.move(getWidth(), FRAME_MARGIN);

            if (overlapsRacket(ball)) {
                ball.bounceOffRacket(racketGraphics.getY());
            } else {
                GObject collider = getCollidingObject(ball.getX(), ball.getY());
                if (collider != null && collider != frameGraphics && collider != racketGraphics) {
                    remove(collider);
                    ball.bounceVertically();
                    bricksLeft--;
                }
            }

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

    private void displayGameOverMessage(boolean won) {
        String text = won ? "YOU WIN!" : "GAME OVER";
        GLabel message = new GLabel(text);
        message.setFont("SansSerif-bold-36");
        message.setColor(won ? Color.GREEN : Color.RED);

        double x = (getWidth() - message.getWidth()) / 2.0;
        double y = (getHeight() + message.getAscent()) / 2.0;
        add(message, x, y);
    }

    private GObject getCollidingObject(double ballX, double ballY) {
        double d = BALL_RADIUS * 2;

        GObject collider = getElementAt(ballX, ballY);
        if (collider != null) return collider;

        collider = getElementAt(ballX + d, ballY);
        if (collider != null) return collider;

        collider = getElementAt(ballX, ballY + d);
        if (collider != null) return collider;

        collider = getElementAt(ballX + d, ballY + d);
        if (collider != null) return collider;

        return null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
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
