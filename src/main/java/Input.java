import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// need to find a way to paint the valid move squares

public class Input extends MouseAdapter {
    public void mousePressed(MouseEvent e) {

    }
}



//package Input;
//
//        import Board.Board;
//        import Pieces.Piece;
//
//        import java.awt.event.MouseAdapter;
//        import java.awt.event.MouseEvent;
//
//public class Input extends MouseAdapter {
//    Board board;
//    public Input(Board board) {
//        this.board = board;
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//        int col = e.getX() / board.SQUARE_SIZE;
//        int row = e.getY() / board.SQUARE_SIZE;
//
//        Piece pieceXY = board.getPiece(col, row);
//        if (pieceXY != null) {
//            board.selectedPiece = pieceXY;
//        }
//    }
//
//    @Override
//    public void mouseDragged(MouseEvent e) {
//        if(board.selectedPiece != null) {
//            board.selectedPiece.xPos = e.getX() - board.SQUARE_SIZE / 2;
//            board.selectedPiece.yPos = e.getY() - board.SQUARE_SIZE / 2;
//            board.repaint();
//        }
//    }
//
//    public void mouseReleased(MouseEvent e) {
//        int col = e.getX() / board.SQUARE_SIZE;
//        int row = e.getY() / board.SQUARE_SIZE;
//
//        if (board.selectedPiece != null) {
//            Move move = new Move(board, board.selectedPiece, col, row);
//
//            if (board.isValidMove(move)){
//                board.makeMove(move);
//            } else {
//                board.selectedPiece.xPos = board.selectedPiece.col * board.SQUARE_SIZE;
//                board.selectedPiece.yPos = board.selectedPiece.row * board.SQUARE_SIZE;
//            }
//        }
//        board.selectedPiece = null;
//        board.repaint();
//    }
//
//}