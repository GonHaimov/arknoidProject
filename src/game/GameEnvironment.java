package game;
import java.util.List;
import java.util.ArrayList;
import geometry.Line;
import geometry.Point;
import interfaces.Collidable;
import velocity.CollisionInfo;

public class GameEnvironment {
    private List<Collidable> collidables;

    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    public void removeCollidable(Collidable c) {
        collidables.remove(c);
    }

    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestCollisionPoint = null;
        Collidable closestCollisionObject = null;
        double minDistance = Double.POSITIVE_INFINITY;

        // Iterate through all collidables
        for (Collidable collidable : collidables) {
            // Get the intersection point with the current collidable
            Point intersectionPoint = trajectory.closestIntersectionToStartOfLine(collidable.getCollisionRectangle());

            // If there is an intersection point
            if (intersectionPoint != null) {
                // Calculate the distance from the start of the trajectory to the intersection point
                double distance = trajectory.start().distance(intersectionPoint);

                // If the distance is smaller than the current minimum distance, update the closest collision
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCollisionPoint = intersectionPoint;
                    closestCollisionObject = collidable;
                }
            }
        }

        // If there was a collision, return the CollisionInfo
        if (closestCollisionPoint != null) {
            return new CollisionInfo(closestCollisionPoint, closestCollisionObject);
        }

        // If no collision was found, return null
        return null;
    }
}