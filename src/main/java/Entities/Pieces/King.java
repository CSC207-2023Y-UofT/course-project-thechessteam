package Entities.Pieces;

import Entities.Constants.PreCalculatedAttacks;
import Entities.Locations.LocationBitboard;

/**
 * Class representing a King piece in a chess game.
 * Implements the Calculator interface to determine valid moves and attack coverage.
 */
public class King implements Calculator {

    /**
     * Calculates the valid moves for a King from a given position.
     * This includes normal King moves and also special castling moves,
     * considering the King's and Rook's move history and the occupancy of the board.
     *
     * @param from  The starting position of the King.
     * @param side  The side of the King (true for white, false for black).
     * @param board The current state of the chessboard.
     * @return      A bitboard representing all valid moves for the King.
     */
    public long valid_moves(long from, boolean side, LocationBitboard board) {
        int position = Long.numberOfTrailingZeros(from);
        long kingCoverage = PreCalculatedAttacks.king_attacks[position];

        // Get the bitboard for all pieces on the current side
        long allPieces = (side) ? board.getWhiteLocations() : board.getBlackLocations();

        // The king can move to a square if it is not already occupied by a piece of the same color
        kingCoverage &= ~allPieces;

        // The UseCases.ActualValidCalculator will check if the king would be put in check by making the move,
        // if so, remove that move

        // Castling Move,
        // Check for if King does not cross a space attacked will be done in ActualValidCalculator.
        if (side) { // White
            if (!board.getWhiteKingMoved() && !board.getLeftRookMovedW()) {
                long needsEmptyL = (1L << 1) | (1L << 2) | (1L << 3);
                if ((board.getOccupied() & needsEmptyL) == 0L) {
                    kingCoverage |= (1L << 2);
                }
            }
            if (!board.getWhiteKingMoved() && !board.getRightRookMovedW()) {
                long needsEmptyR = (1L << 5) | (1L << 6);
                if ((board.getOccupied() & needsEmptyR) == 0L) {
                    kingCoverage |= (1L << 6);
                }
            }
        }
        else { // Black
            if (!board.getBlackKingMoved() && !board.getLeftRookMovedB()) {
                long needsEmptyL = (1L << 57) | (1L << 58) | (1L << 59);
                if ((board.getOccupied() & needsEmptyL) == 0L) {
                    kingCoverage |= (1L << 58);
                }
            }
            if (!board.getBlackKingMoved() && !board.getRightRookMovedB()) {
                long needsEmptyR = (1L << 61) | (1L << 62);
                if ((board.getOccupied() & needsEmptyR) == 0L) {
                    kingCoverage |= (1L << 62);
                }
            }
        }
        return kingCoverage;
    }

    /**
     * Calculates the attack coverage for the Kings of a given side on the current board.
     *
     * @param side  The side of the King (true for white, false for black).
     * @param board The current state of the chessboard.
     * @return      A bitboard representing all squares attacked by the Kings of the given side.
     */
    public long attack_coverage(boolean side, LocationBitboard board) {
        long coverage = 0L;
        long kingPositions = side ? board.whiteKing[0] : board.blackKing[0];

        for (int i = 0; i < 64; i++) {
            // Check if the bit at the i-th position is set
            if ((kingPositions & (1L << i)) != 0) {
                coverage |= PreCalculatedAttacks.king_attacks[i];
            }
        }
        return coverage;
    }
}
