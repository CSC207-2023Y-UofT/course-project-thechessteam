public class Bishop implements Calculator {

    private long sameColoredPieces;

    public Bishop(){}

    /**
     * @param from current location, long
     * @param side either black side or white side, binary (side should actually be a boolean, need to be mindful of implementation).
     * @param board the current board state (contains a bunch of attributes)
     * @return valid moves, long
     */
    @Override
    public long valid_moves(long from, int side, LocationBitboard board) {
        // due to occupied being duplicate code, may want to make it reusable.
        // occupied provides occupied places on the board. (recall, OR | operator)
        // also recommended that whiteBishop shouldn't be a long array, just keep it as long.
        long occupied = board.whiteBishop[0] | board.whiteKing[0] | board.whiteRook[0] | board.whiteKnight[0] | board.whitePawn[0] | board.whiteQueen[0] |
                board.blackBishop[0] | board.blackKing[0] | board.blackRook[0] | board.blackKnight[0] | board.blackPawn[0] | board.blackQueen[0] ;

        // numberOfTrailingZeros built-in method, tells you the number of trailing zeros from where the piece currently stands on the 64 bit array
        int s = Long.numberOfTrailingZeros(from);
        long possibilitiesDiagonal = ((occupied&FileAndRank.DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * from)) ^ Long.reverse(Long.reverse(occupied&FileAndRank.DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * Long.reverse(from)));
        long possibilitiesAntiDiagonal = ((occupied&FileAndRank.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * from)) ^ Long.reverse(Long.reverse(occupied&FileAndRank.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * Long.reverse(from)));

        // depending on whether you are black or white side (white == 0), generates the pieces that are the same color as the piece in question (i.e., can't step on your own pieces).
        this.sameColoredPieces = (side == 0) ?
                board.whitePawn[0] | board.whiteRook[0] | board.whiteKnight[0]
                        | board.whiteBishop[0] | board.whiteQueen[0] | board.whiteKing[0] :
                board.blackPawn[0] | board.blackRook[0] | board.blackKnight[0]
                        | board.blackBishop[0] | board.blackQueen[0] | board.blackKing[0];

        // return long, of the valid moves this specific Bishop may take
        return calculateFinalPosition((possibilitiesDiagonal&FileAndRank.DiagonalMasks8[(s / 8) + (s % 8)])) | calculateFinalPosition(possibilitiesAntiDiagonal&FileAndRank.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]);
    }

    // helper, to calculate the final position (makes sure piece in question can't step on their own colored pieces)
    public long calculateFinalPosition(long candidate) {
        return candidate & ~this.sameColoredPieces;
    }

    /**
     * @param side
     * @param board
     * @return
     */
    @Override
    public long attack_coverage(int side, LocationBitboard board) {
        return 0;
    }
}
