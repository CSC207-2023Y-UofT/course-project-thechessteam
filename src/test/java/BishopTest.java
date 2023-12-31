import entities.locations.LocationBitboard;
import entities.pieces.Bishop;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Bishop behavior in a chess game.
 * Tests include various scenarios such as different positions,
 * possible moves, attacking coverages, and how the bishop's moves are affected
 * by ally and enemy pieces on the board.
 */
class BishopTest {

    Bishop bishopCalculator = new Bishop();
    LocationBitboard board = new LocationBitboard();

    // Valid Move Tests

    @Test
    void bishopMovesAtCorner() {
        // The array representation of bishopLocation
        int[][] bishopLocation = {
                {1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Remove all pieces from board.
        TestHelper.removeAllPieces(board);
        board.blackBishop[0] = TestHelper.bitboardRepresentation(bishopLocation);
        board.updateLocationVariables();

        int[][] expectedValidMove = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1}
        };

        long validMove = bishopCalculator.validMoves(TestHelper.bitboardRepresentation(bishopLocation), false, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboardRepresentation(expectedValidMove), validMove);
    }


    @Test
    void bishopMovesAtOrigin() {
        // The array representation of bishopLocation
        int[][] bishopLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0}
        };

        // We don't remove all pieces here because we want the pieces that can block its original movement.
        board.whiteBishop[0] = TestHelper.bitboardRepresentation(bishopLocation);
        board.updateLocationVariables();

        int[][] expectedValidMove = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        long validMove = bishopCalculator.validMoves(TestHelper.bitboardRepresentation(bishopLocation), true, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboardRepresentation(expectedValidMove), validMove);
    }


    // ----------------------------------------------------------------------------------------------------------
    // Attack Coverage Tests

    @Test
    void bishopAttackCoverage() {
        // The array representation of bishopLocation
        int[][] bishopLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Remove all pieces from board.
        TestHelper.removeAllPieces(board);
        board.whiteBishop[0] = TestHelper.bitboardRepresentation(bishopLocation);
        board.updateLocationVariables();

        int[][] expectedAttackCoverage = {
                {0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 1, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 1, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 0, 0, 1, 0}
        };

        long attackCoverage = bishopCalculator.attackCoverage(true, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboardRepresentation(expectedAttackCoverage), attackCoverage);
    }

    @Test
    void bishopAttackCoverageAllyBlocking() {
        // The array representation of bishopLocation
        int[][] bishopLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] pawnLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Remove all pieces from board.
        TestHelper.removeAllPieces(board);
        board.whiteBishop[0] = TestHelper.bitboardRepresentation(bishopLocation);
        board.whitePawn[0] = TestHelper.bitboardRepresentation(pawnLocation);
        board.updateLocationVariables();

        int[][] expectedAttackCoverage = {
                {1, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 1, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        long attackCoverage = bishopCalculator.attackCoverage(true, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboardRepresentation(expectedAttackCoverage), attackCoverage);
    }

    @Test
    void bishopAttackCoverageWithEnemy() {
        // The array representation of bishopLocation
        int[][] bishopLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] pawnLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Remove all pieces from board.
        TestHelper.removeAllPieces(board);
        board.whiteBishop[0] = TestHelper.bitboardRepresentation(bishopLocation);
        board.blackPawn[0] = TestHelper.bitboardRepresentation(pawnLocation);
        board.updateLocationVariables();

        int[][] expectedAttackCoverage = {
                {1, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 1, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        long attackCoverage = bishopCalculator.attackCoverage(true, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboardRepresentation(expectedAttackCoverage), attackCoverage);
    }

    @Test
    void bishopAttackCoverageEnemyAndAlly() {
        // The array representation of bishopLocation
        int[][] blackBishopLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] whitePawnLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] blackPawnLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Remove all pieces from board.
        TestHelper.removeAllPieces(board);
        board.blackBishop[0] = TestHelper.bitboardRepresentation(blackBishopLocation);
        board.whitePawn[0] = TestHelper.bitboardRepresentation(whitePawnLocation);
        board.blackPawn[0] = TestHelper.bitboardRepresentation(blackPawnLocation);
        board.updateLocationVariables();

        int[][] expectedAttackCoverage = {
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        long attackCoverage = bishopCalculator.attackCoverage(false, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboardRepresentation(expectedAttackCoverage), attackCoverage);
    }

}