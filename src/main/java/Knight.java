public class Knight implements Calculator {
    // Knight Class intended to hold the calculations for knight piece movements
    private long sameColoredPieces;

    public Knight() {}

    // method that calculates valid moves for knight
    public long valid_moves(long from, int side, LocationBitboard board) {

        // Amount of empty bits in front of piece
        int s = Long.numberOfTrailingZeros(from);

        // Gets the pieces of the same team
        this.sameColoredPieces = (side == 0) ?
                board.whitePawn[0] | board.whiteRook[0] | board.whiteKnight[0]
                        | board.whiteBishop[0] | board.whiteQueen[0] | board.whiteKing[0] :
                board.blackPawn[0] | board.blackRook[0] | board.blackKnight[0]
                        | board.blackBishop[0] | board.blackQueen[0] | board.blackKing[0];

        // calculates the position of knight
        return calculateFinalPosition(PreCalculatedAttacks.knight_attacks[s]);
    }

    public long calculateFinalPosition(long candidate) {
        return candidate & ~this.sameColoredPieces;
    }

    public long attack_coverage(int side, LocationBitboard board) {
        long attacked = 0L;  // bits where knight is attacking
        long knights_location;  // knights location based on team

        if (side == 0) {  // white side
            knights_location = board.whiteKnight[0];
        } else {  // black side
            knights_location = board.blackKnight[0];
        }

        for (int i = 0; i < 64; i++) {
            // Shifting knight location
            if ((int) ((knights_location >>> i) & 1) == 1) {
                attacked |= valid_moves(1L << i, side, board);
            }
        }

        return attacked;
    }
}
