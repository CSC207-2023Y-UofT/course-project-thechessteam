package View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageRendering {
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
            img = ImageIO.read(Objects.requireNonNull(ImageRendering.class.getClassLoader().getResource("projectimages/ChessPieces.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static int imgW = img.getWidth();
    static int imgH = img.getHeight();
    static int OPW = imgW/6;
    static int OPH = imgH/2;
    public static Image pieceImg;

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
