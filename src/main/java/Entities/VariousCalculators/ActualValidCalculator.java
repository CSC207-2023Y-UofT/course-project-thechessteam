package Entities.VariousCalculators;

import Entities.Pieces.Calculator;
import Entities.Locations.LocationBitboard;

public class ActualValidCalculator {
    // A use case class that filters moves calculated by Entities.VariousCalculators.Calculators.
    // Filters moves that would put side's Entities.Pieces.King in check (Illegal moves).
    // side == true for white, side == false for black.
    private Calculators calculators;
    private CheckCalculator checkCalc;
    public ActualValidCalculator(Calculators calculators, CheckCalculator checkCalc) {
        this.calculators = calculators;
        this.checkCalc = checkCalc;
    }

    // ----------------------------------------------------------------------------------------------------------
    public long actual_valid_moves(long from, boolean side, LocationBitboard currentBoard) {
        // Precondition: There is a piece at from inside currentBoard.
        // Precondition: The piece is on side.
        // Precondition: There cannot be two pieces in the same location of currentBoard.

        // Determine what calculator to use (i.e. What is the piece type at from?)
        Calculator calculator = identify_calculator(from, currentBoard);

        // Read comment on UseCases.CheckCalculator below for this variable
        boolean fromIsKing = ((from & currentBoard.whiteKing[0]) != 0) || ((from & currentBoard.blackKing[0]) != 0);

        // Calculate candidate valid moves
        long candidates = calculator.valid_moves(from, side, currentBoard);

        // We will remove invalid moves from actualValid
        long actualValid = candidates;

        // Remove invalid moves
        for (int i = 0; i < 64; i++) {
            long moveCandidate = 1L << i;
            if ((candidates & moveCandidate) != 0L) {
                // Make a copy of currentBoard for testing if candidate position is valid
                LocationBitboard copy = locations_copy(currentBoard);
                copy.move_piece(from, moveCandidate, side);
                copy.updateLocationVariables();

                if (checkCalc.is_in_check(side, copy)) {
                    actualValid &= ~moveCandidate;
                }
                // Since UseCases.CheckCalculator does not include attack coverage of opponent king,
                // since it assumes we are not checked by opponent's king,
                // we have to make sure that when we move a king,
                // it should not be in the attack range of opponent's king
                else if (fromIsKing) {
                    if ((moveCandidate & calculators.kingCalculator.attack_coverage(!side, copy)) != 0L) {
                        actualValid &= ~moveCandidate;
                    }
                }
            }
        }
        // Check the King does not leave or cross over a square attacked by an enemy piece for castling
        if (fromIsKing) {
            // Is king leaving a square attacked by enemy?
            boolean canCastle = !checkCalc.is_in_check(side, currentBoard);
            if (canCastle) { // Check if we do not cross over a square attacked by an enemy piece
                if (side) { // White castling
                    if ((from == (1L << 4)) && (((1L << 2) & actualValid) != 0L)) { // left castling valid candidate
                        LocationBitboard copy = locations_copy(currentBoard);
                        copy.move_piece(from, 1L << 3, true); // Suppose king is at the square we cross over
                        copy.updateLocationVariables();

                        if (checkCalc.is_in_check(true, copy)) { // Is king in check?
                            actualValid &= ~(1L << 2);
                        } else { // Is king in attack range of opponent king?
                            if (((1L << 3) & calculators.kingCalculator.attack_coverage(false, copy)) != 0L) {
                                actualValid &= ~(1L << 2);
                            }
                        }
                    }
                    if ((from == (1L << 4)) && (((1L << 6) & actualValid) != 0L)) { // right castling valid candidate
                        LocationBitboard copy = locations_copy(currentBoard);
                        copy.move_piece(from, 1L << 5, true); // Suppose king is at the square we cross over
                        copy.updateLocationVariables();

                        if (checkCalc.is_in_check(true, copy)) { // Is king in check?
                            actualValid &= ~(1L << 6);
                        } else { // Is king in attack range of opponent king?
                            if (((1L << 5) & calculators.kingCalculator.attack_coverage(false, copy)) != 0L) {
                                actualValid &= ~(1L << 6);
                            }
                        }
                    }
                }
                else { // Black castling
                    if ((from == (1L << 60)) && (((1L << 58) & actualValid) != 0L)) { // left castling valid candidate
                        LocationBitboard copy = locations_copy(currentBoard);
                        copy.move_piece(from, 1L << 59, false); // Suppose king is at the square we cross over
                        copy.updateLocationVariables();

                        if (checkCalc.is_in_check(false, copy)) { // Is king in check?
                            actualValid &= ~(1L << 58);
                        } else { // Is king in attack range of opponent king?
                            if (((1L << 59) & calculators.kingCalculator.attack_coverage(true, copy)) != 0L) {
                                actualValid &= ~(1L << 58);
                            }
                        }
                    }
                    if ((from == (1L << 60)) && (((1L << 62) & actualValid) != 0L)) { // right castling valid candidate
                        LocationBitboard copy = locations_copy(currentBoard);
                        copy.move_piece(from, 1L << 61, false); // Suppose king is at the square we cross over
                        copy.updateLocationVariables();

                        if (checkCalc.is_in_check(false, copy)) { // Is king in check?
                            actualValid &= ~(1L << 62);
                        } else { // Is king in attack range of opponent king?
                            if (((1L << 61) & calculators.kingCalculator.attack_coverage(true, copy)) != 0L) {
                                actualValid &= ~(1L << 62);
                            }
                        }
                    }
                }
            }
            else {
                if (side) {
                    if (from == 1L << 4) {
                        actualValid &= ~(1L << 2);
                        actualValid &= ~(1L << 6);
                    }
                }
                else {
                    if (from == 1L << 60) {
                        actualValid &= ~(1L << 58);
                        actualValid &= ~(1L << 62);
                    }
                }
            }
        }
        return actualValid;
    }
    // ----------------------------------------------------------------------------------------------------------
    // Helper method for finding piece type
    // Throws RuntimeException if side does not have any piece at from
    private Calculator identify_calculator(long from, LocationBitboard currentBoard) {
        Calculator calculator;

        if (((from & currentBoard.whitePawn[0]) != 0L) || ((from & currentBoard.blackPawn[0]) != 0L)) {
            calculator = calculators.pawnCalculator;
        }
        else if (((from & currentBoard.whiteRook[0]) != 0L) || ((from & currentBoard.blackRook[0]) != 0L)) {
            calculator = calculators.rookCalculator;
        }
        else if (((from & currentBoard.whiteKnight[0]) != 0L) || ((from & currentBoard.blackKnight[0]) != 0L)) {
            calculator = calculators.knightCalculator;
        }
        else if (((from & currentBoard.whiteBishop[0]) != 0L) || ((from & currentBoard.blackBishop[0]) != 0L)) {
            calculator = calculators.bishopCalculator;
        }
        else if (((from & currentBoard.whiteQueen[0]) != 0L) || ((from & currentBoard.blackQueen[0]) != 0L)) {
            calculator = calculators.queenCalculator;
        }
        else if (((from & currentBoard.whiteKing[0]) != 0L) || ((from & currentBoard.blackKing[0]) != 0L)) {
            calculator = calculators.kingCalculator;
        } else {
            throw new RuntimeException("No piece found at position " + Long.numberOfTrailingZeros(from));
        }
        return calculator;
    }
    // Helper method for creating a copy of Entities.Locations.LocationBitboard
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
