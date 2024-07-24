package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import geometry.Paddle;
import geometry.Ball;
import geometry.Block;
import geometry.Point;
import geometry.Rectangle;
import interfaces.Sprite;
import interfaces.Collidable;
import notifiers.BlockRemover;
import notifiers.BallRemover;
import notifiers.Counter;
import notifiers.ScoreIndicator;
import notifiers.ScoreTrackingListener;
import velocity.SpriteCollection;


public class Game {
    public static final int WIDTH_SCREEN = 800;
    public static final int HEIGHT_SCREEN = 600;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;
    private GUI gui;

    public Game() {
        sprites = new SpriteCollection();
        environment = new GameEnvironment();
        remainingBlocks = new Counter();
        remainingBalls = new Counter();
        score = new Counter();
    }

    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
    }

    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    public SpriteCollection getSprites() {
        return sprites;
    }

    public void initialize() {
        this.gui = new GUI("Multiple Frames Bouncing Balls Animation", WIDTH_SCREEN, HEIGHT_SCREEN);
        biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();
        biuoop.Sleeper sleeper = new biuoop.Sleeper();

        // Initialize the game entities (e.g., blocks, ball, paddle) and add them to the game.
        // Add blocks
        addBlocks();
        // Add balls
        addBalls();
        // Add paddle
        addPaddle(keyboard);
        ScoreIndicator scoreIndicator = new ScoreIndicator(score);
        addSprite(scoreIndicator);


    }

    private void addBlocks() {
        // Initialize the score counter
        score = new Counter();
        score.setCount(0);
        // Create and add the ScoreIndicator sprite
        ScoreIndicator scoreIndicator = new ScoreIndicator(score);
        addSprite(scoreIndicator);
        // Create a ScoreTrackingListener with the score counter
        ScoreTrackingListener scoreListener = new ScoreTrackingListener(score);

        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};
        int startX = 50;
        int startY = 100;
        int width = 50;
        int height = 25;
        int rows = 4;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 9 - i; j++) {
                Block block = new Block(
                        new geometry.Point(730 - j * width, 160 + height * i), width, height, colors[i]);
                block.addToGame(this);
                block.addHitListener(new BlockRemover(this, remainingBlocks));
                this.remainingBlocks.increase(1);
                block.addHitListener(scoreListener);
            }
        }

        Block[] borderBlocks = {
                new Block(new geometry.Point(0, 0), 20, 600, Color.GRAY),
                new Block(new geometry.Point(780, 0), 20, 600, Color.GRAY),
                new Block(new geometry.Point(20, 0), 800, 20, Color.GRAY),

        };

        BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);
        for (Block block : borderBlocks) {
            block.addToGame(this);
            block.addHitListener(blockRemover);
        }

        Block deathRegion = new Block(new geometry.Point(0, HEIGHT_SCREEN), WIDTH_SCREEN, 1, Color.BLACK);
        deathRegion.addToGame(this);
        BallRemover ballRemover = new BallRemover(this, this.remainingBalls);
        deathRegion.addHitListener(ballRemover);

    }

    private void addBalls() {
        Ball ball1 = new Ball(new geometry.Point(30, 50), 5, Color.BLUE, environment);
        ball1.setVelocity(-1, -2);
        Ball ball2 = new Ball(new geometry.Point(40, 60), 5, Color.BLACK, environment);
        ball2.setVelocity(-2, -1);
        Ball ball3 = new Ball(new geometry.Point(50, 70), 5, Color.RED, environment);
        ball3.setVelocity(2, -1);
        ball1.addToGame(this);
        ball2.addToGame(this);
        ball3.addToGame(this);
        remainingBalls.increase(3);
    }

    private void addPaddle(biuoop.KeyboardSensor keyboard) {
        geometry.Rectangle paddleShape = new Rectangle(new Point(30, 565), 100, 15, Color.ORANGE);
        double paddleSpeed = 5;
        Paddle paddle = new Paddle(paddleShape, keyboard, paddleSpeed);
        paddle.addToGame(this);
        paddle.setGameEnvironment(environment);
    }

    public void run() {
        biuoop.Sleeper sleeper = new biuoop.Sleeper();

        // Start the animation loop
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (remainingBlocks.getValue() > 0 && this.remainingBalls.getValue() > 0) {
            long startTime = System.currentTimeMillis(); // timing

            // Create a copy of the sprites collection
            List<Sprite> spritesCopy = new ArrayList<>(this.sprites.getSpritesList());
            // Iterate over the copy of the sprites collection
            for (Sprite sprite : spritesCopy) {
                sprite.timePassed();
            }
            //DrawSurface d = gui.getDrawSurface();
            DrawSurface d = gui.getDrawSurface();
            d.setColor(new Color(0, 100, 0));
            d.fillRectangle(0, 0, WIDTH_SCREEN, HEIGHT_SCREEN);
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            if (remainingBlocks.getValue() == 0) {
                this.score.increase(100); // Add 100 points when all blocks are removed
            }

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
        gui.close();
    }


}

