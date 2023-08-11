/*package Entities.VariousCalculators;

import Entities.Pieces.Calculator;
import Entities.Locations.LocationBitboard;

public class StalemateCalculator {

    // side = true to represent White
    // side = false to represent Black

    public boolean is_stalemate(boolean side, LocationBitboard board) {

        // If the king is in check, it cannot be stalemate
        if (CheckCalculator.isInCheck(side, board)) {
            return false;
        }

        // List all pieces for the current player
        long[] allPieces = side ?
                new long[] {board.whitePawn[0], board.whiteRook[0], board.whiteKnight[0], board.whiteBishop[0], board.whiteQueen[0], board.whiteKing[0]} :
                new long[] {board.blackPawn[0], board.blackRook[0], board.blackKnight[0], board.blackBishop[0], board.blackQueen[0], board.blackKing[0]};

        // For each piece, see if it has any legal move that would not result in check
        for (long pieces : allPieces) {
            while (pieces != 0) {
                int position = Long.numberOfTrailingZeros(pieces);
                pieces ^= (1L << position); // Clear the least significant bit
                // Get a Calculator for the piece at `position`
                Calculator pieceCalculator = getPieceCalculator(position, board);
                long validMoves = pieceCalculator.validMoves(1L << position, side, board);
                while (validMoves != 0) {
                    long move = Long.lowestOneBit(validMoves); // Get a possible move
                    validMoves ^= move; // Remove this move from the list of valid moves
                    // Simulate the move and check if it would leave the king in check
                    LocationBitboard clonedBoard = new LocationBitboard();
                    clonedBoard.movePiece(1L << position, move, side);
                    if (!CheckCalculator.isInCheck(side, clonedBoard)) {
                        return false; // Found a move that would not leave the player in check
                    }
                }
            }
        }

        // If no legal move is found that does not result in check, it's stalemate
        return true;
    }

    // This function would return the appropriate Calculator for the piece at a given position
    Calculator getPieceCalculator(int position, LocationBitboard board) {
        if ((board.whitePawn[0] & (1L << position)) != 0 || (board.blackPawn[0] & (1L << position)) != 0) {
            return Calculators.pawnCalculator;
        } else if ((board.whiteRook[0] & (1L << position)) != 0 || (board.blackRook[0] & (1L << position)) != 0) {
            return Calculators.rookCalculator;
        } else if ((board.whiteKnight[0] & (1L << position)) != 0 || (board.blackKnight[0] & (1L << position)) != 0) {
            return Calculators.knightCalculator;
        } else if ((board.whiteBishop[0] & (1L << position)) != 0 || (board.blackBishop[0] & (1L << position)) != 0) {
            return Calculators.bishopCalculator;
        } else if ((board.whiteQueen[0] & (1L << position)) != 0 || (board.blackQueen[0] & (1L << position)) != 0) {
            return Calculators.queenCalculator;
        } else if ((board.whiteKing[0] & (1L << position)) != 0 || (board.blackKing[0] & (1L << position)) != 0) {
            return Calculators.kingCalculator;
        } else {
            throw new RuntimeException("No piece found at position " + position);
        }
    }
}*/
