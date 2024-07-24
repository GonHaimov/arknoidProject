package geometry;
import java.util.List;

import biuoop.DrawSurface;
import java.awt.Color;

import game.GameEnvironment;
import game.Game;
import interfaces.Sprite;
import velocity.Velocity;
import velocity.CollisionInfo;

public class Ball implements Sprite {
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;
    private List<Rectangle> frames;
    private GameEnvironment environment; // Reference to the game environment

    // Draw the ball on the given DrawSurface, considering movement

    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        int x = (int) this.center.getX();
        int y = (int) this.center.getY();
        d.fillCircle(x, y, this.radius);
    }

    // Constructor
    public Ball(Point center, int radius, Color color, GameEnvironment environment) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.environment = environment; // Set the game environment
    }

    public Ball(double x, double y, int radius, Color color) {
        this.center = new Point(x, y);
        this.radius = radius;
        this.color = color;
    }

    // Additional constructor to include Velocity
    public Ball(Point center, int radius, Color color, Velocity velocity) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.velocity = velocity;
    }

    public Ball(double x, double y, int radius, Color color, Velocity velocity) {
        this.center = new Point(x, y);
        this.radius = radius;
        this.color = color;
        this.velocity = velocity;
    }

    // Accessors
    public int getX() {
        return (int) this.center.getX();
    }

    public int getY() {
        return (int) this.center.getY();
    }

    public Point getCenter() {
        return center;
    }

    // Get the velocity of the ball
    public Velocity getVelocity() {
        return this.velocity;
    }

    public int getSize() {
        return this.radius * 2;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public int getRadius() {
        return this.radius;
    }

    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.environment = gameEnvironment;
    }


    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    // Set the velocity of the ball using dx and dy
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    public void setFrames(List<Rectangle> frame) {
        this.frames = frame;
    }

    public List<Rectangle> getFrames() {
        return this.frames;
    }

    public void addFrame(Rectangle frame) {
        this.frames.add(frame);
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }

    public void moveOneStep() {
        Point applyToPoint =  velocity.applyToPoint(center);
        double newX = applyToPoint.getX() + getRadius() * (velocity.getDx() / Math.abs(velocity.getDx()));
        double newY = applyToPoint.getY() + getRadius() * (velocity.getDy() / Math.abs(velocity.getDy()));
        // Compute the ball trajectory
        Line trajectory = new Line(center, new Point(newX, newY));

        // Check if the trajectory will hit anything
        CollisionInfo collision = environment.getClosestCollision(trajectory);

        if (collision == null) {
            // No collision, move the ball to the end of the trajectory
            center = applyToPoint;
        } else {
            double epsilon = 0.001; // Small value to move slightly before the collision point
            // Collision occurred
            Point collisionPoint = collision.collisionPoint();
            Point or = new Point(collisionPoint.getX() - epsilon * velocity.getDx() / Math.abs(velocity.getDx()),
                    collisionPoint.getY() - epsilon * velocity.getDy() / Math.abs(velocity.getDy()));
            collisionPoint.set(collisionPoint.getX() - getRadius() * (velocity.getDx() / Math.abs(velocity.getDx())),
                    collisionPoint.getY() - getRadius() * (velocity.getDy() / Math.abs(velocity.getDy())));
            // Move the ball just slightly before the collision point
            center = new Point(
                    collisionPoint.getX() - epsilon * (getRadius() * (velocity.getDx() / Math.abs(velocity.getDx()))),
                    collisionPoint.getY() - epsilon * (getRadius() * (velocity.getDy() / Math.abs(velocity.getDy()))));

            // Notify the collidable object about the collision and get the new velocity
            velocity = collision.collisionObject().hit(this, or, velocity);
        }
    }

    public void addToGame(Game g) {
        g.addSprite(this);
    }

    public void removeFromGame(Game game) {
        game.removeSprite(this);
        //this.environment.removeBall(this);
    }
}

