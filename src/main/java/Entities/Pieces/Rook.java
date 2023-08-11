package entities.pieces;

import entities.constants.FileAndRank;
import entities.locations.LocationBitboard;

public class Rook implements PieceCalculator {

    private long sameColoredPieces;

    public Rook() {}

    public long validMoves(long from, boolean side, LocationBitboard board) {
        // occupied provides occupied places on the board.
        long occupied = board.getOccupied();

        // numberOfTrailingZeros built-in method,
        // tells you the number of trailing zeros from where the piece currently stands on the 64 bit array
        int s = Long.numberOfTrailingZeros(from);
        long horizontalMask = FileAndRank.RANK_MASKS_8[s / 8];
        long verticalMask = FileAndRank.FILE_MASKS_8[s % 8];

        // Candidate horizontal valid move positions. Apply horizontal mask to filter out relevant position.
        long horizontal = (occupied - 2 * from) ^ Long.reverse(
                Long.reverse(occupied) - 2 * Long.reverse(from));
        // Candidate vertical valid move positions. Apply vertical mask again to filter out relevant positions.
        long vertical = ((occupied & verticalMask) - (2 * from)) ^ Long.reverse(
                Long.reverse(occupied & verticalMask) - (2 * Long.reverse(from)));

        // depending on whether you are black or white side (white == 0),
        // generates the pieces that are the same color as the piece in question (i.e., can't step on your own pieces).
        this.sameColoredPieces = (side) ? board.getWhiteLocations() : board.getBlackLocations();

        // Return long, of the valid moves this specific Entities.Pieces.Rook may take
        // Applies corresponding masks again to filter out relevant positions.
        return calculateFinalPosition(horizontal & horizontalMask)
                | calculateFinalPosition(vertical& verticalMask);
    }

    private long calculateFinalPosition(long candidate) {
        return candidate & ~this.sameColoredPieces;
    }

    public long attackCoverage(boolean side, LocationBitboard board) {
        long attacked = 0L;  // bits where rook is attacking
        long rookLocations;  // rook locations based on side/color

        // get the locations of the rook based on the input side
        if (side) {  // white
            rookLocations = board.whiteRook[0];
        } else {  // black
            rookLocations = board.blackRook[0];
        }

        // Loop through all possible rook positions
        for (int i = 0; i < 64; i++) {
            // If a rook is at the current position, get its valid moves and add it to coverage
            // shift rook locations i bits to the right, if it == 1, means rook is in the ith position
            // if rook is in the ith position, add that to the attacked bits
            if ((int) ((rookLocations >>> i) & 1) == 1) {
                attacked |= validMoves(1L << i, side, board);
            }
        }

        // Return all squares attacked by the rooks of the given side
        return attacked;
    }
}
