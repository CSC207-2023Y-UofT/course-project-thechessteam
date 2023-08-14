package presenter;

import presenterinterface.PresenterInterface;
import viewinterface.ViewInterface;


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

    public Presenter() {}

    /**
     * Set the view that this presenter will communicate with.
     *
     * @param view The ViewInterface implementation representing the game's user interface.
     */
    public void setView(ViewInterface view) {
        this.view = view;
    }

    @Override
    public void updateLocations(int j, long bitboard, boolean color) {
        int i = color ? 0 : 1;
        pieceLocations[i][j] = bitboard;
    }

    @Override
    public void setLocation() {
        view.setBoard(pieceLocations);
    }

    @Override
    public void setHighlight(long highlight) {
        view.setHighlights(highlight);
    }

    @Override
    public void setTurn(boolean currentTurn) {
        view.setTurn(currentTurn);
    }

    /**
     * Signals the view that the game has ended.
     */
    public void notifyGameOver() {
        view.drawEndScreen();
    }
}
