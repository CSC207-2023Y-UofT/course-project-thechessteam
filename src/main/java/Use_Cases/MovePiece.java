package Use_Cases;

import Entities.VariousCalculators.ActualValidCalculator;
import Entities.ChessGame;
import Entities.Locations.LocationBitboard;
import Presenter_Interface.PresenterInterface;

/**
 * A use case class that handles the operations related to moving chess pieces.
 */
public class MovePiece {

    // Interface to communicate with the presenter layer
    private PresenterInterface presenter;

    // Current game instance
    private final ChessGame currentGame;

    // Utility to check for valid moves
    private final ActualValidCalculator actualValidCalc;

    /**
     * Constructor for the MovePiece use case.
     *
     * @param currentGame       The current game instance.
     * @param actualValidCalc   The utility to check for valid moves.
     */
    public MovePiece(ChessGame currentGame, ActualValidCalculator actualValidCalc) {
        this.currentGame = currentGame;
        this.actualValidCalc = actualValidCalc;
    }

    /**
     * Sets the presenter instance for this use case.
     *
     * @param presenter   The presenter instance.
     */
    public void set_presenter(PresenterInterface presenter) {
        this.presenter = presenter;
    }

    /**
     * Handles the movement of a chess piece from a start position to an end position.
     *
     * @param start   The starting position of the piece.
     * @param end     The ending position of the piece.
     */
    public void move_piece(long start, long end) {
        boolean turn = currentGame.getTurn();
        LocationBitboard currentBoard = currentGame.getCurrentBoard();
        boolean moveSuccessful = move_piece_helper(currentBoard, start, end, turn);

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
     * Fetches the current board state.
     *
     * @return The current board state.
     */
    public LocationBitboard getCurrentBoard() {
        return this.currentGame.getCurrentBoard();
    }

    /**
     * Fetches the current player turn.
     *
     * @return True if it's white's turn, false otherwise.
     */
    public boolean getCurrentTurn() {
        return this.currentGame.getTurn();
    }

    /**
     * Handles game-over scenarios.
     */
    public void handleGameOver() {
        presenter.notifyGameOver();
        // You can add any other game-over related logic here, if needed.
    }

    /**
     * Handles stalemate scenarios.
     */
    public void handleStalemate() {
        presenter.notifyGameOver();
        // You can add any other stalemate-related logic here, if needed.
    }

    /**
     * Assists in the movement of a chess piece, ensuring it adheres to the game rules.
     *
     * @param currentBoard   The current board state.
     * @param start          The starting position of the piece.
     * @param end            The ending position of the piece.
     * @param turn           True if it's white's turn, false otherwise.
     * @return               True if the move is successful, false otherwise.
     */
    private boolean move_piece_helper(LocationBitboard currentBoard, long start, long end, boolean turn) {
        boolean canMove = (turn && (start & currentBoard.getWhiteLocations()) != 0L)
                || (!turn && (start & currentBoard.getBlackLocations()) != 0L);
        if (canMove) {
            // Check if the desired end position is a valid move
            if ((end & actualValidCalc.actual_valid_moves(start, turn, currentBoard)) != 0) {
                return currentBoard.move_piece(start, end, turn);
            }
        }
        return false;
    }
}
