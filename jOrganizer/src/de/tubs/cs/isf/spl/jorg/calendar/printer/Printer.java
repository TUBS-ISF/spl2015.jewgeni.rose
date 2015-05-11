package de.tubs.cs.isf.spl.jorg.calendar.printer;

import de.tubs.cs.isf.spl.jorg.calendar.Meeting;

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
