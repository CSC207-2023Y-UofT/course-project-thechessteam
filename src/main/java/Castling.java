public class Castling {
    private final long whiteKingInitialPosition = InitialPositions.WHITE_KING;
    private final long whiteKingSideCastlingPosition = 0x40L;  // White king moves to square 'g1'
    private final long whiteQueenSideCastlingPosition = 0x4L;  // White king moves to square 'c1'
    private final long whiteKingSideRookInitialPosition = InitialPositions.WHITE_ROOK & 0x80L;
    private final long whiteQueenSideRookInitialPosition = InitialPositions.WHITE_ROOK & 0x1L;

    private final long blackKingInitialPosition = InitialPositions.BLACK_KING;
    private final long blackKingSideCastlingPosition = 0x40000000000000L;  // Black king moves to square 'g8'
    private final long blackQueenSideCastlingPosition = 0x400000000000L;  // Black king moves to square 'c8'
    private final long blackKingSideRookInitialPosition = InitialPositions.BLACK_ROOK & 0x80000000000000L;
    private final long blackQueenSideRookInitialPosition = InitialPositions.BLACK_ROOK & 0x100000000000000L;

    private boolean whiteKingMoved;
    private boolean whiteKingSideRookMoved;
    private boolean whiteQueenSideRookMoved;

    private boolean blackKingMoved;
    private boolean blackKingSideRookMoved;
    private boolean blackQueenSideRookMoved;

    // none of the pieces required are moved at first, set to false
    public Castling() {
        this.whiteKingMoved = false;  // TODO: update these to true if moved respectively
        this.whiteKingSideRookMoved = false;
        this.whiteQueenSideRookMoved = false;

        this.blackKingMoved = false;
        this.blackKingSideRookMoved = false;
        this.blackQueenSideRookMoved = false;
    }

    public boolean canCastleKingsideWhite() {
        long path = 0x60L; // squares between white king and king-side rook
        return !whiteKingMoved && !whiteKingSideRookMoved && isPathClear(path) && !isKingInCheck(whiteKingInitialPosition);
    }

    public boolean canCastleQueensideWhite() {
        long path = 224L; // squares between white king and queen-side rook
        return !whiteKingMoved && !whiteQueenSideRookMoved && isPathClear(path) && !isKingInCheck(whiteKingInitialPosition);
    }

    public boolean canCastleKingsideBlack() {
        long path = 0x6000000000000000L; // squares between black king and king-side rook
        return !blackKingMoved && !blackKingSideRookMoved && isPathClear(path) && !isKingInCheck(blackKingInitialPosition);
    }

    public boolean canCastleQueensideBlack() {
        long path = 2459565876494606880L; // squares between black king and queen-side rook
        return !blackKingMoved && !blackQueenSideRookMoved && isPathClear(path) && !isKingInCheck(blackKingInitialPosition);
    }

    // checks if the path that the king needs to move to castle is clear
    public boolean isPathClear(long path) {
        // Assuming you have a method that returns a long representing all pieces on the board
        long occupiedSquares = getOccupied();  // TODO: update this
        return (path & occupiedSquares) == 0;  // returns true if path is clear
    }

    // update this method when the Check class/methods are completed
    public boolean isKingInCheck(long kingPosition) {
        // TODO: implement when check is completed
    }

    public void performCastleKingsideWhite() {
        if (canCastleKingsideWhite()) {
            // Move the white king and the king-side rook to their castling positions
            movePiece(whiteKingInitialPosition, whiteKingSideCastlingPosition);
            movePiece(whiteKingSideRookInitialPosition, whiteKingInitialPosition >> 1); // Rook moves to the square next to the King
        }
    }

    public void performCastleQueensideWhite() {
        if (canCastleQueensideWhite()) {
            // Move the white king and the queen-side rook to their castling positions
            movePiece(whiteKingInitialPosition, whiteQueenSideCastlingPosition);
            movePiece(whiteQueenSideRookInitialPosition, whiteKingInitialPosition << 1); // Rook moves to the square next to the King
        }
    }

    public void performCastleKingsideBlack() {
        if (canCastleKingsideBlack()) {
            // Move the black king and the king-side rook to their castling positions
            movePiece(blackKingInitialPosition, blackKingSideCastlingPosition);
            movePiece(blackKingSideRookInitialPosition, blackKingInitialPosition >> 1); // Rook moves to the square next to the King
        }
    }

    public void performCastleQueensideBlack() {
        if (canCastleQueensideBlack()) {
            // Move the black king and the queen-side rook to their castling positions
            movePiece(blackKingInitialPosition, blackQueenSideCastlingPosition);
            movePiece(blackQueenSideRookInitialPosition, blackKingInitialPosition << 1); // Rook moves to the square next to the King
        }
    }

    // TODO: refactor this part
    public void movePiece(long from, long to) {
        occupied &= ~from;
        occupied |= to;
    }
}
