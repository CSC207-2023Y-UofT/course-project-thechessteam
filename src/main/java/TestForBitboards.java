public class TestForBitboards {
    public static void main(String[] args){
        print_board_state();
    }

    // array_representation follows standard bitboard representation where
    // bit 0 represent square a1, bit 7 is square h1,
    // bit 56 is square a8, and bit 63 is square h8.

    // [a8 b8 c8 d8 e8 f8 g8 h8]
    // [a7 b7 c7 d7 e7 f7 g7 h7]
    // [a6 b6 c6 d6 e6 f6 g6 h6]
    // [a5 b5 c5 d5 e5 f5 g5 h5]
    // [a4 b4 c4 d4 e4 f4 g4 h4]
    // [a3 b3 c3 d3 e3 f3 g3 h3]
    // [a2 b2 c2 d2 e2 f2 g2 h2]
    // [a1 b1 c1 d1 e1 f1 g1 h1]

    public static int[][] array_representation(long bitboard) {
        int[][] board = new int[8][8];
        for (int i = 0; i < 64; i++) {
            // Evaluate the value of i-th bit
            board[7 - i / 8][i % 8] = (int) ((bitboard >>> i) & 1);
        }
        return board;
    }

    public static void print_2d_chessboard(int[][] chessboard_array){
        // Print 2D chessboard from top to bottom
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + chessboard_array[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void print_board_state(){
        LocationBitboard locations = new LocationBitboard();

        // Print all white pieces
        System.out.println("WHITE PIECE LOCATIONS");
        System.out.println();

        System.out.println("PAWN LOCATIONS");
        print_2d_chessboard(array_representation(locations.whitePawn[0]));
        System.out.println("ROOK LOCATIONS");
        print_2d_chessboard(array_representation(locations.whiteRook[0]));
        System.out.println("KNIGHT LOCATIONS");
        print_2d_chessboard(array_representation(locations.whiteKnight[0]));
        System.out.println("BISHOP LOCATIONS");
        print_2d_chessboard(array_representation(locations.whiteBishop[0]));
        System.out.println("QUEEN LOCATIONS");
        print_2d_chessboard(array_representation(locations.whiteQueen[0]));
        System.out.println("KING LOCATIONS");
        print_2d_chessboard(array_representation(locations.whiteKing[0]));
        System.out.println("ALL WHITE PIECE LOCATIONS");
        print_2d_chessboard(array_representation(
                locations.whitePawn[0] | locations.whiteRook[0] | locations.whiteKnight[0]
                        | locations.whiteBishop[0] | locations.whiteQueen[0] | locations.whiteKing[0]));
        System.out.println();

        // Print all black pieces
        System.out.println("BLACK PIECE LOCATIONS");
        System.out.println();

        System.out.println("PAWN LOCATIONS");
        print_2d_chessboard(array_representation(locations.blackPawn[0]));
        System.out.println("ROOK LOCATIONS");
        print_2d_chessboard(array_representation(locations.blackRook[0]));
        System.out.println("KNIGHT LOCATIONS");
        print_2d_chessboard(array_representation(locations.blackKnight[0]));
        System.out.println("BISHOP LOCATIONS");
        print_2d_chessboard(array_representation(locations.blackBishop[0]));
        System.out.println("QUEEN LOCATIONS");
        print_2d_chessboard(array_representation(locations.blackQueen[0]));
        System.out.println("KING LOCATIONS");
        print_2d_chessboard(array_representation(locations.blackKing[0]));
        System.out.println("ALL BLACK PIECE LOCATIONS");
        print_2d_chessboard(array_representation(
                locations.blackPawn[0] | locations.blackRook[0] | locations.blackKnight[0]
                        | locations.blackBishop[0] | locations.blackQueen[0] | locations.blackKing[0]));
        System.out.println();

        // Print all files
        System.out.println("FILE BITBOARDS");
        System.out.println();

        System.out.println("FILE_A");
        print_2d_chessboard(array_representation(FileAndRank.FILE_A));
        System.out.println("FILE_H");
        print_2d_chessboard(array_representation(FileAndRank.FILE_H));
        System.out.println("FILE_AB");
        print_2d_chessboard(array_representation(FileAndRank.FILE_AB));
        System.out.println("FILE_GH");
        print_2d_chessboard(array_representation(FileAndRank.FILE_GH));
        System.out.println("RANK_1");
        print_2d_chessboard(array_representation(FileAndRank.RANK_1));
        System.out.println("RANK_2");
        print_2d_chessboard(array_representation(FileAndRank.RANK_2));
        System.out.println("RANK_7");
        print_2d_chessboard(array_representation(FileAndRank.RANK_7));
        System.out.println("RANK_8");
        print_2d_chessboard(array_representation(FileAndRank.RANK_8));
        System.out.println("KNIGHT_SPAN");
        print_2d_chessboard(array_representation(FileAndRank.KNIGHT_SPAN));
        System.out.println("KING_SPAN");
        print_2d_chessboard(array_representation(FileAndRank.KING_SPAN));
        System.out.println();

        System.out.println("PRE-CALCULATED ATTACKS");
        System.out.println();

        System.out.println("WHITE PAWN ATTACKS");
        for(int i = 0; i < 64; i++){
            System.out.println("WHITE PAWN ATTACKS FROM SQUARE " + "#" + (i + 1));
            print_2d_chessboard(array_representation(PreCalculatedAttacks.pawn_attacks[0][i]));
            System.out.println();
        }

        System.out.println("BLACK PAWN ATTACKS");
        for(int i = 0; i < 64; i++){
            System.out.println("BLACK PAWN ATTACKS FROM SQUARE " + "#" + (i + 1));
            print_2d_chessboard(array_representation(PreCalculatedAttacks.pawn_attacks[1][i]));
            System.out.println();
        }

        System.out.println("KNIGHT ATTACKS (SAME FOR BOTH WHITE AND BLACK)");
        for(int i = 0; i < 64; i++){
            System.out.println("KNIGHT ATTACKS FROM SQUARE " + "#" + (i + 1));
            print_2d_chessboard(array_representation(PreCalculatedAttacks.knight_attacks[i]));
            System.out.println();
        }

        System.out.println("KING ATTACKS (SAME FOR BOTH WHITE AND BLACK)");
        for(int i = 0; i < 64; i++){
            System.out.println("KING ATTACKS FROM SQUARE " + "#" + (i + 1));
            print_2d_chessboard(array_representation(PreCalculatedAttacks.king_attacks[i]));
            System.out.println();
        }
        System.out.println();
        print_2d_chessboard(array_representation(
                0b1000000000000000000000000000000000000000000000000000000000000000L));
        System.out.println();
        print_2d_chessboard(array_representation(
                0b1000000000000000000000000000000000010000000000000000000000000000L - 1));
        System.out.println();
        print_2d_chessboard(array_representation(FileAndRank.RANK_2));
        System.out.println();
        print_2d_chessboard(array_representation(FileAndRank.RANK_7));
    }
}
