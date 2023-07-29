package UseCases;

import Entities.ChessGame;
import Entities.LocationBitboard;
import RenamePackage.TestForBitboards;

public class MovePiece {
    // Use case class for moving pieces, including castling.
    // Returns true if there's a piece at start and end is a valid move of that piece.
    public static void move_piece(long start, long end) {
        TestForBitboards TestForBitboards = null;
        TestForBitboards.print_2d_chessboard(TestForBitboards.array_representation(start));
        System.out.println();
        TestForBitboards.print_2d_chessboard(TestForBitboards.array_representation(end));
        System.out.println();
        boolean turn = ChessGame.getTurn();
        // Change turn to integer representation (Refactor later)
        // int color = (turn) ? 0 : 1;
        LocationBitboard currentBoard = ChessGame.getCurrentBoard();
        boolean moveSuccessful = move_piece_helper(currentBoard, start, end, turn);
        System.out.println(moveSuccessful);
        System.out.println();
        if (moveSuccessful) {
            // Presenter.update_UI(currentBoard);
            ChessGame.change_turn();
        }
    }

    public static boolean move_piece_helper(LocationBitboard currentBoard, long start, long end, boolean turn) {
        boolean canMove = false;
        if (turn) { // White's turn
            for (long[] pieceType : currentBoard.getWhitePieces()) {
                if ((pieceType[0] & start) != 0L) { // if from is in White's bitboard of this pieceType
                    canMove = true;
                    break;
                }
            }
        } else {
            for (long[] pieceType : currentBoard.getBlackPieces()) {
                if ((pieceType[0] & start) != 0L) { // if from is in Black's bitboard of this pieceType
                    canMove = true;
                    break;
                }
            }
        }
        if (canMove) {
            if ((end & ActualValidMove.actual_valid_moves(start, turn, currentBoard)) != 0) { // end is a valid move
                return currentBoard.move_piece(start, end, turn);
            } else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}