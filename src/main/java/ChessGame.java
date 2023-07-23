public class ChessGame {
    // A class to represent the game board we are using. Keeps track whose turn it is as well.
    // Castling will be tied to King valid moves. We assume that if King moves two spaces, we are castling.

    // Various useful attributes. Use getters to access them.
    private final LocationBitboard currentBoard = new LocationBitboard();
    private final long[][] whitePieces = {currentBoard.whitePawn, currentBoard.whiteRook, currentBoard.whiteKnight,
            currentBoard.whiteBishop, currentBoard.whiteQueen, currentBoard.whiteKing};
    private final long[][] blackPieces = {currentBoard.blackPawn, currentBoard.blackRook, currentBoard.blackKnight,
            currentBoard.blackBishop, currentBoard.blackQueen, currentBoard.blackKing};
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
    private boolean turn = true; // true for White's turn, false for Black's turn

    // ----------------------------------------------------------------------------------------------------------
    // Getter methods
    public LocationBitboard getCurrentBoard() { // returns the board this ChessGame is using
        return currentBoard;
    }
    public long getWhiteLocations() { // returns a bitboard showing all squares occupied by White
        return whiteLocations;
    }
    public long getBlackLocations() { // returns a bitboard showing all squares occupied by Black
        return blackLocations;
    }
    public long getOccupied() { // returns a bitboard showing occupied squares
        return occupied;
    }
    public boolean getTurn() { return turn; }

    // ----------------------------------------------------------------------------------------------------------
    // Constructor
    public ChessGame(){}
    // General Methods for updating location and changing turn

    // move_piece updates all variables related to the move.
    // Returns true if move was successful, returns false if there was no piece at "from".
    // Assume moving piece from "from" to "to" is a valid move.
    public boolean move_piece(long from, long to) {
        boolean moved = false;
        if (turn) { // White's turn
            for (long[] pieceType : whitePieces) {
                if ((pieceType[0] & from) != 0L) { // if from is in White's bitboard of this pieceType
                    update_piece(pieceType, from, to); // Update location of the bitboard above
                    moved = true;
                    break;
                }
            }
        }
        else {
            for (long[] pieceType : blackPieces) {
                if ((pieceType[0] & from) != 0L) { // if from is in Black's bitboard of this pieceType
                    update_piece(pieceType, from, to); // Update location of the bitboard above
                    moved = true;
                    break;
                }
            }
        }
        return moved;
    }
    // Helper method for move_piece
    private void update_piece(long[] pieceType, long from, long to) {
        if (pieceType[0] == currentBoard.whiteKing[0]) { // if we are moving a white king
            if (from >>> 2 == to) { // castling to queen side
                update_rook_for_castling(currentBoard.whiteRook, true, true);
            } else if (from << 2 == to) { // castling to king side
                update_rook_for_castling(currentBoard.whiteRook, false, true);
            }
        }
        else if (pieceType[0] == currentBoard.blackKing[0]) { // if we are moving a black king
            if (from >>> 2 == to) { // castling to queen side
                update_rook_for_castling(currentBoard.blackRook, true, false);
            } else if (from << 2 == to) { // castling to king side
                update_rook_for_castling(currentBoard.blackRook, false, false);
            }

        }
        pieceType[0] = (pieceType[0] & ~from) | to; // Move the piece of pieceType
        updateLocationVariables(); // Update all the location variables to reflect the move
    }

    // Helper method for update_piece
    private void update_rook_for_castling(long[] rookLocations, boolean direction, boolean color) {
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
    // Helper method for updating all variables representing locations
    private void updateLocationVariables(){
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

    // Use this to change turn from White's turn to Black's turn, or vice versa
    public void change_turn() {
        turn = !turn;
    }
}
