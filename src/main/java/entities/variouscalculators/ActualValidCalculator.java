package entities.variouscalculators;

import entities.pieces.PieceCalculator;
import entities.locations.LocationBitboard;

/**
 * Class responsible for filtering valid moves calculated by various calculators.
 * Filters moves that would put the side's king in check (illegal moves).
 * Provides functionality to calculate valid moves considering the rules of chess.
 * Can handle the complexities of castling and en passant.
 * side == true for white, side == false for black.
 */
public class ActualValidCalculator {
    // A use case class that filters moves calculated by entities.VariousCalculators.Calculators.
    // Filters moves that would put side's entities.pieces.King in check (Illegal moves).
    // side == true for white, side == false for black.
    private final Calculators calculators;
    private final CheckCalculator checkCalc;

    /**
     * Constructor for the ActualValidCalculator.
     *
     * @param calculators Reference to the various piece calculators.
     * @param checkCalc   Reference to the check calculator.
     */
    public ActualValidCalculator(Calculators calculators, CheckCalculator checkCalc) {
        this.calculators = calculators;
        this.checkCalc = checkCalc;
    }

    /**
     * Calculates the actual valid moves for a piece at a given position.
     * This method filters out moves that would put the side's king in check.
     * Ensures that the king does not move into, out of, or across a check.
     *
     * Precondition: There is a piece at 'from' inside 'currentBoard'.
     * Precondition: The piece is on the given 'side'.
     * Precondition: There cannot be two pieces in the same location of 'currentBoard'.
     *
     * @param from          The starting position of the piece.
     * @param side          The side of the piece (true for white, false for black).
     * @param currentBoard  The current state of the chessboard.
     * @return              A bitboard representing all actual valid moves for the piece.
     */
    public long actualValidMoves(long from, boolean side, LocationBitboard currentBoard) {
        // Precondition: There is a piece at from inside currentBoard.
        // Precondition: The piece is on side.
        // Precondition: There cannot be two pieces in the same location of currentBoard.

        // Determine what calculator to use (i.e. What is the piece type at from?)
        PieceCalculator calculator = identifyCalculator(from, currentBoard);

        // Read comment on UseCases.CheckCalculator below for this variable
        boolean fromIsKing = ((from & currentBoard.whiteKing[0]) != 0) || ((from & currentBoard.blackKing[0]) != 0);

        // Calculate candidate valid moves
        long candidates = calculator.validMoves(from, side, currentBoard);

        // We will remove invalid moves from actualValid
        long actualValid = candidates;

        // Remove invalid moves
        for (int i = 0; i < 64; i++) {
            long moveCandidate = 1L << i;
            if ((candidates & moveCandidate) != 0L) {
                // Make a copy of currentBoard for testing if candidate position is valid
                LocationBitboard copy = locationsCopy(currentBoard);
                copy.movePiece(from, moveCandidate, side);
                copy.updateLocationVariables();

                if (checkCalc.isInCheck(side, copy)) {
                    actualValid &= ~moveCandidate;
                }
                // Since UseCases.CheckCalculator does not include attack coverage of opponent king,
                // since it assumes we are not checked by opponent's king,
                // we have to make sure that when we move a king,
                // it should not be in the attack range of opponent's king
                else if (fromIsKing) {
                    if ((moveCandidate & calculators.kingCalculator.attackCoverage(!side, copy)) != 0L) {
                        actualValid &= ~moveCandidate;
                    }
                }
            }
        }
        // Check the King does not leave or cross over a square attacked by an enemy piece for castling
        if (fromIsKing) {
            actualValid = processCastlingActualValidMoves(from, side, currentBoard, actualValid);
        }
        return actualValid;
    }

    // ----------------------------------------------------------------------------------------------------------
    // Helper Methods

    /**
     * Helper method to identify the type of piece at a given position.
     * Throws a RuntimeException if the side does not have any piece at the given position.
     *
     * @param from         The position to identify the piece.
     * @param currentBoard The current state of the chessboard.
     * @return             The calculator corresponding to the identified piece.
     */
    private PieceCalculator identifyCalculator(long from, LocationBitboard currentBoard) {
        PieceCalculator calculator;

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

    /**
     * Helper method to create a copy of a LocationBitboard.
     * Useful for making hypothetical moves and checking their legality.
     *
     * @param currentBoard The current state of the chessboard.
     * @return             A new LocationBitboard object that is a copy of 'currentBoard'.
     */
    private static LocationBitboard locationsCopy(LocationBitboard currentBoard) {
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

    // Returns the actual valid moves of king, removing castling moves that violates castling conditions
    private long processCastlingActualValidMoves(long from, boolean side,
                                                 LocationBitboard currentBoard, long actualValid) {
        long updatedActualValid = actualValid;
        // Is king leaving a square attacked by enemy?
        boolean canCastle = !checkCalc.isInCheck(side, currentBoard);
        if (canCastle) { // Check if we do not cross over a square attacked by an enemy piece
            if (side) { // White castling
                updatedActualValid = updateWhiteKingCastlingMoves(from, currentBoard, updatedActualValid);
            }
            else { // Black castling
                updatedActualValid = updateBlackKingCastlingMoves(from, currentBoard, updatedActualValid);
            }
        }
        else { // We can now remove all castling moves since we now know it is now not possible
            updatedActualValid = removeAllCastlingMoves(from, side, updatedActualValid);
        }
        return updatedActualValid;
    }

    // Helper methods for removing moves that violate castling conditions.
    // Returns the actual valid moves after the removal.
    private long updateWhiteKingCastlingMoves(long from, LocationBitboard currentBoard, long actualValid) {
        long updatedActualValid = actualValid;
        updatedActualValid = updateWhiteKingLeftCastle(from, currentBoard, updatedActualValid);
        updatedActualValid = updateWhiteKingRightCastle(from, currentBoard, updatedActualValid);
        return updatedActualValid;
    }

    private long updateBlackKingCastlingMoves(long from, LocationBitboard currentBoard, long actualValid) {
        long updatedActualValid = actualValid;
        updatedActualValid = updateBlackKingLeftCastle(from, currentBoard, updatedActualValid);
        updatedActualValid = updateBlackKingRightCastle(from, currentBoard, updatedActualValid);
        return updatedActualValid;
    }

    private long updateWhiteKingLeftCastle(long from, LocationBitboard currentBoard, long actualValid) {
        long updatedActualValid = actualValid;
        if ((from == (1L << 4)) && (((1L << 2) & actualValid) != 0L)) { // left castling valid candidate
            LocationBitboard copy = locationsCopy(currentBoard);
            copy.movePiece(from, 1L << 3, true); // Suppose king is at the square we cross over
            copy.updateLocationVariables();

            if (checkCalc.isInCheck(true, copy)) { // Is king in check?
                updatedActualValid &= ~(1L << 2);
            } else { // Is king in attack range of opponent king?
                if (((1L << 3) & calculators.kingCalculator.attackCoverage(false, copy)) != 0L) {
                    updatedActualValid &= ~(1L << 2);
                }
            }
        }
        return updatedActualValid;
    }

    private long updateWhiteKingRightCastle(long from, LocationBitboard currentBoard, long actualValid) {
        long updatedActualValid = actualValid;
        if ((from == (1L << 4)) && (((1L << 6) & actualValid) != 0L)) { // right castling valid candidate
            LocationBitboard copy = locationsCopy(currentBoard);
            copy.movePiece(from, 1L << 5, true); // Suppose king is at the square we cross over
            copy.updateLocationVariables();

            if (checkCalc.isInCheck(true, copy)) { // Is king in check?
                updatedActualValid &= ~(1L << 6);
            } else { // Is king in attack range of opponent king?
                if (((1L << 5) & calculators.kingCalculator.attackCoverage(false, copy)) != 0L) {
                    updatedActualValid &= ~(1L << 6);
                }
            }
        }
        return updatedActualValid;
    }

    private long updateBlackKingLeftCastle(long from, LocationBitboard currentBoard, long actualValid) {
        long updatedActualValid = actualValid;
        if ((from == (1L << 60)) && (((1L << 58) & actualValid) != 0L)) { // left castling valid candidate
            LocationBitboard copy = locationsCopy(currentBoard);
            copy.movePiece(from, 1L << 59, false); // Suppose king is at the square we cross over
            copy.updateLocationVariables();

            if (checkCalc.isInCheck(false, copy)) { // Is king in check?
                updatedActualValid &= ~(1L << 58);
            } else { // Is king in attack range of opponent king?
                if (((1L << 59) & calculators.kingCalculator.attackCoverage(true, copy)) != 0L) {
                    updatedActualValid &= ~(1L << 58);
                }
            }
        }
        return updatedActualValid;
    }

    private long updateBlackKingRightCastle(long from, LocationBitboard currentBoard, long actualValid) {
        long updatedActualValid = actualValid;
        if ((from == (1L << 60)) && (((1L << 62) & actualValid) != 0L)) { // right castling valid candidate
            LocationBitboard copy = locationsCopy(currentBoard);
            copy.movePiece(from, 1L << 61, false); // Suppose king is at the square we cross over
            copy.updateLocationVariables();

            if (checkCalc.isInCheck(false, copy)) { // Is king in check?
                updatedActualValid &= ~(1L << 62);
            } else { // Is king in attack range of opponent king?
                if (((1L << 61) & calculators.kingCalculator.attackCoverage(true, copy)) != 0L) {
                    updatedActualValid &= ~(1L << 62);
                }
            }
        }
        return updatedActualValid;
    }

    // Returns actualValid with all castling moves removed
    private long removeAllCastlingMoves(long from, boolean side, long actualValid) {
        long updatedActualValid = actualValid;
        if (side) {
            if (from == 1L << 4) {
                updatedActualValid &= ~(1L << 2);
                updatedActualValid &= ~(1L << 6);
            }
        }
        else {
            if (from == 1L << 60) {
                updatedActualValid &= ~(1L << 58);
                updatedActualValid &= ~(1L << 62);
            }
        }
        return updatedActualValid;
    }
}
