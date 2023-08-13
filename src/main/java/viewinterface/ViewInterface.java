package viewinterface;

/**
 * ViewInterface defines the contract for the View layer in a chess application. It provides methods
 * to update the user interface (UI) based on the game's state, including the chessboard, highlighting moves,
 * displaying player turns, and showing points.
 * Implementing classes should provide concrete implementations for all the methods defined in this interface.
 *
 */
public interface ViewInterface {

    /**
     * Sets the representation of the chessboard in the View using a bitboard array.
     * The array should be in the format [white, black] [Pawn, Rook, Knight, Bishop, Queen, King].
     *
     * @param bitboardArray 2D array representing the chessboard state.
     */
    void setBoard(long[][] bitboardArray);

    /**
     * Sets the representation of highlighted squares in the View using a bitboard.
     * This can be used to show legal moves or other important information to the player.
     *
     * @param highlight Bitboard representing the squares to highlight.
     */
    void setHighlights(long highlight);

    /**
     * Sets the current turn in the View. The argument must be true for white's turn and false for black's turn.
     *
     * @param turn True for white's turn, false for black's turn.
     */
    void setTurn(boolean turn);

    /* Unused code for now
    // Sets the white points and black points in view.
    void setWhtPoints(int whtPoints);
    void setBlkPoints(int blkPoints); */
}
