package velocity;

import geometry.Point;

// 314711326 Gon Haimov
public class Velocity {
    private double dx;
    private double dy;

    // Constructor
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // Getters
    public double getDx() {
        return this.dx;
    }

    public double getDy() {
        return this.dy;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    // Apply the velocity to a given point
    public Point applyToPoint(Point p) {
        double newX = p.getX() + this.dx;
        double newY = p.getY() + this.dy;
        return new Point(newX, newY);
    }

    // Static method to create Velocity from angle and speed
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // Convert angle to radians
        double radians = Math.toRadians(angle);
        // Calculate dx and dy based on angle and speed
        double dx = speed * Math.cos(radians);
        double dy = speed * -Math.sin(radians);
        if (angle == 90) {
            return new Velocity(dx, dy);
        }
        return new Velocity(dx, dy);
    }
}

