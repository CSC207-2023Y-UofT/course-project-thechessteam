package UseCases;

import Entities.ChessGame;
import Entities.LocationBitboard;
import RenamePackage.TestForBitboards;

public class MovePiece {
    // Use case class for moving pieces, including castling.
    // Returns true if there's a piece at start and end is a valid move of that piece.
    public static void move_piece(long start, long end) {

        boolean turn = ChessGame.getTurn();
        LocationBitboard currentBoard = ChessGame.getCurrentBoard();
        boolean moveSuccessful = move_piece_helper(currentBoard, start, end, turn);

        System.out.println(moveSuccessful);
        System.out.println();

        if (moveSuccessful) {
            // TODO Presenter.update_UI(currentBoard);
            ChessGame.change_turn();
        }
    }

    public static boolean move_piece_helper(LocationBitboard currentBoard, long start, long end, boolean turn) {
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
            if ((end & ActualValidCalculator.actual_valid_moves(start, turn, currentBoard)) != 0) {
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