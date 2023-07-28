package Entities;

import Entities.Calculator;

public class King implements Calculator {

    public long valid_moves(long from, int side, LocationBitboard board) {
        int position = Long.numberOfTrailingZeros(from);
        long kingCoverage = PreCalculatedAttacks.king_attacks[position];

        // Get the bitboard for all pieces on the current side
        long allPieces = getAllPieces(side, board);

        // The king can move to a square if it is not already occupied by a piece of the same color
        kingCoverage &= ~allPieces;

        // The UseCases.ActualValidMove use case class will check if the king would be put in check by making the move,
        // if so, remove that move

        return kingCoverage;
    }

    public long attack_coverage(int side, LocationBitboard board) {
        long coverage = 0L;
        long kingPositions = side == 0 ? board.whiteKing[0] : board.blackKing[0];

        for (int i = 0; i < 64; i++) {
            // Check if the bit at the i-th position is set
            if ((kingPositions & (1L << i)) != 0) {
                coverage |= PreCalculatedAttacks.king_attacks[i];
            }
        }
        return coverage;
    }

    private long getAllPieces(int side, LocationBitboard board) {
        if (side == 0) {
            return board.whitePawn[0] | board.whiteRook[0] | board.whiteKnight[0] |
                    board.whiteBishop[0] | board.whiteQueen[0] | board.whiteKing[0];
        } else {
            return board.blackPawn[0] | board.blackRook[0] | board.blackKnight[0] |
                    board.blackBishop[0] | board.blackQueen[0] | board.blackKing[0];
        }
    }
}
