package entities;

import entities.locations.LocationBitboard;

/**
 * Class to represent a chess game, including the current game state, turn, and points.
 * This class provides methods to manipulate the game state, change turns, manage points, and reset the game.
 */
public class ChessGame {
    // A class to represent the game board we are using. Keeps track whose turn it is as well.

    // Various useful attributes. Use getters to access them.
    private LocationBitboard currentBoard = new LocationBitboard();
    private boolean turn = true; // true for White's turn, false for Black's turn

    // Getter methods

    /**
     * Gets the current game board.
     *
     * @return The LocationBitboard representing the current state of the game board.
     */
    public LocationBitboard getCurrentBoard() { // returns the board this ChessGame is using
        return currentBoard;
    }

    /**
     * Gets the current turn.
     *
     * @return True if it's White's turn, false if it's Black's turn.
     */
    public boolean getTurn() { return turn; }

    /**
     * Changes the turn from White's turn to Black's turn, or vice versa.
     *
     * @return The updated turn (true for White's turn, false for Black's turn).
     */
    public boolean changeTurn() {
        turn = !turn;
        return turn;
    }

    /**
     * Start a new game by setting a new location bitboard
     */
    public void newGame() {
        currentBoard = new LocationBitboard();
        turn = true; // Make sure to start with White's turn
    }
}
