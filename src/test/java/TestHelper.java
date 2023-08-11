import entities.locations.LocationBitboard;
/**
 * Holds helper methods for the test cases to visualize bitboard.
 */
public class TestHelper {

    // Remove all pieces from board
    public static void remove_all_pieces(LocationBitboard board) {
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
    public static long bitboard_representation(int[][] arrayRepresentation) {
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
}
