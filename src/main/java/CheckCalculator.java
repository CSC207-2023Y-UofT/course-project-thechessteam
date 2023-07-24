public class CheckCalculator {

    public static boolean is_in_check(int side, LocationBitboard board) {
        long opponentAttacks = 0L;
        int opponentSide = 1 - side;

        // Get all valid moves for each type of opponent piece and combine them to get the attack coverage
        // Replace the dummy functions with actual implementation
        opponentAttacks |= Calculators.pawnCalculator.attack_coverage(opponentSide, board);
        opponentAttacks |= Calculators.rookCalculator.attack_coverage(opponentSide, board);
        opponentAttacks |= Calculators.knightCalculator.attack_coverage(opponentSide, board);
        opponentAttacks |= Calculators.bishopCalculator.attack_coverage(opponentSide, board);
        opponentAttacks |= Calculators.queenCalculator.attack_coverage(opponentSide, board);

        // No need to include the opponent's king in the attack coverage

        // Get the location of the king
        long kingLocation = side == 0 ? board.whiteKing[0] : board.blackKing[0];

        // Check if the king is in the attack coverage
        return (opponentAttacks & kingLocation) != 0;
    }
}