package Controller;

import Use_Cases.HighlightValid;
import Use_Cases.MovePiece;
import Use_Cases.NewGame;

public class Controller {
    private final MovePiece movePieceClass;
    private final HighlightValid highlightValidClass;
    private final NewGame newGameClass;
    public Controller(MovePiece movePiece, HighlightValid highlightValid, NewGame newGameClass) {
        this.movePieceClass = movePiece;
        this.highlightValidClass = highlightValid;
        this.newGameClass = newGameClass;
    }

    // Takes in two clicks and pass it to a MovePiece class.
    // Precondition: twoClicks is an integer array of length 2.
    public void process_two_clicks(int[] twoClicks) {
        long from = 1L << twoClicks[0];
        long to = 1L << twoClicks[1];

        movePieceClass.move_piece(from, to);
    }
    // Returns false if there is no piece at clickIndex belonging to the player of current turn.
    public boolean process_highlight(int clickIndex) {
        long clicked = 1L << clickIndex;
        return highlightValidClass.create_highlight(clicked);
    }

    // Calls NewGame use case class. if disposePrevious is true, we want to get rid of current JFrame.
    public void start_new_game() {
        newGameClass.start_new_game();
    }
}