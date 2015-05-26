package de.tubs.cs.isf.spl.jorg.calendar.exports;

import de.tubs.cs.isf.spl.jorg.App;
import de.tubs.cs.isf.spl.jorg.Feature;
import de.tubs.cs.isf.spl.jorg.calendar.exports.share.HtmlShareExporter;

import java.util.HashSet;
import java.util.Set;

import static de.tubs.cs.isf.spl.jorg.App.EXIT;
import static de.tubs.cs.isf.spl.jorg.App.clear;

/**
 * @author rose
 */
public class ExportMenu extends Feature {

    private static final String PLAIN_TXT_FORMAT = "txt";
    private static final String MARKDOWN_FORMAT = "md";
    private static final String ICS_FORMAT = "ics";
    private static final String HTML_FORMAT = "html";
    private static final String CSV_FORMAT = "csv";


    private final Set<Exporter> exporters;
    private final String menuString;

    public ExportMenu(final String key, final String printrs, final String sharers) {
        super(key, "export function for meetings");
        exporters = new HashSet<Exporter>();
        for (final String opt : printrs.split(",")) {
            if (HTML_FORMAT.equals(opt) && sharers == null) {
                exporters.add(new HtmlExporter(HTML_FORMAT));
            } else if (HTML_FORMAT.equals(opt)) {
                exporters.add(new HtmlShareExporter(HTML_FORMAT, sharers));
            } else if (PLAIN_TXT_FORMAT.equals(opt)) {
                exporters.add(new PlainExporter(PLAIN_TXT_FORMAT));
            } else if (MARKDOWN_FORMAT.equals(opt)) {
                exporters.add(new MarkdownExporter(MARKDOWN_FORMAT));
            } else if (ICS_FORMAT.equals(opt)) {
                exporters.add(new IcsExporter(ICS_FORMAT));
            } else if (CSV_FORMAT.equals(opt)) {
                exporters.add(new CsvExporter(CSV_FORMAT));
            }
        }

        final StringBuilder sb = new StringBuilder();
        sb.append(App.PROMPT_BOLD + "export menu:\n" + App.PROMPT_NORMAL);
        sb.append(String.format("%10s - Exits export menu\n", "[exit]"));
        for (final Exporter p : exporters) {
            sb.append(String.format("%10s - %s%n", "[" + p.name() + "]",
                    "save meetings into file with " + p.name() + "-format (add '_p' for preview only)"));
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
            for (final Exporter exporter : exporters) {
                if (exporter.name().equals(input)) {
                    exporter.toFile();
                } else if ((exporter.name() + "_p").equals(input)) {
                    println(exporter.format());
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
