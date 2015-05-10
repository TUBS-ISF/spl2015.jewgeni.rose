package de.tubs.cs.isf.spl.jorg.calendar.meeting.reminder;

import de.tubs.cs.isf.spl.jorg.calendar.meeting.Meeting;
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
        this.time = meeting.getDate().minus(before);
        this.shutdown = false;
    }

    protected abstract void stop();
}
