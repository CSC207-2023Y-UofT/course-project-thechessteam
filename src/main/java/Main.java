/**
 * This is the main class that starts and manages the game of chess.
 * It initializes the chess board and prints it to the standard output.
 */
 public class Main {
    /**
     * The main method for testing the gme of chess.
     * It initializes a 2D array representing the chessboard
     * and then creates a ChessBoard object with this initial setup.
     * It then prnts the ChessBoard object to the standard output
     *
     * @param args the command-line arguments (not used here)
     */
    public static void main(String[] args) {
        // lowercase for Black, uppercase for White.
        String[][] chessBoard = {
                {"r", "n", "b", "q", "k", "b", "n", "r"},
                {"p", "p", "p", "p", "p", "p", "p", "p"},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {"P", "P", "P", "P", "P", "P", "P", "P"},
                {"R", "N", "B", "Q", "K", "B", "N", "R"}
        };

        // Create a ChessBoard object with the initial board setup
        ChessBoard cb = new ChessBoard(chessBoard);
        // Print the ChessBoard object
        System.out.println(cb);
    }
}