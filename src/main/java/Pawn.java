// pawn class
public final class Pawn extends Piece implements Calculator{
    // TODO add attributes
    public long valid_moves(long from, int side, LocationBitboard board){
        // Precondition: side must be 0 or 1;

        // Get piece positions for black and white pieces separately
//        long black_board = TODO
//        long white_board = TODO

        //white pawn
        if side == 0 {
            // Pawn's curent position is a valid move
            long wpawn_valid_moves = from;
            // pawn's 1 tile ahead move, if there's no ally piece there already
            wpawn_valid_moves &= from >>> 8 ^ white_board;

            // pawn's 2 tile ahead move, only first move and if there's no ally piece there already
            if (from & FileAndRank.RANK_7) {
                wpawn_valid_moves &= from >>> 16 ^ white_board;
            }
            // get curent attacks and take intersection with opponent pieces to find valid attack tiles.
            wpawn_valid_moves &= white_pawn_attacks( long from) &black_board;

            return wpwan_valid_moves;
        }
        // black pawn
        else  {
            // Pawn's curent position is a valid move
            long bpawn_valid_moves = from;
            // pawn's 1 tile ahead move, if there's no ally piece there already
            bpawn_valid_moves &= from <<< 8 ^ black_board;

            // pawn's 2 tile ahead move, only first move and if there's no ally piece there already
            if (from & FileAndRank.RANK_2) {
                bpawn_valid_moves &= from <<< 16 ^ black_board;
            }

            // get curent attacks and take intersection with opponent pieces to find valid attack tiles.
            bpawn_valid_moves &= black_pawn_attacks(long from) & white_board;

            return bpwan_valid_moves;
        }
    }
}