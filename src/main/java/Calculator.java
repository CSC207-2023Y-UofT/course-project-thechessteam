public interface Calculator {
    // side = 0 to represent White
    // side = 1 to represent Black

    // Calculates all valid moves a player can make for the piece at from.
    // from is a bitboard with 1 at the location of the piece and 0s for the rest.
    // Returns a bitboard with 1s at valid moves and 0s for the rest.
    long valid_moves(long from, int side, LocationBitboard board);

    // Calculates the attack coverage of the piece type inheriting Calculator interface.
    // Note: It is attack coverage so only diagonal moves of pawns are attacks.
    // Used to check for check.
    // For example, for Pawn class, it could use the bit "or" operation to combine bitboards in pawn_attacks
    // Returns a bitboard with 1s at "can attack" locations and 0s for the rest.
    long attack_coverage(int side, LocationBitboard board);

    // TODO Create Pawn, Rook, Knight, Bishop, Queen, and King Class and have them implement Calculator.
    // TODO Implement the methods accordingly.
    // TODO Use PreCalculatedAttacks accordingly.

}