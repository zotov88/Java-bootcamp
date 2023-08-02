package edu.school21.printer.app;

import edu.school21.printer.logic.ImageToChar;

import java.io.File;
import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        parserArguments(args);
        try {
            ImageToChar imageToChar = new ImageToChar(args[0], args[1].charAt(0), args[2].charAt(0));
            imageToChar.print();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void parserArguments(String[] args) {
        if (args.length == 3 && args[1].length() == 1 && args[2].length() == 1) {
            if (!(new File(args[0]).exists())) {
                System.err.println("File is not exist");
                System.exit(-2);
            }
        } else {
            System.err.println("Illegal arguments");
            System.exit(-1);
        }
    }
}
