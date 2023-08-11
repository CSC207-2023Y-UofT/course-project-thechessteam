package entities;

import entities.locations.LocationBitboard;

public class ChessGame {
    // A class to represent the game board we are using. Keeps track whose turn it is as well.

    // Various useful attributes. Use getters to access them.
    private LocationBitboard currentBoard = new LocationBitboard();
    private boolean turn = true; // true for White's turn, false for Black's turn

    // ----------------------------------------------------------------------------------------------------------
    // Getter methods
    public LocationBitboard getCurrentBoard() { // returns the board this Entities.ChessGame is using
        return currentBoard;
    }
    public boolean getTurn() { return turn; }

    // ----------------------------------------------------------------------------------------------------------

    // Use this to change turn from White's turn to Black's turn, or vice versa
    public boolean changeTurn() {
        turn = !turn;
        return turn;
    }

    // Start a new game by setting a new location bitboard
    public void newGame() {
        currentBoard = new LocationBitboard();
        turn = true; // Make sure to start with White's turn
    }
}
