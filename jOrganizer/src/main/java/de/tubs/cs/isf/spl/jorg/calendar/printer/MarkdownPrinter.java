package de.tubs.cs.isf.spl.jorg.calendar.printer;

import static de.tubs.cs.isf.spl.jorg.App.app;
import de.tubs.cs.isf.spl.jorg.calendar.Meeting;

/**
 *
 * @author rose
 */
public class MarkdownPrinter extends Printer {

    public MarkdownPrinter(final String name) {
        super(name);
    }

    @Override
    public String print() {
        final StringBuilder sb = new StringBuilder();

        sb.append("Meetings for '").append(app().currentUser()).append("'\n");
        sb.append("-------------------------------\n\n");
        for (final Meeting m : app().calendar().meetings()) {
            sb.append(m.toString());
            sb.append("-------------------------------\n");
        }
        return sb.toString();
    }
}
