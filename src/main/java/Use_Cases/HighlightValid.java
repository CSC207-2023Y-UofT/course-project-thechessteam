package Use_Cases;

import Entities.VariousCalculators.ActualValidCalculator;
import Entities.ChessGame;
import Entities.Locations.LocationBitboard;
import Presenter_Interface.PresenterInterface;

/**
 * HighlightValid is responsible for calculating and displaying the valid moves of a selected piece on the chessboard.
 * It works with the ActualValidCalculator class to find the legal moves for the selected piece and instructs the presenter
 * to highlight those locations on the board.
 *
 */
public class HighlightValid {
    private PresenterInterface presenter;
    private ChessGame currentGame;
    private ActualValidCalculator actualValidCalc;

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
    public void set_presenter(PresenterInterface presenter) {
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
    public boolean create_highlight(long from) {
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
        long highlight = actualValidCalc.actual_valid_moves(from, turn, currentBoard);
        presenter.set_highlight(highlight);
        return true;
    }
}