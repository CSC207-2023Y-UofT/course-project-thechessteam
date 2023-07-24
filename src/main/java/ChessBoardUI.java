import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.AbstractMap;
import java.util.Map;

public class ChessBoardUI extends JPanel {
    private LocationBitboard locationBitboard;
    public int SQUARE_SIZE = 85;
    private Map.Entry<String, Long> draggedPiece = null;

    public ChessBoardUI() {
        this.locationBitboard = new LocationBitboard();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / SQUARE_SIZE;
                int y = 7 - e.getY() / SQUARE_SIZE;
                int index = y * 8 + x;

                // Iterate over all piece types to find which piece attribute was clicked
                for (String pieceType : locationBitboard.getAllPieces().keySet()) {
                    long bitboard = locationBitboard.getBitboard(pieceType);
                    if ((bitboard & (1L << index)) != 0) {
                        bitboard &= ~(1L << index);  // Clear the bit at the selected index
                        locationBitboard.setBitboard(pieceType, bitboard);
                        draggedPiece = new AbstractMap.SimpleEntry<>(pieceType, bitboard);
                        repaint();
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (draggedPiece != null) {
                    int x = e.getX() / SQUARE_SIZE;
                    int y = 7 - e.getY() / SQUARE_SIZE;
                    int index = y * 8 + x;
                    long bitboard = draggedPiece.getValue() | (1L << index);  // Update the bit at the selected index for the piece attribute
                    locationBitboard.setBitboard(draggedPiece.getKey(), bitboard);
                    draggedPiece = null;
                    repaint();
                }
            }
        });
    }

    // new
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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
    }

    private void drawPieces(Graphics g, long bitboard, Image pieceImage) {
        for (int i = 0; i < 64; i++) {
            if ((bitboard & (1L << i)) != 0) {
                int x = i % 8;
                int y = 7 - i / 8;
                g.drawImage(pieceImage, x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, null);
            }
        }
    }

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

