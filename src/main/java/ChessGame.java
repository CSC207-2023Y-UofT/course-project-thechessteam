public class ChessGame {
    // A class to represent the game board we are using. Keeps track whose turn it is as well.

    // Various useful attributes. Use getters to access them.
    private static LocationBitboard currentBoard = new LocationBitboard();
    private static boolean turn = true; // true for White's turn, false for Black's turn

    // ----------------------------------------------------------------------------------------------------------
    // Getter methods
    public static LocationBitboard getCurrentBoard() { // returns the board this ChessGame is using
        return currentBoard;
    }
    public static boolean getTurn() { return turn; }

    // ----------------------------------------------------------------------------------------------------------

    // Use this to change turn from White's turn to Black's turn, or vice versa
    public static void change_turn() {
        turn = !turn;
    }

    // Start a new game by setting a new location bitboard
    public static void new_game() {
        currentBoard = new LocationBitboard();
        turn = true; // Make sure to start with White's turn
    }
}
