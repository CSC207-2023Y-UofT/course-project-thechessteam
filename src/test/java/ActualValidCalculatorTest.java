import Entities.Locations.LocationBitboard;
import Entities.VariousCalculators.ActualValidCalculator;
import Entities.VariousCalculators.Calculators;
import Entities.VariousCalculators.CheckCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActualValidCalculatorTest {

    Calculators calculators = new Calculators();
    ActualValidCalculator actualValidCalculator = new ActualValidCalculator(
            calculators, new CheckCalculator(calculators));
    LocationBitboard board = new LocationBitboard();

    @Test
    void queenMovingResultsInCheck() {
        // The array representation of queenLocation
        int[][] whiteQueenLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] whiteKingLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] blackRookLocation = {
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Remove all pieces from board. Only have white queen on board.
        TestHelper.remove_all_pieces(board);
        board.whiteQueen[0] = TestHelper.bitboard_representation(whiteQueenLocation);
        board.whiteKing[0] = TestHelper.bitboard_representation(whiteKingLocation);
        board.blackRook[0] = TestHelper.bitboard_representation(blackRookLocation);
        board.updateLocationVariables();

        // Only moves that does not put the king in check should be valid
        int[][] expectedActualValidMove = {
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        long actualValidMove = actualValidCalculator.actual_valid_moves(
                TestHelper.bitboard_representation(whiteQueenLocation), true, board);
        // Check if valid move is as expected
        assertEquals(TestHelper.bitboard_representation(expectedActualValidMove), actualValidMove);
    }
}