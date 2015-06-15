package de.tubs.cs.isf.spl.jorg.calendar;

import de.tubs.cs.isf.spl.jorg.calendar.reminder.Reminder;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author rose
 */
public class Meeting implements Comparable<Meeting> {

    private final String title, note, place;
    private final LocalDateTime date;
    private final Duration duration;
    private Reminder reminder;

    public Meeting(final String title, final String note, final String place,
                   final LocalDateTime date, final Duration duration) {
        this.title = title;
        this.note = note;
        this.place = place;
        this.date = date;
        this.duration = duration;
    }

    public int compareTo(final Meeting m) {
        return date.compareTo(m.date);
    }

    public String title() {
        return title;
    }

    public String place() {
        return place;
    }

    public String note() {
        return note;
    }

    public Duration duration() {
        return duration;
    }

    public LocalDateTime date() {
        return date;
    }

    public void changeReminder(final Reminder reminder) {
        this.reminder = reminder;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.title != null ? this.title.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Meeting other = (Meeting) obj;
        return !((this.title == null) ? (other.title != null) : !this.title.equals(other.title));
    }

    @Override
    public String toString() {
        String remind = "";

        if (reminder != null) {
            remind = "Reminder:    " + reminder.toString() + "\n";
        }
        return String.format(""
                + "Title:       %1$s%n"
                + "Description: %2$s%n"
                + "Date:        %3$te. %3$tB %3$tY%n"
                + "Start:       %3$tR%n"
                + "End:         %4$tR%n"
                + "Place:       %5$s%n"
                + "%6$s", title, note, date, date.plus(duration), place, remind);
    }
}
