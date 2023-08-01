package View;

import Controller.Controller;
import Entities.ChessGame;
import Entities.LocationBitboard;
import UseCases.ActualValidCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class ChessBoardUI extends JPanel {
    private static LocationBitboard locationBitboard;
    static JFrame javaF=new JFrame("Chess Engine");//must be declared as static so that other class' can repaint
    static ChessBoardUI javaUI=new ChessBoardUI();//must be declared as static so that other class' can repaint
    public int squareSize = 64;
    private final int[] twoClicks = new int[2];
    private int numClicks = 0;
    private long highlightSquares = 0L;
    static JButton forfeitButton = new JButton("forfeitButton");
    static JButton stalemateButton = new JButton("stalemateButton");
    static int border = 10; //the amount of empty space around the frame

    static JButton pawnPromoteQueen = new JButton("stalemateButton"); // queen pawn promotion button
    static JButton pawnPromoteKnight = new JButton("stalemateButton"); // queen pawn promotion button
    static JButton pawnPromoteBishop = new JButton("stalemateButton"); // queen pawn promotion button
    static JButton pawnPromoteRook = new JButton("stalemateButton"); // queen pawn promotion button
    private final boolean isButtonConfigured = false;

    public static void main (String[]args){
        new MainMenu();
    }

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
        drawPieces(g, locationBitboard.getBitboard("whitePawn"), ImageRendering.whitePawn);
        drawPieces(g, locationBitboard.getBitboard("whiteRook"), ImageRendering.whiteRook);
        drawPieces(g, locationBitboard.getBitboard("whiteKnight"), ImageRendering.whiteKnight);
        drawPieces(g, locationBitboard.getBitboard("whiteBishop"), ImageRendering.whiteBishop);
        drawPieces(g, locationBitboard.getBitboard("whiteQueen"), ImageRendering.whiteQueen);
        drawPieces(g, locationBitboard.getBitboard("whiteKing"), ImageRendering.whiteKing);
        drawPieces(g, locationBitboard.getBitboard("blackPawn"), ImageRendering.blackPawn);
        drawPieces(g, locationBitboard.getBitboard("blackRook"), ImageRendering.blackRook);
        drawPieces(g, locationBitboard.getBitboard("blackKnight"), ImageRendering.blackKnight);
        drawPieces(g, locationBitboard.getBitboard("blackBishop"), ImageRendering.blackBishop);
        drawPieces(g, locationBitboard.getBitboard("blackQueen"), ImageRendering.blackQueen);
        drawPieces(g, locationBitboard.getBitboard("blackKing"), ImageRendering.blackKing);
        drawBorders(g);
        drawBlackTeam(g);
        drawWhiteTeam(g);
        drawTimer(g);
        drawForfeit();
        drawStalemate();
        drawPromotionButtons();
        if (ChessGame.getIsNewGame()) {
            ChessGame.setGameState(true);
        }
        drawHighlights(g);
    }

    // Holds chessboard exclusive listeners
    public ChessBoardUI() {
        // Get the current chessboard
        locationBitboard = ChessGame.getCurrentBoard();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (ChessGame.getIsGameOver()) {
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    System.out.println(locationBitboard);
                    int x = mouseX / squareSize;
                    int y = 7 - mouseY / squareSize;
                    int index = y * 8 + x;
                    boolean pieceFound = false;

                    if (mouseX >= 0 && mouseX < 8 * squareSize && mouseY >= 0 && mouseY < 8 * squareSize) {
                        for (String pieceType : locationBitboard.getAllPieces().keySet()) {
                            long bitboard = locationBitboard.getBitboard(pieceType);
                            // Want to check if there is a piece at where we clicked.
                            // We only need valid move highlight for first click.
                            if (((bitboard & (1L << index)) != 0) & (numClicks == 0)) {
                                boolean isWhite = pieceType.startsWith("white");
                                boolean isWhitesTurn = ChessGame.getTurn();
                                if ((isWhite && isWhitesTurn) || (!isWhite && !isWhitesTurn)) {
                                    pieceFound = true;
                                    long pieceLocation = bitboard & (1L << index);
                                    // need to refactor this for CA
                                    highlightSquares = ActualValidCalculator.actual_valid_moves(pieceLocation, isWhite, locationBitboard);
                                    break;
                                }
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
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (ChessGame.getIsGameOver()) {
                    // If this was the second click
                    if (numClicks == 2) {
                        // Process the two clicks for valid moves
                        Controller.process_two_clicks(twoClicks);
                        numClicks = 0;
                        highlightSquares = 0L;  // Clear the highlights
                        System.out.println(locationBitboard);
                    }
                    repaint();
                }
            }
        });
    }

    // Draws the chess board
    public void drawBoard(Graphics g) {
        for (int i=0;i<64;i+=2) {//draw chess board
            g.setColor(new Color(255, 255, 255));
            g.fillRect(((i%8+(i/8)%2)*squareSize) +border, ((i/8)*squareSize) +border, squareSize, squareSize);
            g.setColor(new Color(43, 45, 48));
            g.fillRect((((i+1)%8-((i+1)/8)%2)*squareSize) +border, (((i+1)/8)*squareSize) +border, squareSize, squareSize);
        }
    }

    // Draws the chess pieces on the board
    private void drawPieces(Graphics g, long bitboard, Image pieceImage) {
        for (int i = 0; i < 64; i++) {
            if ((bitboard & (1L << i)) != 0) {
                int x = i % 8;
                int y = 7 - i / 8;
                g.drawImage(pieceImage, x * squareSize + 10, y * squareSize + 10, squareSize, squareSize, null);
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

    // Draws the game borders
    public void drawBorders(Graphics g) {
        g.setColor(new Color(43, 45, 48));
        g.fill3DRect(0, border, border, 8*squareSize, true);
        g.fill3DRect((8*squareSize) +border, border, border, 8*squareSize, true);
        g.fill3DRect(border, 0, 8*squareSize, border, true);
        g.fill3DRect(border, (int)(8*squareSize)+border, 8*squareSize, border, true);

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

    // Creation of the white teams points and captured pieces.
    public void drawWhiteTeam(Graphics g) {
        if (ChessGame.getTurn()) {
            g.setColor(new Color(93, 111, 155));
        } else {
            g.setColor(new Color(52, 53, 56));
        }
        g.fill3DRect((8*squareSize) +2*border, border+(int)(2.8*squareSize), 200, (int)(2.8*squareSize), true);

        g.setColor(new Color(220, 216, 216, 255));
        g.drawString("White Team:", (8*squareSize) +2*border + 2, border*2+ (int)(2.8*squareSize) + 2);
        g.drawString(Integer.toString(ChessGame.getPoints(true)), (8*squareSize) +2*border + 175 - border, border*2+ (int)(2.8*squareSize) + 2);
    }

    // Creation of the black teams points and captured pieces.
    public void drawBlackTeam(Graphics g) {
        if (!ChessGame.getTurn()) {
            g.setColor(new Color(93, 111, 155));
        } else {
            g.setColor(new Color(103, 106, 110, 255));
        }

        g.fill3DRect((8*squareSize) +2*border, border, 200, (int)(2.8*squareSize), true);

        g.setColor(new Color(0, 0, 0, 255));
        g.drawString("Black Team:", (8*squareSize) +2*border + 2, border*2+ 2);
        g.drawString(Integer.toString(ChessGame.getPoints(false)), (8*squareSize) +2*border + 175 - border, border*2 + 2);
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
        if (ChessGame.getIsNewGame()) {
            stalemateButton.addActionListener(e -> {
                if (ChessGame.getIsGameOver()) { // Makes sure the game isn't over
                    // TODO put draw code here

                    ChessGame.setWinMsg("draw");
                    drawEndScreen();
                }
            });
        }
        this.add(stalemateButton);
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

    // Creation of the forfeit button.
    public void drawForfeit() {
        Image unscaledIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/Forfeit.png"))).getImage();
        Image forfeitIcon = unscaledIcon.getScaledInstance(40 , squareSize - 25, java.awt.Image.SCALE_SMOOTH);


        forfeitButton.setText("");
        forfeitButton.setFocusPainted(false);
        forfeitButton.setBounds((8*squareSize) +2*border,border+(int)(6.15*squareSize),100, squareSize - 10);
        forfeitButton.setBackground(new Color(44, 46, 51));
        forfeitButton.setIcon(new ImageIcon(forfeitIcon));
        if (ChessGame.getIsNewGame()) {
            forfeitButton.addActionListener(e -> {
                if (ChessGame.getIsGameOver()) { // Makes sure the game isn't over
                    if (ChessGame.getTurn()) {
                        ChessGame.setWinMsg("Black");
                    } else {
                        ChessGame.setWinMsg("White");
                    }
                    drawEndScreen();
                }
            });
        }
        this.add(stalemateButton);
        this.add(forfeitButton);
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
        if (ChessGame.getIsNewGame()) { // Ensures the action listeners are only added once
            pawnPromoteQueen.addActionListener(e -> {
                System.out.println("Promoting Queen!"); // Just a check that the button works can delete
                //TODO Put pawn promotion function/code here on button press

                // hides the buttons after a pawn promotion
                pawnPromoteBishop.setVisible(false);
                pawnPromoteQueen.setVisible(false);
                pawnPromoteKnight.setVisible(false);
                pawnPromoteRook.setVisible(false);
            });
            pawnPromoteKnight.addActionListener(e -> {
                System.out.println("Promoting Knight!"); // Just a check that the button works can delete
                //TODO Put pawn promotion function/code here on button press

                // hides the buttons after a pawn promotion
                pawnPromoteBishop.setVisible(false);
                pawnPromoteQueen.setVisible(false);
                pawnPromoteKnight.setVisible(false);
                pawnPromoteRook.setVisible(false);
            });
            pawnPromoteRook.addActionListener(e -> {
                System.out.println("Promoting Rook!"); // Just a check that the button works can delete
                //TODO Put pawn promotion function/code here on button press

                // hides the buttons after a pawn promotion
                pawnPromoteBishop.setVisible(false);
                pawnPromoteQueen.setVisible(false);
                pawnPromoteKnight.setVisible(false);
                pawnPromoteRook.setVisible(false);
            });
            pawnPromoteBishop.addActionListener(e -> {
                System.out.println("Promoting Bishop!"); // Just a check that the button works can delete
                //TODO Put pawn promotion function/code here on button press

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

    // Draws the end screen for the game
    public void drawEndScreen() {
        Graphics g = this.getGraphics();
        g.setColor(new Color(0, 0, 0, 184));
        g.fillRect(0, 0, ((8)*squareSize) +border*2, ((8)*squareSize) +border*2);

        g.setColor(new Color(220, 216, 216));
        g.setFont(new Font("TRUE TYPE_FONT", Font.BOLD, squareSize - 20));
        g.drawString(ChessGame.getWinMsg(), (3)*squareSize - (squareSize + 40), squareSize * 4);

        javaF.setIgnoreRepaint(true);
        javaF.setResizable(false);
        ChessGame.setGameState(false);

        new GameOver();
    }

    // starts a new game instance
    public static void newGame() {
        // Setting initial game state
        pawnPromoteQueen.setVisible(false);
        pawnPromoteKnight.setVisible(false);
        pawnPromoteBishop.setVisible(false);
        pawnPromoteRook.setVisible(false);
        ChessGame.new_game();
        ChessBoardUI board = new ChessBoardUI();

        // Setting the UI configurations
        javaF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        javaF.add(javaUI);
        javaF.setSize(757, 570);
        javaF.add(board);
        javaF.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-javaF.getWidth())/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height-javaF.getHeight())/2);

        javaF.setVisible(true);
        javaF.setIgnoreRepaint(false);
        javaF.setResizable(true);

        javaF.repaint();
    }
}

