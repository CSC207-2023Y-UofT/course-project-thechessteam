import java.util.Arrays;

// A class representing a chess board using bitboards
public class ChessBoard {

    // An enumeration of the different pieces and their colors
    public enum Piece {
        EMPTY, WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK
    }

    // An array of bitboards, one for each piece
    private static long[] bitboards = new long[Piece.values().length];

    // Constructor that takes a 2D array of strings representing the initial board setup
    public ChessBoard(String[][] board) {
        for (int i = 0; i < 64; i++) {
            String piece = board[i / 8][i % 8];
            if (!piece.equals(" ")) {  // if piece on the current square is not empty
                // Append "W" for white pieces and "B" for black pieces
                String pieceEnumName = (piece.equals(piece.toUpperCase()) ? "W" : "B") + piece.toUpperCase();
                Piece pieceEnum = Piece. valueOf(pieceEnumName);
                bitboards[pieceEnum.ordinal()] |= (1L << i);
            }
        }
    }

    public static void initiateStandardChess() {
        long WP=0L,WN=0L,WB=0L,WR=0L,WQ=0L,WK=0L,BP=0L,BN=0L,BB=0L,BR=0L,BQ=0L,BK=0L;  // initializes every piece to 000000...0

        String chessBoard[][]={
                {"r","n","b","q","k","b","n","r"},
                {"p","p","p","p","p","p","p","p"},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {"P","P","P","P","P","P","P","P"},
                {"R","N","B","Q","K","B","N","R"}
        };

        // need to convert this 2d string array into bitboards, we are initializing it that way
        arrayToBitboards(chessBoard,WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK);
//        System.out.println(Arrays.toString(bitboards));
    }

    // converts the 2DStringArray
    public static void arrayToBitboards(String[][] chessBoard,long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK) {
        String Binary;
        for (int i=0;i<64;i++) {
            Binary="0000000000000000000000000000000000000000000000000000000000000000";
            Binary=Binary.substring(i+1)+"1"+Binary.substring(0, i);
            switch (chessBoard[i/8][i%8]) {
                case "P": WP+=convertStringToBitboard(Binary);
                    break;
                case "N": WN+=convertStringToBitboard(Binary);
                    break;
                case "B": WB+=convertStringToBitboard(Binary);
                    break;
                case "R": WR+=convertStringToBitboard(Binary);
                    break;
                case "Q": WQ+=convertStringToBitboard(Binary);
                    break;
                case "K": WK+=convertStringToBitboard(Binary);
                    break;
                case "p": BP+=convertStringToBitboard(Binary);
                    break;
                case "n": BN+=convertStringToBitboard(Binary);
                    break;
                case "b": BB+=convertStringToBitboard(Binary);
                    break;
                case "r": BR+=convertStringToBitboard(Binary);
                    break;
                case "q": BQ+=convertStringToBitboard(Binary);
                    break;
                case "k": BK+=convertStringToBitboard(Binary);
                    break;
            }
        }
        drawArray(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK);
        UI.WP=WP; UI.WN=WN; UI.WB=WB;
        UI.WR=WR; UI.WQ=WQ; UI.WK=WK;
        UI.BP=BP; UI.BN=BN; UI.BB=BB;
        UI.BR=BR; UI.BQ=BQ; UI.BK=BK;
    }

    public static long convertStringToBitboard(String Binary) {
        if (Binary.charAt(0)=='0') {//not going to be a negative number
            return Long.parseLong(Binary, 2);
        } else {
            return Long.parseLong("1"+Binary.substring(2), 2)*2;
        }
    }

    public static void drawArray(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK) {
        String chessBoard[][]=new String[8][8];
        for (int i=0;i<64;i++) {
            chessBoard[i/8][i%8]=" ";
        }
        for (int i=0;i<64;i++) {
            if (((WP>>i)&1)==1) {chessBoard[i/8][i%8]="P";}
            if (((WN>>i)&1)==1) {chessBoard[i/8][i%8]="N";}
            if (((WB>>i)&1)==1) {chessBoard[i/8][i%8]="B";}
            if (((WR>>i)&1)==1) {chessBoard[i/8][i%8]="R";}
            if (((WQ>>i)&1)==1) {chessBoard[i/8][i%8]="Q";}
            if (((WK>>i)&1)==1) {chessBoard[i/8][i%8]="K";}
            if (((BP>>i)&1)==1) {chessBoard[i/8][i%8]="p";}
            if (((BN>>i)&1)==1) {chessBoard[i/8][i%8]="n";}
            if (((BB>>i)&1)==1) {chessBoard[i/8][i%8]="b";}
            if (((BR>>i)&1)==1) {chessBoard[i/8][i%8]="r";}
            if (((BQ>>i)&1)==1) {chessBoard[i/8][i%8]="q";}
            if (((BK>>i)&1)==1) {chessBoard[i/8][i%8]="k";}
        }
        for (int i=0;i<8;i++) {
            System.out.println(Arrays.toString(chessBoard[i]));
        }
    }

    // Method that converts the bitboards back into a 2D array of strings
    private String[][] bitboardsToArray() {
        String[][] board = new String[8][8];
        for (int i = 0; i < 64; i++) {
            // Initialize all squares to empty (" ")
            board[i / 8][i % 8] = " ";
        }
        for (Piece p : Piece.values()) {
            if (p != Piece.EMPTY) {
                for (int i = 0; i < 64; i++) {
                    if ((bitboards[p.ordinal()] & (1L << i)) != 0) {
                        // Add the piece to the array
                        String pieceName = p.name().substring(1); // Remove the prefix 'W' or 'B'
                        if (p.ordinal() > 6) {
                            // if the piece is black, convert to lower case
                            board[i / 8][i % 8] = pieceName.toLowerCase();
                        } else {
                            // if the piece is white, keep it in upper case
                            board[i / 8][i % 8] = pieceName.toUpperCase();
                        }
                    }
                }
            }
        }
        return board;
    }

    // Method that returns a string representation of the board
    @Override
    public String toString() {
        String[][] board = bitboardsToArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(Arrays.toString(board[i])).append("\n");
        }
        return sb.toString();
    }
}