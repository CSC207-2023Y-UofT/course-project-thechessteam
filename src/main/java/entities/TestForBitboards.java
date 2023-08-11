package entities;

import entities.constants.FileAndRank;
import entities.constants.PreCalculatedAttacks;
import entities.locations.LocationBitboard;

// Used for debugging, not actually any component of our program

public class TestForBitboards {
    public static void main(String[] args){
        printBoardState();
    }

    // arrayRepresentation follows standard bitboard representation where
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

    public static int[][] arrayRepresentation(long bitboard) {
        int[][] board = new int[8][8];
        for (int i = 0; i < 64; i++) {
            // Evaluate the value of i-th bit
            board[7 - i / 8][i % 8] = (int) ((bitboard >>> i) & 1);
        }
        return board;
    }

    public static void print2dChessboard(int[][] chessboardArray){
        // Print 2D chessboard from top to bottom
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + chessboardArray[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printBoardState(){
        LocationBitboard locations = new LocationBitboard();

        // Print all white pieces
        System.out.println("WHITE PIECE LOCATIONS");
        System.out.println();

        System.out.println("PAWN LOCATIONS");
        print2dChessboard(arrayRepresentation(locations.whitePawn[0]));
        System.out.println("ROOK LOCATIONS");
        print2dChessboard(arrayRepresentation(locations.whiteRook[0]));
        System.out.println("KNIGHT LOCATIONS");
        print2dChessboard(arrayRepresentation(locations.whiteKnight[0]));
        System.out.println("BISHOP LOCATIONS");
        print2dChessboard(arrayRepresentation(locations.whiteBishop[0]));
        System.out.println("QUEEN LOCATIONS");
        print2dChessboard(arrayRepresentation(locations.whiteQueen[0]));
        System.out.println("KING LOCATIONS");
        print2dChessboard(arrayRepresentation(locations.whiteKing[0]));
        System.out.println("ALL WHITE PIECE LOCATIONS");
        print2dChessboard(arrayRepresentation(
                locations.whitePawn[0] | locations.whiteRook[0] | locations.whiteKnight[0]
                        | locations.whiteBishop[0] | locations.whiteQueen[0] | locations.whiteKing[0]));
        System.out.println();

        // Print all black pieces
        System.out.println("BLACK PIECE LOCATIONS");
        System.out.println();

        System.out.println("PAWN LOCATIONS");
        print2dChessboard(arrayRepresentation(locations.blackPawn[0]));
        System.out.println("ROOK LOCATIONS");
        print2dChessboard(arrayRepresentation(locations.blackRook[0]));
        System.out.println("KNIGHT LOCATIONS");
        print2dChessboard(arrayRepresentation(locations.blackKnight[0]));
        System.out.println("BISHOP LOCATIONS");
        print2dChessboard(arrayRepresentation(locations.blackBishop[0]));
        System.out.println("QUEEN LOCATIONS");
        print2dChessboard(arrayRepresentation(locations.blackQueen[0]));
        System.out.println("KING LOCATIONS");
        print2dChessboard(arrayRepresentation(locations.blackKing[0]));
        System.out.println("ALL BLACK PIECE LOCATIONS");
        print2dChessboard(arrayRepresentation(
                locations.blackPawn[0] | locations.blackRook[0] | locations.blackKnight[0]
                        | locations.blackBishop[0] | locations.blackQueen[0] | locations.blackKing[0]));
        System.out.println();

        // Print all files
        System.out.println("FILE BITBOARDS");
        System.out.println();

        System.out.println("FILE_A");
        print2dChessboard(arrayRepresentation(FileAndRank.FILE_A));
        System.out.println("FILE_H");
        print2dChessboard(arrayRepresentation(FileAndRank.FILE_H));
        System.out.println("FILE_AB");
        print2dChessboard(arrayRepresentation(FileAndRank.FILE_AB));
        System.out.println("FILE_GH");
        print2dChessboard(arrayRepresentation(FileAndRank.FILE_GH));
        System.out.println("RANK_1");
        print2dChessboard(arrayRepresentation(FileAndRank.RANK_1));
        System.out.println("RANK_2");
        print2dChessboard(arrayRepresentation(FileAndRank.RANK_2));
        System.out.println("RANK_7");
        print2dChessboard(arrayRepresentation(FileAndRank.RANK_7));
        System.out.println("RANK_8");
        print2dChessboard(arrayRepresentation(FileAndRank.RANK_8));
        System.out.println("KNIGHT_SPAN");
        print2dChessboard(arrayRepresentation(FileAndRank.KNIGHT_SPAN));
        System.out.println("KING_SPAN");
        print2dChessboard(arrayRepresentation(FileAndRank.KING_SPAN));
        System.out.println();

        System.out.println("PRE-CALCULATED ATTACKS");
        System.out.println();

        System.out.println("WHITE PAWN ATTACKS");
        for(int i = 0; i < 64; i++){
            System.out.println("WHITE PAWN ATTACKS FROM SQUARE " + "#" + (i + 1));
            print2dChessboard(arrayRepresentation(PreCalculatedAttacks.pawnAttacks[0][i]));
            System.out.println();
        }

        System.out.println("BLACK PAWN ATTACKS");
        for(int i = 0; i < 64; i++){
            System.out.println("BLACK PAWN ATTACKS FROM SQUARE " + "#" + (i + 1));
            print2dChessboard(arrayRepresentation(PreCalculatedAttacks.pawnAttacks[1][i]));
            System.out.println();
        }

        System.out.println("KNIGHT ATTACKS (SAME FOR BOTH WHITE AND BLACK)");
        for(int i = 0; i < 64; i++){
            System.out.println("KNIGHT ATTACKS FROM SQUARE " + "#" + (i + 1));
            print2dChessboard(arrayRepresentation(PreCalculatedAttacks.knightAttacks[i]));
            System.out.println();
        }

        System.out.println("KING ATTACKS (SAME FOR BOTH WHITE AND BLACK)");
        for(int i = 0; i < 64; i++){
            System.out.println("KING ATTACKS FROM SQUARE " + "#" + (i + 1));
            print2dChessboard(arrayRepresentation(PreCalculatedAttacks.kingAttacks[i]));
            System.out.println();
        }
        System.out.println();
        print2dChessboard(arrayRepresentation(
                0b1000000000000000000000000000000000000000000000000000000000000000L));
        System.out.println();
        print2dChessboard(arrayRepresentation(
                0b1000000000000000000000000000000000010000000000000000000000000000L - 1));
        System.out.println();
        print2dChessboard(arrayRepresentation(FileAndRank.RANK_2));
        System.out.println();
        print2dChessboard(arrayRepresentation(FileAndRank.RANK_7));
    }
}
