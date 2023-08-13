package usecases;

import entities.variouscalculators.ActualValidCalculator;
import entities.ChessGame;
import entities.locations.LocationBitboard;
import presenterinterface.PresenterInterface;

public class HighlightValid {
    private PresenterInterface presenter;
    private final ChessGame currentGame;
    private final ActualValidCalculator actualValidCalc;

    public HighlightValid(ChessGame currentGame, ActualValidCalculator actualValidCalc) {
        this.currentGame = currentGame;
        this.actualValidCalc = actualValidCalc;
    }

    public void setPresenter(PresenterInterface presenter) {
        this.presenter = presenter;
    }
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