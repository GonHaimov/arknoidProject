package velocity;

import geometry.Point;
import interfaces.Collidable;

public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    public Point collisionPoint() {
        return collisionPoint;
    }

    public Collidable collisionObject() {
        return collisionObject;
    }
}