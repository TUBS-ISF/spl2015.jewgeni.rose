package de.tubs.cs.isf.spl.jorg.calendar.reminder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author rose
 */
public class MuteReminder extends Reminder {

    /**
     *
     * @param time   LDT of Meeting
     * @param before Duration to play before meeting begin
     */
    public MuteReminder(final LocalDateTime time, final Duration before) {
        super(time.minus(before));
    }

    @Override
    public void action() {

    }

    @Override
    public String menuKey() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String title() {
        return "MuteReminder";
    }

    @Override
    public String toString() {
        return title() + " für den " + time.format(DateTimeFormatter.ISO_LOCAL_DATE) + " um "
            + time.format(DateTimeFormatter.ISO_LOCAL_TIME) + " Uhr eingerichtet.";
    }
}
