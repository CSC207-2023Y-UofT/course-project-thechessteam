import Entities.Locations.LocationBitboard;
import Entities.Pieces.Queen;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

    Queen queenCalculator = new Queen();
    LocationBitboard board = new LocationBitboard();

    // ----------------------------------------------------------------------------------------------------------
    // Helper methods

    // Remove all pieces from board
    private void remove_all_pieces(LocationBitboard board) {
        board.whitePawn[0] = 0L;
        board.whiteRook[0] = 0L;
        board.whiteKnight[0] = 0L;
        board.whiteBishop[0] = 0L;
        board.whiteQueen[0] = 0L;
        board.whiteKing[0] = 0L;

        board.blackPawn[0] = 0L;
        board.blackRook[0] = 0L;
        board.blackKnight[0] = 0L;
        board.blackBishop[0] = 0L;
        board.blackQueen[0] = 0L;
        board.blackKing[0] = 0L;

        board.updateLocationVariables();
    }

    // Array into Bitboard
    private long bitboard_representation(int[][] arrayRepresentation) {
        long bitboard = 0L;
        int rowBitShift;
        for (int i = 0; i < 8; i++) {
            rowBitShift = 56 - (8 * i);
            for (int j = 0; j < 8; j++) {
                if (arrayRepresentation[i][j] == 1) {
                    bitboard |= 1L << (rowBitShift + j);
                }
            }
        }
        return bitboard;
    }
    // ----------------------------------------------------------------------------------------------------------
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
        remove_all_pieces(board);
        board.whiteQueen[0] = bitboard_representation(queenLocation);
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

        long validMove = queenCalculator.valid_moves(bitboard_representation(queenLocation), true, board);
        // Check if valid move is as expected
        assertEquals(bitboard_representation(expectedValidMove), validMove);
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
        remove_all_pieces(board);
        board.whiteQueen[0] = bitboard_representation(queenLocation);
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

        long validMove = queenCalculator.valid_moves(bitboard_representation(queenLocation), true, board);
        // Check if valid move is as expected
        assertEquals(bitboard_representation(expectedValidMove), validMove);
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
        remove_all_pieces(board);
        board.whiteQueen[0] = bitboard_representation(queenLocation);
        board.whitePawn[0] = bitboard_representation(pawnLocation);
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

        long validMove = queenCalculator.valid_moves(bitboard_representation(queenLocation), true, board);
        // Check if valid move is as expected
        assertEquals(bitboard_representation(expectedValidMove), validMove);
    }

    @org.junit.jupiter.api.Test
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
        remove_all_pieces(board);
        board.whiteQueen[0] = bitboard_representation(queenLocation);
        board.blackPawn[0] = bitboard_representation(pawnLocation);
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

        long validMove = queenCalculator.valid_moves(bitboard_representation(queenLocation), true, board);
        // Check if valid move is as expected
        assertEquals(bitboard_representation(expectedValidMove), validMove);
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
        remove_all_pieces(board);
        board.blackQueen[0] = bitboard_representation(blackQueenLocation);
        board.whiteBishop[0] = bitboard_representation(whiteBishopLocation);
        board.blackKnight[0] = bitboard_representation(blackKnightLocation);
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

        long validMove = queenCalculator.valid_moves(bitboard_representation(blackQueenLocation), false, board);
        // Check if valid move is as expected
        assertEquals(bitboard_representation(expectedValidMove), validMove);
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
        remove_all_pieces(board);
        board.whiteQueen[0] = bitboard_representation(queenLocation);
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

        long attackCoverage = queenCalculator.attack_coverage(true, board);
        // Check if valid move is as expected
        assertEquals(bitboard_representation(expectedAttackCoverage), attackCoverage);
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
        remove_all_pieces(board);
        board.whiteQueen[0] = bitboard_representation(queenLocation);
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

        long attackCoverage = queenCalculator.attack_coverage(true, board);
        // Check if valid move is as expected
        assertEquals(bitboard_representation(expectedAttackCoverage), attackCoverage);
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
        remove_all_pieces(board);
        board.whiteQueen[0] = bitboard_representation(queenLocation);
        board.whitePawn[0] = bitboard_representation(pawnLocation);
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

        long attackCoverage = queenCalculator.attack_coverage(true, board);
        // Check if valid move is as expected
        assertEquals(bitboard_representation(expectedAttackCoverage), attackCoverage);
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
        remove_all_pieces(board);
        board.whiteQueen[0] = bitboard_representation(queenLocation);
        board.blackPawn[0] = bitboard_representation(pawnLocation);
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

        long attackCoverage = queenCalculator.attack_coverage(true, board);
        // Check if valid move is as expected
        assertEquals(bitboard_representation(expectedAttackCoverage), attackCoverage);
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
        remove_all_pieces(board);
        board.blackQueen[0] = bitboard_representation(blackQueenLocation);
        board.whiteBishop[0] = bitboard_representation(whiteBishopLocation);
        board.blackKnight[0] = bitboard_representation(blackKnightLocation);
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

        long attackCoverage = queenCalculator.attack_coverage(false, board);
        // Check if valid move is as expected
        assertEquals(bitboard_representation(expectedAttackCoverage), attackCoverage);
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
        remove_all_pieces(board);
        board.blackQueen[0] = bitboard_representation(blackQueenLocation);
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

        long attackCoverage = queenCalculator.attack_coverage(false, board);
        // Check if valid move is as expected
        assertEquals(bitboard_representation(expectedAttackCoverage), attackCoverage);
    }
}