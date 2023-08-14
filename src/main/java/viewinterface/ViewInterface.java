package viewinterface;

public interface ViewInterface {
    // Sets Presenter in view so that we can access it everytime we want to update UI.
    // When view updates UI with update_board_UI, it will be calling Presenter's update_pieces_UI,
    // making the Presenter update view.

    // Sets a representation of bitboardArray in view
    // bitboardArray is in the format [white, black] [Pawn, Rook, Knight, Bishop. Queen, King]
    void setBoard(long[][] bitboardArray);

    // Sets a representation of highlight in view. The highlight argument must be in bitboard representation
    void setHighlights(long highlight);

    // ----------------------------------------------------------------------------------------------------------
    //Setters of Game State

    // Sets the turn in view. The turn argument must be true for white and false for black
    void setTurn(boolean turn);

    /* Unused code for now
    // Sets the white points and black points in view.
    void setWhtPoints(int whtPoints);
    void setBlkPoints(int blkPoints); */
}
