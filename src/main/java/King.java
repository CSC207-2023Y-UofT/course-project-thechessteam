public class King implements Calculator {

    @Override
    public long valid_moves(long from, int side, LocationBitboard board) {
        int position = Long.numberOfTrailingZeros(from);
        long kingCoverage = PreCalculatedAttacks.king_attacks[position];

        // Get the bitboard for all pieces on the current side
        long allPieces = getAllPieces(side, board);

        // The king can move to a square if it is not already occupied by a piece of the same color
        kingCoverage &= ~allPieces;

        // TODO: Check if the king would be put in check by making the move, if so, remove that move

        return kingCoverage;
    }

    @Override
    public long attack_coverage(int side, LocationBitboard board) {
        long coverage = 0L;
        long kingPositions = side == 0 ? board.whiteKing[0] : board.blackKing[0];

        while (kingPositions != 0) {
            int position = Long.numberOfTrailingZeros(kingPositions);
            coverage |= PreCalculatedAttacks.king_attacks[position];
            kingPositions &= kingPositions - 1; // Clear the least significant bit
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
