package entities.variouscalculators;
import entities.locations.LocationBitboard;

/**
 * GameStateCalculator is an abstract class that provides common utility methods
 * for evaluating different game states in chess, such as checkmate and stalemate.
 * It uses utility methods from ActualValidCalculator and CheckCalculator for
 * this purpose.
 */
public abstract class GameStateCalculator {

    // Utility for calculating valid moves for a chess piece
    protected final ActualValidCalculator validCalculator;
    // Utility to determine if a given side is in check
    protected final CheckCalculator checkCalc;

    /**
     * Constructor for GameStateCalculator.
     *
     * @param validCalculator   Utility to calculate valid moves for a piece.
     * @param checkCalc         Utility to check if a side is in check.
     */
    public GameStateCalculator(ActualValidCalculator validCalculator, CheckCalculator checkCalc) {
        this.validCalculator = validCalculator;
        this.checkCalc = checkCalc;
    }

    /**
     * Checks if a given side has no valid moves in the current position.
     *
     * @param side         True for white and false for black.
     * @param currentBoard The current position of pieces on the board.
     * @return True if the side has no valid moves, otherwise false.
     */
    protected boolean hasNoValidMoves(boolean side, LocationBitboard currentBoard) {
        for (int i = 0; i < 64; i++) {
            long position = 1L << i;

            // Check if there's a piece for the side at the given position
            boolean isPiecePresent = side ?
                    (position & (currentBoard.whitePawn[0] | currentBoard.whiteRook[0] | currentBoard.whiteKnight[0] |
                            currentBoard.whiteBishop[0] | currentBoard.whiteQueen[0] | currentBoard.whiteKing[0])) != 0L :
                    (position & (currentBoard.blackPawn[0] | currentBoard.blackRook[0] | currentBoard.blackKnight[0] |
                            currentBoard.blackBishop[0] | currentBoard.blackQueen[0] | currentBoard.blackKing[0])) != 0L;

            if (isPiecePresent) {
                // Get valid moves for this piece
                long validMoves = validCalculator.actualValidMoves(position, side, currentBoard);

                // If there's at least one valid move
                if (validMoves != 0L) {
                    return false;
                }
            }
        }
        // Return true if none of the pieces for the side have any valid moves
        return true;
    }
}