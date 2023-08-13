package usecases;

import entities.ChessGame;

/**
 * NewGame is responsible for initiating a new game within the current ChessGame object.
 * It interacts with the ChessGame class to reset the game state and start a new game.
 *
 */
public class NewGame {
    private final ChessGame currentGame;

    /**
     * Constructs a new NewGame object.
     *
     * @param currentGame The current ChessGame object containing the game state.
     */
    public NewGame(ChessGame currentGame) {
        this.currentGame = currentGame;
    }

    /**
     * Starts a new game by calling the new_game() method on the current ChessGame object.
     * This resets the game state to its initial configuration.
     */
    public void startNewGame() {
        currentGame.newGame();
    }
}
