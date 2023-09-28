package processor;

import annotations.HtmlForm;
import annotations.HtmlInput;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("annotations.HtmlForm")
public class HtmlProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            List<String> form = new ArrayList<>();
            HtmlForm htmlForm = element.getAnnotation(HtmlForm.class);
            form.add("<form action = \"" + htmlForm.action() + "\" method = \"" + htmlForm.method() + "\">");
            for (Element field : roundEnv.getElementsAnnotatedWith(HtmlInput.class)) {
                HtmlInput input = field.getAnnotation(HtmlInput.class);
                form.add("\t<input type = \"" + input.type() + "\" name = \"" + input.name() + "\" placeholder = \"" + input.placeholder() + "\">");
            }
            form.add("\t<input type = \"submit\" value = \"Send\">\n</form>");
            createFile(form, htmlForm.fileName());
        }
        return true;
    }

    private void createFile(List<String> form, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("target/classes/" + fileName))) {
            for (String str : form) {
                writer.write(str + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
