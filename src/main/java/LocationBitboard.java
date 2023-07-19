public class LocationBitboard {
    // Create this class each time a new game starts; holds location of pieces.
    // long[] to use as container for updates.
    // Public variables for readability and to avoid too many getters and setters.
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

    public LocationBitboard(){}

}
