package notifiers;

import geometry.Ball;
import geometry.Block;
import interfaces.HitListener;

public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Update the score when a block is hit
        currentScore.increase(5);
    }
}
