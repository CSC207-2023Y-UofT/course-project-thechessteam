public class BishopTest {
    public static void main(String[] args) {
        Bishop bishop = new Bishop();
        LocationBitboard board = new LocationBitboard();
        board.whiteBishop[0] = 0L;
        board.whiteBishop[0] = 1L << 28;
        long validMoves = bishop.valid_moves(board.whiteBishop[0], 0, board);
        System.out.println("Bishop's valid moves:\n" + Long.toBinaryString(validMoves));
        System.out.println("Expected bishop's valid moves:\n" + "10000010010001000010100000000000001010000000000000000000");
    }
}