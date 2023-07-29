package Entities;

import Entities.Calculator;

public class Queen implements Calculator {
    public Queen(){}
    public long valid_moves(long from, int side, LocationBitboard board) {
        // Hyperbola Quintessence Calculation
        int s = Long.numberOfTrailingZeros(from);
        long diag_m = FileAndRank.DiagonalMasks8[(s / 8) + (s % 8)];
        long anti_m = FileAndRank.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)];
        long vertical_m = FileAndRank.FileMasks8[s % 8];
        long horizontal_m = FileAndRank.RankMasks8[s / 8];
        long o = board.blackPawn[0] | board.blackRook[0] | board.blackKnight[0]
                | board.blackBishop[0] | board.blackQueen[0] | board.blackKing[0]
                | board.whitePawn[0] | board.whiteRook[0] | board.whiteKnight[0]
                | board.whiteBishop[0] | board.whiteQueen[0] | board.whiteKing[0];
        long diagonal_candidate = (
                ((o & diag_m) - 2 * from) ^ Long.reverse(
                        Long.reverse(o & diag_m) - 2 * Long.reverse(from))
        ) & diag_m;
        long anti_diagonal_candidate = (
                ((o & anti_m) - 2 * from) ^ Long.reverse(
                        Long.reverse(o & anti_m) - 2 * Long.reverse(from))
        ) & anti_m;
        long horizontal_candidate = (
                (o - 2 * from) ^ Long.reverse(
                        Long.reverse(o) - 2 * Long.reverse(from))
        ) & horizontal_m;
        long vertical_candidate = (
                ((o & vertical_m) - 2 * from) ^ Long.reverse(
                        Long.reverse(o & vertical_m) - 2 * Long.reverse(from))
        ) & vertical_m;

        long your_pieces;
        if (side == 0) {
            your_pieces = board.whitePawn[0] | board.whiteRook[0] | board.whiteKnight[0]
                    | board.whiteBishop[0] | board.whiteQueen[0] | board.whiteKing[0];
        }
        else {
            your_pieces = board.blackPawn[0] | board.blackRook[0] | board.blackKnight[0]
                    | board.blackBishop[0] | board.blackQueen[0] | board.blackKing[0];
        }

        long actual_diagonal = diagonal_candidate & ~your_pieces;
        long actual_anti = anti_diagonal_candidate & ~your_pieces;
        long actual_horizontal = horizontal_candidate & ~your_pieces;
        long actual_vertical = vertical_candidate & ~your_pieces;

        return actual_diagonal | actual_anti | actual_horizontal | actual_vertical;
    }

    public long attack_coverage(int side, LocationBitboard board){
        long coverage = 0L;
        long queen_locations;
        if (side == 0) {
            queen_locations = board.whiteQueen[0];
        }
        else {
            queen_locations = board.blackQueen[0];
        }
        for(int i = 0; i < 64; i++) {
            if ((int) ((queen_locations >>> i) & 1) == 1) {
                coverage |= valid_moves(1L << i, side, board);
            }
        }
        return coverage;
    }
}
