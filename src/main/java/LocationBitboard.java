import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a chessboard using bitboards for each pieces location
 * A new instance of this class should be created at the beginning of each game
 * Each type of piece (for both white and black) is represented by a long array, where each element
 * is a bitboard that represents the locations of pieces of that type.
 * Array of longs for flexibility. (long[])
 */
public class LocationBitboard {
    private Map<String, long[]> pieceBitboards = new HashMap<>();

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
    public LocationBitboard(){
        pieceBitboards.put("whitePawn", new long[]{InitialPositions.WHITE_PAWN});
        pieceBitboards.put("whiteRook", new long[]{InitialPositions.WHITE_ROOK});
        pieceBitboards.put("whiteKnight", new long[]{InitialPositions.WHITE_KNIGHT});
        pieceBitboards.put("whiteBishop", new long[]{InitialPositions.WHITE_BISHOP});
        pieceBitboards.put("whiteQueen", new long[]{InitialPositions.WHITE_QUEEN});
        pieceBitboards.put("whiteKing", new long[]{InitialPositions.WHITE_KING});
        pieceBitboards.put("blackPawn", new long[]{InitialPositions.BLACK_PAWN});
        pieceBitboards.put("blackRook", new long[]{InitialPositions.BLACK_ROOK});
        pieceBitboards.put("blackKnight", new long[]{InitialPositions.BLACK_KNIGHT});
        pieceBitboards.put("blackBishop", new long[]{InitialPositions.BLACK_BISHOP});
        pieceBitboards.put("blackQueen", new long[]{InitialPositions.BLACK_QUEEN});
        pieceBitboards.put("blackKing", new long[]{InitialPositions.BLACK_KING});
    }

    public Map<String, Long> getAllPieces() {
        Map<String, Long> bitboards = new HashMap<>();
        for (Map.Entry<String, long[]> entry : pieceBitboards.entrySet()) {
            bitboards.put(entry.getKey(), entry.getValue()[0]);
        }
        return bitboards;
    }

    public long getBitboard(String pieceType) {
        return pieceBitboards.get(pieceType)[0];
    }

    public void setBitboard(String pieceType, long bitboard) {
        pieceBitboards.get(pieceType)[0] = bitboard;
    }
}
