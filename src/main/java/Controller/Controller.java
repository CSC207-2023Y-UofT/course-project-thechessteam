package Controller;

import Use_Cases.HighlightValid;
import Use_Cases.MovePiece;
import Use_Cases.NewGame;

/**
 * Controller class serves as a coordinator between different use case classes.
 * It acts as a bridge, passing information between the classes involved in handling game logic.
 */
public class Controller {
    private MovePiece movePieceClass;
    private HighlightValid highlightValidClass;
    private NewGame newGameClass;

    /**
     * Constructor to initialize the Controller class with the relevant use case classes.
     *
     * @param movePiece       Instance of the MovePiece class.
     * @param highlightValid  Instance of the HighlightValid class.
     * @param newGameClass    Instance of the NewGame class.
     */
    public Controller(MovePiece movePiece, HighlightValid highlightValid, NewGame newGameClass) {
        this.movePieceClass = movePiece;
        this.highlightValidClass = highlightValid;
        this.newGameClass = newGameClass;
    }

    /**
     * Processes two clicks and passes them to the MovePiece class to perform the move.
     * Precondition: twoClicks is an integer array of length 2.
     *
     * @param twoClicks An array containing the indexes of the from and to positions.
     */
    public void process_two_clicks(int[] twoClicks) {
        long from = 1L << twoClicks[0];
        long to = 1L << twoClicks[1];

        movePieceClass.move_piece(from, to);
    }

    /**
     * Processes a click to check if there is a piece at the clicked index belonging to the player of the current turn.
     *
     * @param clickIndex The index of the clicked position.
     * @return true if there is a valid piece, false otherwise.
     */
    public boolean process_highlight(int clickIndex) {
        long clicked = 1L << clickIndex;
        return highlightValidClass.create_highlight(clicked);
    }

    /**
     * Starts a new game by calling the NewGame use case class.
     */
    public void start_new_game() {
        newGameClass.start_new_game();
    }
}