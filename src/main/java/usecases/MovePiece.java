package usecases;

import entities.variouscalculators.ActualValidCalculator;
import entities.ChessGame;
import entities.locations.LocationBitboard;
import presenterinterface.PresenterInterface;

public class MovePiece {
    // Use case class for moving pieces, including castling.
    // Returns true if there's a piece at start and end is a valid move of that piece.
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

    public  boolean movePieceHelper(LocationBitboard currentBoard, long start, long end, boolean turn) {
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
            // Check if end is a valid move
            if ((end & actualValidCalc.actualValidMoves(start, turn, currentBoard)) != 0) {
                return currentBoard.movePiece(start, end, turn);
            } else { // If end is not a valid move, do not move piece.
                return false;
            }
        }
        else { // We do not move piece.
            return false;
        }
    }
}