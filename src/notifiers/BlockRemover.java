package notifiers;

import game.Game;
import geometry.Ball;
import geometry.Block;
import interfaces.HitListener;

public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Remove the block from the game
        beingHit.removeFromGame(this.game);
        // Decrease the remaining blocks count
        this.remainingBlocks.decrease(1);

    }
}
