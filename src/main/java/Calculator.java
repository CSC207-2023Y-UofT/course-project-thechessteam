/**
 * This interface is used to calculate valid moves and attack coverage for a chess piece on a chess board.
 * Classes that represent specific types of chess pieces (e.g. Pawn, Rook, Knight, Bishop, Queen, King)
 * should implement this interface and provide appropriate implementations for the methods.
 */
public interface Calculator {
    // side = 0 to represent White
    // side = 1 to represent Black
    // Calculates all valid moves a player can make for the piece at from.
    // from is a bitboard with 1 at the location of the piece and 0s for the rest.
    // Returns a bitboard with 1s at valid moves and 0s for the rest.

    /**
     * Calculates all valid moves a player can make for the piece at the given location.
     *
     * @param from A bitboard with a single 1-bit at the location of the piece and 0-bits elsewhere.
     * @param side The player's side, represented as an integer (0 for White, 1 for Black).
     * @param board The current state of the chess board.
     * @return A bitboard with 1-bits at the locations of valid moves and 0-bits elsewhere.
     */
    long valid_moves(long from, int side, LocationBitboard board);

    // Calculates the attack coverage of the piece type inheriting Calculator interface.
    // Note: It is attack coverage so only diagonal moves of pawns are attacks.
    // Used to check for check.
    // For example, for Pawn class, it could use the bit "or" operation to combine bitboards in pawn_attacks
    // Returns a bitboard with 1s at "can attack" locations and 0s for the rest.

    /**
     * Calculates the attack coverage of the chess piece type that is implementing this interface.
     * Note that for pawns, only diagonal moves are considered attacks.
     * This method is used to check for check situations.
     *
     * @param side The player's side, represented as an integer (0 for White, 1 for Black).
     * @param board The current state of the chess board.
     * @return A bitboard with 1-bits at the locations that the piece can attack and 0-bits elsewhere.
     */
    long attack_coverage(int side, LocationBitboard board);

    // TODO Create Pawn, Rook, Knight, Bishop, Queen, and King Class and have them implement Calculator.
    // TODO Implement the methods accordingly.
    // TODO Use PreCalculatedAttacks accordingly.
}
