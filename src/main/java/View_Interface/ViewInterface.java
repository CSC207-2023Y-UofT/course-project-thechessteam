package View_Interface;

public interface ViewInterface {
    // Sets Presenter in View so that we can access it everytime we want to update UI.
    // When View updates UI with update_board_UI, it will be calling Presenter's update_pieces_UI,
    // making the Presenter update View.

    // Sets a representation of bitboardArray in View
    // bitboardArray is in the format [white, black] [Pawn, Rook, Knight, Bishop. Queen, King]
    void setBoard(long[][] bitboardArray);

    // Sets a representation of highlight in View. The highlight argument must be in bitboard representation
    void setHighlights(long highlight);

    // ----------------------------------------------------------------------------------------------------------
    //Setters of Game State

    // Sets the turn in View. The turn argument must be true for white and false for black
    void setTurn(boolean turn);

    // Sets the white points and black points in View.
    void setWhtPoints(int whtPoints);
    void setBlkPoints(int blkPoints);
}
