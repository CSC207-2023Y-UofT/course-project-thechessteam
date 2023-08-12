import entities.locations.LocationBitboard;
import entities.pieces.Rook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class RookTest {

    Rook rookCalculator = new Rook();
    LocationBitboard board = new LocationBitboard();

    // Valid Move Tests

    @Test
    void rookMovesAtCorner() {
        // The array representation of rookLocation
        int[][] rookLocation = {
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
        board.blackRook[0] = TestHelper.bitboardRepresentation(rookLocation);
        board.updateLocationVariables();

        int[][] expectedValidMove = {
                {0, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0}
        };

        long validMove = rookCalculator.validMoves(TestHelper.bitboardRepresentation(rookLocation), false, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboardRepresentation(expectedValidMove), validMove);
    }


    @Test
    void rookMovesAtOrigin() {
        // The array representation of rookLocation
        int[][] rookLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1}
        };

        // We don't remove all pieces here because we want the pieces that can block its original movement.
        board.whiteRook[0] = TestHelper.bitboardRepresentation(rookLocation);
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

        long validMove = rookCalculator.validMoves(TestHelper.bitboardRepresentation(rookLocation), true, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboardRepresentation(expectedValidMove), validMove);
    }

    @Test
    void rookFreeMoveAtOrigin() {
        // The array representation of rookLocation
        int[][] rookLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1}
        };

        // Remove all pieces from board.
        TestHelper.removeAllPieces(board);
        board.whiteRook[0] = TestHelper.bitboardRepresentation(rookLocation);
        board.updateLocationVariables();

        int[][] expectedValidMove = {
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 0}
        };

        long validMove = rookCalculator.validMoves(TestHelper.bitboardRepresentation(rookLocation), true, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboardRepresentation(expectedValidMove), validMove);
    }


    // ----------------------------------------------------------------------------------------------------------
    // Attack Coverage Tests

    @Test
    void rookAttackCoverage() {
        // The array representation of rookLocation
        int[][] rookLocation = {
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
        board.whiteRook[0] = TestHelper.bitboardRepresentation(rookLocation);
        board.updateLocationVariables();

        int[][] expectedAttackCoverage = {
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {1, 1, 1, 0, 1, 1, 1, 1},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0}
        };

        long attackCoverage = rookCalculator.attackCoverage(true, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboardRepresentation(expectedAttackCoverage), attackCoverage);
    }
}