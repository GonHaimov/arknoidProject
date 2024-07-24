package geometry;
import java.util.List;
public class Line {
    private Point start;
    private Point end;

    /**
     * Constructs a line segment given two points.
     *
     * @param start The starting point of the line segment.
     * @param end   The ending point of the line segment.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a line segment given coordinates of two points.
     *
     * @param x1 The x-coordinate of the starting point.
     * @param y1 The y-coordinate of the starting point.
     * @param x2 The x-coordinate of the ending point.
     * @param y2 The y-coordinate of the ending point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Returns the length of the line segment.
     *
     * @return The length of the line segment.
     */
    public double length() {
        return start.distance(end);
    }

    public Point getEnd() {
        return end;
    }

    public Point getStart() {
        return start;
    }

    /**
     * Returns the middle point of the line segment.
     *
     * @return The middle point of the line segment.
     */
    public Point middle() {
        double middleX = (start.getX() + end.getX()) / 2;
        double middleY = (start.getY() + end.getY()) / 2;
        return new Point(middleX, middleY);
    }

    /**
     * Returns the starting point of the line segment.
     *
     * @return The starting point of the line segment.
     */
    public Point start() {
        return start;
    }

    /**
     * Returns the ending point of the line segment.
     *
     * @return The ending point of the line segment.
     */
    public Point end() {
        return end;
    }

    /**
     * Checks if this line segment intersects with another line segment.
     *
     * @param other The other line segment to check for intersection.
     * @return True if the line segments intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        double minX1 = Math.min(this.start.getX(), this.end.getX());
        double minX2 = Math.min(other.start.getX(), other.end.getX());
        double maxX1 = Math.max(this.start.getX(), this.end.getX());
        double maxX2 = Math.max(other.start.getX(), other.end.getX());
        double minY1 = Math.min(this.start.getY(), this.end.getY());
        double minY2 = Math.min(other.start.getY(), other.end.getY());
        double maxY1 = Math.max(this.start.getY(), this.end.getY());
        double maxY2 = Math.max(other.start.getY(), other.end.getY());

        // If one or two lines are vertical
        if (this.start.getX() == this.end.getX()) {
            if (other.start.getX() == other.end.getX()) {
                return minY1 == maxY2 || maxY1 == minY2;
            }
            return isVerticalIntersecting1(minX2, maxX2, minY2, maxY2);
        }

        if (other.start.getX() == other.end.getX()) {
            return isVerticalIntersecting2(other, minX1, maxX1, minY1, maxY1);
        }
        // Calculate the incline of both lines
        double incline1 = (this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX());
        double incline2 = (other.start.getY() - other.end.getY()) / (other.start.getX() - other.end.getX());
        // Finding the intersection of the line with the y-axis
        double axisY1 = (incline1 * (-this.start.getX())) + this.start.getY();
        double axisY2 = (incline2 * (-other.start.getX())) + other.start.getY();
        // If the two lines have the same slope and intersection with y-axis
        if (incline1 == incline2) {
            if (axisY1 == axisY2) {
                if (maxX1 >= minX2 && maxX1 <= maxX2 || minX1 >= minX2 && minX1 <= maxX2) {
                    return true;
                }
                // If their intersection point is exactly at the end of one line, which is the start of the other
                return minX1 == maxX2 || maxX1 == minX2;
            }
            return false;
        }
        // If the two lines do not have the same slope
        double x;
        double y;
        // Finding the intersection point
        x = (axisY2 - axisY1) / (incline1 - incline2);
        y = (incline1 * x) + axisY1;
        // Check if the intersection point is in the range of both lines
        if (minX1 <= x && maxX1 >= x) {
            if (minX2 <= x && maxX2 >= x) {
                if (minY1 <= y && maxY1 >= y) {
                    return minY2 <= y && maxY2 >= y;
                }
            }
        }
        return false;
    }

    /**
     * Checks if this vertical line segment intersects with another line segment.
     *
     * @param minX Minimum x-coordinate of the other line segment.
     * @param maxX Maximum x-coordinate of the other line segment.
     * @param minY Minimum y-coordinate of the other line segment.
     * @param maxY Maximum y-coordinate of the other line segment.
     * @return True if the vertical line segment intersects, false otherwise.
     */
    public boolean isVerticalIntersecting1(double minX, double maxX, double minY, double maxY) {
        if (minX > this.start.getX() || maxX < this.start.getX()) {
            return false;
        }
        return !(maxY < Math.min(this.start.getY(), this.end.getY()))
                && !(minY > Math.max(this.start.getY(), this.end.getY()));
    }

    /**
     * Checks if the other vertical line segment intersects with this line segment.
     *
     * @param other The other line segment.
     * @param minX  Minimum x-coordinate of this line segment.
     * @param maxX  Maximum x-coordinate of this line segment.
     * @param minY  Minimum y-coordinate of this line segment.
     * @param maxY  Maximum y-coordinate of this line segment.
     * @return True if the other vertical line segment intersects, false otherwise.
     */
    public boolean isVerticalIntersecting2(Line other, double minX, double maxX, double minY, double maxY) {
        if (minX > other.start.getX() || maxX < other.start.getX()) {
            return false;
        }
        return !(maxY < Math.min(other.start.getY(), other.end.getY()))
                && !(minY > Math.max(other.start.getY(), other.end.getY()));
    }

    /**
     * Checks if this line segment intersects with two other line segments.
     *
     * @param other1 The first other line segment to check for intersection.
     * @param other2 The second other line segment to check for intersection.
     * @return True if this line segment intersects with any of the other line segments, false otherwise.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return isIntersecting(other1) || isIntersecting(other2);
    }

    public boolean isMultiIntersecting(Line other1, Line other2, Line other3, Line other4) {
        return isIntersecting(other1) || isIntersecting(other2) || isIntersecting(other3) || isIntersecting(other4);
    }

    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     *
     * @param other The other line segment.
     * @return The intersection point if the lines intersect, null otherwise.
     */
    public Point intersectionWith(Line other) {
        if (isIntersecting(other)) {
            double minX1 = Math.min(this.start.getX(), this.end.getX());
            double minX2 = Math.min(other.start.getX(), other.end.getX());
            double maxX1 = Math.max(this.start.getX(), this.end.getX());
            double maxX2 = Math.max(other.start.getX(), other.end.getX());
            double minY1 = Math.min(this.start.getY(), this.end.getY());
            double minY2 = Math.min(other.start.getY(), other.end.getY());
            double maxY1 = Math.max(this.start.getY(), this.end.getY());
            double maxY2 = Math.max(other.start.getY(), other.end.getY());
            int flag = 0;

            if (this.start.getX() == this.end.getX()) {
                if (other.start.getX() == other.end.getX()) {
                    if (minY1 == maxY2) {
                        return new Point(maxX1, minY1);
                    }
                    return new Point(maxX1, minY2);
                }
                flag = 1;
            }

            if (this.start.getX() != this.end.getX() && other.start.getX() == other.end.getX()) {
                flag = 2;
            }

            if (flag == 1) {
                double incline2 = (other.start.getY() - other.end.getY()) / (other.start.getX() - other.end.getX());
                double axisY2 = (incline2 * (-other.start.getX())) + other.start.getY();
                double y = (incline2 * maxX1) + axisY2;
                return new Point(maxX1, y);
            }

            if (flag == 2) {
                double incline1 = (this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX());
                double axisY1 = (incline1 * (-this.start.getX())) + this.start.getY();
                double y = (incline1 * maxX2) + axisY1;
                return new Point(maxX2, y);
            }

            double incline1 = (this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX());
            double incline2 = (other.start.getY() - other.end.getY()) / (other.start.getX() - other.end.getX());
            // Finding the intersection of the line with the y-axis
            double axisY1 = (incline1 * (-this.start.getX())) + this.start.getY();
            double axisY2 = (incline2 * (-other.start.getX())) + other.start.getY();

            if (incline1 == incline2) {
                if (axisY1 == axisY2) {
                    if (maxX1 > minX2 && maxX1 < maxX2 || minX1 > minX2 && minX1 < maxX2) {
                        return null;
                    }
                    if (maxX1 == minX2) {
                        return new Point(maxX1, maxX1 + axisY1);
                    }
                    if (minX1 == maxX2) {
                        return new Point(minX1, minY1 + axisY1);
                    }
                }
            }
            double x;
            double y;
            // Finding the intersection point
            x = (axisY2 - axisY1) / (incline1 - incline2);
            y = (incline1 * x) + axisY1;
            return new Point(x, y);
        }
        return null;
    }

    // If this line does not intersect with the rectangle, return null.
    // Otherwise, return the closest intersection point to the
    // start of the line.
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersections = rect.intersectionPoints(this);
        if (intersections.isEmpty()) {
            return null;
        }

        // Calculate the closest intersection point to the start of the line
        Point closestIntersection = intersections.get(0);
        double closestDistance = this.start().distance(closestIntersection);

        for (int i = 1; i < intersections.size(); i++) {
            Point intersection = intersections.get(i);
            double distance = this.start().distance(intersection);
            if (distance < closestDistance) {
                closestIntersection = intersection;
                closestDistance = distance;
            }
        }

        return closestIntersection;
    }
    /**
     * Returns true if this line segment equals another line segment.
     *
     * @param other The other line segment to compare.
     * @return True if the line segments are equal, false otherwise.
     */
    public boolean equals(Line other) {
        return (start.equals(other.start()) && end.equals(other.end()))
                || (start.equals(other.end()) && end.equals(other.start()));
    }
}
