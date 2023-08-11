package entities.variouscalculators;

import entities.pieces.*;

// Contains all the calculators for valid move calculations
public class Calculators {
    public Pawn pawnCalculator = new Pawn();
    public Rook rookCalculator = new Rook();
    public Knight knightCalculator = new Knight();
    public Bishop bishopCalculator = new Bishop();
    public Queen queenCalculator = new Queen();
    public King kingCalculator = new King();
}
