package Use_Cases;

import Entities.VariousCalculators.ActualValidCalculator;
import Entities.ChessGame;
import Entities.Locations.LocationBitboard;
import Presenter_Interface.PresenterInterface;

/**
 * MovePiece is responsible for handling piece movement, including castling, on the chessboard.
 * It works with the ActualValidCalculator class to verify legal moves and updates the current game state accordingly.
 *
 */
public class MovePiece {
    // Use case class for moving pieces, including castling.
    // Returns true if there's a piece at start and end is a valid move of that piece.
    private PresenterInterface presenter;
    private ChessGame currentGame;
    private ActualValidCalculator actualValidCalc;

    /**
     * Constructs a new MovePiece object.
     *
     * @param currentGame      The current ChessGame object containing the game state.
     * @param actualValidCalc  The ActualValidCalculator object to determine the actual valid moves.
     */
    public MovePiece(ChessGame currentGame, ActualValidCalculator actualValidCalc) {
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
     * Moves a piece on the chessboard from the start location to the end location.
     * The method verifies that the move is legal and updates the board accordingly, signaling the view to update via the presenter.
     *
     * @param start    Bitboard representing the start location of the piece to move.
     * @param end      Bitboard representing the end location of the move.
     */
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

    /**
     * Helper method to verify and execute a piece move.
     * Checks if the piece at the start location belongs to the current player and if the end location is a valid move for that piece.
     *
     * @param currentBoard The LocationBitboard object representing the current game board.
     * @param start        Bitboard representing the start location of the piece to move.
     * @param end          Bitboard representing the end location of the move.
     * @param turn         Boolean representing the current turn; true for white, false for black.
     * @return             True if the move was successful; false otherwise.
     */
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