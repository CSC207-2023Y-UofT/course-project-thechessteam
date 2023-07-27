public class ChessPiece {
    private final String pieceType;
    private final long location;

    public ChessPiece(String pieceType, long location) {
        this.pieceType = pieceType;
        this.location = location;
    }

    public String getPieceType() {
        return pieceType;
    }

    public long getLocation() {
        return location;
    }
}
