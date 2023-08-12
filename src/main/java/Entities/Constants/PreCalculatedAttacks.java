package Entities.Constants;

/**
 * The PreCalculatedAttacks class precomputes and stores the attack patterns for different chess pieces.
 * It provides three different sets of arrays for pawn, knight, and king attack patterns, separately for each square on the board.
 * The class also encapsulates methods to calculate the attack patterns for white and black pawns, knights, and kings.
 *
 * The class relies on constants defined in FileAndRank to perform bitwise manipulations to determine the attack patterns.
 */
public class PreCalculatedAttacks {
    public static long[][] pawn_attacks = new long[2][64];
    static {
        for (int i = 0; i < 64; i++) {
            pawn_attacks[0][i] = white_pawn_attacks(1L << i);
            pawn_attacks[1][i] = black_pawn_attacks(1L << i);
        }
    }
    public static long[] knight_attacks = new long[64];
    static {
        for (int i = 0; i < 64; i++) {
            knight_attacks[i] = knight_attacks(1L << i);
        }
    }
    public static long[] king_attacks = new long[64];
    static {
        for (int i = 0; i < 64; i++) {
            king_attacks[i] = king_attacks(1L << i);
        }
    }

    /**
     * Calculates the attack pattern for a white pawn at a given location.
     *
     * @param from Position of the pawn represented as a bitboard.
     * @return Bitboard representing the attack pattern.
     */
    private static long white_pawn_attacks(long from) {
        long attacks_right = (from & ~FileAndRank.RANK_8 & ~FileAndRank.FILE_A)<<7; // capture right
        long attacks_left = (from & ~FileAndRank.RANK_8 & ~FileAndRank.FILE_H)<<9; // capture left
        return attacks_right|attacks_left;
    }

    /**
     * Calculates the attack pattern for a black pawn at a given location.
     *
     * @param from Position of the pawn represented as a bitboard.
     * @return Bitboard representing the attack pattern.
     */
    private static long black_pawn_attacks(long from) {
        // Need to use >>> to fill 0s on the left.
        // Java uses signed two's complement integer to represent long data type.
        long attacks_left = (from & ~FileAndRank.RANK_1 & ~FileAndRank.FILE_H)>>>7; // capture left
        long attacks_right = (from & ~FileAndRank.RANK_1 & ~FileAndRank.FILE_A)>>>9; // capture right
        return attacks_left|attacks_right;
    }

    /**
     * Calculates the attack pattern for a knight at a given location.
     *
     * @param from Position of the knight represented as a bitboard.
     * @return Bitboard representing the attack pattern.
     */
    private static long knight_attacks(long from) {
        long attacks;
        int bit_location = Long.numberOfTrailingZeros(from);

        if (bit_location > 18) {
            attacks = FileAndRank.KNIGHT_SPAN << (bit_location - 18);
        }
        else {
            attacks = FileAndRank.KNIGHT_SPAN >>> (18 - bit_location);
        }

        // Cut off "wrappings" of moves
        if (bit_location % 8 < 4) {
            attacks &= ~FileAndRank.FILE_GH;
        }
        else {
            attacks &= ~FileAndRank.FILE_AB;
        }
        return attacks;
    }

    /**
     * Calculates the attack pattern for a king at a given location.
     *
     * @param from Position of the king represented as a bitboard.
     * @return Bitboard representing the attack pattern.
     */
    private static long king_attacks(long from) {
        long attacks;
        int bit_location = Long.numberOfTrailingZeros(from);

        if (bit_location > 9) {
            attacks = FileAndRank.KING_SPAN << (bit_location - 9);
        }
        else {
            attacks = FileAndRank.KING_SPAN >>> (9 - bit_location);
        }

        // Cut off "wrappings" of moves
        if (bit_location % 8 < 4) {
            attacks &= ~FileAndRank.FILE_GH;
        }
        else {
            attacks &= ~FileAndRank.FILE_AB;
        }
        return attacks;
    }
}
