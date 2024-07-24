package geometry;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.GameEnvironment;
import game.Game;

import interfaces.Sprite;
import interfaces.Collidable;

import velocity.Velocity;


public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rectangle; // Represents the paddle's shape
    private double paddleSpeed;
    private GameEnvironment gameEnvironment;
    public static final int WIDTH_SCREEN = 800;
    public static final int HEIGHT_SCREEN = 600;
    private static final int NUM_REGIONS = 5; // Number of regions to divide the paddle

    public Paddle(Rectangle rectangle, biuoop.KeyboardSensor keyboard, double paddleSpeed) {
        this.rectangle = rectangle;
        this.keyboard = keyboard;
        this.paddleSpeed = paddleSpeed;
    }

    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }


    public void moveLeft() {
        // Move the paddle to the left
        double newX = rectangle.getUpperLeft().getX() - paddleSpeed;
        if (newX < 0) {
            newX = WIDTH_SCREEN - rectangle.getWidth(); // Move paddle to the right edge of the screen
        }
        rectangle = new Rectangle(new Point(newX, rectangle.getUpperLeft().getY()), rectangle.getWidth(),
                rectangle.getHeight(), rectangle.getColor());
    }

    public void moveRight() {
        // Move the paddle to the right
        double newX = rectangle.getUpperLeft().getX() + paddleSpeed;
        if (newX + rectangle.getWidth() > WIDTH_SCREEN) {
            newX = 0; // Move paddle to the left edge of the screen
        }
        rectangle = new Rectangle(new Point(newX, rectangle.getUpperLeft().getY()), rectangle.getWidth(),
                rectangle.getHeight(), rectangle.getColor());
    }





    @Override
    public void timePassed() {
        // Check if left or right keys are pressed and move the paddle accordingly
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        // Draw the paddle on the DrawSurface
        rectangle.drawOn(d);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        // Return the paddle's collision rectangle
        return rectangle;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double regionWidth = rectangle.getWidth() / NUM_REGIONS;
        double region = (collisionPoint.getX() - rectangle.getUpperLeft().getX()) / regionWidth;
        double speed = Math.sqrt(Math.pow(currentVelocity.getDx(), 2) + Math.pow(currentVelocity.getDy(), 2));

        if (region >= 0 && region < 1) {
            return Velocity.fromAngleAndSpeed(150, speed);
        } else if (region >= 1 && region < 2) {
            return Velocity.fromAngleAndSpeed(120, speed);
        } else if (region >= 2 && region < 3) {
            return Velocity.fromAngleAndSpeed(90, speed);

        } else if (region >= 3 && region < 4) {
            return Velocity.fromAngleAndSpeed(60, speed);
        } else {
            return Velocity.fromAngleAndSpeed(30, speed);
        }
    }


    public void addToGame(Game g) {
        // Add the paddle to the game
        g.addCollidable(this);
        g.addSprite(this);
    }
}
