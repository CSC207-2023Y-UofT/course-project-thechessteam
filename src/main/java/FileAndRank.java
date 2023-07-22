/**
 * This class defines the bitboard representations of the specific files, ranks, and possible moves of chess pieces.
 * FILE means a column on a chessboard. We labeled from A to H, with A file as the right most column, H as the rightmost column.
 * RANK means a row on a chessboard. 8 ranks in total; Rank 1 is the bottom row, Rank 8 is the top most row.
 */
public class FileAndRank {
    // Bitboards representing the A, H, AB, and GH files on the board
    static long FILE_A =
            0b0000000100000001000000010000000100000001000000010000000100000001L;
    static long FILE_H =
            0b1000000010000000100000001000000010000000100000001000000010000000L;
    static long FILE_AB =
            0b0000001100000011000000110000001100000011000000110000001100000011L;
    static long FILE_GH =
            0b1100000011000000110000001100000011000000110000001100000011000000L;

    // Bitboards representing the 1st, 4th, 5th, and 8th ranks on the board
    static long RANK_1 =
            0b0000000000000000000000000000000000000000000000000000000011111111L;
    static long RANK_4 =
            0b0000000000000000000000000000000011111111000000000000000000000000L;
    static long RANK_5 =
            0b0000000000000000000000001111111100000000000000000000000000000000L;
    static long RANK_8 =
            0b1111111100000000000000000000000000000000000000000000000000000000L;

    // Bitboards representing the possible moves of a knight and a king from a given square
    static long KNIGHT_SPAN =
            0b0000000000000000000000000000101000010001000000000001000100001010L;
    static long KING_SPAN =
            0b0000000000000000000000000000000000000000000001110000010100000111L;

    // Array of bitboards representing each rank on the board, from rank 1 to rank 8
    static long[] RankMasks8 =/*from rank1 to rank8*/
            {
                    0xFFL, 0xFF00L, 0xFF0000L, 0xFF000000L,
                    0xFF00000000L, 0xFF0000000000L, 0xFF000000000000L, 0xFF00000000000000L
            };

    // Array of bitboards representing each file on the board, from file A to file H
    static long[] FileMasks8 =/*from fileA to FileH*/
            {
                    0x101010101010101L, 0x202020202020202L, 0x404040404040404L, 0x808080808080808L,
                    0x1010101010101010L, 0x2020202020202020L, 0x4040404040404040L, 0x8080808080808080L
            };

    // Array of bitboards representing each diagonal on the board, from top left to bottom right
    static long[] DiagonalMasks8 =/*from top left to bottom right*/
            {
                    0x1L, 0x102L, 0x10204L, 0x1020408L, 0x102040810L, 0x10204081020L, 0x1020408102040L,
                    0x102040810204080L, 0x204081020408000L, 0x408102040800000L, 0x810204080000000L,
                    0x1020408000000000L, 0x2040800000000000L, 0x4080000000000000L, 0x8000000000000000L
            };

    // Array of bitboards representing each anti-diagonal on the board, from top right to bottom left
    static long[] AntiDiagonalMasks8 =/*from top right to bottom left*/
            {
                    0x80L, 0x8040L, 0x804020L, 0x80402010L, 0x8040201008L, 0x804020100804L, 0x80402010080402L,
                    0x8040201008040201L, 0x4020100804020100L, 0x2010080402010000L, 0x1008040201000000L,
                    0x804020100000000L, 0x402010000000000L, 0x201000000000000L, 0x100000000000000L
            };
}
