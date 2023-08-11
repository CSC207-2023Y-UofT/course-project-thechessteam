package frameworkanddrivers;

import controller.Controller;
import entities.variouscalculators.ActualValidCalculator;
import entities.variouscalculators.Calculators;
import entities.ChessGame;
import entities.variouscalculators.CheckCalculator;
import presenter.Presenter;
import usecases.HighlightValid;
import usecases.MovePiece;
import usecases.NewGame;
import view.BoardUI;
import view.MenuUI;

public class Main {
    public static void main(String[] args) {
        // Entities.
        // Dependencies are non-cyclical and within the same entity layer
        ChessGame game = new ChessGame();
        Calculators calculators = new Calculators();
        CheckCalculator checkCalc = new CheckCalculator(calculators);
        ActualValidCalculator actualValidCalc = new ActualValidCalculator(calculators, checkCalc);

        // Use Cases.
        // Depends on ChessGame(Entity) and ActualValidCalculator(Entity)
        MovePiece movePieceClass = new MovePiece(game, actualValidCalc);
        HighlightValid highlightClass = new HighlightValid(game, actualValidCalc);
        NewGame newGameClass = new NewGame(game);

        // Presenter.
        // implements PresenterInterface, depends on ViewInterface
        Presenter presenter = new Presenter();

        // Controller.
        // Depends on MovePiece(Use Case), HighlightValid(Use Case), and NewGame(Use Case)
        Controller clickController = new Controller(movePieceClass, highlightClass, newGameClass);

        // View.
        // Our main UI called ChessBoardUI.
        // Implements ViewInterface and depends on Controller. Also depends on GameOver view class.
        BoardUI ui = new BoardUI(clickController, presenter);

        // Need to set ViewInterface instance in Presenter because it depends on a view interface
        presenter.set_view(ui);
        // Need to set PresenterInterface instance in use case classes because it depends on a presenter interface
        movePieceClass.set_presenter(presenter);
        highlightClass.set_presenter(presenter);

        // Start View.
        // MainMenu, LeaderBoardUI, and GameOver extends View.
        new MenuUI(clickController, presenter);
    }
}
