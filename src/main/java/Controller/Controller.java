package Controller;

import Entities.Locations.LocationBitboard;
import Entities.VariousCalculators.CheckmateCalculator;
import Entities.VariousCalculators.StalemateCalculator;
import Use_Cases.HighlightValid;
import Use_Cases.MovePiece;
import Use_Cases.NewGame;

/**
 * The Controller class acts as the intermediary between the game's logic and its interface.
 * It handles input events from the user and processes them using the game's use case classes.
 */
public class Controller {
    private final MovePiece movePieceClass;
    private final HighlightValid highlightValidClass;
    private final NewGame newGameClass;
    private final CheckmateCalculator checkmateCalculator;
    private final StalemateCalculator stalemateCalculator;

    /**
     * Initializes the controller with instances of game's logic classes.
     *
     * @param movePiece                 Instance of MovePiece class to handle piece movements.
     * @param highlightValid            Instance of HighlightValid class to handle highlighting valid moves.
     * @param newGameClass              Instance of NewGame class to initiate a new game.
     * @param checkmateCalculatorClass  Instance of CheckmateCalculator to check for checkmate conditions.
     * @param stalemateCalculatorClass  Instance of StalemateCalculator to check for stalemate conditions.
     */
    public Controller(MovePiece movePiece, HighlightValid highlightValid, NewGame newGameClass,
                      CheckmateCalculator checkmateCalculatorClass, StalemateCalculator stalemateCalculatorClass) {
        this.movePieceClass = movePiece;
        this.highlightValidClass = highlightValid;
        this.newGameClass = newGameClass;
        this.checkmateCalculator = checkmateCalculatorClass;
        this.stalemateCalculator = stalemateCalculatorClass;
    }

    /**
     * Processes two clicks from the user to make a move.
     *
     * @param twoClicks An array of two integers representing the clicked squares.
     */
    public void process_two_clicks(int[] twoClicks) {
        long from = 1L << twoClicks[0];
        long to = 1L << twoClicks[1];

        movePieceClass.move_piece(from, to);
        checkForCheckmate();
        checkForStalemate();
    }

    /**
     * Processes a click for highlighting valid moves.
     *
     * @param clickIndex Index of the clicked square.
     * @return Returns false if there's no piece at the clicked square for the current turn's player.
     */
    public boolean process_highlight(int clickIndex) {
        long clicked = 1L << clickIndex;
        return highlightValidClass.create_highlight(clicked);
    }

    /**
     * Checks for checkmate condition and notifies the MovePiece class if a checkmate is detected.
     */
    private void checkForCheckmate() {
        LocationBitboard currentBoard = movePieceClass.getCurrentBoard();
        boolean currentSide = movePieceClass.getCurrentTurn();
        boolean isCheckmate = checkmateCalculator.is_checkmate(currentSide, currentBoard);
        if (isCheckmate) {
            movePieceClass.handleGameOver();
        }
    }

    /**
     * Checks for stalemate condition and notifies the MovePiece class if a stalemate is detected.
     */
    private void checkForStalemate() {
        LocationBitboard currentBoard = movePieceClass.getCurrentBoard();
        boolean currentSide = movePieceClass.getCurrentTurn();
        boolean isStalemate = stalemateCalculator.is_stalemate(currentSide, currentBoard);
        if (isStalemate) {
            movePieceClass.handleStalemate();
        }
    }

    /**
     * Initiates a new game session.
     */
    public void start_new_game() {
        newGameClass.start_new_game();
    }
}
