public class StalemateCalculator {

    // side = 0 to represent White
    // side = 1 to represent Black
    public boolean is_stalemate(int side, LocationBitboard board){
        // Check if the King is in check
        if (CheckCalculator.is_in_check(side, board)) {
            return false; // The King is in check, so it's not stalemate
        }

        // List all pieces for the side
        long[] allPieces = side == 0 ?
                new long[] {board.whitePawn[0], board.whiteRook[0], board.whiteKnight[0], board.whiteBishop[0], board.whiteQueen[0], board.whiteKing[0]} :
                new long[] {board.blackPawn[0], board.blackRook[0], board.blackKnight[0], board.blackBishop[0], board.blackQueen[0], board.blackKing[0]};

        // For each piece, see if it has any valid move
        for (long pieces : allPieces) {
            while (pieces != 0) {
                int position = Long.numberOfTrailingZeros(pieces);
                pieces -= Long.lowestOneBit(pieces); // Clear the least significant bit
                Calculator pieceCalculator = CheckmateCalculator.getPieceCalculator(position, board);
                if (pieceCalculator.valid_moves(1L << position, side, board) != 0) {
                    return false; // Found a piece that can move, so it's not stalemate
                }
            }
        }

        // If no piece can move and the King is not in check, it's stalemate
        return true;
    }
}
