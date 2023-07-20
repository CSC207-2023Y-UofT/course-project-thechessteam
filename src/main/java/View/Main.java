package View;

import Entities.ChessBoard;

public class Main {
    // View.Main method for testing
    public static void main(String[] args) {
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

        // Create a Entities.ChessBoard object with the initial board setup
        ChessBoard cb = new ChessBoard(chessBoard);
        // Print the Entities.ChessBoard object
        System.out.println(cb);
    }
}