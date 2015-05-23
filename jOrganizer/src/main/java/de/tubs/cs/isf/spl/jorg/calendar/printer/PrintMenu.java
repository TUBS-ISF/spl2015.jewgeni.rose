package de.tubs.cs.isf.spl.jorg.calendar.printer;

import de.tubs.cs.isf.spl.jorg.App;
import static de.tubs.cs.isf.spl.jorg.App.EXIT;
import static de.tubs.cs.isf.spl.jorg.App.clear;
import de.tubs.cs.isf.spl.jorg.Feature;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rose
 */
public class PrintMenu extends Feature {

    private static final String PLAIN_PRINTER = "txt";
    private static final String MARKDOWN_PRINTER = "md";
    private static final String ICAL_PRINTER = "ical";
    private static final String HTML_PRINTER = "html";
    private static final String CSV_PRINTER = "csv";


    private final List<Printer> printers;
    private final String menuString;

    public PrintMenu(final String key, final String printrs) {
        super(key, key);
        this.printers = new ArrayList<Printer>();
        for (final String opt : printrs.split(",")) {
            if (HTML_PRINTER.equals(opt)) {
                this.printers.add(new HtmlPrinter(HTML_PRINTER));
            } else if (PLAIN_PRINTER.equals(opt)) {
                this.printers.add(new PlainPrinter(PLAIN_PRINTER));
            } else if (MARKDOWN_PRINTER.equals(opt)) {
                this.printers.add(new MarkdownPrinter(MARKDOWN_PRINTER));
            } else if (ICAL_PRINTER.equals(opt)) {
                this.printers.add(new iCalPrinter(ICAL_PRINTER));
            } else if (CSV_PRINTER.equals(opt)) {
                this.printers.add(new CsvPrinter(CSV_PRINTER));
            }
        }

        final StringBuilder sb = new StringBuilder();
        sb.append("printer menu:\n");
        sb.append(String.format("%10s - Exits printer menu\n", "[exit]"));
        for (final Printer p : printers) {
            sb.append(String.format("%10s - %s%n", "[" + p.name() + "]",
                                    "print meetings to console in " + p.name() + "-format (add '_f' for file output)"));
        }
        menuString = sb.toString();
    }

    @Override
    public void action() {
        clear();
        println(menuString);

        String input;
        while (true) {
            input = readLine();
            if (EXIT.equals(input)) {
                break;
            }
            for (final Printer printer : printers) {
                if (printer.name().equals(input)) {
                    println(printer.print());
                } else if ((printer.name() + "_f").equals(input)) {
                    printer.toFile();
                }
            }
        }
    }

    @Override
    protected void println(final Object obj) {
        App.println(obj, "calendar/" + key);
    }

    @Override
    protected String readLine() {
        return App.readLine("", "calendar/" + key);
    }
}
