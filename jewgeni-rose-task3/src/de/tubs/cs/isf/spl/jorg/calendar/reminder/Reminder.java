// #condition Reminder
package de.tubs.cs.isf.spl.jorg.calendar.reminder;

import java.time.Duration;
import java.time.LocalDateTime;

import de.tubs.cs.isf.spl.jorg.calendar.Meeting;

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
