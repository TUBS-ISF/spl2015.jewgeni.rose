package de.tubs.cs.isf.spl.jorg.calendar.exports;

import static de.tubs.cs.isf.spl.jorg.App.app;
import de.tubs.cs.isf.spl.jorg.calendar.Meeting;

/**
 *
 * @author rose
 */
public class PlainExporter extends Exporter {

	private static final String FORMAT = "txt";

	public PlainExporter() {
		super(FORMAT);
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
