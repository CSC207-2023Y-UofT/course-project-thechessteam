package Entities;

import java.util.Objects;

public class ChessGame {
    // A class to represent the game board we are using. Keeps track whose turn it is as well.

    // Various useful attributes. Use getters to access them.
    private static LocationBitboard currentBoard = new LocationBitboard();
    private static boolean turn = true; // true for White's turn, false for Black's turn
    private static int blkPoints = 0;
    private static int whtPoints = 0;
    private static boolean newGame = true; // Indicates that the game has not started yet
    private static boolean gameOver = false; // Indicates that the game is in the over and frames should not be updating
    private static String win_msg = ""; // Set at the end of the game for the team that won

    // ----------------------------------------------------------------------------------------------------------
    // Getter methods
    public static LocationBitboard getCurrentBoard() { // returns the board this Entities.ChessGame is using
        return currentBoard;
    }
    public static boolean getTurn() { return turn; }
    public static int getPoints(boolean t) {if (t) {return whtPoints;} else {return blkPoints;}}
    public static String getWinMsg() { return win_msg; }
    public static boolean getIsNewGame() { return newGame; }
    public static boolean getIsGameOver() { return !gameOver; }

    // ----------------------------------------------------------------------------------------------------------

    // Use this to change turn from White's turn to Black's turn, or vice versa
    public static void change_turn() {
        turn = !turn;
    }

    public static void setPoints(boolean team, int increment) {
        if (team) {
            whtPoints += increment;
        } else {
            blkPoints += increment;
        }
    }

    public static void setGameState(boolean gameState) {
        if (gameState) {
            newGame = !newGame;
        } else {
            gameOver = !gameOver;
        }
    }

    public static void setWinMsg(String winTeam) {
        if (Objects.equals(winTeam, "Black")) {
            win_msg = "Black Team Wins!";
        } else if (Objects.equals(winTeam, "White")){
            win_msg = "White Team Wins!";
        } else {
            win_msg = "It's a Draw!";
        }
    }

    // Start a new game by setting a new location bitboard
    public static void new_game() {
        currentBoard = new LocationBitboard();
        turn = true; // Make sure to start with White's turn
        newGame = true;
        gameOver = false;
        blkPoints = 0;
        whtPoints = 0;
        win_msg = "";
    }
}
