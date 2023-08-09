package Presenter_Interface;

/**
 * PresenterInterface defines the contract for the Presenter class.
 * It declares methods for updating and setting various view components,
 * such as the locations of chess pieces, highlights on the board, and the current turn.
 *
 */
public interface PresenterInterface {

    /**
     * Updates the locations of chess pieces held in an instance variable of the presenter.
     * The information is stored as an array of bitboards in the order: [white, black] [Pawn, Rook, Knight, Bishop, Queen, King].
     *
     * @param j        Index to identify the type of piece.
     * @param bitboard Bitboard representing the piece locations.
     * @param color    Boolean indicating the color of the piece (true for white, false for black).
     */
    void update_locations(int j, long bitboard, boolean color);

    /**
     * Instructs the view to set the chessboard based on the locations of the pieces.
     */
    void set_location();

    /**
     * Instructs the view to highlight specific locations on the chessboard.
     *
     * @param highlight Bitboard representing the locations to be highlighted.
     */
    void set_highlight(long highlight);

    /**
     * Instructs the view to display the current turn.
     *
     * @param currentTurn Boolean indicating the current turn (true for white, false for black).
     */
    void set_turn(boolean currentTurn);
}
