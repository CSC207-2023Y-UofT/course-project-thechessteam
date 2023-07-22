// pawn class
public final class Pawn implements Calculator{
    public Pawn(){}
    // TODO add attributes
    public long valid_moves(long from, int side, LocationBitboard board){
        // Precondition: side must be 0 or 1;

        // Get piece positions for black and white pieces separately
        long black_board = board.whitePawn[0] | board.whiteRook[0] | board.whiteKnight[0]
                    | board.whiteBishop[0] | board.whiteQueen[0] | board.whiteKing[0];
        long white_board = board.blackPawn[0] | board.blackRook[0] | board.blackKnight[0]
                    | board.blackBishop[0] | board.blackQueen[0] | board.blackKing[0];

        //curent position is a valid move
        long pawn_valid_move = 0L;

        //white pawn
        if side == 0 {
            // pawn's 1 tile ahead move, if there's no ally piece there already
            if (from & FileAndRank.RANK_1 == 0L) {
                pawn_valid_moves |= from >>> 8 ^ white_board;
            }

            // pawn's 2 tile ahead move, only first move and if there's no ally piece there already
            if (from & FileAndRank.RANK_7 != 0L) {
                pawn_valid_moves |= from >>> 16 ^ white_board;
            }
            // get curent attacks and take intersection with opponent pieces to find valid attack tiles
            pawn_valid_moves |= white_pawn_attacks(long from) &black_board;
        }
        // black pawn
        else  {
            // pawn's 1 tile ahead move, if there's no ally piece there already
            if (from & FileAndRank.RANK_8 == 0L) {
                pawn_valid_moves |= from << < 8 ^ black_board;
            }

            // pawn's 2 tile ahead move, only first move and if there's no ally piece there already
            if (from & FileAndRank.RANK_2 != 0L) {
                pawn_valid_moves |= from <<< 16 ^ black_board;
            }

            // get curent attacks and take intersection with opponent pieces to find valid attack tiles
            pawn_valid_moves |= black_pawn_attacks(long from) & white_board;
        }

        // return all possible valid moves
        return pwan_valid_moves;
    }

    public long attack_coverage(int side, LocationBitboard board) {
        // Get piece positions for black and white pieces separately
        long black_board = board.whitePawn[0] | board.whiteRook[0] | board.whiteKnight[0]
                | board.whiteBishop[0] | board.whiteQueen[0] | board.whiteKing[0];
        long white_board = board.blackPawn[0] | board.blackRook[0] | board.blackKnight[0]
                | board.blackBishop[0] | board.blackQueen[0] | board.blackKing[0];

        pawn_attacks = 0L;
        if side == 0 {
            for (long p: board.whitePawn[0]) // pseudocode
            {
                pawn_attacks |= white_pawn_attacks(long p) & black_board;
            }
        } else {
            for (long p: board.blackPawn[0]) // pseudocode
            {
                panw_attacks |= black_pawn_attacks(long from) & white_board;
            }
        }
        return pawn_attacks;
    }
}