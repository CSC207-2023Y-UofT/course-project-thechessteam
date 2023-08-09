package Entities;

import Entities.Locations.LocationBitboard;

/**
 * Class to represent a chess game, including the current game state, turn, and points.
 * This class provides methods to manipulate the game state, change turns, manage points, and reset the game.
 */
public class ChessGame {
    // A class to represent the game board we are using. Keeps track whose turn it is as well.

    // Various useful attributes. Use getters to access them.
    private LocationBitboard currentBoard = new LocationBitboard();
    private boolean turn = true; // true for White's turn, false for Black's turn
    private int blkPoints = 0;
    private int whtPoints = 0;

    // Getter methods

    /**
     * Gets the current game board.
     *
     * @return The LocationBitboard representing the current state of the game board.
     */
    public LocationBitboard getCurrentBoard() { // returns the board this Entities.ChessGame is using
        return currentBoard;
    }

    /**
     * Gets the current turn.
     *
     * @return True if it's White's turn, false if it's Black's turn.
     */
    public boolean getTurn() { return turn; }

    /**
     * Gets the points for the given side.
     *
     * @param t The side to get points for (true for White, false for Black).
     * @return The points for the given side.
     */
    public int getPoints(boolean t) {if (t) {return whtPoints;} else {return blkPoints;}}

    /**
     * Changes the turn from White's turn to Black's turn, or vice versa.
     *
     * @return The updated turn (true for White's turn, false for Black's turn).
     */    public boolean change_turn() {
        turn = !turn;
        return turn;
    }

    /**
     * Updates the points for the given side by the specified increment.
     *
     * @param team      The side to update points for (true for White, false for Black).
     * @param increment The amount to add to the current points for the given side.
     */
    public void setPoints(boolean team, int increment) {
        if (team) {
            whtPoints += increment;
        } else {
            blkPoints += increment;
        }
    }

    /**
     * Starts a new game by resetting the game board, turn, and points.
     */
    public void new_game() {
        currentBoard = new LocationBitboard();
        turn = true; // Make sure to start with White's turn
        blkPoints = 0;
        whtPoints = 0;
    }
}
