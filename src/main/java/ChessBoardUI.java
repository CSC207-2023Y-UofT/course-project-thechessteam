import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class ChessBoardUI extends JPanel {
    private static LocationBitboard locationBitboard;
    public int SQUARE_SIZE = 85;
    private final int[] twoClicks = new int[2];
    private int numClicks = 0;

    static JButton forfeitButton = new JButton("forfeitButton");
    static JButton stalemateButton = new JButton("stalemateButton");
    static int blkPoints = 0;
    static int whtPoints = 0;
    static int border = 10; //the amount of empty space around the frame
    private boolean isButtonConfigured = false;

    public static void main (String[]args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        ChessBoardUI board = new ChessBoardUI();
        frame.add(board);
        frame.setMinimumSize(new Dimension(910, 725));
        frame.setVisible(true);
    }

    public ChessBoardUI() {
        // Get the current chessboard
        locationBitboard = ChessGame.getCurrentBoard();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                System.out.println(locationBitboard);
                int x = mouseX / SQUARE_SIZE;
                int y = 7 - mouseY / SQUARE_SIZE;
                int index = y * 8 + x;
                boolean pieceFound = false;

                if (mouseX >= 0 && mouseX < 8 * SQUARE_SIZE && mouseY >= 0 && mouseY < 8 * SQUARE_SIZE) {
                    for (String pieceType : locationBitboard.getAllPieces().keySet()) {
                        long bitboard = locationBitboard.getBitboard(pieceType);
                        if ((bitboard & (1L << index)) != 0) {
                            pieceFound = true;  // for the next if statement
                            break;
                        }
                    }
                    // If a piece was clicked or this is not the first click
                    if (pieceFound || (numClicks != 0)) {
                        // Save the clicked location
                        twoClicks[numClicks] = index;
                        numClicks += 1;
                        System.out.println(numClicks);
                        repaint();
                    }
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                // If this was the second click
                if (numClicks == 2) {
                    // Process the two clicks for valid moves
                    Controller.process_two_clicks(twoClicks);
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

        drawBorders(g);
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

    public void drawBorders(Graphics g) {
        g.setColor(new Color(43, 45, 48));
        g.fill3DRect(0, border, border, (int)(8*SQUARE_SIZE), true);
        g.fill3DRect((int)(8*SQUARE_SIZE)+border, border, border, (int)(8*SQUARE_SIZE), true);
        g.fill3DRect(border, 0, (int)(8*SQUARE_SIZE), border, true);
        g.fill3DRect(border, (int)(8*SQUARE_SIZE)+border, (int)(8*SQUARE_SIZE), border, true);

        g.setColor(Color.BLACK);
        g.fill3DRect(0, 0, border, border, true);
        g.fill3DRect((int)(8*SQUARE_SIZE)+border, 0, border, border, true);
        g.fill3DRect(0, (int)(8*SQUARE_SIZE)+border, border, border, true);
        g.fill3DRect((int)(8*SQUARE_SIZE)+border, (int)(8*SQUARE_SIZE)+border, border, border, true);
        g.fill3DRect((int)(8*SQUARE_SIZE)+2*border+200, 0, border, border, true);
        g.fill3DRect((int)(8*SQUARE_SIZE)+2*border+200, (int)(8*SQUARE_SIZE)+border, border, border, true);

        g.setColor(new Color(43, 45, 48));
        g.fill3DRect((int)(8*SQUARE_SIZE)+2*border, 0, 200, border, true);

        g.fill3DRect((int)(8*SQUARE_SIZE)+2*border+200, border, border, (int)(8*SQUARE_SIZE), true);
        g.fill3DRect((int)(8*SQUARE_SIZE)+2*border, (int)(8*SQUARE_SIZE)+border, 200, border, true);

        // Cast Graphics to Graphics2D to apply line thickness
        Graphics2D g2d = (Graphics2D) g;
        // Set the line thickness
        float thickness = 18.0f;
        g2d.setStroke(new BasicStroke(thickness));
        // Set the color for the new borders
        g2d.setColor(Color.BLACK);
        // Draw the first line from (x:680, y:680) to (x:680, y:0)
        g2d.drawLine(689, 689, 689, 0);
        // Draw the second line from (x:0, y:680) to (x:680, y:680)
        g2d.drawLine(0, 689, 689, 689);
    }

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

        // Calculate string width
        FontMetrics fm = g.getFontMetrics();
        int stringWidth = fm.stringWidth("0:00");
        int boxCenter = 190 / 2;
        int stringXPosition = (int)(8*SQUARE_SIZE)+2*border + 5 + boxCenter - stringWidth / 2;

        g.drawString("0:00", stringXPosition, border+(int)(7*SQUARE_SIZE+ (int)(1*SQUARE_SIZE)- 15));
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

        if (!isButtonConfigured) {
            forfeitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Forfeited");
                }
            });
            isButtonConfigured = true;
        }

        // Add button to panel
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
        // commented out for safety
//        stalemateButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Requesting Stalemate");
//            }
//        });
//        this.add(stalemateButton);
    }
}

