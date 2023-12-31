package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * A utility class responsible for rendering and managing images of chess pieces.
 * This class contains static references to white and black chess pieces' images,
 * and it initializes these images by reading from a given source.
 */
public class PieceRendering {
    public static Image whitePawn;
    public static Image whiteRook;
    public static Image whiteKnight;
    public static Image whiteBishop;
    public static Image whiteQueen;
    public static Image whiteKing;

    public static Image blackPawn;
    public static Image blackRook;
    public static Image blackKnight;
    public static Image blackBishop;
    public static Image blackQueen;
    public static Image blackKing;

    static BufferedImage img;
    static {
        try {
            img = ImageIO.read(Objects.requireNonNull(PieceRendering.class.getClassLoader().getResource("projectimages/ChessPieces.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static int imgW = img.getWidth();
    static int imgH = img.getHeight();
    static int OPW = imgW/6;
    static int OPH = imgH/2;

    static {
        whitePawn = img.getSubimage(OPW*5, 0, OPW, OPH);
        whiteRook = img.getSubimage(OPW*4, 0, OPW, OPH);
        whiteKnight = img.getSubimage(OPW*3, 0, OPW, OPH);
        whiteBishop = img.getSubimage(OPW*2, 0, OPW, OPH);
        whiteQueen = img.getSubimage(OPW, 0, OPW, OPH);
        whiteKing = img.getSubimage(0, 0, OPW, OPH);

        blackPawn = img.getSubimage(OPW*5, OPH, OPW, OPH);
        blackRook = img.getSubimage(OPW*4, OPH, OPW, OPH);
        blackKnight = img.getSubimage(OPW*3, OPH, OPW, OPH);
        blackBishop = img.getSubimage(OPW*2, OPH, OPW, OPH);
        blackQueen = img.getSubimage(OPW, OPH, OPW, OPH);
        blackKing = img.getSubimage(0, OPH, OPW, OPH);
    }
}
