package view;

import controller.Controller;
import viewinterface.ViewInterface;

import presenter.Presenter; // Used for reestablishing framework after we create a new view class.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class BoardUI extends JPanel implements ViewInterface {

    private final Controller clickController;
    private final Presenter presenter;
    private GameOverUI gameOverScreen;

    // Used for initial location initializations
    private static final long WHITE_PAWN =
            0b0000000000000000000000000000000000000000000000001111111100000000L;
    private static final long WHITE_ROOK =
            0b0000000000000000000000000000000000000000000000000000000010000001L;
    private static final long WHITE_KNIGHT =
            0b0000000000000000000000000000000000000000000000000000000001000010L;
    private static final long WHITE_BISHOP =
            0b0000000000000000000000000000000000000000000000000000000000100100L;
    private static final long WHITE_QUEEN =
            0b0000000000000000000000000000000000000000000000000000000000001000L;
    private static final long WHITE_KING =
            0b00000000000000000000000000000000000000000000000000000000000010000L;
    private static final long BLACK_PAWN =
            0b0000000011111111000000000000000000000000000000000000000000000000L;
    private static final long BLACK_ROOK =
            0b1000000100000000000000000000000000000000000000000000000000000000L;
    private static final long BLACK_KNIGHT =
            0b0100001000000000000000000000000000000000000000000000000000000000L;
    private static final long BLACK_BISHOP =
            0b0010010000000000000000000000000000000000000000000000000000000000L;
    private static final long BLACK_QUEEN =
            0b0000100000000000000000000000000000000000000000000000000000000000L;
    private static final long BLACK_KING =
            0b0001000000000000000000000000000000000000000000000000000000000000L;

    // ----------------------------------------------------------------------------------------------------------
    // Game State
    private boolean gameOver = false;
    private boolean isNewGame = true;
    private boolean turn = true;
    private String winMsg = ""; // Set at the end of the game for the team that won
    private final long[][] pieceLocations = new long[2][6]; // bitboards in the format [white, black] [Pawn, Rook, Knight, Bishop. Queen, King]

    // ----------------------------------------------------------------------------------------------------------
    private final JFrame javaF = new JFrame("Chess Engine");
    public int squareSize = 64;
    private final int[] twoClicks = new int[2];
    private int numClicks = 0;


    // Highlights in bitboard representation
    private long highlightSquares = 0L;
    private final JButton forfeitButton = new JButton("forfeitButton");
    private final JButton stalemateButton = new JButton("stalemateButton");
    static int border = 10; //the amount of empty space around the frame

    private final JButton pawnPromoteQueen = new JButton("stalemateButton"); // queen pawn promotion button
    private final JButton pawnPromoteKnight = new JButton("stalemateButton"); // queen pawn promotion button
    private final JButton pawnPromoteBishop = new JButton("stalemateButton"); // queen pawn promotion button
    private final JButton pawnPromoteRook = new JButton("stalemateButton"); // queen pawn promotion button


    // Holds chessboard exclusive listeners
    public BoardUI(Controller clickController, Presenter presenter) {
        this.clickController = clickController;
        this.presenter = presenter;
        addMouseListener(new MouseAdapter() {
            boolean highlightPossible;
            @Override
            public void mousePressed(MouseEvent e) {
                if (!gameOver) {
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    int x = mouseX / squareSize;
                    int y = 7 - mouseY / squareSize;
                    int index = y * 8 + x;
                    if (mouseX >= 0 && mouseX < 8 * squareSize && mouseY >= 0 && mouseY < 8 * squareSize) {
                        // Want to check if there is a piece at where we clicked.
                        // We only need valid move highlight for first click.
                        if (numClicks == 0) {
                            highlightPossible = clickController.processHighlight(index);
                            if (highlightPossible) {
                                twoClicks[numClicks] = index;
                                numClicks += 1;
                                repaint();
                            }
                        }
                        else {
                            twoClicks[numClicks] = index;
                            numClicks += 1;
                        }
                    }
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!gameOver) {
                    // If this was the second click
                    if (numClicks == 2) {
                        // Process the two clicks for valid moves
                        clickController.processTwoClicks(twoClicks);
                        numClicks = 0;
                        highlightSquares = 0L;
                    }
                    repaint();
                }
            }
        });
    }
    // ----------------------------------------------------------------------------------------------------------
    // Paint Chess Board
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(new Color(43, 45, 48));
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                squareSize= (Math.min(getHeight(), getWidth()-200-border)-2*border) /8;
            }
        });
        drawBoard(g);
        drawPieces(g, pieceLocations[0][0], PieceRendering.whitePawn);
        drawPieces(g, pieceLocations[0][1], PieceRendering.whiteRook);
        drawPieces(g, pieceLocations[0][2], PieceRendering.whiteKnight);
        drawPieces(g, pieceLocations[0][3], PieceRendering.whiteBishop);
        drawPieces(g, pieceLocations[0][4], PieceRendering.whiteQueen);
        drawPieces(g, pieceLocations[0][5], PieceRendering.whiteKing);
        drawPieces(g, pieceLocations[1][0], PieceRendering.blackPawn);
        drawPieces(g, pieceLocations[1][1], PieceRendering.blackRook);
        drawPieces(g, pieceLocations[1][2], PieceRendering.blackKnight);
        drawPieces(g, pieceLocations[1][3], PieceRendering.blackBishop);
        drawPieces(g, pieceLocations[1][4], PieceRendering.blackQueen);
        drawPieces(g, pieceLocations[1][5], PieceRendering.blackKing);
        drawBorders(g);
        drawBlackTeam(g);
        drawWhiteTeam(g);
        drawTimer(g);
        drawForfeit();
        drawStalemate();
        drawPromotionButtons();
        if (isNewGame) {
            isNewGame = false;
        }
        drawHighlights(g);
    }
    // ----------------------------------------------------------------------------------------------------------
    // ViewInterface methods
    @Override
    public void setBoard(long[][] bitboardArray) {
        for(int i = 0; i < 6; i++) {
            this.pieceLocations[0][i] = bitboardArray[0][i];
            this.pieceLocations[1][i] = bitboardArray[1][i];
        }
    }

    @Override
    public void setHighlights(long highlight) {
        this.highlightSquares = highlight;
    }

    @Override
    public void setTurn(boolean turn) {
        this.turn = turn;
    }
    // ----------------------------------------------------------------------------------------------------------
    // Components of paintComponent

    // Draws the chess board
    public void drawBoard(Graphics g) {
        for (int i=0;i<64;i+=2) {//draw chess board
            g.setColor(new Color(255, 255, 255));
            g.fillRect(((i%8+(i/8)%2)*squareSize) +border, ((i/8)*squareSize) +border, squareSize, squareSize);
            g.setColor(new Color(43, 45, 48));
            g.fillRect((((i+1)%8-((i+1)/8)%2)*squareSize) +border, (((i+1)/8)*squareSize) +border, squareSize, squareSize);
        }
    }
    // Draws the game borders
    public void drawBorders(Graphics g) {
        g.setColor(new Color(43, 45, 48));
        g.fill3DRect(0, border, border, 8*squareSize, true);
        g.fill3DRect((8*squareSize) +border, border, border, 8*squareSize, true);
        g.fill3DRect(border, 0, 8*squareSize, border, true);
        g.fill3DRect(border, (8*squareSize)+border, 8*squareSize, border, true);

        g.setColor(Color.BLACK);
        g.fill3DRect(0, 0, border, border, true);
        g.fill3DRect((8*squareSize) +border, 0, border, border, true);
        g.fill3DRect(0, (8*squareSize) +border, border, border, true);
        g.fill3DRect((8*squareSize) +border, (8*squareSize) +border, border, border, true);
        g.fill3DRect((8*squareSize) +2*border+200, 0, border, border, true);
        g.fill3DRect((8*squareSize) +2*border+200, (8*squareSize) +border, border, border, true);

        g.setColor(new Color(43, 45, 48));
        g.fill3DRect((8*squareSize) +2*border, 0, 200, border, true);

        g.fill3DRect((8*squareSize) +2*border+200, border, border, 8*squareSize, true);
        g.fill3DRect((8*squareSize) +2*border, (8*squareSize) +border, 200, border, true);
    }
    // Draws pieces
    public void drawPieces(Graphics g, long bitboard, Image pieceImage) {
        for (int i = 0; i < 64; i++) {
            if ((bitboard & (1L << i)) != 0) {
                int x = i % 8;
                int y = 7 - i / 8;
                g.drawImage(pieceImage,
                        x * squareSize + 10, y * squareSize + 10, squareSize, squareSize, null);
            }
        }
    }

    // Draw the piece move highlight
    public void drawHighlights(Graphics g) {
        int diameter = squareSize / 4;
        int radius = diameter / 2;
        for (int i = 0; i < 64; i++) {
            if ((highlightSquares & (1L << i)) != 0) {
                int x = i % 8;
                int y = 7 - i / 8;
                int centerX = x * squareSize + squareSize / 2;
                int centerY = y * squareSize + squareSize / 2;
                g.setColor(new Color(93, 111, 155));
                g.fillOval(centerX - radius + 9, centerY - radius + 10, diameter, diameter);
            }
        }
    }
    // ----------------------------------------------------------------------------------------------------------
    // Creation of the white teams points and captured pieces.
    public void drawWhiteTeam(Graphics g) {
        if (turn) {
            g.setColor(new Color(93, 111, 155));
        } else {
            g.setColor(new Color(52, 53, 56));
        }
        g.fill3DRect((8*squareSize) +2*border, border+(int)(2.8*squareSize), 200, (int)(2.8*squareSize), true);

        g.setColor(new Color(220, 216, 216, 255));
        g.drawString("White Team:", (8*squareSize) +2*border + 2, border*2+ (int)(2.8*squareSize) + 2);
        int whtPoints = 0;
        g.drawString(Integer.toString(whtPoints), (8*squareSize) +2*border + 175 - border, border*2+ (int)(2.8*squareSize) + 2);
    }

    // Creation of the black teams points and captured pieces.
    public void drawBlackTeam(Graphics g) {
        if (!turn) {
            g.setColor(new Color(93, 111, 155));
        } else {
            g.setColor(new Color(103, 106, 110, 255));
        }

        g.fill3DRect((8*squareSize) +2*border, border, 200, (int)(2.8*squareSize), true);

        g.setColor(new Color(0, 0, 0, 255));
        g.drawString("Black Team:", (8*squareSize) +2*border + 2, border*2+ 2);
        int blkPoints = 0;
        g.drawString(Integer.toString(blkPoints), (8*squareSize) +2*border + 175 - border, border*2 + 2);
    }
    // ----------------------------------------------------------------------------------------------------------
    // Buttons that updates who won and drawing for end screen.

    // Dispose frame here. Used by GameOver class
    public void disposeFrame() {
        javaF.dispose();
    }

    // Set win message for end screen
    public void setWinMsg(String winTeam) {
        if (Objects.equals(winTeam, "Black")) {
            winMsg = "Black Team Wins!";
        } else if (Objects.equals(winTeam, "White")){
            winMsg = "White Team Wins!";
        } else {
            winMsg = "It's a Draw!";
        }
    }

    // Creation of the draw button.
    public void drawStalemate() {
        Image unscaledIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/DrawIcon.png"))).getImage();
        Image stalemateIcon = unscaledIcon.getScaledInstance(50 , squareSize - 10, java.awt.Image.SCALE_SMOOTH);


        stalemateButton.setText("");
        stalemateButton.setFocusPainted(false);
        stalemateButton.setBounds((8*squareSize) +2*border + 100,border+(int)(6.15*squareSize),100, squareSize - 10);
        stalemateButton.setBackground(new Color(103, 106, 110));
        stalemateButton.setIcon(new ImageIcon(stalemateIcon));
        if (isNewGame) {
            stalemateButton.addActionListener(e -> {
                if (!gameOver) { // Makes sure the game isn't over

                    setWinMsg("draw");
                    drawEndScreen();
                }
            });
        }
        this.add(stalemateButton);
    }
    // Creation of the forfeit button.
    public void drawForfeit() {
        Image unscaledIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/Forfeit.png"))).getImage();
        Image forfeitIcon = unscaledIcon.getScaledInstance(40 , squareSize - 25, java.awt.Image.SCALE_SMOOTH);


        forfeitButton.setText("");
        forfeitButton.setFocusPainted(false);
        forfeitButton.setBounds((8*squareSize) +2*border,border+(int)(6.15*squareSize),100, squareSize - 10);
        forfeitButton.setBackground(new Color(44, 46, 51));
        forfeitButton.setIcon(new ImageIcon(forfeitIcon));
        if (isNewGame) {
            forfeitButton.addActionListener(e -> {
                if (!gameOver) { // Makes sure the game isn't over
                    if (turn) {
                        setWinMsg("Black");
                    } else {
                        setWinMsg("White");
                    }
                    drawEndScreen();
                }
            });
        }
        this.add(stalemateButton);
        this.add(forfeitButton);
    }

    // Creation of the timer UI located at the bottom right.
    public void drawTimer(Graphics g) {
        // Creation of the clock Borders
        g.setColor(new Color(44, 46, 51));
        g.fill3DRect((8*squareSize) +2*border, border+ (7*squareSize), 200, squareSize, true);
        g.setColor(new Color(190, 178, 157));
        g.fill3DRect((8*squareSize) +2*border + 5, border + 7 * squareSize + 5, 190, squareSize - 10, true);


        // Creation of the clock Label
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("TRUE TYPE_FONT", Font.BOLD, squareSize - 20));
        g.drawString("0:00", (8*squareSize) +2*border + squareSize - 15, border+ (7*squareSize+ squareSize - 15));
    }

    // Draws the pawn promotion buttons
    public void drawPromotionButtons() {
        // Queen promotion button configurations
        Image unscaledQIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/QueenPromote.png"))).getImage();
        Image queenIcon = unscaledQIcon.getScaledInstance(40 ,(int)(squareSize / 1.75), java.awt.Image.SCALE_SMOOTH);

        pawnPromoteQueen.setText("");
        pawnPromoteQueen.setFocusPainted(false);
        pawnPromoteQueen.setBounds((int)(8*squareSize +2.5*border), border + 5 * squareSize + (int) (squareSize / 1.7),40, (int)(squareSize / 1.75));
        pawnPromoteQueen.setIcon(new ImageIcon(queenIcon));

        // Knight promotion button configurations
        Image unscaledKIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/KnightPromote.png"))).getImage();
        Image knightIcon = unscaledKIcon.getScaledInstance(40 ,(int)(squareSize / 1.75), java.awt.Image.SCALE_SMOOTH);

        pawnPromoteKnight.setText("");
        pawnPromoteKnight.setFocusPainted(false);
        pawnPromoteKnight.setBounds((int)(8*squareSize +7.5 * border), border + 5 * squareSize + (int) (squareSize / 1.7),40, (int)(squareSize / 1.75));
        pawnPromoteKnight.setIcon(new ImageIcon(knightIcon));

        // Rook promotion button configurations
        Image unscaledRIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/RookPromote.png"))).getImage();
        Image rookIcon = unscaledRIcon.getScaledInstance(40 ,(int)(squareSize / 1.75), java.awt.Image.SCALE_SMOOTH);

        pawnPromoteRook.setText("");
        pawnPromoteRook.setFocusPainted(false);
        pawnPromoteRook.setBounds((int)(8*squareSize + 12.5 *border), border + 5 * squareSize + (int) (squareSize / 1.7),40, (int)(squareSize / 1.75));
        pawnPromoteRook.setIcon(new ImageIcon(rookIcon));

        // Bishop promotion button configurations
        Image unscaledBIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/BishopPromote.png"))).getImage();
        Image bishopIcon = unscaledBIcon.getScaledInstance(40 ,(int)(squareSize / 1.75), java.awt.Image.SCALE_SMOOTH);

        pawnPromoteBishop.setText("");
        pawnPromoteBishop.setFocusPainted(false);
        pawnPromoteBishop.setBounds((int)(8*squareSize + 17.5 *border), border + 5 * squareSize + (int) (squareSize / 1.7),40, (int)(squareSize / 1.75));
        pawnPromoteBishop.setIcon(new ImageIcon(bishopIcon));

        // Action listeners for pawn promotion buttons
        if (isNewGame) { // Ensures the action listeners are only added once
            pawnPromoteQueen.addActionListener(e -> {
                System.out.println("Promoting Queen!"); // Just a check that the button works can delete

                // hides the buttons after a pawn promotion
                pawnPromoteBishop.setVisible(false);
                pawnPromoteQueen.setVisible(false);
                pawnPromoteKnight.setVisible(false);
                pawnPromoteRook.setVisible(false);
            });
            pawnPromoteKnight.addActionListener(e -> {
                System.out.println("Promoting Knight!"); // Just a check that the button works can delete

                // hides the buttons after a pawn promotion
                pawnPromoteBishop.setVisible(false);
                pawnPromoteQueen.setVisible(false);
                pawnPromoteKnight.setVisible(false);
                pawnPromoteRook.setVisible(false);
            });
            pawnPromoteRook.addActionListener(e -> {
                System.out.println("Promoting Rook!"); // Just a check that the button works can delete

                // hides the buttons after a pawn promotion
                pawnPromoteBishop.setVisible(false);
                pawnPromoteQueen.setVisible(false);
                pawnPromoteKnight.setVisible(false);
                pawnPromoteRook.setVisible(false);
            });
            pawnPromoteBishop.addActionListener(e -> {
                System.out.println("Promoting Bishop!"); // Just a check that the button works can delete

                // hides the buttons after a pawn promotion
                pawnPromoteBishop.setVisible(false);
                pawnPromoteQueen.setVisible(false);
                pawnPromoteKnight.setVisible(false);
                pawnPromoteRook.setVisible(false);
            });
        }

        this.add(pawnPromoteQueen);
        this.add(pawnPromoteKnight);
        this.add(pawnPromoteRook);
        this.add(pawnPromoteBishop);
    }

    public void drawEndScreen() {
        Graphics g = this.getGraphics();
        g.setColor(new Color(0, 0, 0, 184));
        g.fillRect(0, 0, ((8)*squareSize) +border*2, ((8)*squareSize) +border*2);

        g.setColor(new Color(220, 216, 216));
        g.setFont(new Font("TRUE TYPE_FONT", Font.BOLD, squareSize - 20));
        g.drawString(winMsg, (3)*squareSize - (squareSize + 40), squareSize * 4);

        javaF.setIgnoreRepaint(true);
        javaF.setResizable(false);
        forfeitButton.setEnabled(false);
        stalemateButton.setEnabled(false);

        gameOver = true;

        clickController.startNewGame(); // Resets board for next game

        if (gameOverScreen == null) {
            gameOverScreen = new GameOverUI(clickController,this, presenter);
        } else {
            gameOverScreen.windowFrame.setVisible(true);
        }
    }

    // starts a new game instance
    public void newBoard() {
        // Setting initial game state
        pawnPromoteQueen.setVisible(false);
        pawnPromoteKnight.setVisible(false);
        pawnPromoteBishop.setVisible(false);
        pawnPromoteRook.setVisible(false);
        forfeitButton.setEnabled(true);
        stalemateButton.setEnabled(true);

        // Initialize piece positions
        long[][] bitboardArray = new long[2][6];

        bitboardArray[0][0] = WHITE_PAWN; // White Pawn
        bitboardArray[0][1] = WHITE_ROOK; // White Rook
        bitboardArray[0][2] = WHITE_KNIGHT; // White Knight
        bitboardArray[0][3] = WHITE_BISHOP; // White Bishop
        bitboardArray[0][4] = WHITE_QUEEN; // White Queen
        bitboardArray[0][5] = WHITE_KING; // White King

        bitboardArray[1][0] = BLACK_PAWN; // Black Pawn
        bitboardArray[1][1] = BLACK_ROOK; // Black Rook
        bitboardArray[1][2] = BLACK_KNIGHT; // Black Knight
        bitboardArray[1][3] = BLACK_BISHOP; // Black Bishop
        bitboardArray[1][4] = BLACK_QUEEN; // Black Queen
        bitboardArray[1][5] = BLACK_KING; // Black King

        this.setBoard(bitboardArray);

        // Setting the UI configurations
        javaF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        javaF.setSize(757, 570);
        javaF.add(this);
        javaF.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-javaF.getWidth())/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height-javaF.getHeight())/2);

        javaF.setVisible(true);
        javaF.setIgnoreRepaint(false);
        javaF.setResizable(true);

        javaF.repaint();
    }
}

