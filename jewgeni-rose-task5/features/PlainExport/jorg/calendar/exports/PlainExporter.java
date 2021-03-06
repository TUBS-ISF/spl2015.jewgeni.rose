package jorg.calendar.exports;

import jorg.calendar.Meeting;

import static jorg.App.app;

/**
 *
 * @author rose
 */
public class PlainExporter extends Exporter {

    public PlainExporter() {
        super("txt");
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
