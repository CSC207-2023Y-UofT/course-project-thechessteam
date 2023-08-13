package Entities.VariousCalculators;

import Entities.Locations.LocationBitboard;

/**
 * StalemateCalculator determines if a player is in stalemate, which means the player is not in check
 * but has no legal moves available. A stalemate results in a draw in chess.
 * This class extends GameStateCalculator to utilize the method that checks if a player has no valid moves.
 */
public class StalemateCalculator extends GameStateCalculator {
    // Instance variable to determine if a side is in check
    private final CheckCalculator checkCalc;

    /**
     * Constructor for StalemateCalculator.
     *
     * @param validCalculator   Utility to calculate valid moves for a piece.
     * @param checkCalc         Utility to check if a side is in check.
     */
    public StalemateCalculator(ActualValidCalculator validCalculator, CheckCalculator checkCalc) {
        super(validCalculator, checkCalc);  // Pass both arguments to the superclass's constructor
        this.checkCalc = checkCalc;
    }

    /**
     * Determines if the given side is in stalemate on the current board.
     *
     * @param side         True for white and false for black.
     * @param currentBoard The current position of pieces on the board.
     * @return True if the side is in stalemate, otherwise false.
     */
    public boolean is_stalemate(boolean side, LocationBitboard currentBoard) {
        // If the side is in check, then it can't be in stalemate
        if (checkCalc.is_in_check(side, currentBoard)) {
            return false;
        }
        // Use the inherited method to check if the side has no valid moves, which would mean a stalemate
        return hasNoValidMoves(side, currentBoard);
    }
}
