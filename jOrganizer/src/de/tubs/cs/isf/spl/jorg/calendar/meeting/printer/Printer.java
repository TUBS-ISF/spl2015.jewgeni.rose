package de.tubs.cs.isf.spl.jorg.calendar.meeting.printer;

import de.tubs.cs.isf.spl.jorg.calendar.meeting.Meeting;

/**
 *
 * @author rose
 */
public abstract class Printer {

    void print(final Meeting m) {
        print(m, null);
    }

    public abstract void print(final Meeting m, final String pathToFile);

}
