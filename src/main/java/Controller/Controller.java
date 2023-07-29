package Controller;

import UseCases.MovePiece;

public class Controller {
    // Takes in two clicks and pass it to use case classes.
    // Precondition: twoClicks is an integer array of length 2.
    public static void process_two_clicks(int[] twoClicks) {
        long from = 1L << twoClicks[0];
        long to = 1L << twoClicks[1];
        MovePiece.move_piece(from, to);
    }
}