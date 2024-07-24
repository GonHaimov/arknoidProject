package geometry;

public class Point {
    private double x;
    private double y;
    private static final double EPSILON = 1e-10;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // distance -- return the distance of this point to the other point
    public double distance(Point other) {
        return Math.sqrt((Math.pow(this.x - other.x, 2)) + Math.pow(this.y - other.y, 2));
    }

    // equals -- return true is the points are equal, false otherwise
    public boolean equals(Point other) {
        return Math.abs(this.x - other.x) < EPSILON && Math.abs(this.y - other.y) < EPSILON;
    }

    // Return the x and y values of this point
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    // Set new coordinates for the point
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

}