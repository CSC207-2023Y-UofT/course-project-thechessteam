package usecases;

import entities.ChessGame;
import entities.locations.LocationBitboard;
import entities.variouscalculators.ActualValidCalculator;
import entities.variouscalculators.Calculators;
import entities.variouscalculators.CheckCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewGameTest {
    ChessGame game;
    Calculators calculators;
    CheckCalculator checkCalc;
    ActualValidCalculator actualValidCalc;
    NewGame newGameClass;

    @BeforeEach
    void setUp() {
        // entities.
        // Dependencies are non-cyclical and within the same entity layer
        this.game = new ChessGame();
        this.calculators = new Calculators();
        this.checkCalc = new CheckCalculator(calculators);
        this.actualValidCalc = new ActualValidCalculator(calculators, checkCalc);

        // Use Case.
        // Depends on ChessGame(Entity) and ActualValidCalculator(Entity)
        this.newGameClass = new NewGame(game);
    }

    @Test
    void startNewGameAtBeginning() {
        LocationBitboard currentBoard = game.getCurrentBoard();
        assertEquals(currentBoard, game.getCurrentBoard());

        // Start New Game
        newGameClass.startNewGame();
        assertNotEquals(currentBoard, game.getCurrentBoard());
        assertTrue(game.getTurn());
    }
}