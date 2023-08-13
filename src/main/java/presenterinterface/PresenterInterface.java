package presenterinterface;

public interface PresenterInterface {

    // Updates locations of pieces held in an instance variable of presenter.
    // It will be an array of bitboards in order of: [white, black] [Pawn, Rook, Knight, Bishop. Queen, King]
    void updateLocations(int j, long bitboard, boolean color);

    // Sets the locations of pieces in view implementing view interface
    void setLocation();

    // Sets the locations of highlight in view implementing view interface
    void setHighlight(long highlight);

    // Sets the current turn in view implementing view interface
    // currentTurn is true for white and false for black
    void setTurn(boolean currentTurn);
}
