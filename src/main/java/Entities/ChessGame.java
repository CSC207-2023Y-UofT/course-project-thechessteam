package Entities;

import Entities.Locations.LocationBitboard;

public class ChessGame {
    // A class to represent the game board we are using. Keeps track whose turn it is as well.

    // Various useful attributes. Use getters to access them.
    private LocationBitboard currentBoard = new LocationBitboard();
    private boolean turn = true; // true for White's turn, false for Black's turn
    private int blkPoints = 0;
    private int whtPoints = 0;

    // ----------------------------------------------------------------------------------------------------------
    // Getter methods
    public LocationBitboard getCurrentBoard() { // returns the board this Entities.ChessGame is using
        return currentBoard;
    }
    public boolean getTurn() { return turn; }
    public int getPoints(boolean t) {if (t) {return whtPoints;} else {return blkPoints;}}

    // ----------------------------------------------------------------------------------------------------------

    // Use this to change turn from White's turn to Black's turn, or vice versa
    public boolean change_turn() {
        turn = !turn;
        return turn;
    }

    public void setPoints(boolean team, int increment) {
        if (team) {
            whtPoints += increment;
        } else {
            blkPoints += increment;
        }
    }

    // Start a new game by setting a new location bitboard
    public void new_game() {
        currentBoard = new LocationBitboard();
        turn = true; // Make sure to start with White's turn
        blkPoints = 0;
        whtPoints = 0;
    }
}
