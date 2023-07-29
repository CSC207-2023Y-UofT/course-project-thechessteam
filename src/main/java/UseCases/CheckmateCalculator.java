package UseCases;

import Entities.Calculator;
import Entities.Calculators;
import Entities.King;
import Entities.LocationBitboard;
import UseCases.CheckCalculator;

public class CheckmateCalculator {

    // side = 0 to represent White
    // side = 1 to represent Black

    public boolean is_checkmate(boolean side, LocationBitboard board){
        CheckCalculator checkCalculator = new CheckCalculator();


        // If the king is not in check, it cannot be checkmate
        if (!CheckCalculator.is_in_check(side, board)) {
            return false;
        }


        // Check if the Entities.King can move out of check
        long kingPositions = side ? board.whiteKing[0] : board.blackKing[0];
        int kingPosition = Long.numberOfTrailingZeros(kingPositions);
        King king = new King();
        if (king.valid_moves(1L << kingPosition, side, board) != 0) {
            return false; // Entities.King can move out of check
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
                // Assuming we have a generic function `getPieceCalculator` that returns a Entities.Calculator for the piece at `position`
                Calculator pieceCalculator = getPieceCalculator(position, board);
                if (pieceCalculator.valid_moves(1L << position, side, board) != 0) {
                    // Simulate the move and check if it would leave the king in check
                    LocationBitboard clonedBoard = new LocationBitboard();
                    clonedBoard.move_piece(1L << position, pieceCalculator.valid_moves(1L << position, side, clonedBoard), side);
                    if (!checkCalculator.is_in_check(side, clonedBoard)) {
                        return false; // Found a piece that can move to block the check or capture the checking piece
                    }
                }
            }
        }

        // If no piece can move to resolve the check, it's checkmate
        return true;
    }

    // This function would return the appropriate Entities.Calculator for the piece at a given position

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
}
