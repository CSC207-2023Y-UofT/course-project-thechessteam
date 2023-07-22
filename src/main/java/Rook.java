public class Rook implements Calculator {

    private long sameColoredPieces;

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

        // depending on whether you are black or white side (white == 0), generates the pieces that are the same color as the piece in question (i.e., can't step on your own pieces).
        this.sameColoredPieces = (side == 0) ?
                board.whitePawn[0] | board.whiteRook[0] | board.whiteKnight[0]
                        | board.whiteBishop[0] | board.whiteQueen[0] | board.whiteKing[0] :
                board.blackPawn[0] | board.blackRook[0] | board.blackKnight[0]
                        | board.blackBishop[0] | board.blackQueen[0] | board.blackKing[0];

        // return long, of the valid moves this specific Rook may take
        return calculateFinalPosition(possibleHorizontalMoves&FileAndRank.RankMasks8[s / 8]) | calculateFinalPosition(possibilitiesVertical&FileAndRank.FileMasks8[s % 8]);
    }

    // worry about refactoring later
    private long calculateFinalPosition(long candidate) {
        return candidate & ~this.sameColoredPieces;
    }

    @Override
    public long attack_coverage(int side, LocationBitboard board) {
        long attacked = 0L;  // bits where rook is attacking
        long rook_locations;  // rook locations based on side/color

        // get the locations of the rook based on the input side
        if (side == 0) {  // white
            rook_locations = board.whiteRook[0];
        } else {  // black
            rook_locations = board.blackRook[0];
        }

        // Loop through all possible rook positions
        for (int i = 0; i < 64; i++) {
            // If a rook is at the current position, get its valid moves and add it to coverage
            // shift rook locations i bits to the right, if it == 1, means rook is in the ith position
            // if rook is in the ith position, add that to the attacked bits
            if ((int) ((rook_locations >>> i) & 1) == 1) {
                attacked |= valid_moves(1L << i, side, board);
            }
        }

        // Return all squares attacked by the rooks of the given side
        return attacked;
    }
}
