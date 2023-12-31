package usecases;

import entities.locations.LocationBitboard;
import entities.variouscalculators.ActualValidCalculator;
import entities.variouscalculators.CheckCalculator;

/*
        * CheckmateCalculator determines if a player is in checkmate, i.e., in check and has no legal moves.
        * This class extends GameStateCalculator to utilize the method that checks if a player has no valid moves.
        */
public class CheckmateCalculator extends GameStateCalculator {
    // Instance variable to determine if a side is in check
    private final CheckCalculator checkCalc;

    /**
     * Constructor for CheckmateCalculator.
     *
     * @param validCalculator   Utility to calculate valid moves for a piece.
     * @param checkCalc         Utility to check if a side is in check.
     */
    public CheckmateCalculator(ActualValidCalculator validCalculator, CheckCalculator checkCalc) {
        super(validCalculator, checkCalc);  // Pass both arguments to the superclass' constructor
        this.checkCalc = checkCalc;
    }

    public boolean isCheckmate(boolean side, LocationBitboard currentBoard) {
        // If the side isn't in check, then it can't be in checkmate
        if (!checkCalc.isInCheck(side, currentBoard)) {
            return false;
        }

        // Use the inherited method to check if the side has no valid moves
        return hasNoValidMoves(side, currentBoard);
    }
}
