package edu.school21.printer.logic;


import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageToChar {

    private final BufferedImage bufferedImage;
    private final ColoredPrinter coloredPrinter;
    private final int height;
    private final int width;
    private final String colorOne;
    private final String colorTwo;

    public ImageToChar(String pathFile, final String colorOne, final String colorTwo) throws IOException {
        this.bufferedImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(pathFile)));
        this.coloredPrinter = new ColoredPrinter();
        this.height = bufferedImage.getHeight();
        this.width = bufferedImage.getWidth();
        this.colorOne = colorOne;
        this.colorTwo = colorTwo;
    }

    public void print() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int p = bufferedImage.getRGB(j, i);
                if (Color.BLACK.getRGB() == p) {
                    coloredPrinter.print("  ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(colorTwo));
                }
                if (Color.WHITE.getRGB() == p) {
                    coloredPrinter.print("  ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(colorOne));
                }
            }
            System.out.println();
        }
    }
}
