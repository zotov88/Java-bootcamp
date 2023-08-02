package edu.school21.printer.app;

import edu.school21.printer.logic.ImageToChar;

import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        parserArguments(args);
        try {
            ImageToChar imageToChar = new ImageToChar("/resources/img/it.bmp", args[0].charAt(0), args[1].charAt(0));
            imageToChar.print();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void parserArguments(String[] args) {
        if (args.length != 2 || args[0].length() != 1 || args[1].length() != 1) {
            System.err.println("Illegal arguments");
            System.exit(-1);
        }
    }
}
