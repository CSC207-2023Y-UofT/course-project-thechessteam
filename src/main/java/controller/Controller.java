package controller;

import entities.locations.LocationBitboard;
import entities.variouscalculators.CheckmateCalculator;
import entities.variouscalculators.StalemateCalculator;
import usecases.HighlightValid;
import usecases.MovePiece;
import usecases.NewGame;

public class Controller {
    private final MovePiece movePieceClass;
    private final HighlightValid highlightValidClass;
    private final NewGame newGameClass;
    private final CheckmateCalculator checkmateCalculator;
    private final StalemateCalculator stalemateCalculator;

    public Controller(MovePiece movePiece, HighlightValid highlightValid, NewGame newGameClass,
                      CheckmateCalculator checkmateCalculatorClass, StalemateCalculator stalemateCalculatorClass) {
        this.movePieceClass = movePiece;
        this.highlightValidClass = highlightValid;
        this.newGameClass = newGameClass;
        this.checkmateCalculator = checkmateCalculatorClass;
        this.stalemateCalculator = stalemateCalculatorClass;
    }

    // Takes in two clicks and pass it to a MovePiece class.
    // Precondition: twoClicks is an integer array of length 2.
    public void processTwoClicks(int[] twoClicks) {
        long from = 1L << twoClicks[0];
        long to = 1L << twoClicks[1];

        movePieceClass.movePiece(from, to);
        checkForCheckmate();
        checkForStalemate();
    }

    // Returns false if there is no piece at clickIndex belonging to the player of current turn.
    public boolean processHighlight(int clickIndex) {
        long clicked = 1L << clickIndex;
        return highlightValidClass.createHighlight(clicked);
    }

    // Calls NewGame use case class.
    public void startNewGame() {
        newGameClass.startNewGame();
    }

    private void checkForCheckmate() {
        LocationBitboard currentBoard = movePieceClass.getCurrentBoard();
        boolean currentSide = movePieceClass.getCurrentTurn();
        boolean isCheckmate = checkmateCalculator.isCheckmate(currentSide, currentBoard);
        if (isCheckmate) {
            movePieceClass.handleGameOver();
        }
    }

    private void checkForStalemate() {
        LocationBitboard currentBoard = movePieceClass.getCurrentBoard();
        boolean currentSide = movePieceClass.getCurrentTurn();
        boolean isStalemate = stalemateCalculator.isStalemate(currentSide, currentBoard);
        if (isStalemate) {
            movePieceClass.handleStalemate();
        }
    }
}
