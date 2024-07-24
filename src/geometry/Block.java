package geometry;

import biuoop.DrawSurface;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import interfaces.Sprite;
import game.Game;

import interfaces.Collidable;
import interfaces.HitNotifier;
import interfaces.HitListener;
import velocity.Velocity;

public class Block implements Collidable, Sprite, HitNotifier {
    private geometry.Rectangle rectangle;
    private Color color;

    private List<HitListener> hitListeners = new ArrayList<>();


    // Constructor
    public Block(geometry.Rectangle rectangle) {
        this.rectangle = rectangle;
    }
    public Block(geometry.Point upperLeft, double width, double height, Color color) {
        this.rectangle = new geometry.Rectangle(upperLeft, width, height, color);
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    // Implementing the getCollisionRectangle method
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    @Override
    public void timePassed() {
    }

    public void drawOn(DrawSurface d) {
        // Set the color of the rectangle
        d.setColor(rectangle.getColor());
        // Draw the rectangle using its upper-left corner coordinates, width, and height
        d.fillRectangle((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }

    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    public boolean ballColorMatch(Ball ball) {
        if (this.color.equals(ball.getColor())) {
            return true;
        }
        return false;
    }

    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    private void notifyHit(Ball hitter) {
        // Copy the listeners list to avoid concurrent modification exceptions
        List<HitListener> listenersCopy = new ArrayList<>(this.hitListeners);
        for (HitListener listener : listenersCopy) {
            listener.hitEvent(this, hitter);
        }
    }

    // Modify the hit method as per the new requirement
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // Implement the collision behavior here


        // If colors match, perform other collision behavior
        if (collisionPoint.getX() < this.rectangle.getUpperLeftX()
                || collisionPoint.getX() > this.rectangle.getDownRightX()) {
            // If the collision occurs on the left or right side, reverse the horizontal velocity component
            currentVelocity.setDx(currentVelocity.getDx() * -1);
            if (this.getColor().equals(Color.GRAY) || this.ballColorMatch(hitter)) {
                hitter.setColor(hitter.getColor());
            } else {
                hitter.setColor(this.getColor());
                this.notifyHit(hitter);
            }

        }
        if (collisionPoint.getY() < this.rectangle.getUpperLeftY()
                || collisionPoint.getY() > this.rectangle.getDownRightY()) {
            // If the collision occurs on the top or bottom side, reverse the vertical velocity component
            currentVelocity.setDy(currentVelocity.getDy() * -1);
            if (this.getColor().equals(Color.GRAY) || this.ballColorMatch(hitter)) {
                hitter.setColor(hitter.getColor());
            } else {
                hitter.setColor(this.getColor());
                this.notifyHit(hitter);
            }
        }

        return currentVelocity; // Return the updated velocity
    }

    // HitNotifier interface methods
    // Implementing the HitNotifier interface methods
    @Override
    public void addHitListener(HitListener hl) {
        if (hitListeners == null) {
            hitListeners = new ArrayList<>();
        }
        hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        if (hitListeners != null) {
            hitListeners.remove(hl);
        }
    }
}
