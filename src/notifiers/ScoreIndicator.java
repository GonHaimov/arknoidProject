package notifiers;

import interfaces.Sprite;
import biuoop.DrawSurface;
public class ScoreIndicator implements Sprite {
    private Counter scoreCounter;

    public ScoreIndicator(Counter scoreCounter) {
        this.scoreCounter = scoreCounter;
    }

    @Override
    public void drawOn(DrawSurface d) {
        // Draw the score on the screen
        d.drawText(340, 16, "Score: " + scoreCounter.getValue(), 15);
    }

    @Override
    public void timePassed() {
        // No need for actions when time passes
    }
}
