package entities.variouscalculators;

import entities.pieces.King;
import entities.locations.LocationBitboard;
import entities.pieces.PieceCalculator;

public class CheckmateCalculator {

    // side = true to represent White
    // side = false to represent Black

    public boolean is_checkmate(boolean side, LocationBitboard board){


        // If the king is not in check, it cannot be checkmate
        if (!CheckCalculator.isInCheck(side, board)) {
            return false;
        }


        // Check if the Entities.Pieces.King can move out of check
        long kingPositions = side ? board.whiteKing[0] : board.blackKing[0];
        int kingPosition = Long.numberOfTrailingZeros(kingPositions);
        King king = new King();
        if (king.validMoves(1L << kingPosition, side, board) != 0) {
            return false; // Entities.Pieces.King can move out of check
        }

        // List all pieces for the side in check
        long[] allPieces = side ?
                new long[] {board.whitePawn[0], board.whiteRook[0], board.whiteKnight[0], board.whiteBishop[0], board.whiteQueen[0]} :
                new long[] {board.blackPawn[0], board.blackRook[0], board.blackKnight[0], board.blackBishop[0], board.blackQueen[0]};

        // For each piece, see if it has any valid move that can block the check or capture the checking piece
        for (long pieces : allPieces) {
            while (pieces != 0) {
                int position = Long.numberOfTrailingZeros(pieces);
                pieces ^= (1L << position); // Clear the least significant bit
                // Assuming we have a generic function `getPieceCalculator` that returns a Entities.Pieces.Calculator for the piece at `position`
                Calculators pieceCalculator = getPieceCalculator(position, board);
                if (pieceCalculator.validMoves(1L << position, side, board) != 0) {
                    // Simulate the move and check if it would leave the king in check
                    LocationBitboard clonedBoard = new LocationBitboard();
                    clonedBoard.movePiece(1L << position, pieceCalculator.validMoves(1L << position, side, clonedBoard), side);
                    if (!CheckCalculator.isInCheck(side, clonedBoard)) {
                        return false; // Found a piece that can move to block the check or capture the checking piece
                    }
                }
            }
        }

        // If no piece can move to resolve the check, it's checkmate
        return true;
    }

    // This function would return the appropriate Entities.Pieces.Calculator for the piece at a given position

    Calculators getPieceCalculator(int position, LocationBitboard board) {
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
}
