// commented out pawn
// this class is used for highlighting the GUI

public class PieceFactory {
    public static Calculator getPiece(String pieceType) {
        switch (pieceType.toLowerCase()) {
            case "pawn":
                return new Pawn();
            case "rook":
                return new Rook();
            case "knight":
                return new Knight();
            case "bishop":
                return new Bishop();
            case "queen":
                return new Queen();
            case "king":
                return new King();
            default:
                throw new IllegalArgumentException("Invalid piece type: " + pieceType);
        }
    }
}
