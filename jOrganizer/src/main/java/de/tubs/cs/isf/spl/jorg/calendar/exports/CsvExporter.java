package de.tubs.cs.isf.spl.jorg.calendar.exports;

import de.tubs.cs.isf.spl.jorg.calendar.Meeting;

import static de.tubs.cs.isf.spl.jorg.App.app;

/**
 * @author rose
 */
public class CsvExporter extends Exporter {

    public CsvExporter(final String name) {
        super(name);
    }

    @Override
    public String format() {
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
