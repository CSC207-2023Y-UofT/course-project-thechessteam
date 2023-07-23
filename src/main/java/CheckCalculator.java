public class CheckCalculator {

    public boolean is_in_check(int side, LocationBitboard board) {
        long opponentAttacks = 0L;
        int opponentSide = 1 - side;

        // Get all valid moves for each type of opponent piece and combine them to get the attack coverage
        // Replace the dummy functions with actual implementation
        opponentAttacks |= Pawn.attack_coverage(opponentSide, board);
        opponentAttacks |= Rook.attack_coverage(opponentSide, board);
        opponentAttacks |= Knight.attack_coverage(opponentSide, board);
        opponentAttacks |= Bishop.attack_coverage(opponentSide, board);
        opponentAttacks |= Queen.attack_coverage(opponentSide, board);

        // No need to include the opponent's king in the attack coverage

        // Get the location of the king
        long kingLocation = side == 0 ? board.whiteKing[0] : board.blackKing[0];

        // Check if the king is in the attack coverage
        return (opponentAttacks & kingLocation) != 0;
    }
}