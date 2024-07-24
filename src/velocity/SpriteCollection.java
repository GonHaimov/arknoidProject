package velocity;
import interfaces.Sprite;
import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

public class SpriteCollection {
    private List<Sprite> sprites;

    public SpriteCollection() {
        sprites = new ArrayList<>();
    }

    public void removeSprite(Sprite s) {
        sprites.remove(s);
    }

    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(sprites);
        for (Sprite sprite : spritesCopy) {
            sprite.timePassed();
        }
    }

    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : sprites) {
            sprite.drawOn(d);
        }
    }

    // New method to get the list of sprites
    public List<Sprite> getSpritesList() {
        return sprites;
    }
}
