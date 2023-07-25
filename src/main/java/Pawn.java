// pawn class
public class Pawn implements Calculator{
    public Pawn(){}
    // TODO add attributes

    // Creating instance of class PreCalculatedAttacks to access variable pawn_attacks
    PreCalculatedAttacks attack_holder = new PreCalculatedAttacks();

    public long valid_moves(long from, int side, LocationBitboard board){
        // Precondition: side must be 0 or 1;

        // Get piece positions for black and white pieces separately
        long black_board = board.whitePawn[0] | board.whiteRook[0] | board.whiteKnight[0]
                    | board.whiteBishop[0] | board.whiteQueen[0] | board.whiteKing[0];
        long white_board = board.blackPawn[0] | board.blackRook[0] | board.blackKnight[0]
                    | board.blackBishop[0] | board.blackQueen[0] | board.blackKing[0];

        //curent position is a valid move
        long pawn_valid_moves = 0L;

        //white pawn
        if (side == 0) {
            // pawn's 1 tile ahead move, if there's no piece there already
            if ((from & FileAndRank.RANK_1) == 0L) {
                pawn_valid_moves |= ((from << 8) & ~white_board & ~black_board);
            }

            // pawn's 2 tile ahead move, only first move and if there's no ally piece there already
            if ((from & FileAndRank.RANK_7) != 0L) {
                pawn_valid_moves |= ((from << 16) & ~white_board);
            }
            // get curent attacks and take intersection with opponent pieces to find valid attack tiles
            long attacks_right = (from & ~FileAndRank.RANK_8 & ~FileAndRank.FILE_A)<<7; // capture right
            long attacks_left = (from & ~FileAndRank.RANK_8 & ~FileAndRank.FILE_H)<<9; // capture left
            pawn_valid_moves |= ((attacks_right|attacks_left) & black_board);
        }
        // black pawn
        else  {
            // pawn's 1 tile ahead move, if there's no ally piece there already
            if ((from & FileAndRank.RANK_8) == 0L) {
                pawn_valid_moves |= ((from >>> 8) & ~black_board & ~white_board);
            }

            // pawn's 2 tile ahead move, only first move and if there's no ally piece there already
            if ((from & FileAndRank.RANK_2) != 0L) {
                pawn_valid_moves |= ((from >>> 16) & ~black_board);
            }

            // get curent attacks and take intersection with opponent pieces to find valid attack tiles
            long attacks_left = (from & ~FileAndRank.RANK_1 & ~FileAndRank.FILE_H)>>>7; // capture left
            long attacks_right = (from & ~FileAndRank.RANK_1 & ~FileAndRank.FILE_A)>>>9; // capture right
            pawn_valid_moves |= ((attacks_left|attacks_right) & white_board);
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
        long valid_pawn_attacks = 0L;
        // white
        if (side == 0) {
            valid_pawn_attacks |= (attack_holder.pawn_attacks[0] & black_board);
        //black
        } else {
            valid_pawn_attacks |= (attack_holder.pawn_attacks[1] & white_board);
        }
        return valid_pawn_attacks;
    }
}