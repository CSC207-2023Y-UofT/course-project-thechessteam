public class MovePiece {
    // Use case class for moving pieces, including castling.
    // Returns true if there's a piece at start and end is a valid move of that piece.
    public static boolean move_piece(LocationBitboard currentBoard, long start, long end, boolean turn) {
        if ((end & ActualValidMove.actual_valid_moves(start, turn, currentBoard)) != 0) { // end is a valid move
            return currentBoard.move_piece(start, end, turn);
        }
        else {
            return false;
        }
    }
}
