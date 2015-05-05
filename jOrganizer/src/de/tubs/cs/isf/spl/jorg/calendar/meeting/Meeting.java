package de.tubs.cs.isf.spl.jorg.calendar.meeting;

import de.tubs.cs.isf.spl.jorg.calendar.reminder.Reminder;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author rose
 */
public class Meeting {

    private String title, desc, place;
    private LocalDateTime date, until;
    private Reminder reminder;

    public Meeting(String title, String desc, String place, LocalDateTime date, Duration duration, Reminder reminder) {
        this.title = title;
        this.desc = desc;
        this.place = place;
        this.date = date;
        until = date.plus(duration);
        this.reminder = reminder;
    }

    public String getTitle() {
        return title;
    }

    public void changeTitle(final String title) {
        this.title = title;
    }

    public void changeDescription(final String newDesc) {
        this.desc = newDesc;
    }

    public void changePlace(final String place) {
        this.place = place;
    }

    public void changeDate(final LocalDateTime date) {
        this.date = date;
    }

    public void changeDuration(final Duration duration) {
        until = date.plus(duration);
    }

    public void changeReminder(final Reminder reminder) {
        this.reminder = reminder;
    }

    @Override
    public String toString() {
        String remind = "";

        if (reminder != null) {
            remind = "Erinnerung: \t" + reminder.toString();
        }
        return String.format("Titel: \t\t%s%n"
            + "Beschreibung: \t%s%n"
            + "Datum: \t\t%3$te. %3$tB %3$tY"
            + "Von: \t\t%3$tR Uhr%n"
            + "Bis: \t\t%4$"
            + "Ort: \t\t%5$s%n"
            + "%6$s%n", title, desc, date, until, place, remind);
    }
}
