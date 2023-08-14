package Presenter;

import Presenter_Interface.PresenterInterface;
import View_Interface.ViewInterface;

/**
 * Implements the PresenterInterface to serve as an intermediary between the view and the model.
 * Handles user actions from the view and updates the view with changes from the model.
 */
public class Presenter implements PresenterInterface {

    // The view interface that the presenter will communicate with.
    private ViewInterface view;

    // 2D array to store piece locations on the board:
    // First dimension differentiates between colors (white or black)
    // and the second dimension differentiates between pieces.
    private final long[][] pieceLocations = new long[2][6];

    /**
     * Default constructor for the Presenter.
     */
    public Presenter() {}

    /**
     * Set the view that this presenter will communicate with.
     *
     * @param view The ViewInterface implementation representing the game's user interface.
     */
    public void set_view(ViewInterface view) {
        this.view = view;
    }

    /**
     * Updates the location of a specific piece in the pieceLocations array.
     *
     * @param j      The index representing the type of the piece (e.g., Pawn, Rook).
     * @param bitboard The bitboard representation of the piece's position.
     * @param color  True if it's a white piece, false if it's black.
     */
    @Override
    public void update_locations(int j, long bitboard, boolean color) {
        int i;
        if (color) {
            i = 0;
        } else {
            i = 1;
        }
        pieceLocations[i][j] = bitboard;
    }

    /**
     * Signals the view to update the displayed board locations based on the pieceLocations array.
     */
    @Override
    public void set_location() {
        view.setBoard(pieceLocations);
    }


    public void notifyGameOver() {
        view.drawEndScreen();
    }

    /**
     * Signals the view to display highlights for valid moves.
     *
     * @param highlight The bitboard representing the positions of the highlights.
     */
    @Override
    public void set_highlight(long highlight) {
        view.setHighlights(highlight);
    }

    /**
     * Updates the view to reflect which player's turn it currently is.
     *
     * @param currentTurn True if it's white's turn, false if it's black's.
     */
    @Override
    public void set_turn(boolean currentTurn) {
        view.setTurn(currentTurn);
    }
}

