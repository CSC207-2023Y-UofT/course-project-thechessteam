public class Rook implements Calculator {
    public Rook() {}

    @Override
    public long valid_moves(long from, int side, LocationBitboard board) {
        // due to occupied being duplicate code, may want to make it reusable.
        // occupied provides occupied places on the board. (recall, OR | operator)
        long occupied = board.whiteBishop[0] | board.whiteKing[0] | board.whiteRook[0] | board.whiteKnight[0] | board.whitePawn[0] | board.whiteQueen[0] |
                board.blackBishop[0] | board.blackKing[0] | board.blackRook[0] | board.blackKnight[0] | board.blackPawn[0] | board.blackQueen[0] ;

        // numberOfTrailingZeros built-in method, tells you the number of trailing zeros from where the piece currently stands on the 64 bit array
        int s = Long.numberOfTrailingZeros(from);
        long possibleHorizontalMoves = (occupied - 2 * from) ^ Long.reverse(Long.reverse(occupied) - 2 * Long.reverse(from));
        long possibilitiesVertical = ((occupied&FileAndRank.FileMasks8[s % 8]) - (2 * from)) ^ Long.reverse(Long.reverse(occupied&FileAndRank.FileMasks8[s % 8]) - (2 * Long.reverse(from)));
        return (possibleHorizontalMoves&FileAndRank.RankMasks8[s / 8]) | (possibilitiesVertical&FileAndRank.FileMasks8[s % 8]);
    }

    @Override
    public long attack_coverage(int side, LocationBitboard board) {
        return 0;
    }
}
