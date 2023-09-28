package edu.school21.spring.app;

import edu.school21.spring.preprocessor.PreProcessor;
import edu.school21.spring.preprocessor.PreProcessorToUpperImpl;
import edu.school21.spring.printer.Printer;
import edu.school21.spring.printer.PrinterWithDateTimeImpl;
import edu.school21.spring.printer.PrinterWithPrefixImpl;
import edu.school21.spring.renderer.Renderer;
import edu.school21.spring.renderer.RendererErrImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {
    public static void main(String[] args) {
        System.out.println("Before Spring:");
        PreProcessor processor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererErrImpl(processor);
        Printer printer = new PrinterWithDateTimeImpl(renderer);
        printer.print("message");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Spring:");
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Printer printer1 = context.getBean("printerWithDate", PrinterWithDateTimeImpl.class);
        Printer printer2 = context.getBean("printerWithPrefix", PrinterWithPrefixImpl.class);
        PrinterWithPrefixImpl printer3 = context.getBean("printerWithPrefix", PrinterWithPrefixImpl.class);
        printer1.print("message");
        printer2.print("message");
        printer3.setPrefix("Spring");
        printer3.print("message");
    }
}
