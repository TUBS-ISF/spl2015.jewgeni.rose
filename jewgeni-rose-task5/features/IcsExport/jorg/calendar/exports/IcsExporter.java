package jorg.calendar.exports;

import jorg.calendar.Meeting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static jorg.App.app;

/**
 * @author rose
 */
public class IcsExporter extends Exporter {

    private static final String FOOTER = "END:VCALENDAR\n";
    private final DateFormat dateFormat;

    public IcsExporter(final String name) {
        super(name);
        dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
    }

    @Override
    public String format() {
        final StringBuilder sb = new StringBuilder();
        sb.append(generateHeader());
        for (final Meeting m : app().calendar().meetings()) {
            sb.append("BEGIN:VEVENT").append("\n");
            sb.append("UID:").append(m.hashCode()).append("@example.com").append("\n");
            sb.append("SUMMARY:").append(m.title()).append("\n");
            sb.append("LOCATION:").append(m.place()).append("\n");
            sb.append("DESCRIPTION:").append(m.note()).append("\n");
            sb.append("DTSTART:").append(dateFormatter(m.date())).append("\n");
            sb.append("DTEND:").append(dateFormatter(m.date().plus(m.duration()))).append("\n");
            sb.append("CLASS:PRIVATE").append("\n");
            sb.append("END:VEVENT").append("\n");
        }
        sb.append(FOOTER);
        return sb.toString();
    }

    private String dateFormatter(final LocalDateTime date) {
        return dateFormat.format(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));
    }

    private static String generateHeader() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BEGIN:VCALENDAR").append("\n");
        sb.append("VERSION:2.0").append("\n");
        sb.append("PRODID:http://www.example.com/").append(app().currentUser()).append("/calendar/").append("\n");
        sb.append("METHOD:PUBLISH").append("\n");
        sb.append("CALSCALE:GREGORIAN").append("\n");
        sb.append("BEGIN:VCALENDAR").append("\n");
        return sb.toString();
    }
}
