package geometry;
import biuoop.DrawSurface;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import interfaces.Collidable;
import velocity.Velocity;

public class Rectangle implements Collidable {
    private Line left;
    private Line right;
    private Line up;
    private Line down;
    private Point upperLeft;
    private int upperLeftX;
    private int upperLeftY;
    private int downRightX;
    private int downRightY;
    private double width;
    private double height;
    private Color color;


    public Line getLeft() {
        return left;
    }

    public Line getRight() {
        return right;
    }

    public Line getUp() {
        return up;
    }

    public Line getDown() {
        return down;
    }

    public int getUpperLeftX() {
        return upperLeftX;
    }

    public int getUpperLeftY() {
        return upperLeftY;
    }

    public int getDownRightX() {
        return downRightX;
    }

    public int getDownRightY() {
        return downRightY;
    }

    public double getWidth() {
        return right.intersectionWith(up).getX() - left.intersectionWith(up).getX();
    }

    public double getHeight() {
        return right.intersectionWith(down).getY() - right.intersectionWith(up).getY();
    }

    // Returns the upper-left point of the rectangle.
    public Point getUpperLeft() {
        return upperLeft;
    }
    public Color getColor() {
        return color;
    }
    @Override
    public Rectangle getCollisionRectangle() {
        return this;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // Implement the collision behavior here
        if (collisionPoint.getX() <= this.upperLeftX) {
            currentVelocity.setDx(currentVelocity.getDx() * -1);
            currentVelocity.setDy(currentVelocity.getDy());
        } else if (collisionPoint.getX() >= this.downRightX) {
            currentVelocity.setDx(currentVelocity.getDx() * -1);
            currentVelocity.setDy(currentVelocity.getDy());
        } else if (collisionPoint.getY() <= this.upperLeftY) {
            currentVelocity.setDx(currentVelocity.getDx());
            currentVelocity.setDy(currentVelocity.getDy() * -1);
        } else if (collisionPoint.getY() >= downRightY) {
            currentVelocity.setDx(currentVelocity.getDx());
            currentVelocity.setDy(currentVelocity.getDy() * -1);
        }
        return currentVelocity; // For now, return the same velocity
    }

    public Rectangle(Line left, Line right, Line up, Line down, Color color) {
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
        this.color = color;
    }

    // Create a new rectangle with location and width/height.
    public Rectangle(Point upperLeft, double width, double height, Color color) {
        this.upperLeft = new Point(upperLeft.getX(), upperLeft.getY());
        this.width = width;
        this.height = height;
        this.color = color;
        this.upperLeftX = (int) upperLeft.getX();
        this.upperLeftY = (int) upperLeft.getY();
        this.downRightX = (int) upperLeft.getX() + (int) width;
        this.downRightY = (int) upperLeft.getY() + (int) height;
        this.left = new Line(upperLeft, new Point(upperLeftX, downRightY));
        this.right = new Line(new Point(downRightX, upperLeftY), new Point(downRightX, downRightY));
        this.up = new Line(upperLeft, new Point(downRightX, upperLeftY));
        this.down = new Line(new Point(upperLeftX, downRightY), new Point(downRightX, downRightY));
    }
    public Rectangle(int upperLeftX, int upperLeftY, int downRightX, int downRightY) {
        this.upperLeftX = (int) left.getStart().getX();
        this.upperLeftY = (int) left.getStart().getY();
        this.downRightX = (int) right.getEnd().getX();
        this.downRightY = (int) right.getEnd().getY();
    }

    public Rectangle(Point upLeft, Point downRight, Color color) {
        this.left = new Line(upLeft, new Point(upLeft.getX(), downRight.getY()));
        this.right = new Line(new Point(downRight.getX(), upLeft.getY()), downRight);
        this.up = new Line(upLeft, new Point(downRight.getX(), upLeft.getY()));
        this.down = new Line(new Point(upLeft.getX(), downRight.getY()), downRight);
        this.color = color;
        this.upperLeftX = (int) upLeft.getX();
        this.upperLeftY = (int) upLeft.getY();
        this.downRightX = (int) downRight.getX();
        this.downRightY = (int) downRight.getY();
    }


    public boolean isInside(Ball ball) {
        Point start = left.intersectionWith(up);
        Point end = right.intersectionWith(down);
        return ball.getX() >= start.getX() + ball.getRadius() && ball.getX() <= end.getX() - ball.getRadius()
                && ball.getY() >= start.getY() + ball.getRadius() && ball.getY() <= end.getY() - ball.getRadius();
    }
    public boolean outInside(Ball ball) {
        Point start = left.intersectionWith(up);
        Point end = right.intersectionWith(down);
        return ball.getX() < start.getX() - ball.getRadius() || ball.getX() > end.getX() + ball.getRadius()
                || ball.getY() < start.getY() - ball.getRadius() || ball.getY() > end.getY() + ball.getRadius();
    }

    public void drawOn(DrawSurface d) {
        // Set the color of the rectangle
        d.setColor(color);

        // Draw the rectangle using its upper-left corner coordinates, width, and height
        d.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(),
                (int) width, (int) height);

        // Draw black lines on the right and bottom edges of the rectangle
        d.setColor(Color.BLACK);
        d.drawRectangle((int) upperLeft.getX(), (int) upperLeft.getY(),
                (int) width, (int) height);
    }



    // Return a (possibly empty) List of intersection points
    // with the specified line.
    public java.util.List<Point> intersectionPoints(Line line) {
        List<Point> intersections = new ArrayList<>();

        // Check for intersection with each side of the rectangle
        Point intersection = this.left.intersectionWith(line);
        if (intersection != null) {
            intersections.add(intersection);
        }

        intersection = this.right.intersectionWith(line);
        if (intersection != null) {
            intersections.add(intersection);
        }

        intersection = this.up.intersectionWith(line);
        if (intersection != null) {
            intersections.add(intersection);
        }

        intersection = this.down.intersectionWith(line);
        if (intersection != null) {
            intersections.add(intersection);
        }

        return intersections;
    }
    public Rectangle radiusRec(Ball b) {
        Point leftUp = new Point(upperLeftX - b.getRadius(), upperLeftY - b.getRadius());
        Point rightDown = new Point(downRightX + b.getRadius(), downRightY + b.getRadius());
        return new Rectangle(leftUp, rightDown, Color.white);
    }

}