package usecases;

import entities.variouscalculators.ActualValidCalculator;
import entities.ChessGame;
import entities.locations.LocationBitboard;
import presenterinterface.PresenterInterface;

/**
 * HighlightValid is responsible for calculating and displaying the valid moves of a selected piece on the chessboard.
 * It works with the ActualValidCalculator class to find the legal moves for the selected piece and instructs the presenter
 * to highlight those locations on the board.
 *
 */
public class HighlightValid {
    private PresenterInterface presenter;
    private final ChessGame currentGame;
    private final ActualValidCalculator actualValidCalc;

    /**
     * Constructs a new HighlightValid object.
     *
     * @param currentGame      The current ChessGame object containing the game state.
     * @param actualValidCalc  The ActualValidCalculator object to determine the actual valid moves.
     */
    public HighlightValid(ChessGame currentGame, ActualValidCalculator actualValidCalc) {
        this.currentGame = currentGame;
        this.actualValidCalc = actualValidCalc;
    }

    /**
     * Sets the PresenterInterface object to allow communication with the view.
     *
     * @param presenter The PresenterInterface object.
     */
    public void setPresenter(PresenterInterface presenter) {
        this.presenter = presenter;
    }

    /**
     * Creates and displays the highlights for the valid moves of a selected piece.
     * The method checks if the selected location corresponds to the current player's piece and calculates the valid moves.
     * The highlights are then set in the view via the presenter.
     *
     * @param from     Bitboard representing the location of the selected piece.
     * @return         True if the highlight was successfully created; false if the selected location is invalid.
     */
    public boolean createHighlight(long from) {
        LocationBitboard currentBoard = currentGame.getCurrentBoard();
        boolean turn = currentGame.getTurn();
        if (turn) { // White's turn
            if ((from & currentBoard.getWhiteLocations()) == 0L) {
                return false;
            }
        } else {
            if ((from & currentBoard.getBlackLocations()) == 0L) {
                return false;
            }
        }
        long highlight = actualValidCalc.actualValidMoves(from, turn, currentBoard);
        presenter.setHighlight(highlight);
        return true;
    }
}