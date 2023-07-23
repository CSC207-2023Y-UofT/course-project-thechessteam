public class MovePiece {
    // Use case class for moving pieces, including castling.
    public static boolean move_piece(ChessGame currentGame, long start, long end) {
        // TODO Check if moving from start to end is a valid move. Implement ValidMove use case class.
        // if (not valid) {return false} else {
        return currentGame.move_piece(start, end); //}
    }
}
