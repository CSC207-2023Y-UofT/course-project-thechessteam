/**
 * This class represents a chessboard using bitboards for each pieces location
 * A new instance of this class should be created at the beginning of each game
 * Each type of piece (for both white and black) is represented by a long array, where each element
 * is a bitboard that represents the locations of pieces of that type.
 * Array of longs for flexibility. (long[])
 */
public class LocationBitboard {
    public long[] whitePawn = new long[]{InitialPositions.WHITE_PAWN};
    public long[] whiteRook = new long[]{InitialPositions.WHITE_ROOK};
    public long[] whiteKnight = new long[]{InitialPositions.WHITE_KNIGHT};
    public long[] whiteBishop = new long[]{InitialPositions.WHITE_BISHOP};
    public long[] whiteQueen = new long[]{InitialPositions.WHITE_QUEEN};
    public long[] whiteKing = new long[]{InitialPositions.WHITE_KING};
    public long[] blackPawn = new long[]{InitialPositions.BLACK_PAWN};
    public long[] blackRook = new long[]{InitialPositions.BLACK_ROOK};
    public long[] blackKnight = new long[]{InitialPositions.BLACK_KNIGHT};
    public long[] blackBishop = new long[]{InitialPositions.BLACK_BISHOP};
    public long[] blackQueen = new long[]{InitialPositions.BLACK_QUEEN};
    public long[] blackKing = new long[]{InitialPositions.BLACK_KING};

    /**
     * Constructor for creating an instance of LocationBitboard.
     * Initializes all the bitboards to the starting positions of the pieces
     */
    public LocationBitboard(){}
}
