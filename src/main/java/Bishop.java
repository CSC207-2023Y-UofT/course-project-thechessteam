// Bishop class implementing the Calculator interface
public class Bishop implements Calculator {

    // Default constructor for Bishop
    public Bishop() {}

    // Method to calculate all valid moves for a bishop given its position, the side it's on (white or black), and the current state of the board
    public long valid_moves(long from, int side, LocationBitboard board) {
        // This method uses a technique called Hyperbola Quintessence for move generation

        // Find the number of trailing zeros in the binary representation of 'from' (bishop's position)
        int s = Long.numberOfTrailingZeros(from);

        // Masks for calculating diagonal and anti-diagonal movements
        long diag_m = FileAndRank.DiagonalMasks8[(s / 8) + (s % 8)];
        long anti_m = FileAndRank.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)];

        // Create a bitboard that represents all occupied squares
        long o = board.blackPawn[0] | board.blackRook[0] | board.blackKnight[0]
                | board.blackBishop[0] | board.blackQueen[0] | board.blackKing[0]
                | board.whitePawn[0] | board.whiteRook[0] | board.whiteKnight[0]
                | board.whiteBishop[0] | board.whiteQueen[0] | board.whiteKing[0];

        // Use the Hyperbola Quintessence technique to find possible diagonal and anti-diagonal moves
        long diagonal_candidate = (
                ((o & diag_m) - 2 * from) ^ Long.reverse(
                        Long.reverse(o & diag_m) - 2 * Long.reverse(from))
        ) & diag_m;
        long anti_diagonal_candidate = (
                ((o & anti_m) - 2 * from) ^ Long.reverse(
                        Long.reverse(o & anti_m) - 2 * Long.reverse(from))
        ) & anti_m;

        // Create a bitboard that represents all pieces of the current side
        long your_pieces;
        if (side == 0) {  // If side is 0, the current side is white
            your_pieces = board.whitePawn[0] | board.whiteRook[0] | board.whiteKnight[0]
                    | board.whiteBishop[0] | board.whiteQueen[0] | board.whiteKing[0];
        }
        else {  // If side is not 0, the current side is black
            your_pieces = board.blackPawn[0] | board.blackRook[0] | board.blackKnight[0]
                    | board.blackBishop[0] | board.blackQueen[0] | board.blackKing[0];
        }

        // Subtract the pieces of the current side from the set of possible moves to prevent self-capture
        long actual_diagonal = diagonal_candidate & ~your_pieces;
        long actual_anti = anti_diagonal_candidate & ~your_pieces;

        // Return a bitboard that represents all valid moves for the bishop
        return actual_diagonal | actual_anti;
    }

    // Method to calculate the squares that all bishops of a side could attack
    public long attack_coverage(int side, LocationBitboard board){
        // Initialize coverage to 0
        long coverage = 0L;
        long bishop_locations;
        // If side is 0, calculate for white's bishops. Otherwise, calculate for black's bishops
        if (side == 0) {
            bishop_locations = board.whiteBishop[0];
        }
        else {
            bishop_locations = board.blackBishop[0];
        }
        // For each square on the board
        for(int i = 0; i < 64; i++) {
            // If a bishop of the current side is on the square
            if ((int) ((bishop_locations >>> i) & 1) == 1) {
                // Add the squares it can attack to coverage
                coverage |= valid_moves(1L << i, side, board);
            }
        }
        // Return a bitboard that represents all squares that the bishops of the current side could attack
        return coverage;
    }
}

