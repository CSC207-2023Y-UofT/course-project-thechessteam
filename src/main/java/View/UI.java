import RenamePackage.ChessBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Objects;

public class gameUI extends JPanel {

    static long WP=0L,WN=0L,WB=0L,WR=0L,WQ=0L,WK=0L,BP=0L,BN=0L,BB=0L,BR=0L,BQ=0L,BK=0L;
    static long UniversalWP=0L,UniversalWN=0L,UniversalWB=0L,UniversalWR=0L,UniversalWQ=0L,UniversalWK=0L,UniversalBP=0L,UniversalBN=0L,UniversalBB=0L,UniversalBR=0L,UniversalBQ=0L,UniversalBK=0L;
    static int humanIsWhite=1;
    static int rating=0;
    static boolean newGame = true; // Indicates that the game has not started yet
    static boolean gameOver = false; // Indicates that the game is in the over and frames should not be updating
    static int blkPoints = 0;
    static int whtPoints = 0;
    static int border=10;//the amount of empty space around the frame
    static double squareSize = 64;//the size of a chess board square
    static String winning_msg = ""; // Set at the end of the game for the team that won
    static JFrame javaF=new JFrame("Chess Engine");//must be declared as static so that other class' can repaint
    static gameUI javaUI=new gameUI();//must be declared as static so that other class' can repaint
    static JButton forfeitButton = new JButton("forfeitButton"); // forfeit Button
    static JButton stalemateButton = new JButton("stalemateButton"); // stalemate Button
    static JButton pawnPromoteQueen = new JButton("stalemateButton"); // queen pawn promotion button
    static JButton pawnPromoteKnight = new JButton("stalemateButton"); // queen pawn promotion button
    static JButton pawnPromoteBishop = new JButton("stalemateButton"); // queen pawn promotion button
    static JButton pawnPromoteRook = new JButton("stalemateButton"); // queen pawn promotion button


    // Ran whenever a new game instance starts
    public static void main(String[] args) {
        MainMenu newMenu = new MainMenu(); // Opens a new menu window when ran
    }

    // Paints the boarder for the board, the pieces, and the board itself
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(new Color(43, 45, 48));
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                squareSize=(double)(Math.min(getHeight(), getWidth()-200-border)-2*border)/8;
            }
        });
        drawBorders(g);
        drawBoard(g);
        drawPieces(g);
        drawBlackTeam(g);
        drawWhiteTeam(g);
        drawTimer(g);
        drawForfeit();
        drawStalemate();
        drawPromotionButtons();
        if (newGame) {
            newGame = false;
        }
    }

    // Creation of the chess board
    public void drawBoard(Graphics g) {
        for (int i=0;i<64;i+=2) {//draw chess board
            g.setColor(new Color(255, 255, 255));
            g.fillRect((int)((i%8+(i/8)%2)*squareSize)+border, (int)((i/8)*squareSize)+border, (int)squareSize, (int)squareSize);
            g.setColor(new Color(43, 45, 48));
            g.fillRect((int)(((i+1)%8-((i+1)/8)%2)*squareSize)+border, (int)(((i+1)/8)*squareSize)+border, (int)squareSize, (int)squareSize);
        }
    }

    // Creation of all the starting pieces
    public void drawPieces(Graphics g) {
        Image chessPieceImage;
        chessPieceImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/ChessPieces.png"))).getImage();
        for (int i=0;i<64;i++) {
            int j=-1,k=-1;
            if (((WP>>i)&1)==1) {j=5; k=1-humanIsWhite;}
            else if (((BP>>i)&1)==1) {j=5; k=humanIsWhite;}
            else if (((WB>>i)&1)==1) {j=3;k=1-humanIsWhite;}
            else if (((BB>>i)&1)==1) {j=3;k=humanIsWhite;}
            else if (((WN>>i)&1)==1) {j=4;k=1-humanIsWhite;}
            else if (((BN>>i)&1)==1) {j=4;k=humanIsWhite;}
            else if (((WQ>>i)&1)==1) {j=1;k=1-humanIsWhite;}
            else if (((BQ>>i)&1)==1) {j=1;k=humanIsWhite;}
            else if (((WR>>i)&1)==1) {j=2;k=1-humanIsWhite;}
            else if (((BR>>i)&1)==1) {j=2;k=humanIsWhite;}
            else if (((WK>>i)&1)==1) {j=0;k=1-humanIsWhite;}
            else if (((BK>>i)&1)==1) {j=0;k=humanIsWhite;}
            if (j!=-1 && k!=-1) {
                g.drawImage(chessPieceImage, (int)((i%8)*squareSize)+border, (int)((i/8)*squareSize)+border, (int)((i%8+1)*squareSize)+border, (int)((i/8+1)*squareSize)+border, j*64, k*64, (j+1)*64, (k+1)*64, this);
            }
        }
    }

    // Creation of the borders around the whole board
    public void drawBorders(Graphics g) {
        g.setColor(new Color(43, 45, 48));
        g.fill3DRect(0, border, border, (int)(8*squareSize), true);
        g.fill3DRect((int)(8*squareSize)+border, border, border, (int)(8*squareSize), true);
        g.fill3DRect(border, 0, (int)(8*squareSize), border, true);
        g.fill3DRect(border, (int)(8*squareSize)+border, (int)(8*squareSize), border, true);

        g.setColor(Color.BLACK);
        g.fill3DRect(0, 0, border, border, true);
        g.fill3DRect((int)(8*squareSize)+border, 0, border, border, true);
        g.fill3DRect(0, (int)(8*squareSize)+border, border, border, true);
        g.fill3DRect((int)(8*squareSize)+border, (int)(8*squareSize)+border, border, border, true);
        g.fill3DRect((int)(8*squareSize)+2*border+200, 0, border, border, true);
        g.fill3DRect((int)(8*squareSize)+2*border+200, (int)(8*squareSize)+border, border, border, true);

        g.setColor(new Color(43, 45, 48));
        g.fill3DRect((int)(8*squareSize)+2*border, 0, 200, border, true);

        g.fill3DRect((int)(8*squareSize)+2*border+200, border, border, (int)(8*squareSize), true);
        g.fill3DRect((int)(8*squareSize)+2*border, (int)(8*squareSize)+border, 200, border, true);
    }

    // Creation of the black teams points and captured pieces
    public void drawBlackTeam(Graphics g) {
        g.setColor(new Color(103, 106, 110, 255));
        g.fill3DRect((int)(8*squareSize)+2*border, border, 200, (int)(2.8*squareSize), true);

        g.setColor(new Color(0, 0, 0, 255));
        g.drawString("Black Team:", (int)(8*squareSize)+2*border + 2, border*2+ 2);
        g.drawString(Integer.toString(blkPoints), (int)(8*squareSize)+2*border + 175 - border, border*2 + 2);
    }

    // Creation of the white teams points and captured pieces
    public void drawWhiteTeam(Graphics g) {
        g.setColor(new Color(52, 53, 56));
        g.fill3DRect((int)(8*squareSize)+2*border, border+(int)(2.8*squareSize), 200, (int)(2.8*squareSize), true);

        g.setColor(new Color(220, 216, 216, 255));
        g.drawString("White Team:", (int)(8*squareSize)+2*border + 2, border*2+ (int)(2.8*squareSize) + 2);
        g.drawString(Integer.toString(whtPoints), (int)(8*squareSize)+2*border + 175 - border, border*2+ (int)(2.8*squareSize) + 2);
    }

    // Creation of the timer UI located at the bottom right
    public void drawTimer(Graphics g) {
        // Creation of the clock Borders
        g.setColor(new Color(44, 46, 51));
        g.fill3DRect((int)(8*squareSize)+2*border, border+(int)(7*squareSize), 200, (int)(1*squareSize), true);
        g.setColor(new Color(190, 178, 157));
        g.fill3DRect((int)(8*squareSize)+2*border + 5, border+(int)(7*squareSize+5), 190, (int)(1*squareSize - 10), true);


        // Creation of the clock Label
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("TRUE TYPE_FONT", Font.BOLD, (int)(1*squareSize) - 20));
        g.drawString("0:00", (int)(8*squareSize)+2*border + (int)(1*squareSize) - 15, border+(int)(7*squareSize+ (int)(1*squareSize)- 15));
    }

    // Creation of the forfeit button
    public void drawForfeit() {
        Image unscaledIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/Forfeit.png"))).getImage();
        Image forfeitIcon = unscaledIcon.getScaledInstance(40 ,(int)(1*squareSize) - 25, java.awt.Image.SCALE_SMOOTH);


        forfeitButton.setText("");
        forfeitButton.setFocusPainted(false);
        forfeitButton.setBounds((int)(8*squareSize)+2*border,border+(int)(6.15*squareSize),100, (int)(1*squareSize) - 10);
        forfeitButton.setBackground(new Color(44, 46, 51));
        forfeitButton.setIcon(new ImageIcon(forfeitIcon));
        if (newGame) {
            forfeitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!gameOver) { // Makes sure the game isn't over
                        drawEndScreen(winning_msg);
                    }
                }
            });
        }
        this.add(stalemateButton);
        this.add(forfeitButton);
    }

    // Creation of the draw button
    public void drawStalemate() {
        Image unscaledIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/DrawIcon.png"))).getImage();
        Image stalemateIcon = unscaledIcon.getScaledInstance(50 ,(int)(1*squareSize) - 10, java.awt.Image.SCALE_SMOOTH);


        stalemateButton.setText("");
        stalemateButton.setFocusPainted(false);
        stalemateButton.setBounds((int)(8*squareSize)+2*border + 100,border+(int)(6.15*squareSize),100, (int)(1*squareSize) - 10);
        stalemateButton.setBackground(new Color(103, 106, 110));
        stalemateButton.setIcon(new ImageIcon(stalemateIcon));
        if (newGame) {
            stalemateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!gameOver) { // Makes sure the game isn't over
                        System.out.println("Requesting a draw");
                        drawEndScreen("It's a draw!");
                    }
                }
            });
        }
        this.add(stalemateButton);
    }

    public void drawPromotionButtons() {
        // Queen promotion button configurations
        Image unscaledQIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/QueenPromote.png"))).getImage();
        Image queenIcon = unscaledQIcon.getScaledInstance(40 ,(int)(1*squareSize / 1.75), java.awt.Image.SCALE_SMOOTH);

        pawnPromoteQueen.setText("");
        pawnPromoteQueen.setFocusPainted(false);
        pawnPromoteQueen.setBounds((int)(8*squareSize +2.5*border),border+(int)(5*squareSize + (int)(squareSize / 1.7)),40, (int)(1*squareSize / 1.75));
        pawnPromoteQueen.setIcon(new ImageIcon(queenIcon));

        // Knight promotion button configurations
        Image unscaledKIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/KnightPromote.png"))).getImage();
        Image knightIcon = unscaledKIcon.getScaledInstance(40 ,(int)(1*squareSize / 1.75), java.awt.Image.SCALE_SMOOTH);

        pawnPromoteKnight.setText("");
        pawnPromoteKnight.setFocusPainted(false);
        pawnPromoteKnight.setBounds((int)(8*squareSize +7.5 * border),border+(int)(5*squareSize + (int)(squareSize / 1.7)),40, (int)(1*squareSize / 1.75));
        pawnPromoteKnight.setIcon(new ImageIcon(knightIcon));

        // Rook promotion button configurations
        Image unscaledRIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/RookPromote.png"))).getImage();
        Image rookIcon = unscaledRIcon.getScaledInstance(40 ,(int)(1*squareSize / 1.75), java.awt.Image.SCALE_SMOOTH);

        pawnPromoteRook.setText("");
        pawnPromoteRook.setFocusPainted(false);
        pawnPromoteRook.setBounds((int)(8*squareSize + 12.5 *border),border+(int)(5*squareSize + (int)(squareSize / 1.7)),40, (int)(1*squareSize / 1.75));
        pawnPromoteRook.setIcon(new ImageIcon(rookIcon));

        // Bishop promotion button configurations
        Image unscaledBIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/projectimages/BishopPromote.png"))).getImage();
        Image bishopIcon = unscaledBIcon.getScaledInstance(40 ,(int)(1*squareSize / 1.75), java.awt.Image.SCALE_SMOOTH);

        pawnPromoteBishop.setText("");
        pawnPromoteBishop.setFocusPainted(false);
        pawnPromoteBishop.setBounds((int)(8*squareSize + 17.5 *border),border+(int)(5*squareSize + (int)(squareSize / 1.7)),40, (int)(1*squareSize / 1.75));
        pawnPromoteBishop.setIcon(new ImageIcon(bishopIcon));

        // Action listeners for pawn promotion buttons
        if (newGame) { // Ensures the action listeners are only added once
            pawnPromoteQueen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Promoting Queen!"); // Just a check that the button works can delete
                    // Put pawn promotion function/code here on button press

                    // hides the buttons after a pawn promotion
                    pawnPromoteBishop.setVisible(false);
                    pawnPromoteQueen.setVisible(false);
                    pawnPromoteKnight.setVisible(false);
                    pawnPromoteRook.setVisible(false);
                }
            });
            pawnPromoteKnight.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Promoting Knight!"); // Just a check that the button works can delete
                    // Put pawn promotion function/code here on button press

                    // hides the buttons after a pawn promotion
                    pawnPromoteBishop.setVisible(false);
                    pawnPromoteQueen.setVisible(false);
                    pawnPromoteKnight.setVisible(false);
                    pawnPromoteRook.setVisible(false);
                }
            });
            pawnPromoteRook.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Promoting Rook!"); // Just a check that the button works can delete
                    // Put pawn promotion function/code here on button press

                    // hides the buttons after a pawn promotion
                    pawnPromoteBishop.setVisible(false);
                    pawnPromoteQueen.setVisible(false);
                    pawnPromoteKnight.setVisible(false);
                    pawnPromoteRook.setVisible(false);
                }
            });
            pawnPromoteBishop.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Promoting Bishop!"); // Just a check that the button works can delete
                    // Put pawn promotion function/code here on button press

                    // hides the buttons after a pawn promotion
                    pawnPromoteBishop.setVisible(false);
                    pawnPromoteQueen.setVisible(false);
                    pawnPromoteKnight.setVisible(false);
                    pawnPromoteRook.setVisible(false);
                }
            });
        }

        this.add(pawnPromoteQueen);
        this.add(pawnPromoteKnight);
        this.add(pawnPromoteRook);
        this.add(pawnPromoteBishop);
    }

    public void drawEndScreen(String winning_msg) {
        Graphics g = this.getGraphics();
        g.setColor(new Color(0, 0, 0, 184));
        g.fillRect(0, 0, (int)((8)*squareSize)+border*2, (int)((8)*squareSize)+border*2);

        g.setColor(new Color(220, 216, 216));
        g.setFont(new Font("TRUE TYPE_FONT", Font.BOLD, (int)(1*squareSize) - 20));
        g.drawString(winning_msg, (int)((3)*squareSize - (squareSize + 40)), (int) squareSize * 4);

        javaF.setIgnoreRepaint(true);
        javaF.setResizable(false);
        gameOver = true;

        GameOver gameOverScreen = new GameOver();
    }

    public static void newGame() {
        // Setting initial game state
        humanIsWhite = 1;
        newGame = true;
        gameOver = false;
        blkPoints = 0;
        whtPoints = 0;
        rating = 0;
        winning_msg = "";
        pawnPromoteQueen.setVisible(false);
        pawnPromoteKnight.setVisible(false);
        pawnPromoteBishop.setVisible(false);
        pawnPromoteRook.setVisible(false);
        ChessBoard.initiateStandardChess();; // initializes pieces and starts the game


        // Setting the UI configurations
        javaF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        javaF.add(javaUI);
        javaF.setSize(757, 570);
        javaF.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-javaF.getWidth())/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height-javaF.getHeight())/2);
        javaF.setVisible(true);
        javaF.repaint();
    }
}