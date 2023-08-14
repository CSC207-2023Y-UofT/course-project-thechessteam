package entities.variouscalculators;

import entities.pieces.*;

/**
 * The Calculators class serves as a container for all the piece calculators
 * responsible for valid move calculations in a chess game.
 * This class provides easy access to calculators for pawns, rooks, knights,
 * bishops, queens, and kings, allowing various calculations and simulations
 * to be performed based on the state of the chessboard.
 */
public class Calculators {
    /** Calculator for pawn moves */
    public Pawn pawnCalculator = new Pawn();
    public Rook rookCalculator = new Rook();
    public Knight knightCalculator = new Knight();
    public Bishop bishopCalculator = new Bishop();
    public Queen queenCalculator = new Queen();
    public King kingCalculator = new King();
}
