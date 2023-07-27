import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;

public class ChessBoardUI extends JPanel {
    private static LocationBitboard locationBitboard;
    public int SQUARE_SIZE = 85;
    private Map.Entry<String, Long> draggedPiece = null;
    private final int[] twoClicks = new int[2];
    private int numClicks = 0;

    // harrison
    static JButton forfeitButton = new JButton("forfeitButton");
    static JButton stalemateButton = new JButton("stalemateButton");
    static int blkPoints = 0;
    static int whtPoints = 0;
    static int humanIsWhite=1;
    static int rating=0;
    static int border=10;//the amount of empty space around the frame


    // Just so it works for now, need to refactor later so that
    // LocationBitboard entity is not connected to View directly.
    static int int1 = 245+680;
    static int int2 = 58+708;

    public static void main (String[]args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        ChessBoardUI board = new ChessBoardUI();
        frame.add(board);
        frame.setMinimumSize(new Dimension(int1, int2));
        frame.setVisible(true);
    }

    public ChessBoardUI() {
        locationBitboard = ChessGame.getCurrentBoard();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(locationBitboard);
                int x = e.getX() / SQUARE_SIZE;
                int y = 7 - e.getY() / SQUARE_SIZE;
                int index = y * 8 + x;
                boolean pieceFound = false;

                // Iterate over all piece types to find which piece attribute was clicked
                for (String pieceType : locationBitboard.getAllPieces().keySet()) {
                    long bitboard = locationBitboard.getBitboard(pieceType);  // gets the bitboard of the piece type
                    if ((bitboard & (1L << index)) != 0) {  // if there is a selected piece
                        pieceFound = true;  // for the next if statement
                        bitboard &= ~(1L << index);  // Clear the bit at the selected index
                        locationBitboard.setBitboard(pieceType, bitboard);  // set the bitboard for the new piece
                        draggedPiece = new AbstractMap.SimpleEntry<>(pieceType, bitboard); // piece disappears when the piece is clicked
                        repaint();
                        break;
                    }
                }
                if (pieceFound || (numClicks != 0)) { // first click has to be a piece
                    twoClicks[numClicks] = index;
                    numClicks += 1;
                    System.out.println(numClicks);
                    repaint();
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
                }
                if (numClicks == 2) {
                    Controller.process_two_clicks(twoClicks);  // handles valid moves
                    numClicks = 0;
                    System.out.println(locationBitboard);
                }
                repaint();
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

        // harrison
//        drawBorders(g);
        drawBlackTeam(g);
        drawWhiteTeam(g);
        drawTimer(g);
        drawForfeit();
        drawStalemate();
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

    // harrison
    // Creation of the chess board

    // Creation of the black teams points and captured pieces
    public void drawBlackTeam(Graphics g) {
        g.setColor(new Color(103, 106, 110, 255));
        g.fill3DRect((int)(8*SQUARE_SIZE)+2*border, border, 200, (int)(2.8*SQUARE_SIZE), true);

        g.setColor(new Color(0, 0, 0, 255));
        g.drawString("Black Team:", (int)(8*SQUARE_SIZE)+2*border + 2, border*2+ 2);
        g.drawString(Integer.toString(blkPoints), (int)(8*SQUARE_SIZE)+2*border + 175 - border, border*2 + 2);
    }

    // Creation of the white teams points and captured pieces
    public void drawWhiteTeam(Graphics g) {
        g.setColor(new Color(52, 53, 56));
        g.fill3DRect((int)(8*SQUARE_SIZE)+2*border, border+(int)(2.8*SQUARE_SIZE), 200, (int)(2.8*SQUARE_SIZE), true);

        g.setColor(new Color(220, 216, 216, 255));
        g.drawString("White Team:", (int)(8*SQUARE_SIZE)+2*border + 2, border*2+ (int)(2.8*SQUARE_SIZE) + 2);
        g.drawString(Integer.toString(whtPoints), (int)(8*SQUARE_SIZE)+2*border + 175 - border, border*2+ (int)(2.8*SQUARE_SIZE) + 2);
    }

    // Creation of the timer UI located at the bottom right
    public void drawTimer(Graphics g) {
        // Creation of the clock Borders
        g.setColor(new Color(44, 46, 51));
        g.fill3DRect((int)(8*SQUARE_SIZE)+2*border, border+(int)(7*SQUARE_SIZE), 200, (int)(1*SQUARE_SIZE), true);
        g.setColor(new Color(190, 178, 157));
        g.fill3DRect((int)(8*SQUARE_SIZE)+2*border + 5, border+(int)(7*SQUARE_SIZE+5), 190, (int)(1*SQUARE_SIZE - 10), true);


        // Creation of the clock Label
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("TRUE TYPE_FONT", Font.BOLD, (int)(1*SQUARE_SIZE) - 20));
        g.drawString("0:00", (int)(8*SQUARE_SIZE)+2*border + (int)(1*SQUARE_SIZE) - 15, border+(int)(7*SQUARE_SIZE+ (int)(1*SQUARE_SIZE)- 15));
    }

    // Creation of the forfeit button
    public void drawForfeit() {
        Image unscaledIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/Forfeit.png"))).getImage();
        Image forfeitIcon = unscaledIcon.getScaledInstance(40 ,(int)(1*SQUARE_SIZE) - 25, java.awt.Image.SCALE_SMOOTH);


        forfeitButton.setText("");
        forfeitButton.setFocusPainted(false);
        forfeitButton.setBounds((int)(8*SQUARE_SIZE)+2*border,border+(int)(6*SQUARE_SIZE),100, (int)(1*SQUARE_SIZE));
        forfeitButton.setBackground(new Color(44, 46, 51));
        forfeitButton.setIcon(new ImageIcon(forfeitIcon));
//        if (newGame) {
//            forfeitButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    System.out.println("Forfeited");
//                }
//            });
//        }
        this.add(stalemateButton);
        this.add(forfeitButton);
    }

    // Creation of the draw button
    public void drawStalemate() {
        Image unscaledIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/DrawIcon.png"))).getImage();
        Image stalemateIcon = unscaledIcon.getScaledInstance(50 ,(int)(1*SQUARE_SIZE) - 10, java.awt.Image.SCALE_SMOOTH);


        stalemateButton.setText("");
        stalemateButton.setFocusPainted(false);
        stalemateButton.setBounds((int)(8*SQUARE_SIZE)+2*border + 100,border+(int)(6*SQUARE_SIZE),100, (int)(1*SQUARE_SIZE));
        stalemateButton.setBackground(new Color(103, 106, 110));
        stalemateButton.setIcon(new ImageIcon(stalemateIcon));
//        if (newGame) {
//            stalemateButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    System.out.println("Requesting Stalemate");
//                }
//            });
//        }
        this.add(stalemateButton);
    }
    
    
    

}

