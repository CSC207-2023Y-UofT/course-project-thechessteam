/**
 * This interface is used to calculate valid moves and attack coverage for a chess piece on a chess board.
 * Used in classes that represent specific types of chess pieces (Pawn, Rook, Knight, Bishop, Queen, King)
 * The group should be implementing this interface and provide appropriate implementations for the methods.
 * Note: the Calculator assumes that the player is not in Check.
 * Note: The King class that implements this does not use the attack coverage for opponents pieces.
 *
 */
public interface Calculator {
    /**
     * Calculates all valid moves a player can make for the piece at the given location.
     *
     * @param from A bitboard with a single bit at the location of the piece and 0-bits elsewhere (long)
     * @param side The player's side, represented as an integer (0 for White, 1 for Black)
     * @param board The current state of the chess board. (LocationBitboard object)
     * @return A bitboard with 1-bits at the locations of valid moves and 0-bits elsewhere. (long)
     */
    long valid_moves(long from, int side, LocationBitboard board);

    /**
     * Calculates the attack coverage of the chess piece type that is implementing this interface.
     * Note that for pawns, only diagonal moves are considered attacks
     * This method is used to check for chess check situations.
     *
     * @param side The players side, represented as an integer (0 for White, 1 for Black).
     * @param board The current state of the chess board
     * @return A bitboard with 1-bits at the locations that the piece can attack and 0-bits otherwise
     */
    long attack_coverage(int side, LocationBitboard board);

    // TODO Create Pawn, Rook, Knight, Bishop, Queen, and King Class and have them implement Calculator.
    // TODO Implement the methods accordingly.
    // TODO Use PreCalculatedAttacks accordingly.
