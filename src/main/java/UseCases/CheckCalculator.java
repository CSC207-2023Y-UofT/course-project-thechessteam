package UseCases;

import Entities.Calculators;
import Entities.LocationBitboard;

public class CheckCalculator {

    public static boolean is_in_check(boolean side, LocationBitboard board) {
        long opponentAttacks = 0L;

        // Get all valid moves for each type of opponent piece and combine them to get the attack coverage
        // Replace the dummy functions with actual implementation
        opponentAttacks |= Calculators.pawnCalculator.attack_coverage(!side, board);
        opponentAttacks |= Calculators.rookCalculator.attack_coverage(!side, board);
        opponentAttacks |= Calculators.knightCalculator.attack_coverage(!side, board);
        opponentAttacks |= Calculators.bishopCalculator.attack_coverage(!side, board);
        opponentAttacks |= Calculators.queenCalculator.attack_coverage(!side, board);

        // No need to include the opponent's king in the attack coverage

        // Get the location of the king
        long kingLocation = side ? board.whiteKing[0] : board.blackKing[0];

        // Check if the king is in the attack coverage
        return (opponentAttacks & kingLocation) != 0;
    }
}