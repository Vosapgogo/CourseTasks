package com.shpp.p2p.cs.oTyshchenko.assignment4;

import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Game extends WindowProgram {
    /** Width and height of application window in pixels */
    public static final int APPLICATION_WIDTH = 600;
    public static final int APPLICATION_HEIGHT = 800;

    /** Dimensions of the paddle */
    private static final int PADDLE_WIDTH = 60;
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

    private double vx, vy;

    private boolean isDraggingRacket = false;

    public void run() {
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
    }

    public void drawFrame() {
        GRect frame = new GRect(FRAME_MARGIN, FRAME_MARGIN, getWidth()-2*FRAME_MARGIN, getHeight()-2*FRAME_MARGIN);
        frame.setColor(Color.BLACK);
        add(frame);
    }

    private void playGame(Ball ball) {
        while (true) {
            ball.move(getWidth(), FRAME_MARGIN);

            GObject collider = getCollidingObject(ball.getX(), ball.getY());

            if (collider == racketGraphics) {
                ball.bounceOffRacket(racketGraphics.getY());
            }

            pause(15);
        }
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
        GObject obj = getElementAt(e.getX(), e.getY());

        if (obj == racketGraphics) {
            isDraggingRacket = true;
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
            }
            else if (newX > rightBound) {
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
