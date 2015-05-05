package de.tubs.cs.isf.spl.jorg.calendar.printer;

import de.tubs.cs.isf.spl.jorg.calendar.meeting.Meeting;

/**
 *
 * @author rose
 */
public abstract class Printer {

	public void print(final Meeting m) {
        print(m, null);
    }

	public abstract void print(final Meeting m, final String pathToFile);

}
