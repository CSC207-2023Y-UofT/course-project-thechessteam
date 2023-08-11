package entities.variouscalculators;

import entities.locations.LocationBitboard;

public class CheckCalculator {
    private final Calculators calculators;
    public CheckCalculator(Calculators calculators) {
        this.calculators = calculators;
    }

    public boolean isInCheck(boolean side, LocationBitboard board) {
        long opponentAttacks = 0L;

        // Get all valid moves for each type of opponent piece and combine them to get the attack coverage
        // Replace the dummy functions with actual implementation
        opponentAttacks |= calculators.pawnCalculator.attackCoverage(!side, board);
        opponentAttacks |= calculators.rookCalculator.attackCoverage(!side, board);
        opponentAttacks |= calculators.knightCalculator.attackCoverage(!side, board);
        opponentAttacks |= calculators.bishopCalculator.attackCoverage(!side, board);
        opponentAttacks |= calculators.queenCalculator.attackCoverage(!side, board);

        // No need to include the opponent's king in the attack coverage

        // Get the location of the king
        long kingLocation = side ? board.whiteKing[0] : board.blackKing[0];

        // Check if the king is in the attack coverage
        return (opponentAttacks & kingLocation) != 0;
    }
}
