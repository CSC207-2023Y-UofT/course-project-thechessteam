// pawn class
public final class Pawn extends Piece implements Calculator{
    // TODO add attributes
    public long valid_moves(long from, int side, LocationBitboard board){
        if side == 0 {
            // pawn's 1 tile ahead move
            long wpawn_default_move_tiles = from >>> 8;

            // pawn's 2 tile ahead move, only first move
            if from in RANK_7 {
                long wpawn_first_move_tiles = from >>> 16;
            }
            // get curent attacks and compare it to game state to find which positions
            // are not occupied by other white pieces, and which positions are occupied
            // by black pieces
            long wpawn_attack_tiles = white_pawn_attacks( long from);

        } else {
            // pawn's 1 tile ahead move
            long bpawn_default_move_tiles = from <<< 8;

            // pawn's 2 tile ahead move, only first move
            long bpawn_first_move_tiles = from >>> 16;

            // get curent attacks and compare it to game state to find which positions
            // are not occupied by other white pieces, and which tiles are occupied
            // by white pieces
            long bpawn_attack_tiles = black_pawn_attacks(long from);

        }
    }
}