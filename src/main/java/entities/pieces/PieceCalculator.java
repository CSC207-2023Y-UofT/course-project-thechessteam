package entities.pieces;

import entities.locations.LocationBitboard;

/**
 * This interface is used to calculate valid moves and attack coverage for a chess piece on a chess board.
 * Used in classes that represent specific types of chess pieces (entities.pieces.Pawn, entities.pieces.Rook, entities.pieces.Knight, entities.pieces.Bishop, entities.pieces.Queen, entities.pieces.King)
 * The group should be implementing this interface and provide appropriate implementations for the methods.
 * Note: the entities.pieces.Calculator assumes that the player is not in Check.
 * Note: The entities.pieces.King class that implements this does not use the attack coverage for opponents pieces.
 *
 */
public interface PieceCalculator {
    /**
     * Calculates all valid moves a player can make for the piece at the given location.
     *
     * @param from A bitboard with a single bit at the location of the piece and 0-bits elsewhere (long)
     * @param side The player's side, represented as a boolean (true for White, false for Black)
     * @param board The current state of the chess board. (entities.Locations.LocationBitboard object)
     * @return A bitboard with 1-bits at the locations of valid moves and 0-bits elsewhere. (long)
     */
    long validMoves(long from, boolean side, LocationBitboard board);

    /**
     * Calculates the attack coverage of the chess piece type that is implementing this interface.
     * Note that for pawns, only diagonal moves are considered attacks
     * This method is used to check for chess check situations.
     *
     * @param side The players side, represented as a boolean (true for White, false for Black).
     * @param board The current state of the chess board
     * @return A bitboard with 1-bits at the locations that the piece can attack and 0-bits otherwise
     */
    long attackCoverage(boolean side, LocationBitboard board);

    // entities.pieces.Pawn, entities.pieces.Rook, entities.pieces.Knight, entities.pieces.Bishop, entities.pieces.Queen, and entities.pieces.King Class
    // implement entities.pieces.Calculator.

    // entities.pieces.Pawn, entities.pieces.Knight, and entities.pieces.King use entities.Constants.PreCalculatedAttacks in their methods.
}