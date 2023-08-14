package entities.constants;

/**
 * The PreCalculatedAttacks class precomputes and stores the attack patterns for different chess pieces.
 * It provides three different sets of arrays for pawn, knight, and king attack patterns, separately for each square on the board.
 * The class also encapsulates methods to calculate the attack patterns for white and black pawns, knights, and kings.
 *
 * The class relies on constants defined in FileAndRank to perform bitwise manipulations to determine the attack patterns.
 */
public class PreCalculatedAttacks {
    public static long[][] pawnAttacks = new long[2][64];
    static {
        for (int i = 0; i < 64; i++) {
            pawnAttacks[0][i] = whitePawnAttacks(1L << i);
            pawnAttacks[1][i] = blackPawnAttacks(1L << i);
        }
    }
    public static long[] knightAttacks = new long[64];
    static {
        for (int i = 0; i < 64; i++) {
            knightAttacks[i] = knightAttacks(1L << i);
        }
    }
    public static long[] kingAttacks = new long[64];
    static {
        for (int i = 0; i < 64; i++) {
            kingAttacks[i] = kingAttacks(1L << i);
        }
    }

    /**
     * Calculates the attack pattern for a white pawn at a given location.
     *
     * @param from Position of the pawn represented as a bitboard.
     * @return Bitboard representing the attack pattern.
     */
    private static long whitePawnAttacks(long from) {
        long attacksRight = (from & ~FileAndRank.RANK_8 & ~FileAndRank.FILE_A)<<7; // capture right
        long attacksLeft = (from & ~FileAndRank.RANK_8 & ~FileAndRank.FILE_H)<<9; // capture left
        return attacksRight | attacksLeft;
    }

    /**
     * Calculates the attack pattern for a black pawn at a given location.
     *
     * @param from Position of the pawn represented as a bitboard.
     * @return Bitboard representing the attack pattern.
     */
    private static long blackPawnAttacks(long from) {
        // Need to use >>> to fill 0s on the left.
        // Java uses signed two's complement integer to represent long data type.
        long attacksLeft = (from & ~FileAndRank.RANK_1 & ~FileAndRank.FILE_H)>>>7; // capture left
        long attacksRight = (from & ~FileAndRank.RANK_1 & ~FileAndRank.FILE_A)>>>9; // capture right
        return attacksLeft | attacksRight;
    }

    /**
     * Calculates the attack pattern for a knight at a given location.
     *
     * @param from Position of the knight represented as a bitboard.
     * @return Bitboard representing the attack pattern.
     */
    private static long knightAttacks(long from) {
        long attacks;
        int bitLocation = Long.numberOfTrailingZeros(from);

        if (bitLocation > 18) {
            attacks = FileAndRank.KNIGHT_SPAN << (bitLocation - 18);
        }
        else {
            attacks = FileAndRank.KNIGHT_SPAN >>> (18 - bitLocation);
        }

        // Cut off "wrappings" of moves
        if (bitLocation % 8 < 4) {
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
    private static long kingAttacks(long from) {
        long attacks;
        int bitLocation = Long.numberOfTrailingZeros(from);

        if (bitLocation > 9) {
            attacks = FileAndRank.KING_SPAN << (bitLocation - 9);
        }
        else {
            attacks = FileAndRank.KING_SPAN >>> (9 - bitLocation);
        }

        // Cut off "wrappings" of moves
        if (bitLocation % 8 < 4) {
            attacks &= ~FileAndRank.FILE_GH;
        }
        else {
            attacks &= ~FileAndRank.FILE_AB;
        }
        return attacks;
    }
}
