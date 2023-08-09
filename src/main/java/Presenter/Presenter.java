package Presenter;


import Presenter_Interface.PresenterInterface;
import View_Interface.ViewInterface;

/**
 * The Presenter class implements the PresenterInterface and acts as an intermediary
 * between the View and the Model. It handles the updating of the view when changes
 * to the data model occur.
 */
public class Presenter implements PresenterInterface {
    private ViewInterface view;
    private final long[][] pieceLocations = new long[2][6];
    public Presenter() {}

    /**
     * Sets the view interface.
     *
     * @param view Reference to the view interface.
     */
    public void set_view(ViewInterface view) {
        this.view = view;
    }

    /**
     * Updates the locations of the chess pieces.
     *
     * @param j        Index to identify the type of piece.
     * @param bitboard Bitboard representing the piece locations.
     * @param color    Boolean indicating the color of the piece (true for white, false for black).
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
     * Notifies the view to set the chessboard based on the piece locations.
     */
    @Override
    public void set_location() {
        view.setBoard(pieceLocations);
    }

    /**
     * Notifies the view to highlight specific locations on the board.
     *
     * @param highlight Bitboard representing the locations to be highlighted.
     */
    @Override
    public void set_highlight(long highlight) {
        view.setHighlights(highlight);
    }

    /**
     * Notifies the view to display the current turn.
     *
     * @param currentTurn Boolean indicating the current turn (true for white, false for black).
     */
    @Override
    public void set_turn(boolean currentTurn) {
        view.setTurn(currentTurn);
    }
}

