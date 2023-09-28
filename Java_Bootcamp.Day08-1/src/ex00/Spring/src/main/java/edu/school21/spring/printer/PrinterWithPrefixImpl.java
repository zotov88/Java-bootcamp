package edu.school21.spring.printer;

import edu.school21.spring.renderer.Renderer;

public class PrinterWithPrefixImpl implements Printer {

    private final Renderer renderer;
    private String prefix;

    public PrinterWithPrefixImpl(Renderer renderer, String prefix) {
        this.renderer = renderer;
        this.prefix = prefix;
    }

    public PrinterWithPrefixImpl(Renderer renderer) {
        this(renderer, "prefix");
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void print(String msg) {
        renderer.renderMsg(prefix + " " + msg);
    }
}
