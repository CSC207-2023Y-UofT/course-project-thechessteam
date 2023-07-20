/**
 * This class represents a chess board using bitboards for each piece's location.
 * A new instance of this class should be created at the beginning of each game.
 * It uses public variables for readability and to avoid excessive usage of getter and setter methods.
 * Each type of piece (for both white and black) is represented by a long array, where each element
 * is a bitboard that represents the locations of pieces of that type.
 */
public class LocationBitboard {
    // Create this class each time a new game starts; holds location of pieces.
    // long[] to use as container for updates.
    // Public variables for readability and to avoid too many getters and setters.

    /**
     * The bitboards representing the locations of the white pawns.
     */
    public long[] whitePawn = new long[]{InitialPositions.WHITE_PAWN};

    /**
     * The bitboards representing the locations of the white rooks.
     */
    public long[] whiteRook = new long[]{InitialPositions.WHITE_ROOK};

    /**
     * The bitboards representing the locations of the white knights.
     */
    public long[] whiteKnight = new long[]{InitialPositions.WHITE_KNIGHT};

    /**
     * The bitboards representing the locations of the white bishops.
     */
    public long[] whiteBishop = new long[]{InitialPositions.WHITE_BISHOP};

    /**
     * The bitboards representing the locations of the white queen.
     */
    public long[] whiteQueen = new long[]{InitialPositions.WHITE_QUEEN};

    /**
     * The bitboards representing the locations of the white king.
     */
    public long[] whiteKing = new long[]{InitialPositions.WHITE_KING};

    /**
     * The bitboards representing the locations of the black pawns.
     */
    public long[] blackPawn = new long[]{InitialPositions.BLACK_PAWN};

    /**
     * The bitboards representing the locations of the black rooks.
     */
    public long[] blackRook = new long[]{InitialPositions.BLACK_ROOK};

    /**
     * The bitboards representing the locations of the black knights.
     */
    public long[] blackKnight = new long[]{InitialPositions.BLACK_KNIGHT};

    /**
     * The bitboards representing the locations of the black bishops.
     */
    public long[] blackBishop = new long[]{InitialPositions.BLACK_BISHOP};


    /**
     * The bitboards representing the locations of the black queen.
     */
    public long[] blackQueen = new long[]{InitialPositions.BLACK_QUEEN};

    /**
     * The bitboards representing the locations of the black king.
     */
    public long[] blackKing = new long[]{InitialPositions.BLACK_KING};

    /**
     * Constructor for creating an instance of LocationBitboard.
     * Initializes all the bitboards to the starting positions of the pieces.
     */
    public LocationBitboard(){}

}
