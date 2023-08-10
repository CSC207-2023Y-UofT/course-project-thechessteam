package Use_Cases;

import Entities.VariousCalculators.ActualValidCalculator;
import Entities.ChessGame;
import Entities.Locations.LocationBitboard;
import Presenter_Interface.PresenterInterface;

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
    public void set_presenter(PresenterInterface presenter) {
        this.presenter = presenter;
    }
    public void move_piece(long start, long end) {

        boolean turn = currentGame.getTurn();
        LocationBitboard currentBoard = currentGame.getCurrentBoard();
        boolean moveSuccessful = move_piece_helper(currentBoard, start, end, turn);

        System.out.println(moveSuccessful);
        System.out.println();

        if (moveSuccessful) {
            for (int i = 0; i < 6; i++) {
                presenter.update_locations(i, currentBoard.getWhitePieces()[i][0], true);
            }
            for (int i = 0; i < 6; i++) {
                presenter.update_locations(i, currentBoard.getBlackPieces()[i][0], false);
            }
            presenter.set_location();
            presenter.set_turn(currentGame.change_turn());
        }
    }

    public  boolean move_piece_helper(LocationBitboard currentBoard, long start, long end, boolean turn) {
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
            if ((end & actualValidCalc.actual_valid_moves(start, turn, currentBoard)) != 0) {
                return currentBoard.move_piece(start, end, turn);
            } else { // If end is not a valid move, do not move piece.
                return false;
            }
        }
        else { // We do not move piece.
            return false;
        }
    }
}