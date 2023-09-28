package edu.school21.spring.printer;

import edu.school21.spring.renderer.Renderer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrinterWithDateTimeImpl implements Printer {

    private final Renderer renderer;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String msg) {
        renderer.renderMsg(LocalDateTime.now().format(format) + " " + msg);
    }
}
