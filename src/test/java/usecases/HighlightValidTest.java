package usecases;

import entities.ChessGame;
import entities.variouscalculators.ActualValidCalculator;
import entities.variouscalculators.Calculators;
import entities.variouscalculators.CheckCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presenterinterface.PresenterInterface;

import static org.junit.jupiter.api.Assertions.*;

class HighlightValidTest {

    static class MockPresenter implements PresenterInterface {
        long highlight;

        @Override
        public void updateLocations(int j, long bitboard, boolean color) {
            // Method not needed for test
        }

        @Override
        public void setLocation() {
            // Method not needed for test
        }

        @Override
        public void setHighlight(long highlight) {
            this.highlight = highlight;
        }

        @Override
        public void setTurn(boolean currentTurn) {
            // Method not needed for test
        }

        @Override
        public void notifyGameOver() {
            // Method not needed for test
        }
    }

    ChessGame game;
    Calculators calculators;
    CheckCalculator checkCalc;
    ActualValidCalculator actualValidCalc;
    HighlightValid highlightClass;
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
        this.highlightClass = new HighlightValid(game, actualValidCalc);

        // Presenter.
        // implements PresenterInterface, depends on ViewInterface
        this.mockPresenter = new MockPresenter();

        // Need to set PresenterInterface instance in use case classes because it depends on a presenter interface
        highlightClass.setPresenter(mockPresenter);
    }

    @Test
    void noPieceToMove() {
        // Remove all pieces from board
        TestHelper.removeAllPieces(game.getCurrentBoard());
        int[][] clickLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] expectedHighlight= {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        assertFalse(highlightClass.createHighlight(TestHelper.bitboardRepresentation(clickLocation)));
        assertEquals(TestHelper.bitboardRepresentation(expectedHighlight), mockPresenter.highlight);
    }

    @Test
    void queenCorner() {
        // Remove all pieces from board
        TestHelper.removeAllPieces(game.getCurrentBoard());
        int[][] queenLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0}
        };
        // Add queen piece to board. It is white's turn because turn has not changed.
        game.getCurrentBoard().whiteQueen[0] = TestHelper.bitboardRepresentation(queenLocation);
        game.getCurrentBoard().updateLocationVariables();

        int[][] expectedQueenHighlight = {
                {1, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1}
        };
        assertTrue(highlightClass.createHighlight(TestHelper.bitboardRepresentation(queenLocation)));
        assertEquals(TestHelper.bitboardRepresentation(expectedQueenHighlight), mockPresenter.highlight);
    }

    @Test
    void queenMiddle() {
        // Remove all pieces from board
        TestHelper.removeAllPieces(game.getCurrentBoard());
        int[][] queenLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        // Add queen piece to board. It is white's turn because turn has not changed.
        game.getCurrentBoard().whiteQueen[0] = TestHelper.bitboardRepresentation(queenLocation);
        game.getCurrentBoard().updateLocationVariables();

        int[][] expectedQueenHighlight = {
                {1, 0, 0, 1, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {1, 1, 1, 0, 1, 1, 1, 1},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 0, 1, 0, 1, 0, 0},
                {1, 0, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 1, 0, 0, 0, 1}
        };
        assertTrue(highlightClass.createHighlight(TestHelper.bitboardRepresentation(queenLocation)));
        assertEquals(TestHelper.bitboardRepresentation(expectedQueenHighlight), mockPresenter.highlight);
    }

    @Test
    void enemyAndAllyBlockingWhiteQueen() {
        // Remove all pieces from board
        TestHelper.removeAllPieces(game.getCurrentBoard());

        int[][] whiteQueenLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] blackPawnLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] whiteRookLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        // Add queen piece to board. It is white's turn because turn has not changed.
        game.getCurrentBoard().whiteQueen[0] = TestHelper.bitboardRepresentation(whiteQueenLocation);
        game.getCurrentBoard().blackPawn[0] = TestHelper.bitboardRepresentation(blackPawnLocation);
        game.getCurrentBoard().whiteRook[0] = TestHelper.bitboardRepresentation(whiteRookLocation);
        game.getCurrentBoard().updateLocationVariables();

        int[][] expectedQueenHighlight = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        assertTrue(highlightClass.createHighlight(TestHelper.bitboardRepresentation(whiteQueenLocation)));
        assertEquals(TestHelper.bitboardRepresentation(expectedQueenHighlight), mockPresenter.highlight);
    }

    @Test
    void enemyAndAllyBlockingBlack() {
        // Remove all pieces from board
        TestHelper.removeAllPieces(game.getCurrentBoard());

        // Change to black's turn
        game.changeTurn();

        int[][] blackQueenLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] blackPawnLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] whiteRookLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        // Add queen piece to board. It is white's turn because turn has not changed.
        game.getCurrentBoard().blackQueen[0] = TestHelper.bitboardRepresentation(blackQueenLocation);
        game.getCurrentBoard().blackPawn[0] = TestHelper.bitboardRepresentation(blackPawnLocation);
        game.getCurrentBoard().whiteRook[0] = TestHelper.bitboardRepresentation(whiteRookLocation);
        game.getCurrentBoard().updateLocationVariables();

        int[][] expectedQueenHighlight = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        assertTrue(highlightClass.createHighlight(TestHelper.bitboardRepresentation(blackQueenLocation)));
        assertEquals(TestHelper.bitboardRepresentation(expectedQueenHighlight), mockPresenter.highlight);
    }
}