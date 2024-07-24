/**
 * This class represents the main entry point for the game application.
 * It creates a Game object, initializes it, and runs the game.
 *
 * @author 314711326 Gon Haimov
 */
import game.Game;

public class Ass5Game {
    /**
     * The main method of the application.
     * It creates a Game object, initializes it, and runs the game.
     *
     * @param args Command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        // Create a game object
        Game game = new Game();

        // Initialize the game
        game.initialize();

        // Run the game
        game.run();
    }
}
