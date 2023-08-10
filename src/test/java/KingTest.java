import Entities.Locations.LocationBitboard;
import Entities.Pieces.King;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {

    King kingCalculator = new King();
    LocationBitboard board = new LocationBitboard();

    @org.junit.jupiter.api.Test
    void kingValidMoveAtCorner() {
        // The array representation of kingLocation
        int[][] kingLocation = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0}
        };

        TestHelper.remove_all_pieces(board);
        board.whiteKing[0] = TestHelper.bitboard_representation(kingLocation);
        board.updateLocationVariables();

        int[][] expectedValidMove = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0}
        };

        long validMove = kingCalculator.valid_moves(TestHelper.bitboard_representation(kingLocation), true, board);
        assertEquals(TestHelper.bitboard_representation(expectedValidMove), validMove);
    }


        @Test
        void kingValidMoveAtTopRightCorner() {
            int[][] kingLocation = {
                    {0, 0, 0, 0, 0, 0, 0, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
            };

            TestHelper.remove_all_pieces(board);
            board.whiteKing[0] = TestHelper.bitboard_representation(kingLocation);
            board.updateLocationVariables();

            int[][] expectedValidMove = {
                    {0, 0, 0, 0, 0, 0, 1, 0},
                    {0, 0, 0, 0, 0, 0, 1, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},

            };

            long validMove = kingCalculator.valid_moves(TestHelper.bitboard_representation(kingLocation), true, board);
            assertEquals(TestHelper.bitboard_representation(expectedValidMove), validMove);
        }

        @Test
        void kingValidMoveSurroundedByEnemies() {
            int[][] kingLocation = {
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
            };

            int[][] enemyPawns = {
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 1, 0, 0, 0},
                    {0, 0, 1, 1, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
            };

            TestHelper.remove_all_pieces(board);
            board.whiteKing[0] = TestHelper.bitboard_representation(kingLocation);
            board.blackPawn[0] = TestHelper.bitboard_representation(enemyPawns);
            board.updateLocationVariables();

            int[][] expectedValidMove = {
                    {0, 0, 1, 1, 1, 0, 0, 0},
                    {0, 0, 1, 0, 1, 0, 0, 0},
                    {0, 0, 1, 1, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
            };

            long validMove = kingCalculator.valid_moves(TestHelper.bitboard_representation(kingLocation), true, board);
            assertEquals(TestHelper.bitboard_representation(expectedValidMove), validMove);
        }

        // Add more tests: Edge cases, surrounded by allies, in check scenarios, etc.
    }




