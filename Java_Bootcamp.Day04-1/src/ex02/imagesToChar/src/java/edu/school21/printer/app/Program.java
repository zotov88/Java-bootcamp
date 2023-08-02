package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import edu.school21.printer.logic.ImageToChar;

import java.io.IOException;

@Parameters(separators = "=")
public class Program {

    @Parameter(required = true, names = "--white")
    private static String white;
    @Parameter(required = true, names = "--black")
    private static String black;

    public static void main(String[] args) throws IOException {
        JCommander.newBuilder()
                .addObject(new Program())
                .build()
                .parse(args);
        new ImageToChar("/resources/img/it.bmp", black, white).print();
    }
}