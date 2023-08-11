import entities.locations.LocationBitboard;
import entities.pieces.Queen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

    Queen queenCalculator = new Queen();
    LocationBitboard board = new LocationBitboard();

    // Valid Move Tests

    @org.junit.jupiter.api.Test
    void queenValidMoveAtCorner() {
        // The array representation of queenLocation
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

        // Remove all pieces from board. Only have white queen on board.
        TestHelper.remove_all_pieces(board);
        board.whiteQueen[0] = TestHelper.bitboard_representation(queenLocation);
        board.updateLocationVariables();

        int[][] expectedValidMove = {
                {1, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1}
        };

        long validMove = queenCalculator.validMoves(TestHelper.bitboard_representation(queenLocation), true, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboard_representation(expectedValidMove), validMove);
    }

    @org.junit.jupiter.api.Test
    void queenValidMoveAtMiddle() {
        // The array representation of queenLocation
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

        // Remove all pieces from board. Only have white queen on board.
        TestHelper.remove_all_pieces(board);
        board.whiteQueen[0] = TestHelper.bitboard_representation(queenLocation);
        board.updateLocationVariables();

        int[][] expectedValidMove = {
                {1, 0, 0, 1, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {1, 1, 1, 0, 1, 1, 1, 1},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 0, 1, 0, 1, 0, 0},
                {1, 0, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 1, 0, 0, 0, 1}
        };

        long validMove = queenCalculator.validMoves(TestHelper.bitboard_representation(queenLocation), true, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboard_representation(expectedValidMove), validMove);
    }

    @org.junit.jupiter.api.Test
    void queenValidMoveSurroundedByAlly() {
        // The array representation of queenLocation
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

        int[][] pawnLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Remove all pieces from board. Only have white queen on board.
        TestHelper.remove_all_pieces(board);
        board.whiteQueen[0] = TestHelper.bitboard_representation(queenLocation);
        board.whitePawn[0] = TestHelper.bitboard_representation(pawnLocation);
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

        long validMove = queenCalculator.validMoves(TestHelper.bitboard_representation(queenLocation), true, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboard_representation(expectedValidMove), validMove);
    }

    @Test
    void queenValidMoveSurroundedByEnemy() {
        // The array representation of queenLocation
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

        int[][] pawnLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Remove all pieces from board. Only have white queen on board.
        TestHelper.remove_all_pieces(board);
        board.whiteQueen[0] = TestHelper.bitboard_representation(queenLocation);
        board.blackPawn[0] = TestHelper.bitboard_representation(pawnLocation);
        board.updateLocationVariables();

        int[][] expectedValidMove = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        long validMove = queenCalculator.validMoves(TestHelper.bitboard_representation(queenLocation), true, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboard_representation(expectedValidMove), validMove);
    }

    @org.junit.jupiter.api.Test
    void queenValidMoveMixed() {
        // The array representation of queenLocation
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

        int[][] whiteBishopLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] blackKnightLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Remove all pieces from board. Only have white queen on board.
        TestHelper.remove_all_pieces(board);
        board.blackQueen[0] = TestHelper.bitboard_representation(blackQueenLocation);
        board.whiteBishop[0] = TestHelper.bitboard_representation(whiteBishopLocation);
        board.blackKnight[0] = TestHelper.bitboard_representation(blackKnightLocation);
        board.updateLocationVariables();

        int[][] expectedValidMove = {
                {1, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 0, 1, 0, 1, 0, 0},
                {1, 0, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1}
        };

        long validMove = queenCalculator.validMoves(TestHelper.bitboard_representation(blackQueenLocation), false, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboard_representation(expectedValidMove), validMove);
    }

    // ----------------------------------------------------------------------------------------------------------
    // Attack Coverage Tests

    @org.junit.jupiter.api.Test
    void queenAttackCoverageAtCorner() {
        // The array representation of queenLocation
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

        // Remove all pieces from board. Only have white queen on board.
        TestHelper.remove_all_pieces(board);
        board.whiteQueen[0] = TestHelper.bitboard_representation(queenLocation);
        board.updateLocationVariables();

        int[][] expectedAttackCoverage = {
                {1, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1}
        };

        long attackCoverage = queenCalculator.attackCoverage(true, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboard_representation(expectedAttackCoverage), attackCoverage);
    }

    @org.junit.jupiter.api.Test
    void queenAttackCoverageAtMiddle() {
        // The array representation of queenLocation
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

        // Remove all pieces from board. Only have white queen on board.
        TestHelper.remove_all_pieces(board);
        board.whiteQueen[0] = TestHelper.bitboard_representation(queenLocation);
        board.updateLocationVariables();

        int[][] expectedAttackCoverage = {
                {1, 0, 0, 1, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {1, 1, 1, 0, 1, 1, 1, 1},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 0, 1, 0, 1, 0, 0},
                {1, 0, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 1, 0, 0, 0, 1}
        };

        long attackCoverage = queenCalculator.attackCoverage(true, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboard_representation(expectedAttackCoverage), attackCoverage);
    }

    @org.junit.jupiter.api.Test
    void queenAttackCoverageSurroundedByAlly() {
        // The array representation of queenLocation
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

        int[][] pawnLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Remove all pieces from board. Only have white queen on board.
        TestHelper.remove_all_pieces(board);
        board.whiteQueen[0] = TestHelper.bitboard_representation(queenLocation);
        board.whitePawn[0] = TestHelper.bitboard_representation(pawnLocation);
        board.updateLocationVariables();

        int[][] expectedAttackCoverage = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        long attackCoverage = queenCalculator.attackCoverage(true, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboard_representation(expectedAttackCoverage), attackCoverage);
    }

    @org.junit.jupiter.api.Test
    void queenAttackCoverageSurroundedByEnemy() {
        // The array representation of queenLocation
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

        int[][] pawnLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Remove all pieces from board. Only have white queen on board.
        TestHelper.remove_all_pieces(board);
        board.whiteQueen[0] = TestHelper.bitboard_representation(queenLocation);
        board.blackPawn[0] = TestHelper.bitboard_representation(pawnLocation);
        board.updateLocationVariables();

        int[][] expectedAttackCoverage = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        long attackCoverage = queenCalculator.attackCoverage(true, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboard_representation(expectedAttackCoverage), attackCoverage);
    }

    @org.junit.jupiter.api.Test
    void queenAttackCoverageMixed() {
        // The array representation of queenLocation
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

        int[][] whiteBishopLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] blackKnightLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Remove all pieces from board. Only have white queen on board.
        TestHelper.remove_all_pieces(board);
        board.blackQueen[0] = TestHelper.bitboard_representation(blackQueenLocation);
        board.whiteBishop[0] = TestHelper.bitboard_representation(whiteBishopLocation);
        board.blackKnight[0] = TestHelper.bitboard_representation(blackKnightLocation);
        board.updateLocationVariables();

        int[][] expectedAttackCoverage = {
                {1, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 0, 1, 0, 1, 0, 0},
                {1, 0, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1}
        };

        long attackCoverage = queenCalculator.attackCoverage(false, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboard_representation(expectedAttackCoverage), attackCoverage);
    }

    @org.junit.jupiter.api.Test
    void queenAttackCoverageMultipleQueen() {
        // The array representation of queenLocation
        int[][] blackQueenLocation = {
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Remove all pieces from board. Only have white queen on board.
        TestHelper.remove_all_pieces(board);
        board.blackQueen[0] = TestHelper.bitboard_representation(blackQueenLocation);
        board.updateLocationVariables();

        int[][] expectedAttackCoverage = {
                {1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 0, 0, 0, 1, 1, 1},
                {0, 1, 0, 0, 1, 1, 0, 1},
                {0, 1, 0, 1, 1, 0, 0, 1},
                {1, 1, 1, 1, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 0, 0, 0, 0, 1},
                {1, 1, 0, 1, 0, 0, 0, 1}
        };

        long attackCoverage = queenCalculator.attackCoverage(false, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboard_representation(expectedAttackCoverage), attackCoverage);
    }
}