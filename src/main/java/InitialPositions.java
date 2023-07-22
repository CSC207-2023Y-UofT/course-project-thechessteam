/**
 * This class provides constants for initializing a new chess game.
 * Each constant represents the initial position of the corresponding piece on a chessboard, with the white pieces at the bottom and the black pieces at the top!
 * The constants are long values that represent bitboards, with each bit corresponding to a square on the chessboard
 * The bitboards are read from right to left and top to bottom (top right square is the most significant bit) (i.e., top right hand side of chess board, h8)
 *
 * Chessboard for reference:
 *   Most significant bit (left-most bit) represents h8. Least significant bit (right-most bit) represents a1.
 *   e.g., Black King on square e7 (file 'e', rank '7')
 *   e.g., White King on square e1 (file 'e', rank '8')
 *
 *      [a8 b8 c8 d8 e8 f8 g8 h8]
 *      [a7 b7 c7 d7 e7 f7 g7 h7]
 *      [a6 b6 c6 d6 e6 f6 g6 h6]
 *      [a5 b5 c5 d5 e5 f5 g5 h5]
 *      [a4 b4 c4 d4 e4 f4 g4 h4]
 *      [a3 b3 c3 d3 e3 f3 g3 h3]
 *      [a2 b2 c2 d2 e2 f2 g2 h2]
 *      [a1 b1 c1 d1 e1 f1 g1 h1]
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
