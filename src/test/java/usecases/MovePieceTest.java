package usecases;

import entities.ChessGame;
import entities.variouscalculators.ActualValidCalculator;
import entities.variouscalculators.Calculators;
import entities.variouscalculators.CheckCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presenterinterface.PresenterInterface;

import static org.junit.jupiter.api.Assertions.*;

class MovePieceTest {
    static class MockPresenter implements PresenterInterface {
        long[][] pieceLocations = new long[2][6];
        @Override
        public void updateLocations(int j, long bitboard, boolean color) {
            int i;
            if (color) {
                i = 0;
            } else {
                i = 1;
            }
            pieceLocations[i][j] = bitboard;
        }

        @Override
        public void setLocation() {
            // Method not needed for test
        }

        @Override
        public void setHighlight(long highlight) {
            // Method not needed for test
        }

        @Override
        public void setTurn(boolean currentTurn) {
            // Method not needed for test
        }
    }

    ChessGame game;
    Calculators calculators;
    CheckCalculator checkCalc;
    ActualValidCalculator actualValidCalc;
    MovePiece movePieceClass;
    MockPresenter mockPresenter;

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
        this.movePieceClass = new MovePiece(game, actualValidCalc);

        // Presenter.
        // implements PresenterInterface, depends on ViewInterface
        this.mockPresenter = new MockPresenter();

        // Need to set PresenterInterface instance in use case classes because it depends on a presenter interface
        movePieceClass.setPresenter(mockPresenter);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                mockPresenter.pieceLocations[i][j] = 0;
            }
        }
    }

    @Test
    void movePieceNoPiece() {
        // Using empty chessboard
        TestHelper.removeAllPieces(game.getCurrentBoard());
        System.out.println(mockPresenter.pieceLocations[0][0]);
        int[][] from = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] to = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        movePieceClass.movePiece(TestHelper.bitboardRepresentation(from), TestHelper.bitboardRepresentation(to));
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                assertEquals(0, mockPresenter.pieceLocations[i][j]);
            }
        }
    }
}