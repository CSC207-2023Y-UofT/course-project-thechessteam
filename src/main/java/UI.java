import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.Objects;

public class UI extends JPanel {

    static long WP=0L,WN=0L,WB=0L,WR=0L,WQ=0L,WK=0L,BP=0L,BN=0L,BB=0L,BR=0L,BQ=0L,BK=0L;
    static long UniversalWP=0L,UniversalWN=0L,UniversalWB=0L,UniversalWR=0L,UniversalWQ=0L,UniversalWK=0L,UniversalBP=0L,UniversalBN=0L,UniversalBB=0L,UniversalBR=0L,UniversalBQ=0L,UniversalBK=0L;
    static int humanIsWhite=1;
    static int rating=0;
    static boolean newGame = true;
    static int blkPoints = 0;
    static int whtPoints = 0;
    static int border=10;//the amount of empty space around the frame
    static double squareSize=64;//the size of a chess board square
    static JFrame javaF=new JFrame("Chess Engine");//must be declared as static so that other class' can repaint
    static UI javaUI=new UI();//must be declared as static so that other class' can repaint
    static JButton forfeitButton = new JButton("forfeitButton");
    static JButton stalemateButton = new JButton("stalemateButton");


    // Ran whenever a new game instance starts
    public static void main(String[] args) {
        javaF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        javaF.add(javaUI);
        javaF.setSize(757, 570);
        javaF.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-javaF.getWidth())/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height-javaF.getHeight())/2);
        javaF.setVisible(true);
        javaF.repaint();
        newGame(); // initializes pieces and starts the game
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
        forfeitButton.setBounds((int)(8*squareSize)+2*border,border+(int)(6*squareSize),100, (int)(1*squareSize));
        forfeitButton.setBackground(new Color(44, 46, 51));
        forfeitButton.setIcon(new ImageIcon(forfeitIcon));
        if (newGame) {
            forfeitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Forfeited");
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
        stalemateButton.setBounds((int)(8*squareSize)+2*border + 100,border+(int)(6*squareSize),100, (int)(1*squareSize));
        stalemateButton.setBackground(new Color(103, 106, 110));
        stalemateButton.setIcon(new ImageIcon(stalemateIcon));
        if (newGame) {
            stalemateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Requesting Stalemate");
                }
            });
        }
        this.add(stalemateButton);
    }

    public static void newGame() {
        ChessBoard.initiateStandardChess();
    }
}