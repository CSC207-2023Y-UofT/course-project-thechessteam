// pawn class
public class Pawn implements Calculator{
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
        if (side == 0) {
            // pawn's 1 tile ahead move, if there's no piece there already
            if (from & FileAndRank.RANK_1 == 0L) {
                pawn_valid_moves |= (from << 8) & ~white_board & ~black_board;
            }

            // pawn's 2 tile ahead move, only first move and if there's no ally piece there already
            if (from & FileAndRank.RANK_7 != 0L) {
                pawn_valid_moves |= (from << 16) & ~white_board;
            }
            // get curent attacks and take intersection with opponent pieces to find valid attack tiles
            pawn_valid_moves |= white_pawn_attacks(long from) & black_board;
        }
        // black pawn
        else  {
            // pawn's 1 tile ahead move, if there's no ally piece there already
            if (from & FileAndRank.RANK_8 == 0L) {
                pawn_valid_moves |= (from >>> 8) & ~black_board & ~white_board;
            }

            // pawn's 2 tile ahead move, only first move and if there's no ally piece there already
            if (from & FileAndRank.RANK_2 != 0L) {
                pawn_valid_moves |= (from >>> 16) & ~black_board;
            }

            // get curent attacks and take intersection with opponent pieces to find valid attack tiles
            pawn_valid_moves |= black_pawn_attacks(long from) & white_board;
        }

        // return all possible valid moves
        return pawn_valid_moves;
    }

    public long attack_coverage(int side, LocationBitboard board) {
        // Get piece positions for black and white pieces separately
        long black_board = board.whitePawn[0] | board.whiteRook[0] | board.whiteKnight[0]
                | board.whiteBishop[0] | board.whiteQueen[0] | board.whiteKing[0];
        long white_board = board.blackPawn[0] | board.blackRook[0] | board.blackKnight[0]
                | board.blackBishop[0] | board.blackQueen[0] | board.blackKing[0];

        // start with no attack positions
        pawn_attacks = 0L;
        // We will use 1L and shift the 1 right once each time to check all board positions
        position_checker = 1L;

        // white
        if (side == 0) {
            for (int i = 0; i < 64; i++){
                // bit shift the 1 in 1L right by i times, i going from 0 to 63
                position = position_checker >>> i;
                // if the position being checked exists in white pawn locations, and has an opponent piece to attack,
                // append it to attacks
                if (position & board.whitePawn != 0L){
                    pawn_attacks |= white_pawn_attacks(long position) & black_board;
                }
            }
        //black
        } else {
            // bit shift the 1 in 1L right by i times, i going from 0 to 63
            for (int i = 0; i < 64; i++){
                position = position_checker >>> i;
                // if the position being checked exists in black pawn locations, and has an opponent piece to attack,
                // append it to attacks
                if (position & board.blackPawn != 0L){
                    pawn_attacks |= black_pawn_attacks(long position) & white_board;
                }
            }
        }
        return pawn_attacks;
    }
}