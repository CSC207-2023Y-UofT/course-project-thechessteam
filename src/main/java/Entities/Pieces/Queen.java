package entities.pieces;

import entities.constants.FileAndRank;
import entities.locations.LocationBitboard;

public class Queen implements PieceCalculator {
    public Queen(){}
    public long validMoves(long from, boolean side, LocationBitboard board) {
        // Hyperbola Quintessence Calculation
        int s = Long.numberOfTrailingZeros(from);
        long diagM = FileAndRank.DIAGONAL_MASKS_8[(s / 8) + (s % 8)];
        long antiM = FileAndRank.ANTI_DIAGONAL_MASKS_8[(s / 8) + 7 - (s % 8)];
        long verticalM = FileAndRank.FILE_MASKS_8[s % 8];
        long horizontalM = FileAndRank.RANK_MASKS_8[s / 8];
        long o = board.getOccupied();
        // Diagonal valid move candidates calculation
        long diagonalCandidate = (
                ((o & diagM) - 2 * from) ^ Long.reverse(
                        Long.reverse(o & diagM) - 2 * Long.reverse(from))
        ) & diagM;
        // Anti-diagonal valid move candidates calculation
        long antiDiagonalCandidate = (
                ((o & antiM) - 2 * from) ^ Long.reverse(
                        Long.reverse(o & antiM) - 2 * Long.reverse(from))
        ) & antiM;
        // Horizontal valid move candidates calculation
        long horizontalCandidate = (
                (o - 2 * from) ^ Long.reverse(
                        Long.reverse(o) - 2 * Long.reverse(from))
        ) & horizontalM;
        // Vertical valid move candidates calculation
        long verticalCandidate = (
                ((o & verticalM) - 2 * from) ^ Long.reverse(
                        Long.reverse(o & verticalM) - 2 * Long.reverse(from))
        ) & verticalM;

        long yourPieces;
        if (side) {
            yourPieces = board.getWhiteLocations();
        }
        else {
            yourPieces = board.getBlackLocations();
        }

        // Remove capturing your own piece as a valid move
        long actualDiagonal = diagonalCandidate & ~yourPieces;
        long actualAnti = antiDiagonalCandidate & ~yourPieces;
        long actualHorizontal = horizontalCandidate & ~yourPieces;
        long actualVertical = verticalCandidate & ~yourPieces;

        // Combine all actual valid moves
        return actualDiagonal | actualAnti | actualHorizontal | actualVertical;
    }

    public long attackCoverage(boolean side, LocationBitboard board){
        long coverage = 0L;
        long queenLocations;
        if (side) {
            queenLocations = board.whiteQueen[0];
        }
        else {
            queenLocations = board.blackQueen[0];
        }
        for(int i = 0; i < 64; i++) {
            if ((int) ((queenLocations >>> i) & 1) == 1) {
                coverage |= validMoves(1L << i, side, board);
            }
        }
        return coverage;
    }
}
