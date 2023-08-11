package entities.Pieces;

import entities.constants.FileAndRank;
import entities.locations.LocationBitboard;

public class Queen implements PieceCalculator {
    public Queen(){}
    public long valid_moves(long from, boolean side, LocationBitboard board) {
        // Hyperbola Quintessence Calculation
        int s = Long.numberOfTrailingZeros(from);
        long diag_m = FileAndRank.DiagonalMasks8[(s / 8) + (s % 8)];
        long anti_m = FileAndRank.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)];
        long vertical_m = FileAndRank.FileMasks8[s % 8];
        long horizontal_m = FileAndRank.RANK_MASKS_8[s / 8];
        long o = board.getOccupied();
        // Diagonal valid move candidates calculation
        long diagonalCandidate = (
                ((o & diag_m) - 2 * from) ^ Long.reverse(
                        Long.reverse(o & diag_m) - 2 * Long.reverse(from))
        ) & diag_m;
        // Anti-diagonal valid move candidates calculation
        long antiDiagonalCandidate = (
                ((o & anti_m) - 2 * from) ^ Long.reverse(
                        Long.reverse(o & anti_m) - 2 * Long.reverse(from))
        ) & anti_m;
        // Horizontal valid move candidates calculation
        long horizontalCandidate = (
                (o - 2 * from) ^ Long.reverse(
                        Long.reverse(o) - 2 * Long.reverse(from))
        ) & horizontal_m;
        // Vertical valid move candidates calculation
        long verticalCandidate = (
                ((o & vertical_m) - 2 * from) ^ Long.reverse(
                        Long.reverse(o & vertical_m) - 2 * Long.reverse(from))
        ) & vertical_m;

        long your_pieces;
        if (side) {
            your_pieces = board.getWhiteLocations();
        }
        else {
            your_pieces = board.getBlackLocations();
        }

        // Remove capturing your own piece as a valid move
        long actualDiagonal = diagonalCandidate & ~your_pieces;
        long actualAnti = antiDiagonalCandidate & ~your_pieces;
        long actualHorizontal = horizontalCandidate & ~your_pieces;
        long actualVertical = verticalCandidate & ~your_pieces;

        // Combine all actual valid moves
        return actualDiagonal | actualAnti | actualHorizontal | actualVertical;
    }

    public long attack_coverage(boolean side, LocationBitboard board){
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
                coverage |= valid_moves(1L << i, side, board);
            }
        }
        return coverage;
    }
}
