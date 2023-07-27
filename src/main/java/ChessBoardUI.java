// TODO: Capture, only can move to valid place on board

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.AbstractMap;
import java.util.Map;

public class ChessBoardUI extends JPanel {
    private final LocationBitboard locationBitboard;

    public int SQUARE_SIZE = 85;  // one square size

    private Map.Entry<String, Long> draggedPiece = null;  // key: pieceType, Long: piece location

    private long highlightSquares = 0L;  // holds bitboard of the highlighted squares

    private Point mousePoint = null; // for mouse dragging

    private long originalPosition;

    // constructor
    public ChessBoardUI() {
        this.locationBitboard = new LocationBitboard();


        // utilize mousePressed and mouseReleased.
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / SQUARE_SIZE;  // x location
                int y = 7 - e.getY() / SQUARE_SIZE;  // y location
                int index = y * 8 + x;  // specifies the bits to shift the long (goes from 0 to 63) to get to position where person is clicking :)

                // Iterates over all the keyset of the locationBitboard. (strings of piece names)
                for (String pieceType : locationBitboard.getAllPieces().keySet()) {
                    long bitboard = locationBitboard.getBitboard(pieceType);  // holds bitboard of the selected piece type
                    if ((bitboard & (1L << index)) != 0) {  // if there is a piece at the selected index
                        originalPosition = bitboard;  // stores the original position
                        bitboard &= ~(1L << index);  // Clear the bit at the selected index immediately
                        locationBitboard.setBitboard(pieceType, bitboard);  // updates the pieces' respective bitboard with the new move

                        draggedPiece = new AbstractMap.SimpleEntry<>(pieceType, bitboard);  // holds the piece currently getting dragged

                        // this part is so the valid moves can be highlighted. Also, valid moves methods correponding to each piece
                        String pieceClass = pieceType.substring(5);  // remove the "black" or "white" prefix and make first letter uppercase
                        pieceClass = pieceClass.substring(0, 1).toUpperCase() + pieceClass.substring(1);  // makes the specific piece uppercase
                        Calculator piece = PieceFactory.getPiece(pieceClass);  // created new class piece factory for the purpose of knowing exactly what piece class is being usd
                        // retrieve the selected piece's valid moves
                        long validMoves = piece.valid_moves(1L << index, pieceType.startsWith("white") ? 0 : 1, locationBitboard);  // calls valid moves class

                        // Highlight the valid moves
                        highlightSquares = validMoves;
                        repaint();
                        break;
                    }
                }
            }

            // when the mouse is released, drop the piece onto the new square, update the location bitboard, clear highlighted squares
            @Override
            public void mouseReleased(MouseEvent e) {
                if (draggedPiece != null) {
                    // same calculation as previously
                    int x = e.getX() / SQUARE_SIZE;
                    int y = 7 - e.getY() / SQUARE_SIZE;
                    int index = y * 8 + x;

                    // only allow the move to take place if the destination square is a valid move (highlighted)
                    if ((highlightSquares & (1L << index)) != 0) {
                        long bitboard = draggedPiece.getValue() | (1L << index);  // Update the bit at the selected index for the piece attribute
                        locationBitboard.setBitboard(draggedPiece.getKey(), bitboard);  // updates the bitboard for that piece
                    } else {
                        locationBitboard.setBitboard(draggedPiece.getKey(), originalPosition);  // resets the piece position if move was invalid
                    }
                    draggedPiece = null;  // resets draggedPiece
                    highlightSquares = 0L;  // Clear the highlighted squares when piece dropped
                    repaint();
                }
            }
        });

        // when mouse dragged, make the selected piece image follow the mouse cursor
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggedPiece != null) {
                    mousePoint = e.getPoint();
                    repaint();
                }
            }
        });
    }

    // new
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw squares
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Color color;
                if ((row + col) % 2 == 0) {
                    color = new Color(255, 255, 255);
                } else {
                    color = new Color(43, 45, 48);
                }
                g.setColor(color);
                g.fillRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
        // draw pieces on board
        drawPieces(g, locationBitboard.getBitboard("whitePawn"), Images.whitePawn);
        drawPieces(g, locationBitboard.getBitboard("whiteRook"), Images.whiteRook);
        drawPieces(g, locationBitboard.getBitboard("whiteKnight"), Images.whiteKnight);
        drawPieces(g, locationBitboard.getBitboard("whiteBishop"), Images.whiteBishop);
        drawPieces(g, locationBitboard.getBitboard("whiteQueen"), Images.whiteQueen);
        drawPieces(g, locationBitboard.getBitboard("whiteKing"), Images.whiteKing);
        drawPieces(g, locationBitboard.getBitboard("blackPawn"), Images.blackPawn);
        drawPieces(g, locationBitboard.getBitboard("blackRook"), Images.blackRook);
        drawPieces(g, locationBitboard.getBitboard("blackKnight"), Images.blackKnight);
        drawPieces(g, locationBitboard.getBitboard("blackBishop"), Images.blackBishop);
        drawPieces(g, locationBitboard.getBitboard("blackQueen"), Images.blackQueen);
        drawPieces(g, locationBitboard.getBitboard("blackKing"), Images.blackKing);

        // Highlight valid moves on the board as dots
        g.setColor(new Color(128, 128, 128, 128));
        int diameter = SQUARE_SIZE / 4;
        int radius = diameter / 2;
        for (int i = 0; i < 64; i++) {
            if ((highlightSquares & (1L << i)) != 0) {
                int x = i % 8;
                int y = 7 - i / 8;
                int centerX = x * SQUARE_SIZE + SQUARE_SIZE / 2;
                int centerY = y * SQUARE_SIZE + SQUARE_SIZE / 2;
                g.fillOval(centerX - radius, centerY - radius, diameter, diameter);
            }
        }

        // Allows the piece to be visually dragged with cursor
        if (draggedPiece != null && mousePoint != null) {
            Image pieceImage = Images.getPieceImage(draggedPiece.getKey());
            g.drawImage(pieceImage, mousePoint.x - SQUARE_SIZE / 2, mousePoint.y - SQUARE_SIZE / 2, SQUARE_SIZE, SQUARE_SIZE, null);
        }
    }

    // helper for drawing pieces onto board
    private void drawPieces(Graphics g, long bitboard, Image pieceImage) {
        for (int i = 0; i < 64; i++) {
            if ((bitboard & (1L << i)) != 0) {
                int x = i % 8;
                int y = 7 - i / 8;
                g.drawImage(pieceImage, x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, null);
            }
        }
    }

    // main
    public static void main (String[]args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        ChessBoardUI board = new ChessBoardUI();
        frame.add(board);
        frame.setMinimumSize(new Dimension(680, 708));
        frame.setVisible(true);
    }
}


