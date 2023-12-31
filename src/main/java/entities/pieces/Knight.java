package entities.pieces;

import entities.constants.PreCalculatedAttacks;
import entities.locations.LocationBitboard;

public class Knight implements PieceCalculator {
    // entities.pieces.Knight Class intended to hold the calculations for knight piece movements
    private long sameColoredPieces;

    public Knight() {}

    // method that calculates valid moves for knight
    public long validMoves(long from, boolean side, LocationBitboard board) {

        // Amount of empty bits in front of piece
        int s = Long.numberOfTrailingZeros(from);

        // Gets the pieces of the same team
        this.sameColoredPieces = (side) ? board.getWhiteLocations() : board.getBlackLocations();

        // calculates the position of knight
        return calculateFinalPosition(PreCalculatedAttacks.knightAttacks[s]);
    }

    public long calculateFinalPosition(long candidate) {
        return candidate & ~this.sameColoredPieces;
    }

    public long attackCoverage(boolean side, LocationBitboard board) {
        long attacked = 0L;  // bits where knight is attacking
        long knightsLocation;  // knights location based on team

        if (side) {  // white side
            knightsLocation = board.whiteKnight[0];
        } else {  // black side
            knightsLocation = board.blackKnight[0];
        }

        for (int i = 0; i < 64; i++) {
            // Shifting knight location
            if ((int) ((knightsLocation >>> i) & 1) == 1) {
                attacked |= validMoves(1L << i, side, board);
            }
        }

        return attacked;
    }
}
