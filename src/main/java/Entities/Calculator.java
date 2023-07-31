package Entities;

/**
 * This interface is used to calculate valid moves and attack coverage for a chess piece on a chess board.
 * Used in classes that represent specific types of chess pieces (Entities.Pawn, Entities.Rook, Entities.Knight, Entities.Bishop, Entities.Queen, Entities.King)
 * The group should be implementing this interface and provide appropriate implementations for the methods.
 * Note: the Entities.Calculator assumes that the player is not in Check.
 * Note: The Entities.King class that implements this does not use the attack coverage for opponents pieces.
 *
 */
public interface Calculator {
    /**
     * Calculates all valid moves a player can make for the piece at the given location.
     *
     * @param from A bitboard with a single bit at the location of the piece and 0-bits elsewhere (long)
     * @param side The player's side, represented as a boolean (true for White, false for Black)
     * @param board The current state of the chess board. (Entities.LocationBitboard object)
     * @return A bitboard with 1-bits at the locations of valid moves and 0-bits elsewhere. (long)
     */
    long valid_moves(long from, boolean side, LocationBitboard board);

    /**
     * Calculates the attack coverage of the chess piece type that is implementing this interface.
     * Note that for pawns, only diagonal moves are considered attacks
     * This method is used to check for chess check situations.
     *
     * @param side The players side, represented as a boolean (true for White, false for Black).
     * @param board The current state of the chess board
     * @return A bitboard with 1-bits at the locations that the piece can attack and 0-bits otherwise
     */
    long attack_coverage(boolean side, LocationBitboard board);

    // Entities.Pawn, Entities.Rook, Entities.Knight, Entities.Bishop, Entities.Queen, and Entities.King Class
    // implement Entities.Calculator.

    // Entities.Pawn, Entities.Knight, and Entities.King use Entities.PreCalculatedAttacks in their methods.



}