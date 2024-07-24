package interfaces;

import geometry.Ball;
import geometry.Block;

public interface HitListener {
    void hitEvent(Block beingHit, Ball hitter);

}
