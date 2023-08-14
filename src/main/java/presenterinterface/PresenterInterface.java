package presenterinterface;

public interface PresenterInterface {

    /**
     * Updates the piece locations stored in an instance variable of the presenter.
     * The arrangement will be an array of bitboards in the order: [white, black] [Pawn, Rook, Knight, Bishop, Queen, King].
     *
     * @param j        The index representing the type of the piece (e.g., Pawn, Rook).
     * @param bitboard The bitboard representing the positions of the pieces.
     * @param color    True for white pieces and false for black pieces.
     */
    void updateLocations(int j, long bitboard, boolean color);

    /**
     * Signals the view to update and display the current piece locations.
     */
    void setLocation();

    /**
     * Signals the view to display the valid move highlights for a selected piece.
     *
     * @param highlight The bitboard representing the positions of the highlights.
     */
    void setHighlight(long highlight);

    /**
     * Updates the view to reflect the current player's turn.
     *
     * @param currentTurn True if it's white's turn, false if it's black's.
     */
    void setTurn(boolean currentTurn);

    /**
     * Signals the view that the game has ended. The view might use this to show an end-of-game dialog or message.
     */
    void notifyGameOver();
}
