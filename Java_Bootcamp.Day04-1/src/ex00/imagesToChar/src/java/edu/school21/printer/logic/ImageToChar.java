package edu.school21.printer.logic;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageToChar {

    private final BufferedImage bufferedImage;
    private final int height;
    private final int width;
    private char white;
    private char black;

    public ImageToChar(String pathFile, char white, char black) throws IOException {
        this.bufferedImage = ImageIO.read(new File(pathFile));
        height = bufferedImage.getHeight();
        width = bufferedImage.getWidth();
        this.white = white;
        this.black = black;
    }

    public char getWhite() {
        return white;
    }

    public void setWhite(char white) {
        this.white = white;
    }

    public char getBlack() {
        return black;
    }

    public void setBlack(char black) {
        this.black = black;
    }

    public void print() {
        int p;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                p = bufferedImage.getRGB(j, i);
                if (Color.BLACK.getRGB() == p) {
                    System.out.print(black);
                }
                if (Color.WHITE.getRGB() == p) {
                    System.out.print(white);
                }
            }
            System.out.println();
        }
    }
}
