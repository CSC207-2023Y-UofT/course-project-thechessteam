package Use_Cases;

import Entities.VariousCalculators.ActualValidCalculator;
import Entities.ChessGame;
import Entities.Locations.LocationBitboard;
import Presenter_Interface.PresenterInterface;

public class HighlightValid {
    private PresenterInterface presenter;
    private ChessGame currentGame;
    private ActualValidCalculator actualValidCalc;

    public HighlightValid(ChessGame currentGame, ActualValidCalculator actualValidCalc) {
        this.currentGame = currentGame;
        this.actualValidCalc = actualValidCalc;
    }

    public void set_presenter(PresenterInterface presenter) {
        this.presenter = presenter;
    }
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