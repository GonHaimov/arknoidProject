package interfaces;

import geometry.Point;
import geometry.Rectangle;
import geometry.Ball;
import velocity.Velocity;

public interface Collidable {
    // Return the "collision shape" of the object.
    Rectangle getCollisionRectangle();
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
