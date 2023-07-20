public interface Calculator {
    // side = 0 to represent White
    // side = 1 to represent Black

    // Calculates all valid moves a player can make for the piece at from.
    // from is a bitboard with 1 at the location of the piece and 0s for the rest.
    // Returns a bitboard with 1s at valid moves and 0s for the rest.
    long valid_moves(long from, int side, LocationBitboard board);

    // Calculates the attack coverage of the piece type inheriting Calculator interface.
    // Note: It is attack coverage so only diagonal moves of pawns are attacks.
    // Used to check for check.
    // For example, for Pawn class, it could use the bit "or" operation to combine bitboards in pawn_attacks
    // Returns a bitboard with 1s at "can attack" locations and 0s for the rest.
    long attack_coverage(int side, LocationBitboard board);

    // TODO Create Pawn, Rook, Knight, Bishop, Queen, and King Class and have them implement Calculator.
    // TODO Implement the methods accordingly.
    // TODO Use PreCalculatedAttacks accordingly.

    public class King implements Calculator {

        // Create this King class for each king and update LocationBitboard when the king moves.

        private final LocationBitboard board;

        public King(LocationBitboard board) {
            this.board = board;
        }

        @Override
        public long valid_moves(long from, int side) {
            int square = Long.numberOfTrailingZeros(from);

            // Calculate possible king attacks from the current square
            long attacks = PreCalculatedAttacks.king_attacks[square];

            // You can't move into a square that's attacked by an opponent's piece
            long opponentAttacks = (side == 0) ? attack_coverage(1) : attack_coverage(0);

            // You can't move into a square occupied by one of your own pieces
            long ownPieces = (side == 0) ?
                    (board.whitePawn[0] | board.whiteKnight[0] | board.whiteBishop[0] |
                            board.whiteRook[0] | board.whiteQueen[0] | board.whiteKing[0]) :
                    (board.blackPawn[0] | board.blackKnight[0] | board.blackBishop[0] |
                            board.blackRook[0] | board.blackQueen[0] | board.blackKing[0]);

            // Remove the squares that the king can't move into
            return attacks & ~opponentAttacks & ~ownPieces;
        }

        @Override
        public long attack_coverage(int side) {
            long kings = (side == 0) ? board.whiteKing[0] : board.blackKing[0];
            long attackBoard = 0L;

            // Loop through each king
            while (kings != 0) {
                int kingLocation = Long.numberOfTrailingZeros(kings);

                // Add the attacks for this king to the attackBoard
                attackBoard |= PreCalculatedAttacks.king_attacks[kingLocation];

                // Remove this king from the kings bitboard
                kings &= kings - 1;
            }

            return attackBoard;
        }
    }



}
