import java.util.Arrays;

/**
 * Represents a chess board using bitboards, where each type of piece (and color) is associated with a separate bitboard. (So there are 12).
 */
public class ChessBoard {

    /**
     * An enumeration of the different pieces and their colors.
     */
    public enum Piece {
        EMPTY, WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK
    }

    // An array of bitboards, one for each piece
    private long[] bitboards = new long[Piece.values().length];

    /**
     * Constructs a new ChessBoard from a given 2D string array.
     *
     * @param board The initial configuration of the board, represented as a 2D string array.
     */
    public ChessBoard(String[][] board) {
        for (int i = 0; i < 64; i++) {
            String piece = board[i / 8][i % 8];
            if (!piece.equals(" ")) {
                // Append "W" for white pieces and "B" for black pieces
                String pieceEnumName = (piece.equals(piece.toUpperCase()) ? "W" : "B") + piece.toUpperCase();
                Piece pieceEnum = Piece. valueOf(pieceEnumName);
                bitboards[pieceEnum.ordinal()] |= (1L << i);
            }
        }
    }

    /**
     * Converts the current bitboards to a 2D string array representation of the board.
     *
     * @return A 2D string array representing the current state of the chess board.
     */
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

    /**
     * Returns a string representation of the chess board.
     *
     * @return A string that visually represents the current state of the chess board.
     */
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
