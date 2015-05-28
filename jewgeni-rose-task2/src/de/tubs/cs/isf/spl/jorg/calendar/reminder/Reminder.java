package de.tubs.cs.isf.spl.jorg.calendar.reminder;

import de.tubs.cs.isf.spl.jorg.calendar.Meeting;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author rose
 */
public abstract class Reminder implements Runnable {

    protected Meeting meeting;
    protected Duration before;
    protected LocalDateTime time;
    protected volatile boolean shutdown;

    protected Reminder(final Meeting meeting, final Duration before) {
        this.meeting = meeting;
        this.before = before;
        this.time = meeting.date().minus(before);
        this.shutdown = false;
    }

    protected abstract void stop();
}
