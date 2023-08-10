package Entities.Locations;

import Entities.Constants.FileAndRank;
import Entities.Constants.InitialPositions;

/**
 * This class represents a chessboard using bitboards for each pieces location
 * A new instance of this class should be created at the beginning of each game
 * Each type of piece (for both white and black) is represented by a long array, where each element
 * is a bitboard that represents the locations of pieces of that type.
 * Array of longs for flexibility. (long[])
 * Castling will be tied to Entities.Pieces.King valid moves. We assume that if Entities.Pieces.King moves two spaces, we are castling.
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
    // ----------------------------------------------------------------------------------------------------------
    // Getter methods
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
    public boolean getWhiteKingMoved(){
        return whiteKingMoved;
    }
    public boolean getBlackKingMoved(){
        return blackKingMoved;
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

    // ----------------------------------------------------------------------------------------------------------
    // move_piece method for updating location

    // move_piece updates all variables related to the move.
    // Returns true if move was successful, returns false if there was no piece at "from".
    // Assume moving piece from "from" to "to" is a valid move.
    public boolean move_piece(long from, long to, boolean turn) {
        boolean moved = false;
        if (turn) { // White's turn
            for (long[] pieceType : whitePieces) {
                if ((pieceType[0] & from) != 0L) { // if from is in White's bitboard of this pieceType
                    update_piece(pieceType, from, to, true); // Update location of the bitboard above
                    moved = true;
                    break;
                }
            }
        }
        else {
            for (long[] pieceType : blackPieces) {
                if ((pieceType[0] & from) != 0L) { // if from is in Black's bitboard of this pieceType
                    update_piece(pieceType, from, to, false); // Update location of the bitboard above
                    moved = true;
                    break;
                }
            }
        }
        return moved;
    }
    // Method for updating all location variables
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
    // ----------------------------------------------------------------------------------------------------------
    // Helper methods

    // Helper method for move_piece
    private void update_piece(long[] pieceType, long from, long to, boolean turn) {
        // These will be updated as true if we moved a pawn two spaces forward.
        whitePawnMovedTwo = 0L;
        blackPawnMovedTwo = 0L;

        update_for_special_conditions(pieceType, from, to);

        pieceType[0] = (pieceType[0] & ~from) | to; // Move the piece of pieceType

        // Remove opponent piece at to
        remove_opponent_piece_at_to(turn, to);

        // Update all location variables: whiteLocations, blackLocations, occupied
        updateLocationVariables();
    }

    // Move pieces for special chess rules: Castling, En Passant.
    // Also updates instance variables in this class related to these rules.
    private void update_for_special_conditions(long[] pieceType, long from, long to) {
        if (pieceType[0] == whiteKing[0]) { // if we are moving a white king
            if (from >>> 2 == to) { // castling to queen side
                update_rook_for_castling(whiteRook, true, true);
            } else if (from << 2 == to) { // castling to king side
                update_rook_for_castling(whiteRook, false, true);
            }
            whiteKingMoved = true;
        } else if (pieceType[0] == blackKing[0]) { // if we are moving a black king
            if (from >>> 2 == to) { // castling to queen side
                update_rook_for_castling(blackRook, true, false);
            } else if (from << 2 == to) { // castling to king side
                update_rook_for_castling(blackRook, false, false);
            }
            blackKingMoved = true;
        } else if (pieceType[0] == whitePawn[0]) { // if we are moving a white pawn
            // if we are moving without capturing
            if (((to & blackLocations) == 0L) &&
                    // if we are not moving straight forward when we are at Rank 5
                    ((from & FileAndRank.RANK_5) != 0L) && (to != (from << 8))) {
                // then we perform en passant
                update_en_passant(to, true);
            }
            // Update whitePawnMovedTwo
            if (((from & FileAndRank.RANK_2) != 0L) && ((to & FileAndRank.RANK_4) != 0L)) {
                whitePawnMovedTwo = to;
            }
        } else if (pieceType[0] == blackPawn[0]) { // if we are moving a black pawn
            // if we are moving without capturing
            if (((to & whiteLocations) == 0L) &&
                    // if we are not moving straight forward when we are at Rank 4
                    ((from & FileAndRank.RANK_4) != 0L) && (to != (from >>> 8))) {
                // then we perform en passant
                update_en_passant(to, false);
            }
            // Update blackPawnMovedTwo
            if (((from & FileAndRank.RANK_7) != 0L) && ((to & FileAndRank.RANK_5) != 0L)) {
                blackPawnMovedTwo = to;
            }
        } else if (pieceType[0] == whiteRook[0]) { // if we are moving a white rook
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
    // Helper methods for update_piece
    private void update_rook_for_castling (long[] rookLocations, boolean direction, boolean color) {
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

    private void update_en_passant (long to, boolean side) {
        if (side) {
            blackPawn[0] = blackPawn[0] & ~(to >>> 8);
        }
        else {
            whitePawn[0] = whitePawn[0] & ~(to << 8);
        }
    }

    // Remove opponent piece at to
    private void remove_opponent_piece_at_to(boolean turn, long to) {
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
    // ----------------------------------------------------------------------------------------------------------

    /**
     * Constructor for creating an instance of Entities.Locations.LocationBitboard.
     * Initializes all the bitboards to the starting positions of the pieces
     */
    public LocationBitboard(){}
}
