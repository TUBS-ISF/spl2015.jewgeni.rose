package de.tubs.cs.isf.spl.jorg.calendar.printer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.markdown4j.Markdown4jProcessor;

/**
 *
 * @author rose
 */
public class HtmlPrinter extends MarkdownPrinter {

    protected final Markdown4jProcessor processor;

    public HtmlPrinter(final String name) {
        super(name);
        processor = new Markdown4jProcessor();
    }

    @Override
    public String print() {
        try {
            return processor.process(super.print());
        } catch (IOException ex) {
            Logger.getLogger(HtmlPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
