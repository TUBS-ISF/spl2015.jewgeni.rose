package de.tubs.cs.isf.spl.jorg.calendar.printer;

import de.tubs.cs.isf.spl.jorg.Feature;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rose
 */
public final class PrintMenu extends Feature {

    private static final String FEATURE_PLAIN_PRINTER = "plain";
    private static final String FEATURE_MARKDOWN_PRINTER = "markdown";
    private static final String FEATURE_PDF_PRINTER = "pdf";
    private static final String FEATURE_HTML_PRINTER = "html";

    private final List<Printer> printers;
    private final String menuString;

    public PrintMenu(final String key, final String printrs) {
        super(key, key);
        this.printers = new ArrayList<Printer>();
        for (final String opt : printrs.split(",")) {
            if (FEATURE_HTML_PRINTER.equals(opt)) {
                this.printers.add(new HtmlPrinter());
            } else if (FEATURE_PLAIN_PRINTER.equals(opt)) {
                this.printers.add(new PlainPrinter());
            } else if (FEATURE_MARKDOWN_PRINTER.equals(opt)) {
                this.printers.add(new MarkdownPrinter());
            } else if (FEATURE_PDF_PRINTER.equals(opt)) {
                this.printers.add(new PdfPrinter());
            }
        }

        final StringBuilder sb = new StringBuilder();
        sb.append("printer menu:\n");
        sb.append(String.format("%10s - Exits printer menu\n", "[exit]"));
//        for (final Feature f : printers) {
//            String keyStr = String.format("%10s - ", "[" + f.menuKey() + "]");
//            sb.print(keyStr).print(f.description()).print("\n");
//        }
        menuString = sb.toString();

    }

    @Override
    public void action() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
