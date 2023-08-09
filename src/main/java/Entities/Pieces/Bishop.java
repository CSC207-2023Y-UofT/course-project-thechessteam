package Entities.Pieces;

import Entities.Constants.FileAndRank;
import Entities.Locations.LocationBitboard;

/**
 * Class representing a Bishop piece in a chess game.
 * Implements the Calculator interface to determine valid moves and attack coverage.
 */
public class Bishop implements Calculator {

    private long sameColoredPieces;

    /**
     * Default constructor for the Bishop class.
     */
    public Bishop(){}

    /**
     * Calculates the valid moves for a Bishop from a given position.
     *
     * @param from  The starting position of the Bishop.
     * @param side  The side of the Bishop (true for white, false for black).
     * @param board The current state of the chessboard.
     * @return      A bitboard representing all valid moves for the Bishop.
     */
    public long valid_moves(long from, boolean side, LocationBitboard board) {
        // occupied provides occupied places on the board.
        long occupied = board.getOccupied();

        // numberOfTrailingZeros built-in method,
        // tells you the number of trailing zeros from where the piece currently stands on the 64 bit array
        int s = Long.numberOfTrailingZeros(from);
        long diagonalMask = FileAndRank.DiagonalMasks8[(s / 8) + (s % 8)];
        long antiDiagonalMask = FileAndRank.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)];

        // Candidate diagonal valid move positions.
        // Apply diagonal mask again to filter out relevant positions.
        long diagonal = ((occupied& diagonalMask) - (2 * from)) ^ Long.reverse(
                Long.reverse(occupied& diagonalMask) - (2 * Long.reverse(from)));
        // Candidate anti-diagonal valid move positions.
        // Apply anti-diagonal mask again to filter out relevant positions.
        long antiDiagonal = ((occupied& antiDiagonalMask) - (2 * from)) ^ Long.reverse(
                Long.reverse(occupied& antiDiagonalMask) - (2 * Long.reverse(from)));

        // depending on whether you are black or white side (white == 0),
        // generates the pieces that are the same color as the piece in question (i.e., can't step on your own pieces).
        this.sameColoredPieces = (side) ?
                board.getWhiteLocations() :
                board.getBlackLocations();

        // return long, of the valid moves this specific Entities.Pieces.Bishop may take
        return calculateFinalPosition((diagonal& diagonalMask)) |
                calculateFinalPosition(antiDiagonal& antiDiagonalMask);
    }

    /**
     * Helper method to calculate the final position of the Bishop, excluding its own colored pieces.
     *
     * @param candidate The candidate positions for the Bishop's move.
     * @return          A bitboard representing the final valid positions.
     */
    private long calculateFinalPosition(long candidate) {
        return candidate & ~this.sameColoredPieces;
    }

    /**
     * Calculates the attack coverage for the Bishops of a given side on the current board.
     *
     * @param side  The side of the Bishop (true for white, false for black).
     * @param board The current state of the chessboard.
     * @return      A bitboard representing all squares attacked by the Bishops of the given side.
     */
    public long attack_coverage(boolean side, LocationBitboard board) {
        long attacked = 0L;  // bits where bishop is attacking
        long bishopLocations;  // bishop locations based on side/color

        // get the locations of the rook based on the input side
        if (side) {  // white
            bishopLocations = board.whiteBishop[0];
        } else {  // black
            bishopLocations = board.blackBishop[0];
        }

        // Loop through all possible bishop positions
        for (int i = 0; i < 64; i++) {
            // If a rook is at the current position, get its valid moves and add it to coverage
            // shift rook locations i bits to the right, if it == 1, means rook is in the ith position
            // if rook is in the ith position, add that to the attacked bits
            if ((int) ((bishopLocations >>> i) & 1) == 1) {
                attacked |= valid_moves(1L << i, side, board);
            }
        }
        // Return all squares attacked by the rooks of the given side
        return attacked;
    }
}
