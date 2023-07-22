/**
 * This class provides constants for initializing a new chess game.
 * Each constant represents the initial position of the corresponding piece on a chessboard, with the white pieces at the bottom and the black pieces at the top!
 * The constants are long values that represent bitboards, with each bit corresponding to a square on the chessboard
 * The bitboards are read from left to right and top to bottom (top left square is the most significant bit) (i.e., top left hand side of chess board)
 */
public class InitialPositions {
    static long WHITE_PAWN =
            0b0000000000000000000000000000000000000000000000001111111100000000L; // The initial position of the white pawns.
    static long WHITE_ROOK =
            0b0000000000000000000000000000000000000000000000000000000010000001L; // The initial position of the white rooks.
    static long WHITE_KNIGHT =
            0b0000000000000000000000000000000000000000000000000000000001000010L;
    static long WHITE_BISHOP =
            0b0000000000000000000000000000000000000000000000000000000000100100L;
    static long WHITE_QUEEN =
            0b0000000000000000000000000000000000000000000000000000000000001000L;
    static long WHITE_KING =
            0b0000000000000000000000000000000000000000000000000000000000010000L;
    static long BLACK_PAWN =
            0b0000000011111111000000000000000000000000000000000000000000000000L;
    static long BLACK_ROOK =
            0b1000000100000000000000000000000000000000000000000000000000000000L;
    static long BLACK_KNIGHT =
            0b0100001000000000000000000000000000000000000000000000000000000000L;
    static long BLACK_BISHOP =
            0b0010010000000000000000000000000000000000000000000000000000000000L;
    static long BLACK_QUEEN =
            0b0000100000000000000000000000000000000000000000000000000000000000L;
    static long BLACK_KING =
            0b0001000000000000000000000000000000000000000000000000000000000000L;
}
