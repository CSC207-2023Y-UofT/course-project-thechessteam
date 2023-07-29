package UseCases;

import Entities.Calculator;
import Entities.Calculators;
import Entities.LocationBitboard;
import UseCases.CheckCalculator;

public class ActualValidMove {
    // A use case class that filters moves calculated by Entities.Calculators.
    // Filters moves that would put side's Entities.King in check (Illegal moves).
    // side == true for white, side == false for black.

    // ----------------------------------------------------------------------------------------------------------
    public static long actual_valid_moves(long from, boolean side, LocationBitboard currentBoard) {
        // Convert into integer representation of side, refactor into boolean later
        int color;
        if (side) {
            color = 0;
        } else {
            color = 1;
        }

        // Determine what calculator to use (i.e. What is the piece type at from?)
        Calculator calculator = identify_calculator(from, currentBoard);

        // Read comment on UseCases.CheckCalculator below for this variable
        boolean fromIsKing = ((from & currentBoard.whiteKing[0]) != 0) || ((from & currentBoard.blackKing[0]) != 0);

        // Calculate candidate valid moves
        long candidates = calculator.valid_moves(from, color, currentBoard);

        // We will remove invalid moves from actualValid
        long actualValid = candidates;

        // Remove invalid moves
        for (int i = 0; i < 64; i++) {
            if ((candidates & (1L << i)) != 0L) {
                // Make a copy of currentBoard for testing if candidate position is valid
                LocationBitboard copy = locations_copy(currentBoard);
                copy.move_piece(from, 1L << i, side);
                copy.updateLocationVariables();

                if (CheckCalculator.is_in_check(color, copy)) {
                    actualValid &= ~(1L << i);
                }
                // Since UseCases.CheckCalculator does not include attack coverage of opponent king,
                // since it assumes we are not checked by opponent's king,
                // we have to make sure that when we move a king,
                // it should not be in the attack range of opponent's king
                else if (fromIsKing) {
                    int opponentColor = 1 - color; // Refactor into boolean later
                    if ((from & Calculators.kingCalculator.attack_coverage(opponentColor, copy)) != 0L) {
                        actualValid &= ~(1L << i);
                    }
                }
            }
        }
        return actualValid;
    }
    // ----------------------------------------------------------------------------------------------------------
    // Helper method for finding piece type
    // Returns null if side does not have any piece at from
    private static Calculator identify_calculator(long from, LocationBitboard currentBoard) {
        Calculator calculator = null;

        if (((from & currentBoard.whitePawn[0]) != 0L) || ((from & currentBoard.blackPawn[0]) != 0L)) {
            calculator = Calculators.pawnCalculator;
        }
        else if (((from & currentBoard.whiteRook[0]) != 0L) || ((from & currentBoard.blackRook[0]) != 0L)) {
            calculator = Calculators.rookCalculator;
        }
        else if (((from & currentBoard.whiteKnight[0]) != 0L) || ((from & currentBoard.blackKnight[0]) != 0L)) {
            calculator = Calculators.knightCalculator;
        }
        else if (((from & currentBoard.whiteBishop[0]) != 0L) || ((from & currentBoard.blackBishop[0]) != 0L)) {
            calculator = Calculators.bishopCalculator;
        }
        else if (((from & currentBoard.whiteQueen[0]) != 0L) || ((from & currentBoard.blackQueen[0]) != 0L)) {
            calculator = Calculators.queenCalculator;
        }
        else if (((from & currentBoard.whiteKing[0]) != 0L) || ((from & currentBoard.blackKing[0]) != 0L)) {
            calculator = Calculators.kingCalculator;
        }
        return calculator;
    }
    // Helper method for creating a copy of Entities.LocationBitboard
    // Probably needs some refactoring to Entities.LocationBitboard later so that this process is easier
    private static LocationBitboard locations_copy(LocationBitboard currentBoard) {
        LocationBitboard copy = new LocationBitboard();
        copy.whitePawn[0] = currentBoard.whitePawn[0];
        copy.whiteRook[0] = currentBoard.whiteRook[0];
        copy.whiteKnight[0] = currentBoard.whiteKnight[0];
        copy.whiteBishop[0] = currentBoard.whiteBishop[0];
        copy.whiteQueen[0] = currentBoard.whiteQueen[0];
        copy.whiteKing[0] = currentBoard.whiteKing[0];

        copy.blackPawn[0] = currentBoard.blackPawn[0];
        copy.blackRook[0] = currentBoard.blackRook[0];
        copy.blackKnight[0] = currentBoard.blackKnight[0];
        copy.blackBishop[0] = currentBoard.blackBishop[0];
        copy.blackQueen[0] = currentBoard.blackQueen[0];
        copy.blackKing[0] = currentBoard.blackKing[0];
        copy.updateLocationVariables();

        return copy;
    }
}
