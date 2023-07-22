public class Knight implements Calculator {

    private long sameColoredPieces;

    public Knight() {}

    @Override
    public long valid_moves(long from, int side, LocationBitboard board) {

        long occupied = board.whiteBishop[0] | board.whiteKing[0] | board.whiteRook[0] | board.whiteKnight[0] | board.whitePawn[0] | board.whiteQueen[0] |
                board.blackBishop[0] | board.blackKing[0] | board.blackRook[0] | board.blackKnight[0] | board.blackPawn[0] | board.blackQueen[0] ;

        int s = Long.numberOfTrailingZeros(from);
        // TODO calculations for possibleHorizontalMoves & possible vertical moves
        // long possibleHorizontalMoves =
        // long possibleVertical =

        this.sameColoredPieces = (side == 0) ?
                board.whitePawn[0] | board.whiteRook[0] | board.whiteKnight[0]
                        | board.whiteBishop[0] | board.whiteQueen[0] | board.whiteKing[0] :
                board.blackPawn[0] | board.blackRook[0] | board.blackKnight[0]
                        | board.blackBishop[0] | board.blackQueen[0] | board.blackKing[0];

        // TODO return calculateFinalPosition instead of placeholder 1
        // return calculateFinalPosition();

        return 1;
    }

    public long calculateFinalPosition(long candidate) {
        return candidate & ~this.sameColoredPieces;
    }

    @Override
    public long attack_coverage(int side, LocationBitboard board) {
        long attacked = 0L;  // bits where rook is attacking
        long knights_location;  // knights location based on team


        // TODO calculations for attack coverage for the knight

        return attacked;
    }
}
