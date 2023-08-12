package entities.constants;

public class PreCalculatedAttacks {

    // [color][square number] for all arrays in entities.Constants.PreCalculatedAttacks
    // 0 for White; 1 for Black
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

    private static long whitePawnAttacks(long from) {
        long attacksRight = (from & ~FileAndRank.RANK_8 & ~FileAndRank.FILE_A)<<7; // capture right
        long attacksLeft = (from & ~FileAndRank.RANK_8 & ~FileAndRank.FILE_H)<<9; // capture left
        return attacksRight | attacksLeft;
    }

    private static long blackPawnAttacks(long from) {
        // Need to use >>> to fill 0s on the left.
        // Java uses signed two's complement integer to represent long data type.
        long attacksLeft = (from & ~FileAndRank.RANK_1 & ~FileAndRank.FILE_H)>>>7; // capture left
        long attacksRight = (from & ~FileAndRank.RANK_1 & ~FileAndRank.FILE_A)>>>9; // capture right
        return attacksLeft | attacksRight;
    }

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
