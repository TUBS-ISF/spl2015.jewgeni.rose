package de.tubs.cs.isf.spl.jorg.calendar.exports;

import de.tubs.cs.isf.spl.jorg.calendar.Meeting;

import java.time.format.DateTimeFormatter;

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
        sb.append("Title;Place;Date;Duration;Note;").append("\n");
        for (final Meeting m : app().calendar().meetings()) {
            sb.append("\"").append(m.title())
                    .append("\"; \"").append(m.place())
                    .append("\"; ").append(m.date().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .append("; ").append(m.duration().toMinutes()).append("min")
                    .append("; \"").append(m.note()).append("\";\n");
        }
        return sb.toString();
    }
}
