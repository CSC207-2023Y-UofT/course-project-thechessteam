package Framework_and_Drivers;

import Controller.Controller;
import Entities.VariousCalculators.*;
import Entities.ChessGame;
import Presenter.Presenter;
import Use_Cases.HighlightValid;
import Use_Cases.MovePiece;
import Use_Cases.NewGame;
import View.BoardUI;
import View.MenuUI;

/**
 * The Main class is responsible for initializing the chess game application.
 * It sets up the dependencies among different components like entities, use cases, presenter, controller, and view.
 * This ensures a clear separation of concerns and a modular architecture.
 */
public class Main {
    public static void main(String[] args) {
        // Entities
        // Initializing game logic entities. These manage the core data and game mechanics.
        ChessGame game = new ChessGame();
        Calculators calculators = new Calculators();
        CheckCalculator checkCalc = new CheckCalculator(calculators);
        ActualValidCalculator actualValidCalc = new ActualValidCalculator(calculators, checkCalc);

        // Use Cases
        // Initializing game action handlers which are responsible for performing specific game-related tasks.
        MovePiece movePieceClass = new MovePiece(game, actualValidCalc);
        HighlightValid highlightClass = new HighlightValid(game, actualValidCalc);
        NewGame newGameClass = new NewGame(game);
        CheckmateCalculator checkmateCalculatorClass = new CheckmateCalculator(actualValidCalc, checkCalc);
        StalemateCalculator stalemateCalculatorClass = new StalemateCalculator(actualValidCalc, checkCalc);

        // Presenter
        // Responsible for handling the presentation logic and updating the view.
        Presenter presenter = new Presenter();

        // Controller
        // Acts as a bridge between the UI and the game's core logic.
        Controller clickController = new Controller(movePieceClass, highlightClass, newGameClass,
                checkmateCalculatorClass, stalemateCalculatorClass);

        // View
        // The main user interface of the chess game. Displays the game board and handles user interactions.
        BoardUI ui = new BoardUI(clickController, presenter);

        // Setting up dependencies
        // Link the view with the presenter and vice versa. Also, link use cases with the presenter.
        presenter.set_view(ui);
        movePieceClass.set_presenter(presenter);
        highlightClass.set_presenter(presenter);

        // Starting the game's main user interface.
        new MenuUI(clickController, presenter);
    }
}

