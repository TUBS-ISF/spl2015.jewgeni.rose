package de.tubs.cs.isf.spl.jorg.calendar.reminder;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author rose
 */
public class SoundReminder extends MuteReminder {

    public SoundReminder(final LocalDateTime time, final Duration before) {
        super(time, before);
    }

    @Override
    public String title() {
        return "SoundReminder";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
