package de.tubs.cs.isf.spl.jorg.calendar.printer;

import de.tubs.cs.isf.spl.jorg.calendar.Calendar;

/**
 *
 * @author rose
 */
public abstract class Printer {

    void print(final Calendar cal) {
        print(cal, null);
    }

    public abstract void print(final Calendar cal, final String pathToFile);

}
