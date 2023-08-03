package Entities.Pieces;

import Entities.Constants.FileAndRank;
import Entities.Constants.PreCalculatedAttacks;
import Entities.Locations.LocationBitboard;

// pawn class
public class Pawn implements Calculator {
    public long valid_moves(long from, boolean side, LocationBitboard board){

        long pawnValidMoves = 0L;
        int s = Long.numberOfTrailingZeros(from); // index to use Entities.Constants.PreCalculatedAttacks

        // white pawn
        if (side) {
            // pawn's 1 tile ahead move for white, if there's no piece there already.
            // Cannot move forward if on Rank 8.
            if ((from & ~FileAndRank.RANK_8) != 0L) {
                pawnValidMoves |= (from << 8) & ~board.getOccupied();
            }

            // pawn's 2 tile ahead move for white,
            // only if we are moving from Rank 2 and if there's no piece in the square we are moving to.
            if ((from & FileAndRank.RANK_2) != 0L) {
                if ((from << 8 & board.getOccupied()) == 0L) { // if there is no piece blocking path
                    pawnValidMoves |= (from << 16) & ~board.getOccupied();
                }
            }

            // get possible attacks; there must be an opponent's piece to capture.
            pawnValidMoves |= PreCalculatedAttacks.pawn_attacks[0][s] & board.getBlackLocations();

            // Add En passant moves
            if (board.locationBlackPawnMovedTwo() != 0L) {
                pawnValidMoves |= PreCalculatedAttacks.pawn_attacks[0][s] & (board.locationBlackPawnMovedTwo() << 8);
            }
        }
        // black pawn
        else  {
            // pawn's 1 tile ahead move for black, if there's no piece there already.
            // Cannot move forward if on Rank 1.
            if ((from & ~FileAndRank.RANK_1) != 0L) {
                pawnValidMoves |= (from >>> 8) & ~board.getOccupied();
            }

            // pawn's 2 tile ahead move for black,
            // only if we are moving from Rank 7 and if there's no piece in the square we are moving to.
            if ((from & FileAndRank.RANK_7) != 0L) {
                if ((from >>> 8 & board.getOccupied()) == 0L) { // if there is no piece blocking path
                    pawnValidMoves |= (from >>> 16) & ~board.getOccupied();
                }
            }

            // get possible attacks; there must be an opponent's piece to capture.
            pawnValidMoves |= PreCalculatedAttacks.pawn_attacks[1][s] & board.getWhiteLocations();

            // Add En passant moves
            if (board.locationWhitePawnMovedTwo() != 0L) {
                pawnValidMoves |= PreCalculatedAttacks.pawn_attacks[1][s] & (board.locationWhitePawnMovedTwo() >>> 8);
            }
        }

        // return all possible valid moves
        return pawnValidMoves;
    }

    public long attack_coverage(boolean side, LocationBitboard board) {
        long pawnAttacks = 0L;

        // Add up all the attacks in Entities.Constants.PreCalculatedAttacks table that is possible,
        // i.e. there's a pawn in location i.
        if (side) {
            for (int i = 0; i < 64; i++) {
                if ((board.whitePawn[0] & (1L << i)) != 0L) { // Check if there's a white pawn at 1L << i.
                    pawnAttacks |= PreCalculatedAttacks.pawn_attacks[0][i];
                }
            }
        }
        else {
            for (int i = 0; i < 64; i++) {
                if ((board.blackPawn[0] & (1L << i)) != 0L) { // Check if there's a black pawn at 1L << i.
                    pawnAttacks |= PreCalculatedAttacks.pawn_attacks[1][i];
                }
            }
        }
        return pawnAttacks;
    }
}