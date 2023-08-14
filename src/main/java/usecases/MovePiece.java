package usecases;

import entities.variouscalculators.ActualValidCalculator;
import entities.ChessGame;
import entities.locations.LocationBitboard;
import presenterinterface.PresenterInterface;

public class MovePiece {
    private PresenterInterface presenter;
    private final ChessGame currentGame;
    private final ActualValidCalculator actualValidCalc;

    public MovePiece(ChessGame currentGame, ActualValidCalculator actualValidCalc) {
        this.currentGame = currentGame;
        this.actualValidCalc = actualValidCalc;
    }

    public void setPresenter(PresenterInterface presenter) {
        this.presenter = presenter;
    }

    public LocationBitboard getCurrentBoard() {
        return this.currentGame.getCurrentBoard();
    }

    public boolean getCurrentTurn() {
        return this.currentGame.getTurn();
    }

    public void movePiece(long start, long end) {
        boolean turn = currentGame.getTurn();
        LocationBitboard currentBoard = currentGame.getCurrentBoard();
        boolean moveSuccessful = movePieceHelper(currentBoard, start, end, turn);

        System.out.println(moveSuccessful);
        System.out.println();

        if (moveSuccessful) {
            for (int i = 0; i < 6; i++) {
                presenter.updateLocations(i, currentBoard.getWhitePieces()[i][0], true);
            }
            for (int i = 0; i < 6; i++) {
                presenter.updateLocations(i, currentBoard.getBlackPieces()[i][0], false);
            }
            presenter.setLocation();
            presenter.setTurn(currentGame.changeTurn());
        }
    }

    public boolean movePieceHelper(LocationBitboard currentBoard, long start, long end, boolean turn) {
        boolean canMove = false;
        if (turn) { // White's turn
            if ((start & currentBoard.getWhiteLocations()) != 0L) {
                canMove = true;
            }
        } else {
            if ((start & currentBoard.getBlackLocations()) != 0L) {
                canMove = true;
            }
        }
        if (canMove) {
            if ((end & actualValidCalc.actualValidMoves(start, turn, currentBoard)) != 0) {
                return currentBoard.movePiece(start, end, turn);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void handleGameOver() {
        presenter.notifyGameOver();
        // You can add any other game-over related logic here, if needed.
    }

    public void handleStalemate() {
        presenter.notifyGameOver();
        // You can add any other stalemate-related logic here, if needed.
    }
}
