package de.tubs.cs.isf.spl.jorg.calendar.reminder;

import java.time.LocalDateTime;

import de.tubs.cs.isf.spl.jorg.Feature;

/**
 *
 * @author rose
 */
public abstract class Reminder extends Feature {

    protected LocalDateTime time;

    protected Reminder(final LocalDateTime time) {
        this.time = time;
    }
}
