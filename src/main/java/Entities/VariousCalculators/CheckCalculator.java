package Entities.VariousCalculators;

import Entities.Locations.LocationBitboard;

/**
 * Class responsible for determining if a given side (White or Black) is in check.
 *
 * By using the calculators for each piece type, it calculates the combined attack coverage
 * of the opponent's pieces and checks if the king of the given side is under attack.
 */
public class CheckCalculator {
    private final Calculators calculators;

    /**
     * Constructs a CheckCalculator with the given calculators.
     *
     * @param calculators The Calculators instance containing the piece calculators.
     */
    public CheckCalculator(Calculators calculators) {
        this.calculators = calculators;
    }

    /**
     * Determines if the given side is in check.
     *
     * This method calculates the combined attack coverage of all opponent's pieces
     * (excluding the king) and checks if the king of the given side is within that coverage.
     *
     * @param side The side to check for check condition (true for White, false for Black).
     * @param board The current LocationBitboard representing the game state.
     * @return True if the side is in check, false otherwise.
     */
    public boolean is_in_check(boolean side, LocationBitboard board) {
        long opponentAttacks = 0L;

        // Get all valid moves for each type of opponent piece and combine them to get the attack coverage
        // Replace the dummy functions with actual implementation
        opponentAttacks |= calculators.pawnCalculator.attack_coverage(!side, board);
        opponentAttacks |= calculators.rookCalculator.attack_coverage(!side, board);
        opponentAttacks |= calculators.knightCalculator.attack_coverage(!side, board);
        opponentAttacks |= calculators.bishopCalculator.attack_coverage(!side, board);
        opponentAttacks |= calculators.queenCalculator.attack_coverage(!side, board);

        // No need to include the opponent's king in the attack coverage

        // Get the location of the king
        long kingLocation = side ? board.whiteKing[0] : board.blackKing[0];

        // Check if the king is in the attack coverage
        return (opponentAttacks & kingLocation) != 0;
    }
}
