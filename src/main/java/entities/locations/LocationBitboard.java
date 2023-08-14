package entities.locations;

import entities.constants.FileAndRank;
import entities.constants.InitialPositions;

/**
 * Represents the chessboard using bitboards to store the locations of each chess piece.
 * It includes attributes and methods to manage and manipulate piece locations.
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
    // Stores the location of the white pawn that moved two in previous turn.
    // 0L if white pawn did not move two space in previous turn.
    private long whitePawnMovedTwo = 0L;
    // Stores the location of the black pawn that moved two in previous turn.
    // 0L if black pawn did not move two space in previous turn.
    private long blackPawnMovedTwo = 0L;
    // Indicates whether white king or either white rooks moved.
    private boolean whiteKingMoved = false;
    private boolean leftWhiteRookMoved = false;
    private boolean rightWhiteRookMoved = false;
    // Indicates whether black king or either black rooks moved.
    private boolean blackKingMoved = false;
    private boolean leftBlackRookMoved = false;
    private boolean rightBlackRookMoved = false;

    // Various useful attributes. Use getters to access them.
    private final long[][] whitePieces = {whitePawn, whiteRook, whiteKnight,
            whiteBishop, whiteQueen, whiteKing};
    private final long[][] blackPieces = {blackPawn, blackRook, blackKnight,
            blackBishop, blackQueen, blackKing};
    private long whiteLocations = 0L;
    private long blackLocations = 0L;
    {
        for (long[] pieceType : whitePieces) {
            whiteLocations |= pieceType[0];
        }

        for (long[] pieceType : blackPieces) {
            blackLocations |= pieceType[0];
        }
    }
    private long occupied = whiteLocations | blackLocations;

    /**
     * Getters returns the specified attributes
     */
    public long[][] getWhitePieces() {return whitePieces; }
    public long[][] getBlackPieces() {return blackPieces; }
    public long getWhiteLocations() { // returns a bitboard showing all squares occupied by White
        return whiteLocations;
    }
    public long getBlackLocations() { // returns a bitboard showing all squares occupied by Black
        return blackLocations;
    }
    public long getOccupied() { // returns a bitboard showing occupied squares
        return occupied;
    }
    public long locationWhitePawnMovedTwo() {
        return whitePawnMovedTwo;
    }
    public long locationBlackPawnMovedTwo() {
        return blackPawnMovedTwo;
    }
    // Moved booleans for kings and rooks
    public boolean getWhiteKingNotMoved(){
        return !whiteKingMoved;
    }
    public boolean getBlackKingNotMoved(){
        return !blackKingMoved;
    }
    public boolean getLeftRookMovedW(){
        return leftWhiteRookMoved;
    }
    public boolean getRightRookMovedW(){
        return rightWhiteRookMoved;
    }
    public boolean getLeftRookMovedB(){
        return leftBlackRookMoved;
    }
    public boolean getRightRookMovedB(){
        return rightBlackRookMoved;
    }

    /**
     * Moves a piece from one location to another.
     * @param from the source location.
     * @param to the destination location.
     * @param turn true for White's turn, false for Black's turn.
     * @return true if the move was successful, false if there was no piece at "from".
     */
    public boolean movePiece(long from, long to, boolean turn) {
        boolean moved = false;
        if (turn) { // White's turn
            for (long[] pieceType : whitePieces) {
                if ((pieceType[0] & from) != 0L) { // if from is in White's bitboard of this pieceType
                    updatePiece(pieceType, from, to, true); // Update location of the bitboard above
                    moved = true;
                    break;
                }
            }
        }
        else {
            for (long[] pieceType : blackPieces) {
                if ((pieceType[0] & from) != 0L) { // if from is in Black's bitboard of this pieceType
                    updatePiece(pieceType, from, to, false); // Update location of the bitboard above
                    moved = true;
                    break;
                }
            }
        }
        return moved;
    }

    /**
     * Method for updating all location variables.
     */
    public void updateLocationVariables(){
        long newWhiteLocations = 0L;
        for (long[] pieceType : whitePieces) {
            newWhiteLocations |= pieceType[0];
        }
        whiteLocations = newWhiteLocations;

        long newBlackLocations = 0L;
        for (long[] pieceType : blackPieces) {
            newBlackLocations |= pieceType[0];
        }
        blackLocations = newBlackLocations;

        occupied = whiteLocations | blackLocations;
    }

    /**
     * Updates the state of a chess piece on the board after a move has been made.
     * Takes into account special moves like castling and en passant.
     *
     * @param pieceType An array containing the piece's type.
     * @param from      The starting location of the piece.
     * @param to        The destination location of the piece.
     * @param turn      A boolean value representing whose turn it is (true for White, false for Black).
     */
    private void updatePiece(long[] pieceType, long from, long to, boolean turn) {
        // These will be updated as true if we moved a pawn two spaces forward.
        whitePawnMovedTwo = 0L;
        blackPawnMovedTwo = 0L;

        updateForSpecialConditions(pieceType, from, to);

        pieceType[0] = (pieceType[0] & ~from) | to; // Move the piece of pieceType

        // Remove opponent piece at to
        removeOpponentPieceAtTo(turn, to);

        // Update all location variables: whiteLocations, blackLocations, occupied
        updateLocationVariables();
    }

    // Move pieces for special chess rules: Castling, En Passant.
    // Also updates instance variables in this class related to these rules.
    private void updateForSpecialConditions(long[] pieceType, long from, long to) {
        if (!checkAndUpdateCastling(pieceType, from, to)) {
            if (!checkAndUpdatePawn(pieceType, from, to)) {
                checkAndUpdateRookMoved(pieceType, from);
            }
        }
    }

    // Helper methods for updatePiece

    // Check if we are castling and update accordingly. Returns true if we castled.
    private boolean checkAndUpdateCastling(long[] pieceType, long from, long to) {
        boolean checkedCastling = false;
        if (pieceType[0] == whiteKing[0]) { // if we are moving a white king
            if (from >>> 2 == to) { // castling to queen side
                updateRookForCastling(whiteRook, true, true);
            } else if (from << 2 == to) { // castling to king side
                updateRookForCastling(whiteRook, false, true);
            }
            whiteKingMoved = true;
            checkedCastling = true;
        } else if (pieceType[0] == blackKing[0]) { // if we are moving a black king
            if (from >>> 2 == to) { // castling to queen side
                updateRookForCastling(blackRook, true, false);
            } else if (from << 2 == to) { // castling to king side
                updateRookForCastling(blackRook, false, false);
            }
            blackKingMoved = true;
            checkedCastling = true;
        }
        return checkedCastling;
    }

    // Check if we are performing en passant. Update if opponent will be able to perform en passant next turn.
    private boolean checkAndUpdatePawn(long[] pieceType, long from, long to) {
        boolean movingPawn = false;
        if (pieceType[0] == whitePawn[0]) { // if we are moving a white pawn
            // if we are moving without capturing
            if (((to & blackLocations) == 0L) &&
                    // if we are not moving straight forward when we are at Rank 5
                    ((from & FileAndRank.RANK_5) != 0L) && (to != (from << 8))) {
                // then we perform en passant
                updateEnPassant(to, true);
            }
            // Update whitePawnMovedTwo
            if (((from & FileAndRank.RANK_2) != 0L) && ((to & FileAndRank.RANK_4) != 0L)) {
                whitePawnMovedTwo = to;
            }
            movingPawn = true;
        } else if (pieceType[0] == blackPawn[0]) { // if we are moving a black pawn
            // if we are moving without capturing
            if (((to & whiteLocations) == 0L) &&
                    // if we are not moving straight forward when we are at Rank 4
                    ((from & FileAndRank.RANK_4) != 0L) && (to != (from >>> 8))) {
                // then we perform en passant
                updateEnPassant(to, false);
            }
            // Update blackPawnMovedTwo
            if (((from & FileAndRank.RANK_7) != 0L) && ((to & FileAndRank.RANK_5) != 0L)) {
                blackPawnMovedTwo = to;
            }
            movingPawn = true;
        }
        return movingPawn;
    }

    // Update boolean instance variables for whether a rook moved
    private void checkAndUpdateRookMoved(long[] pieceType, long from) {
        if (pieceType[0] == whiteRook[0]) { // if we are moving a white rook
            if (from == 1L) {
                leftWhiteRookMoved = true;
            }
            else if (from == (1L << 7)) {
                rightWhiteRookMoved = true;
            }
        } else if (pieceType[0] == blackRook[0]) { // if we are moving a black rook
            if (from == (1L << 56)) {
                leftBlackRookMoved = true;
            } else if (from == (1L << 63)) {
                rightBlackRookMoved = true;
            }
        }
    }

    /**
     * Updates the rook's position when castling.
     * This method handles both king side and queen side castling for both colors.
     *
     * @param rookLocations An array representing the rook's current locations.
     * @param direction     A boolean value representing the direction of castling (true for queen side, false for king side).
     * @param color         A boolean value representing the color of the rook (true for White, false for Black).
     */
    private void updateRookForCastling (long[] rookLocations, boolean direction, boolean color) {
        // direction == true for queen side, direction == false for king side
        // color == true for White, color == false for Black
        if (color) { // if moving a white king
            if (direction) {
                // Move the rook for queen side castling
                rookLocations[0] = (rookLocations[0] & ~(1L)) | (1L << 3);
            } else {
                // Move the rook for king side castling
                rookLocations[0] = (rookLocations[0] & ~(1L << 7)) | (1L << 5);
            }
        } else { // if moving a black king
            if (direction) {
                // Move the rook for queen side castling
                rookLocations[0] = (rookLocations[0] & ~(1L << 56)) | (1L << 59);
            } else {
                // Move the rook for king side castling
                rookLocations[0] = (rookLocations[0] & ~(1L << 63)) | (1L << 61);
            }
        }
    }

    /**
     * Executes the en passant move by updating the pawn's location.
     *
     * @param to   The destination location of the pawn that performed en passant.
     * @param side A boolean value representing the side that performed en passant (true for White, false for Black).
     */
    private void updateEnPassant (long to, boolean side) {
        if (side) {
            blackPawn[0] = blackPawn[0] & ~(to >>> 8);
        }
        else {
            whitePawn[0] = whitePawn[0] & ~(to << 8);
        }
    }

    // Remove opponent piece at to
    private void removeOpponentPieceAtTo(boolean turn, long to) {
        if (turn) { // White's turn
            for (long[] blackPieceType : getBlackPieces()) {
                blackPieceType[0] &= ~to;
            }
        } else {
            for (long[] whitePieceType : getWhitePieces()) {
                whitePieceType[0] &= ~to;
            }
        }
    }

    /**
     * Constructor for creating an instance of Entities.Locations.LocationBitboard.
     * Initializes all the bitboards to the starting positions of the pieces
     */
    public LocationBitboard(){}
}
